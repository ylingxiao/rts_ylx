package cn.ylx.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.ylx.common.pojo.EasyUIDateGridResult;
import cn.ylx.common.utils.RtsResult;
import cn.ylx.pojo.TbItem;
import cn.ylx.service.ItemService;

/**
 * 商品管理Controller
 * @author Yang
 *
 */
@Controller
public class ItemController {
	
	@Autowired
	private ItemService itemService;
	
	@RequestMapping("/item/{itemId}")
	@ResponseBody
	public TbItem getItemById(@PathVariable long itemId){
		System.out.println(itemService.getItemById(itemId));
		return itemService.getItemById(itemId);
	}
	
	@RequestMapping("/item/list")
	@ResponseBody
	public EasyUIDateGridResult getItemList(Integer page,Integer rows){
		//调用服务查询列表接口
		EasyUIDateGridResult result = itemService.getItemList(page,rows);
		return result;
	}
	
	@RequestMapping(value="/item/save",method=RequestMethod.POST)
	@ResponseBody
	public RtsResult getItemList(TbItem item, String desc){
		//调用服务查询列表接口
		RtsResult result = itemService.addItem(item, desc);
		return result;
	}
	
	@RequestMapping(value="/rest/item/delete",method=RequestMethod.POST)
	@ResponseBody
	public RtsResult deleteItemList(String ids){
		//调用服务查询列表接口
		RtsResult result = itemService.deleteItem(ids);
		return result;
	}
	
	@RequestMapping(value="/rest/item/instock",method=RequestMethod.POST)
	@ResponseBody
	public RtsResult instockItemList(String ids){
		//调用服务查询列表接口
		RtsResult result = itemService.changeItem(ids,"2");
		return result;
	}
	
	@RequestMapping(value="/rest/item/reshelf",method=RequestMethod.POST)
	@ResponseBody
	public RtsResult reshelfItemList(String ids){
		//调用服务查询列表接口
		RtsResult result = itemService.changeItem(ids,"1");
		return result;
	}
	
	@RequestMapping(value="/item/update",method=RequestMethod.POST)
	@ResponseBody
	public RtsResult editItem(TbItem item, String desc){
		//调用服务查询列表接口
		RtsResult result = itemService.editItem(item, desc);
		return result;
	}
	
}
