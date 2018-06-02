package cn.ylx.cart.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import cn.ylx.common.utils.CookieUtils;
import cn.ylx.common.utils.RtsResult;
import cn.ylx.pojo.TbUser;
import cn.ylx.sso.service.TokenService;

/**
 * 用户登录处理
 * @author Yang
 *
 */
public class LoginInterceptor implements HandlerInterceptor {
	
	@Autowired
	private TokenService tokenService;
	
	//前处理: 执行handle之前执行此方法
	//true放行 false拦截
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object obj) throws Exception {
		//从cookie取token
		String token = CookieUtils.getCookieValue(request, "token");
		//取不到未登录，直接放行
		if(StringUtils.isBlank(token)){
			return true;
		}
		//取到token，调用sso系统服务，根据token获取用户信息
		RtsResult result = tokenService.getUserByToken(token);
		//没有取到用户。登录过期，直接放行
		if(result.getStatus() != 200){
			return true;
		}
		//取到用户信息。登录状态
		TbUser user = (TbUser)result.getData();
		//把用户信息放入request，只需在Controller中判断request是否包含用户信息
		request.setAttribute("user", user);
		return true;
	}
	
	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {
		//handle执行之后，返回ModeAndView之前
	}
	
	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		//完成处理，返回ModelAndView之后
		//可以在此处理异常
	}
	

}
