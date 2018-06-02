package cn.ylx.content.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.ylx.common.jedis.JedisClient;
import cn.ylx.common.jedis.JedisClientPool;
import cn.ylx.common.pojo.EasyUIDateGridResult;
import cn.ylx.common.utils.JsonUtils;
import cn.ylx.common.utils.RtsResult;
import cn.ylx.content.service.ContentService;
import cn.ylx.mapper.TbContentMapper;
import cn.ylx.pojo.TbContent;
import cn.ylx.pojo.TbContentExample;
import cn.ylx.pojo.TbItem;
import cn.ylx.pojo.TbItemExample;
import cn.ylx.pojo.TbContentExample.Criteria;

/**
 * 内容管理service
 * @author Yang
 *
 */
@Service
public class ContentServiceImpl implements ContentService {
	
	@Autowired
	private TbContentMapper tbContentMapper;
	@Autowired
	private JedisClient jedisClient;
	@Value("CONTENT_LIST")
	private String CONTENT_LIST;
	
	/**
	 * 根据节点id插入广告内容
	 */
	@Override
	public RtsResult addContent(TbContent content) {
		//补充属性
		content.setCreated(new Date());
		content.setUpdated(new Date());
		//插入数据库
		tbContentMapper.insert(content);
		//缓存同步
		jedisClient.hdel(CONTENT_LIST, content.getCategoryId().toString());
		return RtsResult.ok();
	}
	
	/**
	 * 根据节点Id查询内容列表
	 */
	@Override
	public List<TbContent> getContentListByCid(long cid) {
		//从缓存中读取
		try{
			String json = jedisClient.hget(CONTENT_LIST, cid+"");
			if(StringUtils.isNotBlank(json)){
				List<TbContent> list = JsonUtils.jsonToList(json, TbContent.class);
				return list;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		//查询数据
		TbContentExample example = new TbContentExample();
		Criteria criteria = example.createCriteria();
		//设置查询条件
		criteria.andCategoryIdEqualTo(cid);
		//执行查询
		List<TbContent> list = tbContentMapper.selectByExampleWithBLOBs(example);
		//将结果存入缓存
		try{
			jedisClient.hset(CONTENT_LIST, cid+"", JsonUtils.objectToJson(list));
		}catch(Exception e){
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public EasyUIDateGridResult getcontentList(String categoryId, Integer page, Integer rows) {
		//设置分页信息
		PageHelper.startPage(page, rows);
		//执行查询
		TbContentExample tbContent = new TbContentExample();
		Criteria criteria = tbContent.createCriteria();
		criteria.andCategoryIdEqualTo(Long.valueOf(categoryId));
		List<TbContent> list = tbContentMapper.selectByExample(tbContent);
		//创建一个返回值对象
		EasyUIDateGridResult result = new EasyUIDateGridResult();
		result.setRows(list);
		//取分业结果
		PageInfo<TbContent> pageInfo = new PageInfo<>(list);
		//取总记录数
		long total = pageInfo.getTotal();
		result.setTotal(total);
		//查询结果
		return result;
	}
	
	//批量删除内容列表
	@Override
	public RtsResult deletecontentList(String ids) {
		String[] id = ids.split(",");
		for(String item:id){
			tbContentMapper.deleteByPrimaryKey(Long.valueOf(item));
		}
		return RtsResult.ok();
	}
	
	//修改内容
	@Override
	public RtsResult updateContent(TbContent content) {
		tbContentMapper.updateByPrimaryKey(content);
		return RtsResult.ok();
	}

}
