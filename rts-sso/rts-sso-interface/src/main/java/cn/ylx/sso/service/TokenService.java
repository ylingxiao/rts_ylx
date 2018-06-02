package cn.ylx.sso.service;

import cn.ylx.common.utils.RtsResult;

public interface TokenService {
	
	RtsResult getUserByToken(String token);
	
}
