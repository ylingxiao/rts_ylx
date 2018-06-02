package cn.ylx.search.mapper;

import java.util.List;

import cn.ylx.common.pojo.SearchItem;

public interface ItemMapper {
	
	List<SearchItem> getItemList();
	
	SearchItem getItemById(Long itemId);
	
}
