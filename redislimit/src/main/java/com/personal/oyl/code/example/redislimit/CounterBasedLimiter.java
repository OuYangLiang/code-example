package com.personal.oyl.code.example.redislimit;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * Hello world!
 *
 */
public class CounterBasedLimiter {
    JedisPool pool = new JedisPool();
    
    static String lua = "local key = KEYS[1] "
            + "local limit = tonumber(ARGV[1]) "
            + "local current = tonumber(redis.call('get', key) or '0') "
            + "if (current + 1) > limit then "
            + "    return 0 "
            + "elseif current == 0 then "
            + "    redis.call('incrby', key, '1') "
            + "    redis.call('expire', key, '2') " // 设置2秒过期时间
            + "    return 1 "
            + "else "
            + "    redis.call('incrby', key, '1') "
            + "    return 1 "
            + "end";
    
    public boolean isLimit() {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            String key = "Limit.Key." + System.currentTimeMillis() / 1000;
            String limit = "100";
            
            Object rlt = jedis.eval(lua, 1, key, limit);
            
            return Long.valueOf(0) == rlt;
        } finally {
            if (null != jedis) {
                jedis.close();
            }
        }
    }
    
    public static void main(String[] args) {
        CounterBasedLimiter app = new CounterBasedLimiter();
        for (int i=0; i<=1000; i++) {
            System.out.println(app.isLimit());
        }
    }
}


