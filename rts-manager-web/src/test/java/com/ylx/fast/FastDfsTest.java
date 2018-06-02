package com.ylx.fast;

import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.junit.Test;

import cn.ylx.common.utils.FastDFSClient;

public class FastDfsTest {
	
	@Test
	public void testUpload() throws Exception{
		//创建一个配置文件,文件名任意,内容为track服务器的地址
		//使用全局对象加载配置文件
		ClientGlobal.init("D:/WorkSpace/eclipse_mars/rts-manager-web/src/main/resources/conf/client.conf");
		//创建一个TrackerClient对象
		TrackerClient trackClient = new TrackerClient();
		//通过TrackClient获得一个TrackerServer对象
		TrackerServer trackServer = trackClient.getConnection();
		//创建一个StrorageServer的引用,可以是Null
		//创建一个StorageClient,参数需要TrackerServer和StrorageServer
		StorageClient storageClient = new StorageClient(trackServer,null);
		//使用StorageClient上传文件
		String[] strings = storageClient.upload_file("C:/Users/Yang/Pictures/wallpaper/polymagnet.png", "png", null);
		for(String s : strings){
			System.out.println(s);
		}
	}
	
	@Test
	public void testFastDfsClient() throws Exception{
		FastDFSClient fastDFSClient = new FastDFSClient("D:/WorkSpace/eclipse_mars/rts-manager-web/src/main/resources/conf/client.conf");
		String string = fastDFSClient.uploadFile("C:/Users/Yang/Pictures/wallpaper/polymagnet.png");
		System.out.println(string);
	}
	
}
