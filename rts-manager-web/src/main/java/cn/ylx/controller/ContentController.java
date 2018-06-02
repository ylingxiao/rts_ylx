package cn.ylx.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.ylx.common.pojo.EasyUIDateGridResult;
import cn.ylx.common.utils.RtsResult;
import cn.ylx.content.service.ContentService;
import cn.ylx.pojo.TbContent;

@Controller
public class ContentController {

	@Autowired
	private ContentService contenService;
	
	@RequestMapping(value="/content/save",method=RequestMethod.POST)
	@ResponseBody
	public RtsResult addContent(TbContent content){
		return contenService.addContent(content);
	}
	
	@RequestMapping(value="/content/query/list")
	@ResponseBody
	public EasyUIDateGridResult contentList(String categoryId, Integer page, Integer rows){
		return contenService.getcontentList(categoryId,page,rows);
	}
	
	@RequestMapping(value="/content/delete")
	@ResponseBody
	public RtsResult deletecontentList(String ids){
		return contenService.deletecontentList(ids);
	}
	
	@RequestMapping(value="/content/edit")
	@ResponseBody
	public RtsResult editContent(TbContent content){
		return contenService.updateContent(content);
	}
}
