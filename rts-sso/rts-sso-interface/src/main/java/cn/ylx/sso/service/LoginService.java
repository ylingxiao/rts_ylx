package cn.ylx.sso.service;

import cn.ylx.common.utils.RtsResult;

public interface LoginService {
	
	RtsResult userLogin(String username,String password);
	
}
