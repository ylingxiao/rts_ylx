package cn.ylc.jedis;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

public class JedisTest {
	
	/*@Test
	public void testJedis(){
		//创建一个jedis对象
		Jedis jedis = new Jedis("127.0.0.1",6379);
		//直接使用jedis操作redis,所有jedis的命令都对应一个方法
		jedis.set("test123", "my first jedis test");
		String string = jedis.get("test123");
		System.out.println(string);
		//关闭连接
		jedis.close();
	}*/
	
	/*@Test
	public void testJedisPool(){
		//创建一个jedisPool对象
		JedisPool jedisPool = new JedisPool("127.0.0.1",6379);
		//从jedisPool得到jedis
		Jedis jedis = jedisPool.getResource();
		//直接使用jedis操作redis,所有jedis的命令都对应一个方法
		jedis.set("test123", "my first test");
		String string = jedis.get("test123");
		System.out.println(string);
		//关闭连接
		jedis.close();
		//关闭连接池
		jedisPool.close();
	}
	
	@Test
	public void testJedisCluster(){
		//创建一个jedisCluster对象,有一个参数nodes是一个set类型,set包含若干个HostAndPort对象
		Set<HostAndPort> nodes = new HashSet<>();
		nodes.add(new HostAndPort("127.0.0.1",6379));
		JedisCluster jedisCluster  = new JedisCluster(nodes);
		//直接使用jedis操作redis,所有jedis的命令都对应一个方法
		jedisCluster.set("test123", "my first test");
		String string = jedisCluster.get("test123");
		System.out.println(string);
		//关闭连接
		jedisCluster.close();
	}*/
	
}
