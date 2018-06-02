package cn.ylx.order.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import cn.ylx.cart.service.CartService;
import cn.ylx.common.utils.CookieUtils;
import cn.ylx.common.utils.JsonUtils;
import cn.ylx.common.utils.RtsResult;
import cn.ylx.pojo.TbItem;
import cn.ylx.pojo.TbUser;
import cn.ylx.sso.service.TokenService;

/**
 * 用户登录拦截器
 * @author Yang
 *
 */
public class LoginInterceptor implements HandlerInterceptor {
	
	@Value("${SSO_URL}")
	private String SSO_URL;
	
	@Autowired
	private TokenService tokenService;
	@Autowired
	private CartService cartService;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object obj) throws Exception {
		//从cookie中取token
		String token = CookieUtils.getCookieValue(request, "token");
		//判断token是否存在
		if(StringUtils.isBlank(token)){
			//如果token不存在，未登录状态，跳转到sso系统登录页面。用户登录成功后调转回当前请求url
			response.sendRedirect(SSO_URL+"/page/login?redirect="+request.getRequestURL());
			return false;
		}
		//如果token存在，调用sso系统服务，根据token取用户信息
		RtsResult result = tokenService.getUserByToken(token);
		//如果取不到，用户登录已经过期，需要登录
		if(result.getStatus() != 200){
			response.sendRedirect(SSO_URL+"/page/login?redirect="+request.getRequestURL());
			return false;
		}
		//如果取到用户token，是登录状态，需要把用户信息写入request
		TbUser user = (TbUser)result.getData();
		request.setAttribute("user", user);
		//判断cookie中是否有购物车数据，如果有，合并到服务端
		String jsonCartList = CookieUtils.getCookieValue(request, "cart", true);
		if(StringUtils.isNotBlank(jsonCartList)){
			//合并购物车
			cartService.mergeCart(user.getId(), JsonUtils.jsonToList(jsonCartList, TbItem.class));
		}
		//放行
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {
		
		
	}

	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		

	}

}
