package cn.ylx.sso.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.ylx.common.utils.JsonUtils;
import cn.ylx.common.utils.RtsResult;
import cn.ylx.sso.service.TokenService;

@Controller
public class TokenController {
	
	@Autowired
	private TokenService tokenService;
	
	@RequestMapping(value="/user/token/{token}",produces="application/json;charset=utf-8")
	@ResponseBody
	public String getUserByToken(@PathVariable String token, String callback){
		RtsResult result = tokenService.getUserByToken(token);
		//响应之前，判断是否为jsonp
		if(StringUtils.isNotBlank(callback)){
			return callback+"("+JsonUtils.objectToJson(result)+");";
		}
		return JsonUtils.objectToJson(result);
	}
	
}
