package cn.ylx.search.message;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;

import cn.ylx.common.pojo.SearchItem;
import cn.ylx.search.mapper.ItemMapper;

/**
 * 监听商品添加消息，接收消息后将消息添加到索引库
 * @author Yang
 *
 */
public class ItemAddMessageListener implements MessageListener{
	
	@Autowired
	private ItemMapper itemMapper;
	@Autowired
	private SolrServer solrServer;
	
	@Override
	public void onMessage(Message message) {
		try{
			//从消息中取出id
			TextMessage textMessage = (TextMessage)message;
			String text = textMessage.getText();
			Long itemId = new Long(text);
			//等待事物提交
			Thread.sleep(1000);
			//根据id取商品信息
			SearchItem item = itemMapper.getItemById(itemId);
			SolrInputDocument document = new SolrInputDocument();
			document.addField("id",item.getId());
			document.addField("item_title",item.getTitle());
			document.addField("item_sell_point",item.getSell_point());
			document.addField("item_price",item.getPrice());
			document.addField("item_image",item.getImage());
			document.addField("item_category_name",item.getCategory_name());
			//把文档对象写入索引库
			solrServer.add(document);
			//提交
			solrServer.commit();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
