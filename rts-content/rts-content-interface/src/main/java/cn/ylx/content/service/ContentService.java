package cn.ylx.content.service;

import java.util.List;

import cn.ylx.common.pojo.EasyUIDateGridResult;
import cn.ylx.common.utils.RtsResult;
import cn.ylx.pojo.TbContent;

public interface ContentService {
	
	RtsResult addContent(TbContent content);
	List<TbContent> getContentListByCid(long cid);
	EasyUIDateGridResult getcontentList(String categoryId, Integer page, Integer rows);
	RtsResult deletecontentList(String ids);
	RtsResult updateContent(TbContent content);
}
