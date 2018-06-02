package cn.ylx.cart.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import cn.ylx.cart.service.CartService;
import cn.ylx.common.jedis.JedisClient;
import cn.ylx.common.utils.JsonUtils;
import cn.ylx.common.utils.RtsResult;
import cn.ylx.mapper.TbItemMapper;
import cn.ylx.pojo.TbItem;

@Service
public class CartServiceImpl implements CartService {
	
	@Autowired 
	private JedisClient jedisClient;
	@Value("${REDIS_CART_PRE}")
	private String REDIS_CART_PRE;
	@Autowired
	private TbItemMapper itemMapper;
	
	@Override
	public RtsResult addCart(long userId, long itemId, int num) {
		//向redis中添加购物车
		//数据类型是hash key:用户id field:商品id value:商品信息
		//判断商品是否存在
		Boolean hexists = jedisClient.hexists(REDIS_CART_PRE+":"+userId, itemId+"");
		if(hexists){
			//如果存在数量相加
			String json = jedisClient.hget(REDIS_CART_PRE+":"+userId, itemId+"");
			TbItem item = JsonUtils.jsonToPojo(json, TbItem.class);
			item.setNum(item.getNum()+num);
			//写回redis
			jedisClient.hset(REDIS_CART_PRE+":"+userId, itemId+"",JsonUtils.objectToJson(item));
			return RtsResult.ok();
		}
		//如果不存在取商品id信息
		TbItem tbItem = itemMapper.selectByPrimaryKey(itemId);
		//设置购物车数据量
		tbItem.setNum(num);
		//取一张图片
		String image = tbItem.getImage();
		if(StringUtils.isNotBlank(image)){
			tbItem.setImage(image.split(",")[0]);
		}
		//添加到购物车列表
		jedisClient.hset(REDIS_CART_PRE+":"+userId, itemId+"",JsonUtils.objectToJson(tbItem));
		//返回成功
		return RtsResult.ok();
	}
	
	/**
	 * 合并购物车
	 */
	@Override
	public RtsResult mergeCart(long userId, List<TbItem> tbItemList) {
		//遍历商品列表
		//把列表添加到购物车
		//判断购车中是否有此商品
		//如果有，数量相加
		//如果没有，，添加新的商品
		//返回成功
		for(TbItem item : tbItemList){
			addCart(userId, item.getId(), item.getNum());
		}
		return RtsResult.ok();
	}

	@Override
	public List<TbItem> getCartList(long userId) {
		// 根据用户di查询购物车列表
		List<String> list = jedisClient.hvals(REDIS_CART_PRE+":"+userId);
		List<TbItem> itemList = new ArrayList<>();
		for(String string : list){
			itemList.add(JsonUtils.jsonToPojo(string, TbItem.class));
		}
		return itemList;
	}

	@Override
	public RtsResult updateCartNum(long userId, long itemId, int num) {
		//从redis中获取商品信息，更新数量，写入redis
		String json = jedisClient.hget(REDIS_CART_PRE+":"+userId, itemId+"");
		TbItem item = JsonUtils.jsonToPojo(json, TbItem.class);
		item.setNum(num);
		//添加到购物车列表
		jedisClient.hset(REDIS_CART_PRE+":"+userId, itemId+"",JsonUtils.objectToJson(item));
		//返回成功
		return RtsResult.ok();
	}

	@Override
	public RtsResult deleteCartItem(long userId, long itemId) {
		//删除购物车商品
		jedisClient.hdel(REDIS_CART_PRE+":"+userId, itemId+"");
		return RtsResult.ok();
	}

	@Override
	public RtsResult clearCartItem(long userId) {
		// 删除购物车信息
		jedisClient.del(REDIS_CART_PRE+":"+userId);
		return RtsResult.ok();
	}

}
