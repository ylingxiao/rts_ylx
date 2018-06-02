package cn.ylx.sso.service;

import cn.ylx.common.utils.RtsResult;
import cn.ylx.pojo.TbUser;

public interface RegisterService {
	
	RtsResult checkData(String param, int type);
	RtsResult register(TbUser user);
}
