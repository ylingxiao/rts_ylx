package cn.ylx.order.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import cn.ylx.common.jedis.JedisClient;
import cn.ylx.common.utils.RtsResult;
import cn.ylx.mapper.TbOrderItemMapper;
import cn.ylx.mapper.TbOrderMapper;
import cn.ylx.mapper.TbOrderShippingMapper;
import cn.ylx.order.pojo.OrderInfo;
import cn.ylx.order.service.OrderService;
import cn.ylx.pojo.TbOrderItem;
import cn.ylx.pojo.TbOrderShipping;

/**
 * 订单处理服务
 * @author Yang
 *
 */
@Service
public class OrderServiceImpl implements OrderService {
	
	@Autowired
	private TbOrderMapper orderMapper;
	@Autowired
	private TbOrderItemMapper orderItemMapper;
	@Autowired
	private TbOrderShippingMapper orderShippingMapper;
	@Autowired
	private JedisClient jedisClient;
	
	@Value("${ORDER_ID_GEN_KEY}")
	private String ORDER_ID_GEN_KEY;
	@Value("${ORDER_ID_START}")
	private String ORDER_ID_START;
	@Value("${ORDER_DETAIL_ID_GEN_KEY}")
	private String ORDER_DETAIL_ID_GEN_KEY;
	
	@Override
	public RtsResult createOrder(OrderInfo orderInfo) {
		//生成订单号.使用redis的incr生成
		if(!jedisClient.exists(ORDER_ID_GEN_KEY)){
			jedisClient.set(ORDER_ID_GEN_KEY, ORDER_ID_START);
		}
		String orderId = jedisClient.incr(ORDER_ID_GEN_KEY).toString();
		//补全orderInfo的属性
		orderInfo.setOrderId(orderId);
		//1.未付款 2.已付款 3.未发货 4.已发货 5.交易成功 6.交易关闭
		orderInfo.setStatus(1);
		orderInfo.setCreateTime(new Date());
		orderInfo.setUpdateTime(new Date());
		//插入订单表
		orderMapper.insert(orderInfo);
		//向订单明细表插入数据
		List<TbOrderItem> orderItems = orderInfo.getOrderItems();
		for(TbOrderItem item: orderItems){
			//生成明细id
			String odId = jedisClient.incr(ORDER_DETAIL_ID_GEN_KEY).toString();
			//补全pojo属性
			item.setId(odId);
			item.setOrderId(orderId);
			//向明细表插入数据
			orderItemMapper.insert(item);
		}
		//向订单物流表插入数据
		TbOrderShipping orderShipping = orderInfo.getOrderShipping();
		orderShipping.setOrderId(orderId);
		orderShipping.setCreated(new Date());
		orderShipping.setUpdated(new Date());
		orderShippingMapper.insert(orderShipping);
		//返回RtsResult,包含订单号
		return RtsResult.ok(orderId);
	}

}
