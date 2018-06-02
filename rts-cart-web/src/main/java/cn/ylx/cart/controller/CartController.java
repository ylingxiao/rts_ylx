package cn.ylx.cart.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.ylx.cart.service.CartService;
import cn.ylx.common.utils.CookieUtils;
import cn.ylx.common.utils.JsonUtils;
import cn.ylx.common.utils.RtsResult;
import cn.ylx.pojo.TbItem;
import cn.ylx.pojo.TbUser;
import cn.ylx.service.ItemService;

@Controller
public class CartController {
	
	@Autowired
	private ItemService itemService;
	@Autowired
	private CartService cartService;
	@Value("${COOKIE_CART_EXPIRE}")
	private Integer COOKIE_CART_EXPIRE;
	
	/**
	 * 添加商品到购物车
	 * @param itemId
	 * @param num
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/cart/add/{itemId}")
	public String addCart(@PathVariable Long itemId, @RequestParam(defaultValue="1") Integer num,
		HttpServletRequest request, HttpServletResponse response){
		//判断用户是否登录
		TbUser user = (TbUser)request.getAttribute("user");
		//如果登录状态，把购物车写到redis
		if(user != null){
			//保存到服务端
			cartService.addCart(user.getId(), itemId, num);
			//返回逻辑视图
			return "cartSuccess";
		}
		//从cookie中取购物车列表
		List<TbItem> cartList = getCartListFromSession(request);		
		//判断商品是否存在购物车列表中
		boolean falg = false;
		for(TbItem tbItem :cartList){
			//如果存在数量相加
			if(tbItem.getId() == itemId.longValue()){
				tbItem.setNum(tbItem.getNum()+num);
				falg = true;
				break;
			}
		}
		//如果不存在根据id获取商品信息
		if(!falg){
			TbItem tbItem = itemService.getItemById(itemId);
			//设置数量
			tbItem.setNum(num);
			//设置图片
			String image = tbItem.getImage();
			if(StringUtils.isNotBlank(image)){
				tbItem.setImage(image.split(",")[0]);
			}
			//把商品添加到商品列表
			cartList.add(tbItem);
		}
		//写入cookie
		CookieUtils.setCookie(request, response, "cart", JsonUtils.objectToJson(cartList), COOKIE_CART_EXPIRE, true);
		//返回添加成功页面
		return "cartSuccess";
	}
	
	/**
	 * 展示购物车列表
	 * @param request
	 * @return
	 */
	@RequestMapping("/cart/cart")
	public String shwoCartList(HttpServletRequest request, HttpServletResponse response){
		//从cookie中取购物车列表
		List<TbItem> cartList = getCartListFromSession(request);
		//登录状态
		TbUser user = (TbUser)request.getAttribute("user");
		//如果登录状态
		if(user != null){
			//从cookie中取购物车列表
			//如果不为空，把购物车中商品和服务端购物车商品合并
			cartService.mergeCart(user.getId(), cartList);
			//把cookie中购物车删除
			CookieUtils.deleteCookie(request, response, "cart");
			//从服务端取购物车列表
			cartList = cartService.getCartList(user.getId());
		}
		//未登录状态
		//把列表传递给页面
		request.setAttribute("cartList", cartList);
		//返回逻辑视图
		return "cart";
	}
	
	/**
	 * 从cookie中获取购物车列表的处理
	 * @param request
	 * @return
	 */
	private List<TbItem> getCartListFromSession(HttpServletRequest request){
		String json = CookieUtils.getCookieValue(request, "cart",true);
		//判断json是否为空
		if(StringUtils.isBlank(json)){
			return new ArrayList<>();
		}
		//把json转换为商品列表
		return JsonUtils.jsonToList(json, TbItem.class);
	}
	
	/**
	 * 更新购物车商品数量
	 */
	@RequestMapping("/cart/update/num/{itemId}/{num}" )
	@ResponseBody
	public RtsResult updateCartNum(@PathVariable Long itemId, @PathVariable Integer num,
			HttpServletRequest request, HttpServletResponse response){
		//判断用户是否为登陆状态
		TbUser user = (TbUser)request.getAttribute("user");
		if(user != null){
			return cartService.updateCartNum(user.getId(), itemId, num);
		}
		
		//从cookie中取出购物车列表
		List<TbItem> cartList = getCartListFromSession(request);
		//遍历列表，找到对应商品
		for(TbItem tbItem :cartList){
			//如果存在数量相加
			if(tbItem.getId() == itemId.longValue()){
				//更新数量
				tbItem.setNum(num);
				break;
			}
		}
		//将购物车写会cookie
		CookieUtils.setCookie(request, response, "cart", JsonUtils.objectToJson(cartList), COOKIE_CART_EXPIRE, true);		
		//返回成功
		return RtsResult.ok();
	}
	
	/**
	 * 删除购物车商品
	 */
	@RequestMapping("/cart/delete/{itemId}" )
	public String updateCartNum(@PathVariable Long itemId,
			HttpServletRequest request, HttpServletResponse response){
		//判断用户是否为登陆状态
		TbUser user = (TbUser)request.getAttribute("user");
		if(user != null){
			cartService.deleteCartItem(user.getId(), itemId);
			//返回成功
			return "redirect:/cart/cart.html";
		}
		//从cookie中取出购物车列表
		List<TbItem> cartList = getCartListFromSession(request);
		//遍历列表，找到对应商品
		for(TbItem tbItem :cartList){
			//如果存在移除商品
			if(tbItem.getId() == itemId.longValue()){
				cartList.remove(tbItem);
				break;
			}
		}
		//将购物车写回cookie
		CookieUtils.setCookie(request, response, "cart", JsonUtils.objectToJson(cartList), COOKIE_CART_EXPIRE, true);		
		//返回成功
		return "redirect:/cart/cart.html";
	}
	
}
