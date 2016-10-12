package org.ehuacui.bbs.config;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.Set;

/**
 * Redis操作管理
 */
public class RedisManager {

    private String host = "127.0.0.1";
    private int port = 6379;
    // 0 - never expire
    private int expire = 0;
    // timeout for jedis try to connect to redis server, not expire time! In
    // milliseconds
    private int timeout = 0;
    // Select the DB with having the specified zero-based numeric index.
    private int index = 0;
    private String password = "";
    private JedisPool jedisPool = null;

    /**
     * 初始化连接
     */
    public void init() {
        if (jedisPool == null) {
            if (password != null && !"".equals(password)) {
                jedisPool = new JedisPool(new JedisPoolConfig(), host, port, timeout, password);
            } else if (timeout != 0) {
                jedisPool = new JedisPool(new JedisPoolConfig(), host, port, timeout);
            } else {
                jedisPool = new JedisPool(new JedisPoolConfig(), host, port);
            }

        }
    }

    /**
     * 获取操作Jedis资源
     */
    private Jedis getJedis() {
        Jedis jedis = jedisPool.getResource();
        jedis.select(index);
        return jedis;
    }

    /**
     * 关闭Jedis资源
     */
    private void closeJedis(Jedis jedis) {
        jedis.close();
        //jedisPool.returnResource(jedis);
    }

    /**
     * 根据键获取值
     * 键类型为字节数组
     */
    public byte[] get(byte[] key) {
        byte[] value = null;
        Jedis jedis = getJedis();
        try {
            value = jedis.get(key);
        } finally {
            closeJedis(jedis);
        }
        return value;
    }

    /**
     * 根据键获取值
     * 键类型为String
     */
    public String get(String key) {
        String value = null;
        Jedis jedis = getJedis();
        try {
            value = jedis.get(key);
        } finally {
            closeJedis(jedis);
        }
        return value;
    }

    /**
     * 设置键值
     * 过期时间为系统默认设置时间
     * 键值为字节数组
     */
    public byte[] set(byte[] key, byte[] value) {
        Jedis jedis = getJedis();
        try {
            jedis.set(key, value);
            if (this.expire != 0) {
                jedis.expire(key, this.expire);
            }
        } finally {
            closeJedis(jedis);
        }
        return value;
    }

    /**
     * 设置键值
     * 过期时间为系统默认设置时间
     * 键值为String
     */
    public String setDefaultExpire(String key, String value) {
        Jedis jedis = getJedis();
        try {
            jedis.set(key, value);
            if (this.expire != 0) {
                jedis.expire(key, this.expire);
            }
        } finally {
            closeJedis(jedis);
        }
        return value;
    }

    /**
     * 设置键值
     * 键值为String
     */
    public String setNoExpire(String key, String value) {
        Jedis jedis = getJedis();
        try {
            jedis.set(key, value);
        } finally {
            closeJedis(jedis);
        }
        return value;
    }

    /**
     * 给指定链表(key)头部插入值
     *
     * @param key    键
     * @param values 值
     * @return 链表长度
     */
    public Long lpush(String key, String... values) {
        Long length;
        Jedis jedis = getJedis();
        try {
            length = jedis.lpush(key, values);
        } finally {
            closeJedis(jedis);
        }
        return length;
    }

    /**
     * 给指定链表(key)尾部插入值
     *
     * @param key    键
     * @param values 值
     * @return 链表长度
     */
    public Long rpush(String key, String... values) {
        Long length;
        Jedis jedis = getJedis();
        try {
            length = jedis.rpush(key, values);
        } finally {
            closeJedis(jedis);
        }
        return length;
    }

    /**
     * 从指定链表(key)头部取出值(并删除该值)
     * 链表为空或者不存在返回 NULL
     *
     * @param key 键
     * @return 弹出的值
     */
    public String lpop(String key) {
        String value;
        Jedis jedis = getJedis();
        try {
            value = jedis.lpop(key);
            if (value.equals("nil")) {
                value = null;
            }
        } finally {
            closeJedis(jedis);
        }
        return value;
    }

    /**
     * 从指定链表(key)尾部取出值(并删除该值)
     * 链表为空或者不存在返回 NULL
     *
     * @param key 键
     * @return 弹出的值
     */
    public String rpop(String key) {
        String value;
        Jedis jedis = getJedis();
        try {
            value = jedis.rpop(key);
            if (value.equals("nil")) {
                value = null;
            }
        } finally {
            closeJedis(jedis);
        }
        return value;
    }

    /**
     * 设置键值并设置过期时间
     * 键值为String
     */
    public String set(String key, String value, int expire) {
        Jedis jedis = getJedis();
        try {
            jedis.set(key, value);
            if (expire != 0) {
                jedis.expire(key, expire);
            }
        } finally {
            closeJedis(jedis);
        }
        return value;
    }

    /**
     * 设置键值并设置过期时间
     * 键值为字节数组
     */
    public byte[] set(byte[] key, byte[] value, int expire) {
        Jedis jedis = getJedis();
        try {
            jedis.set(key, value);
            if (expire != 0) {
                jedis.expire(key, expire);
            }
        } finally {
            closeJedis(jedis);
        }
        return value;
    }

    /**
     * 删除键值
     * 键类型为字节数组
     */
    public void del(byte[] key) {
        Jedis jedis = getJedis();
        try {
            jedis.del(key);
        } finally {
            closeJedis(jedis);
        }
    }

    /**
     * 删除键值
     * 键类型为String
     */
    public void del(String key) {
        Jedis jedis = getJedis();
        try {
            jedis.del(key);
        } finally {
            closeJedis(jedis);
        }
    }

    /**
     * 清洗数据库
     */
    public void flushDB() {
        Jedis jedis = getJedis();
        try {
            jedis.flushDB();
        } finally {
            closeJedis(jedis);
        }
    }

    /**
     * 数据库键值数
     */
    public Long dbSize() {
        Long dbSize = 0L;
        Jedis jedis = getJedis();
        try {
            dbSize = jedis.dbSize();
        } finally {
            closeJedis(jedis);
        }
        return dbSize;
    }

    /**
     * 根据正则匹配键值
     * 键值类型为字节数组
     */
    public Set<byte[]> keys(String pattern) {
        Set<byte[]> keys = null;
        Jedis jedis = getJedis();
        try {
            keys = jedis.keys(pattern.getBytes());
        } finally {
            closeJedis(jedis);
        }
        return keys;
    }

    /**
     * 根据正则匹配键值
     * 键值类型为String
     */
    public Set<String> returnStringKeys(String pattern) {
        Set<String> keys = null;
        Jedis jedis = getJedis();
        try {
            keys = jedis.keys(pattern);
        } finally {
            closeJedis(jedis);
        }
        return keys;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getExpire() {
        return expire;
    }

    public void setExpire(int expire) {
        this.expire = expire;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

}
