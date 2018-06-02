package cn.ylx.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.ylx.common.jedis.JedisClient;
import cn.ylx.common.pojo.EasyUIDateGridResult;
import cn.ylx.common.utils.IDUtils;
import cn.ylx.common.utils.JsonUtils;
import cn.ylx.common.utils.RtsResult;
import cn.ylx.mapper.TbItemDescMapper;
import cn.ylx.mapper.TbItemMapper;
import cn.ylx.pojo.TbItem;
import cn.ylx.pojo.TbItemDesc;
import cn.ylx.pojo.TbItemExample;
import cn.ylx.pojo.TbItemExample.Criteria;
import cn.ylx.service.ItemService;

/**
 * 商品管理service
 * @author Yang
 *
 */
@Service
public class ItemServiceImpl implements ItemService {
	@Autowired
	private TbItemMapper itemMapper;
	@Autowired
	private TbItemDescMapper itemDescMapper;
	@Autowired
	private JmsTemplate jmsTemplate;
	@Resource
	private Destination topicDestination;
	@Autowired
	private JedisClient jedisClient;
	
	@Value("${REDIS_ITEM_PRE}")
	private String REDIS_ITEM_PRE;
	@Value("${ITEM_CACHE_EXPIRE}")
	private Integer ITEM_CACHE_EXPIRE;
	
	@Override
	public TbItem getItemById(long itemId) {
		//查询缓存
		try {
			String json = jedisClient.get(REDIS_ITEM_PRE + ":" + itemId + ":BASE");
			if(StringUtils.isNotBlank(json)) {
				TbItem tbItem = JsonUtils.jsonToPojo(json, TbItem.class);
				return tbItem;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		//缓存中没有，查询数据库
		//根据主键查询
		//return itemMapper.selectByPrimaryKey(itemId);
		TbItemExample example = new TbItemExample();
		Criteria criteria = example.createCriteria();
		//设置查询条件
		criteria.andIdEqualTo(itemId);
		//执行查询
		List<TbItem> list = itemMapper.selectByExample(example);
		if(list != null && list.size()>0){	
			//把结果添加到缓存
			try {
				jedisClient.set(REDIS_ITEM_PRE + ":" + itemId + ":BASE", JsonUtils.objectToJson(list.get(0)));
				//设置过期时间
				jedisClient.expire(REDIS_ITEM_PRE + ":" + itemId + ":BASE", ITEM_CACHE_EXPIRE);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return list.get(0);
		}
		return null;
	}

	@Override
	public EasyUIDateGridResult getItemList(int page, int rows) {
		//设置分页信息
		PageHelper.startPage(page, rows);
		//执行查询
		TbItemExample example = new TbItemExample();
		List<TbItem> list = itemMapper.selectByExample(example);
		//创建一个返回值对象
		EasyUIDateGridResult result = new EasyUIDateGridResult();
		result.setRows(list);
		//取分业结果
		PageInfo<TbItem> pageInfo = new PageInfo<>(list);
		//取总记录数
		long total = pageInfo.getTotal();
		result.setTotal(total);
		//查询结果
		return result;
	}

	@Override
	public RtsResult addItem(TbItem item, String desc) {
		//生成商品Id
		final long itemId = IDUtils.genItemId();
		//补全item属性
		item.setId(itemId);
		//1-正常 2-下架 3-删除
		item.setStatus((byte)1);
		item.setCreated(new Date());
		item.setUpdated(new Date());
		//向商品表插入数据
		itemMapper.insert(item);
		//创建商品描述表对应Pojo
		TbItemDesc tbItemDesc = new TbItemDesc();
		//补全属性
		tbItemDesc.setItemId(itemId);
		tbItemDesc.setItemDesc(desc);
		tbItemDesc.setCreated(new Date());
		tbItemDesc.setUpdated(new Date());
		//向商品描述表插入数据
		itemDescMapper.insert(tbItemDesc);
		//发送商品添加消息
		jmsTemplate.send(topicDestination,new MessageCreator() {
			
			@Override
			public Message createMessage(Session session) throws JMSException {
				TextMessage message = session.createTextMessage(itemId+"");
				return message;
			}
		});
		return RtsResult.ok();
	}

	@Override
	public RtsResult deleteItem(String ids) {
		String[] id = ids.split(",");
		for(String item:id){
			itemMapper.deleteByPrimaryKey(Long.valueOf(item));
			itemDescMapper.deleteByPrimaryKey(Long.valueOf(item));
		}
		return RtsResult.ok();
	}

	@Override
	public RtsResult changeItem(String ids, String string) {
		String[] id = ids.split(",");
		TbItem tbItem = new TbItem();
		tbItem.setStatus(Byte.valueOf(string));
		for(String item:id){
			tbItem.setId(Long.valueOf(item));
			itemMapper.updateByPrimaryKeySelective(tbItem);
			itemMapper.deleteByPrimaryKey(Long.valueOf(item));
		}
		return RtsResult.ok();
	}

	@Override
	public RtsResult editItem(TbItem item, String desc) {
		item.setUpdated(new Date());
		item.setStatus(Byte.valueOf("1"));
		itemMapper.updateByPrimaryKeySelective(item);
		//创建商品描述表对应Pojo
		TbItemDesc tbItemDesc = new TbItemDesc();
		//补全属性
		tbItemDesc.setItemId(item.getId());
		tbItemDesc.setItemDesc(desc);
		tbItemDesc.setUpdated(new Date());
		//向商品描述表插入数据
		itemDescMapper.updateByPrimaryKeySelective(tbItemDesc);
		return RtsResult.ok();
	}

	@Override
	public TbItemDesc getTbItemDescById(long itemId) {
		//查询缓存
		try {
			String json = jedisClient.get(REDIS_ITEM_PRE + ":" + itemId + ":DESC");
			if(StringUtils.isNotBlank(json)) {
				TbItemDesc tbItemDesc = JsonUtils.jsonToPojo(json, TbItemDesc.class);
				return tbItemDesc;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		TbItemDesc itemDesc = itemDescMapper.selectByPrimaryKey(itemId);
		//把结果添加到缓存
		try {
			jedisClient.set(REDIS_ITEM_PRE + ":" + itemId + ":DESC", JsonUtils.objectToJson(itemDesc));
			//设置过期时间
			jedisClient.expire(REDIS_ITEM_PRE + ":" + itemId + ":DESC", ITEM_CACHE_EXPIRE);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return itemDesc;
	}

}
