package cn.ylx.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.ylx.common.utils.RtsResult;
import cn.ylx.search.service.SearchItemService;

/**
 * 导入商品到索引库
 * @author Yang
 *
 */
@Controller
public class SearchItemController {
	
	@Autowired
	private SearchItemService searchItemService;
	
	@RequestMapping("/item/import")
	@ResponseBody
	public RtsResult importItemList(){
		RtsResult result = searchItemService.improtAllItems();
		return result;
	}
	
}
