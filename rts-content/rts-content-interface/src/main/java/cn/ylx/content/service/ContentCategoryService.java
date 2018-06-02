package cn.ylx.content.service;

import java.util.List;

import cn.ylx.common.pojo.EasyUITreeNode;
import cn.ylx.common.utils.RtsResult;

public interface ContentCategoryService {
	
	List<EasyUITreeNode> getContentCatList(long parent);
	
	RtsResult addContentCategory(long parentId,String name);

	RtsResult updateContentCategory(long id, String name);

	RtsResult deleteContentCategory(long id);
	
}
