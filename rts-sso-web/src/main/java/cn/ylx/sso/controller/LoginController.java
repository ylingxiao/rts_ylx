package cn.ylx.sso.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.ylx.common.utils.CookieUtils;
import cn.ylx.common.utils.RtsResult;
import cn.ylx.sso.service.LoginService;

@Controller
public class LoginController {
	
	@Autowired
	private LoginService loginService;
	@Value("${TOKEN_KEY}")
	private String TOKEN_KEY;
	
	@RequestMapping("/page/login")
	public String showLogin(String redirect, Model model){
		model.addAttribute("redirect", redirect);
		return "login";
	}
	
	@RequestMapping(value="/user/login",method=RequestMethod.POST)
	@ResponseBody
	public RtsResult login(String username, String password, 
			HttpServletRequest request, HttpServletResponse response){
		RtsResult result = loginService.userLogin(username, password);
		//判断是否登录成功
		if(result.getStatus() == 200 ){
			String token =result.getData().toString();
			//如果成功就token写入cookie
			CookieUtils.setCookie(request, response, TOKEN_KEY, token);
		}
		return result;
	}
}
