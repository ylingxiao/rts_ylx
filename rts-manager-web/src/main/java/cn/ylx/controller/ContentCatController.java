package cn.ylx.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.ylx.common.pojo.EasyUITreeNode;
import cn.ylx.common.utils.RtsResult;
import cn.ylx.content.service.ContentCategoryService;

/**
 * 内容分类管理Controller
 * @author Yang
 */
@Controller
public class ContentCatController {
	
	@Autowired
	private ContentCategoryService contentCategoryService;
	
	/**
	 * 分类列表查询
	 * @param parentId
	 * @return
	 */
	@RequestMapping("/content/category/list")
	@ResponseBody
	public List<EasyUITreeNode> getContentCatList(@RequestParam(name="id",defaultValue="0")Long parentId){
		List<EasyUITreeNode> list = contentCategoryService.getContentCatList(parentId);
		return list;
	}
	
	/**
	 * 添加分类节点
	 */
	@RequestMapping(value="content/category/create",method=RequestMethod.POST)
	@ResponseBody
	public RtsResult createContentCategory(long parentId,String name){
		//调用服务添加节点
		RtsResult rtsResult = contentCategoryService.addContentCategory(parentId, name);
		return rtsResult;	
	}
	
	/**
	 * 重命名节点
	 */
	@RequestMapping(value="content/category/update",method=RequestMethod.POST)
	@ResponseBody
	public RtsResult updateContentCategory(long id,String name){
		//调用服务添加节点
		RtsResult rtsResult = contentCategoryService.updateContentCategory(id, name);
		return rtsResult;	
	}
	
	/**
	 * 删除节点
	 */
	@RequestMapping(value="content/category/delete",method=RequestMethod.POST)
	@ResponseBody
	public RtsResult deleteContentCategory(long id){
		//调用服务添加节点
		RtsResult rtsResult = contentCategoryService.deleteContentCategory(id);
		return rtsResult;	
	}
	
}
