package cn.ylx.order.service;

import cn.ylx.common.utils.RtsResult;
import cn.ylx.order.pojo.OrderInfo;

public interface OrderService {
	
	RtsResult createOrder(OrderInfo orderInfo);
	
}
