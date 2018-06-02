package cn.ylx.sso.service.impl;

import java.util.List;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import cn.ylx.common.utils.RtsResult;
import cn.ylx.mapper.TbUserMapper;
import cn.ylx.pojo.TbUser;
import cn.ylx.pojo.TbUserExample;
import cn.ylx.pojo.TbUserExample.Criteria;
import cn.ylx.sso.service.RegisterService;

@Service
public class RegisterServiceImpl implements RegisterService {
	
	@Autowired
	private TbUserMapper userMapper;
	
	@Override
	public RtsResult checkData(String param, int type) {
		// 根据不同type生成不同查询条件
		TbUserExample example = new TbUserExample();
		Criteria criteria = example.createCriteria();
		//1:用户名 2：手机号 3:邮箱
		switch(type){
		case 1:
			criteria.andUsernameEqualTo(param);
			break;
		case 2:
			criteria.andPhoneEqualTo(param);
			break;
		case 3:
			criteria.andEmailEqualTo(param);
			break;
		default:
			return RtsResult.build(400, "数据类型错误");
		}
		//执行查询
		List<TbUser> list = userMapper.selectByExample(example);
		//判断结果中是否包含数据
		if(list != null && list.size()>0){
			//如果有数据返回false
			return RtsResult.ok(false);
		}
		//没有数据返回true，否则false
		return RtsResult.ok(true);
	}

	@Override
	public RtsResult register(TbUser user) {
		//数据有效性校验
		if(StringUtils.isBlank(user.getUsername()) || StringUtils.isBlank(user.getPassword()) || StringUtils.isBlank(user.getPhone())){
			return RtsResult.build(400, "用户数据不完整，注册失败！");
		}
		//1 用户名 2 手机号 3 邮箱
		RtsResult result = checkData(user.getUsername(),1);
		if(!(boolean)result.getData()){
			return RtsResult.build(400, "该用户名已被注册！");
		}
		result = checkData(user.getPhone(),2);
		if(!(boolean)result.getData()){
			return RtsResult.build(400, "该手机号已被注册！");
		}
		//补全属性
		user.setCreated(new Date());
		user.setUpdated(new Date());
		//对密码Md5加密
		user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
		//把用户数据插入到数据库
		userMapper.insert(user);
		//返回添加成功
		return RtsResult.ok();
	}

}
