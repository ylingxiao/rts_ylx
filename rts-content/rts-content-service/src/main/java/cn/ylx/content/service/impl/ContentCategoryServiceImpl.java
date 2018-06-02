package cn.ylx.content.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.ylx.common.pojo.EasyUITreeNode;
import cn.ylx.common.utils.RtsResult;
import cn.ylx.content.service.ContentCategoryService;
import cn.ylx.mapper.TbContentCategoryMapper;
import cn.ylx.pojo.TbContentCategory;
import cn.ylx.pojo.TbContentCategoryExample;
import cn.ylx.pojo.TbContentCategoryExample.Criteria;

/**
 * 内容分类管理
 * @author Yang
 *
 */
@Service
public class ContentCategoryServiceImpl implements ContentCategoryService {
	
	@Autowired
	private TbContentCategoryMapper contentCategoryMapper;
	
	/**
	 * 根据父节点查询子节点
	 */
	@Override
	public List<EasyUITreeNode> getContentCatList(long parentId) {
		//根据parentid查询子节点列表
		TbContentCategoryExample example = new TbContentCategoryExample();
		Criteria criteria = example.createCriteria();
		//设置查询条件
		criteria.andParentIdEqualTo(parentId);
		//执行查询
		List<TbContentCategory> catList = contentCategoryMapper.selectByExample(example);
		//转换成EasyUITreeNode的列表
		List<EasyUITreeNode> nodeList = new ArrayList<>();
		EasyUITreeNode node;
		for(TbContentCategory item : catList){
			node = new EasyUITreeNode();
			node.setId(item.getId());
			node.setText(item.getName());
			node.setState(item.getIsParent()?"closed":"open");
			nodeList.add(node);
		}
		return nodeList;
	}
	
	/**
	 * 根据父节点及名字创建子节点
	 */
	@Override
	public RtsResult addContentCategory(long parentId, String name) {
		//创建一个pojo对象
		TbContentCategory contentCategory= new TbContentCategory();
		//设置属性
		contentCategory.setParentId(parentId);
		contentCategory.setName(name);
		//1 正常 2 删除
		contentCategory.setStatus(1);
		//默认排序为1
		contentCategory.setSortOrder(1);
		//新添加的节点就是叶子节点
		contentCategory.setIsParent(false);
		contentCategory.setCreated(new Date());
		contentCategory.setUpdated(new Date());
		//插入数据
		contentCategoryMapper.insert(contentCategory);
		//判断父节点isparent属性,如不是true改成true
		TbContentCategory parent = contentCategoryMapper.selectByPrimaryKey(parentId);
		if(!parent.getIsParent()){
			parent.setIsParent(true);
			contentCategoryMapper.updateByPrimaryKey(parent);
		}
		//返回RtsResult,包含pojo
		return RtsResult.ok(contentCategory);
	}
	
	/**
	 * 根据id及name更新子节点
	 */
	@Override
	public RtsResult updateContentCategory(long id, String name) {
		//创建一个pojo对象
		TbContentCategory contentCategory= new TbContentCategory();
		//设置属性
		contentCategory.setId(id);
		contentCategory.setName(name);
		//更新数据
		contentCategoryMapper.updateByPrimaryKeySelective(contentCategory);
		return RtsResult.ok();
	}
	
	/**
	 * 根据子节点名称删除该节点并更新父节点
	 */
	@Override
	public RtsResult deleteContentCategory(long id) {
		//获取该节点的父节点
		long parentId = contentCategoryMapper.selectByPrimaryKey(id).getParentId();
		//删除子节点数据
		contentCategoryMapper.deleteByPrimaryKey(id);
		//根据parentid查询子节点列表
		TbContentCategoryExample example = new TbContentCategoryExample();
		Criteria criteria = example.createCriteria();
		//设置查询条件
		criteria.andParentIdEqualTo(parentId);
		//执行查询
		List<TbContentCategory> catList = contentCategoryMapper.selectByExample(example);
		if(catList.size() == 0){
			TbContentCategory contentCategory= new TbContentCategory();
			//设置属性
			contentCategory.setId(parentId);
			contentCategory.setIsParent(false);
			contentCategoryMapper.updateByPrimaryKeySelective(contentCategory);
		}
		
		return RtsResult.ok();
	}

}
