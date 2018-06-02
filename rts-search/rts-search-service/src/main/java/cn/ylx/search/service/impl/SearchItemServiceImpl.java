package cn.ylx.search.service.impl;

import java.util.List;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.ylx.common.pojo.SearchItem;
import cn.ylx.common.utils.RtsResult;
import cn.ylx.search.mapper.ItemMapper;
import cn.ylx.search.service.SearchItemService;

/**
 * 索引库维护
 * @author Yang
 *
 */
@Service
public class SearchItemServiceImpl implements SearchItemService {

	@Autowired
	private ItemMapper itemMapper;
	
	@Autowired
	private SolrServer solrServer;
	
	@Override
	public RtsResult improtAllItems() {
		//查询商品列表
		List<SearchItem> list = itemMapper.getItemList();
		//遍历商品列表
		SolrInputDocument document;
		try{
			for(SearchItem searchItem : list){
				//创建文本对象
				document = new SolrInputDocument();
				//把文本对象添加到业务域
				document.addField("id",searchItem.getId());
				document.addField("item_title",searchItem.getTitle());
				document.addField("item_sell_point",searchItem.getSell_point());
				document.addField("item_price",searchItem.getPrice());
				document.addField("item_image",searchItem.getImage());
				document.addField("item_category_name",searchItem.getCategory_name());
				//把文档对象写入索引库
				solrServer.add(document);
			}
			//提交
			solrServer.commit();
			//返回导入成功
			return RtsResult.ok();
		}catch(Exception e){
			e.printStackTrace();
			return RtsResult.build(500, "数据导入时发生异常！");
		}
	}

}
