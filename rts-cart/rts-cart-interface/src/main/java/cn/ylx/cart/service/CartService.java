package cn.ylx.cart.service;

import java.util.List;

import cn.ylx.common.utils.RtsResult;
import cn.ylx.pojo.TbItem;

public interface CartService {
	
	RtsResult addCart(long userId, long itemId, int num);
	RtsResult mergeCart(long userId, List<TbItem> tbItemList);
	List<TbItem> getCartList(long userId);
	RtsResult updateCartNum(long userId, long itemId, int num);
	RtsResult deleteCartItem(long userId, long itemId);
	RtsResult clearCartItem(long userId);
	
}
