package cn.ylx.sso.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import cn.ylx.common.jedis.JedisClient;
import cn.ylx.common.utils.JsonUtils;
import cn.ylx.common.utils.RtsResult;
import cn.ylx.pojo.TbUser;
import cn.ylx.sso.service.TokenService;

@Service
public class TokenServiceImpl implements TokenService {
	
	@Autowired
	private JedisClient jedisClient;
	@Value("${SESSION_EXPIRE}")
	private Integer SESSION_EXPIRE;
	
	@Override
	public RtsResult getUserByToken(String token) {
		//根据token到redis中取用户信息
		String json = jedisClient.get("SESSION:"+token);
		//取不到，返回登录过期
		if(StringUtils.isBlank(json)){
			return RtsResult.build(201, "用户登录已经过期，请重新登录！");
		}
		//取到用户信息更新token过期时间
		jedisClient.expire("SESSION:"+token,SESSION_EXPIRE);
		//返回结果，RtsResult包含TbUser对象
		TbUser user = JsonUtils.jsonToPojo(json, TbUser.class);
		return RtsResult.ok(user);
	}

}
