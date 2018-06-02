package cn.ylx.service;

import java.util.List;

import cn.ylx.common.pojo.EasyUITreeNode;

public interface ItemCatService {
	
	List<EasyUITreeNode> getItemCat(long parentId);
	
	
}
