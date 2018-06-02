package cn.ylx.solrj;

import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

import cn.ylx.common.pojo.SearchResult;
import cn.ylx.search.dao.SearchDao;

public class TestSolrJ {
	
	/*@Test
	public void addDocument() throws Exception{
		//创建一个SolrServer对象，创建连接，参数solr服务器url
		SolrServer solrServer = new HttpSolrServer("http://127.0.0.1:8090/solr/collection1");
		//创建一个文档对象SolrInputDocument
		SolrInputDocument document = new SolrInputDocument();
		//向文档对象中添加域。文档中必须包含一个id域，所有的域必须在schema。xml中配置
		document.addField("id", "doc01");
		document.addField("item_title", "测试商品01");
		document.addField("item_price", 1000);
		//把文档对象写入数据库
		solrServer.add(document);
		//提交
		solrServer.commit();
	}
	
	@Test
	public void deleteDocument() throws Exception {
		//创建对象
		SolrServer solrServer = new HttpSolrServer("http://127.0.0.1:8090/solr/collection1");
		
		 * 删除文档对象
		 * 	1.根据id删除
		 *  2.根据query删：query写法：id:条件
		 
		solrServer.deleteById("doc01");
		//solrServer.deleteByQuery("id:doc001");
		solrServer.commit();
	}
	
	@Test
	public void queryIndex() throws Exception{
		//创建一个SolrServer对象
		SolrServer solrServer = new HttpSolrServer("http://127.0.0.1:8090/solr/collection1");
		//创建一个SolrQuery对象
		SolrQuery query  =new SolrQuery();
		//设置查询条件
		query.set("q", "*:*");
		//执行查询
		QueryResponse response = solrServer.query(query);
		//取文档列表,取查询结果总记录数
		SolrDocumentList results = response.getResults();
		System.out.println("查询结果总记录数为："+results.getNumFound());
		//遍历文档列表
		for(SolrDocument solrDocument : results){
			System.out.println(solrDocument.get("id"));
			System.out.println(solrDocument.get("item_title"));
			System.out.println(solrDocument.get("item_sell_point"));
			System.out.println(solrDocument.get("item_price"));
			System.out.println(solrDocument.get("item_image"));
			System.out.println(solrDocument.get("item_category_name"));
		}
	}
	
	@Test
	public void queryIndexFuza() throws Exception{
		//创建一个SolrServer对象
		SolrServer solrServer = new HttpSolrServer("http://127.0.0.1:8090/solr/collection1");
		//创建一个SolrQuery对象
		SolrQuery query  =new SolrQuery();
		//设置查询条件
		query.setQuery("手机");
		//设置开始
		query.setStart(0);
		//设置行数
		query.setRows(20);
		//设置默认查询域
		query.set("df","item_title");
		//设置高亮显示
		query.setHighlight(true);
		//设置高亮显示字段
		query.addHighlightField("item_title");
		//设置标签
		query.setHighlightSimplePre("<em>");
		query.setHighlightSimplePost("</em>");
		//执行查询
		QueryResponse response = solrServer.query(query);
		//取文档列表,取查询结果总记录数
		SolrDocumentList results = response.getResults();
		System.out.println("查询结果总记录数为："+results.getNumFound());
		//取高亮列表
		Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();
		//遍历文档列表
		String title;
		for(SolrDocument solrDocument : results){
			System.out.println(solrDocument.get("id"));
			List<String> list = highlighting.get(solrDocument.get("id")).get("item_title");
			if(list!=null && list.size()>0){
				title = list.get(0);
			}else{
				title = (String) solrDocument.get("item_title");
			}
			System.out.println(title);
			System.out.println(solrDocument.get("item_sell_point"));
			System.out.println(solrDocument.get("item_price"));
			System.out.println(solrDocument.get("item_image"));
			System.out.println(solrDocument.get("item_category_name"));
		}
	}
	
	@Test
	public void queryIndexFuza2() throws Exception{
		//创建一个一个SolrQuery对象
		SolrQuery solrQuery = new SolrQuery();
		//设置查询条件
		solrQuery.setQuery(new String("手机".getBytes(),"utf-8"));
		//设置分页条件
		solrQuery.setStart(0);
		solrQuery.setRows(20);
		//设置默认搜索域
		solrQuery.set("df", "item_title");
		//开启高亮显示
		solrQuery.setHighlight(true);
		solrQuery.addHighlightField("item_title");
		solrQuery.setHighlightSimplePre("<em style=\"color:red\">");
		solrQuery.setHighlightSimplePost("</em>");
		//调用dao执行查询
		SearchResult search = new SearchDao().search(solrQuery);
		//计算总页数
		long recordCount = search.getRecordCount();
		System.out.println(recordCount);
		System.out.println("aaa"+search.toString());
	}*/
	
}
