package cn.ylx.sso.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.ylx.common.utils.RtsResult;
import cn.ylx.pojo.TbUser;
import cn.ylx.sso.service.RegisterService;

/**
 * 注册功能
 * @author Yang
 *
 */
@Controller
public class RegitsterController {
	
	@Autowired
	private RegisterService registerService;
	
	@RequestMapping("/page/register")
	public String showRegister(){
		return "register";
	}
	
	@RequestMapping("/user/check/{param}/{type}")
	@ResponseBody
	public RtsResult checkData(@PathVariable String param,@PathVariable Integer type){
		return registerService.checkData(param, type);
	}
	
	@RequestMapping("/user/register")
	@ResponseBody
	public RtsResult register(TbUser user){
		return registerService.register(user);
	}
	
}
