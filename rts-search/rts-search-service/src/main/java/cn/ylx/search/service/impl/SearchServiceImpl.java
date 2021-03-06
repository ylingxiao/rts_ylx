package cn.ylx.search.service.impl;

import org.apache.solr.client.solrj.SolrQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.ylx.common.pojo.SearchResult;
import cn.ylx.search.dao.SearchDao;
import cn.ylx.search.service.SearchService;

/**
 * 商品搜索service
 * @author Yang
 *
 */
@Service
public class SearchServiceImpl implements SearchService {
	
	@Autowired
	private SearchDao searchDao;
	
	@Override
	public SearchResult search(String keyword, int page, int rows) throws Exception {
		//创建一个一个SolrQuery对象
		SolrQuery solrQuery = new SolrQuery();
		//设置查询条件
		solrQuery.setQuery(keyword);
		//设置分页条件
		solrQuery.setStart(page <= 0 ? 0 : (page-1) * rows);
		solrQuery.setRows(rows);
		//设置默认搜索域
		solrQuery.set("df", "item_title");
		//开启高亮显示
		solrQuery.setHighlight(true);
		solrQuery.addHighlightField("item_title");
		solrQuery.setHighlightSimplePre("<em style=\"color:red\">");
		solrQuery.setHighlightSimplePost("</em>");
		//调用dao执行查询
		SearchResult search = searchDao.search(solrQuery);
		//计算总页数
		long recordCount = search.getRecordCount();
		int totalPage = (int)recordCount/rows;
		if(recordCount % rows > 0){
			totalPage++;
		}
		search.setTotalPages(totalPage);
		//返回结果
		return search;
	}	
	
}
