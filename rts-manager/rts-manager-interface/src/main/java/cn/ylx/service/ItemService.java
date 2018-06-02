package cn.ylx.service;

import cn.ylx.common.pojo.EasyUIDateGridResult;
import cn.ylx.common.utils.RtsResult;
import cn.ylx.pojo.TbItem;
import cn.ylx.pojo.TbItemDesc;

public interface ItemService {
	
	TbItem getItemById(long itemId);
	TbItemDesc getTbItemDescById(long itemId);
	EasyUIDateGridResult getItemList(int page,int rows);
	RtsResult addItem(TbItem item, String desc);
	RtsResult deleteItem(String ids);
	RtsResult changeItem(String ids, String string);
	RtsResult editItem(TbItem item, String desc);
}
