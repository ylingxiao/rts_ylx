package cn.ylx.sso.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import cn.ylx.common.jedis.JedisClient;
import cn.ylx.common.utils.JsonUtils;
import cn.ylx.common.utils.RtsResult;
import cn.ylx.mapper.TbUserMapper;
import cn.ylx.pojo.TbUser;
import cn.ylx.pojo.TbUserExample;
import cn.ylx.pojo.TbUserExample.Criteria;
import cn.ylx.sso.service.LoginService;

/**
 * 登录服务
 * @author Yang
 *
 */
@Service
public class LoginServiceImpl implements LoginService {
	
	@Autowired
	private TbUserMapper userMapper;
	@Autowired
	private JedisClient jedisClient;
	@Value("${SESSION_EXPIRE}")
	private Integer SESSION_EXPIRE;
	
	@Override
	public RtsResult userLogin(String username, String password) {
		//1.判断用户名和密码是否正确
		//根据用户名查询用户信息
		TbUserExample example = new TbUserExample();
		Criteria criteria = example.createCriteria();
		criteria.andUsernameEqualTo(username);
		//执行查询
		List<TbUser> list = userMapper.selectByExample(example);
		if(list == null || list.size() == 0){
			//返回登录失败
			return RtsResult.build(400, "用户名或密码错误！");
		}
		//获取用户信息
		TbUser user = list.get(0);
		//判断密码是否正确
		if(!DigestUtils.md5DigestAsHex(password.getBytes()).equals(user.getPassword())){
			//2.如果不正确，返回登录失败
			return RtsResult.build(400, "用户名或密码错误！");
		}
		//3.如果正确生成token
		String token = UUID.randomUUID().toString();
		//4.把用户信息写入redis,key：token value:用户信息
		user.setPassword(null);
		jedisClient.set("SESSION:"+token, JsonUtils.objectToJson(user));
		//5.设置session过期时间
		jedisClient.expire("SESSION:"+token, SESSION_EXPIRE);
		//6.把token返回
		return RtsResult.ok(token);
	}
	
	
	
	
}
