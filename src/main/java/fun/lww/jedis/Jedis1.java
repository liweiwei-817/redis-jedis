package fun.lww.jedis;

import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.ArrayList;
import java.util.List;

/**
 * Create by lww on 18/4/16
 * jedis test
 */
public class Jedis1 {

    /**
     * 单实例测试
     */
    @Test
    public void test1() {
        //设置IP地址和端口
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        //设置数据
        jedis.set("name", "lww");
        //获取数据
        String name = jedis.get("name");
        System.out.println(name);
        //释放资源
        jedis.close();
    }

    /**
     * 使用连接池方式
     */
    @Test
    public void test2() {
        //获得连接池的配置对象
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        //设置最大连接数
        jedisPoolConfig.setMaxTotal(30);
        //设置最大空闲连接数
        jedisPoolConfig.setMaxIdle(10);
        //获取连接池对象
        JedisPool jedisPool = new JedisPool(jedisPoolConfig, "127.0.0.1", 6379);
        //获取核心对象
        Jedis jedis = null;
        try {
            //通过连接池获取连接
            jedis = jedisPool.getResource();
            //设置数据
            jedis.set("name", "liweiwei");
            String name = jedis.get("name");
            System.out.println(name);

            jedis.lpush("list2", "l1", "l2");
            jedis.del("set2");
            jedis.sadd("set2", "s1", "s2");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (jedis != null) {
                jedis.close();
            }
            if (jedisPool != null) {
                jedisPool.close();
            }
        }
    }
}
