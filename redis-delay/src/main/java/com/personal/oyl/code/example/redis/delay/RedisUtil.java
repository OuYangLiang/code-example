package com.personal.oyl.code.example.redis.delay;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.List;

/**
 * @author OuYang Liang
 * @since 2019-09-24
 */
public class RedisUtil {

    private static final String key_separator = ":";
    private static final String prefix = "delay" + key_separator;

    JedisPool pool = new JedisPool();

    static String push_lua = "local setKey = KEYS[1] "
            + "local msg = ARGV[1] "
            + "local _score = KEYS[2] "
            + "local listKey = ARGV[2] "
            + "local existScore = tonumber(redis.call('zscore', setKey, listKey) or '0') "
            + "if (existScore == 0) then "
            + "    redis.call('zadd', setKey, _score, listKey) "
            + "end "
            + "redis.call('sadd', listKey, msg) ";

    static String query_lua = "local listKeys = redis.call('zrangebyscore', KEYS[1], 0, ARGV[1]) " +
            "local rlt = {} " +
            "for i in pairs(listKeys) do " +
            "    local msgs = redis.call('smembers', listKeys[i]) " +
            "    for j in pairs(msgs) do " +
            "        table.insert(rlt, msgs[j]) " +
            "    end " +
            "    redis.call('del', listKeys[i]) " +
            "end " +
            "for i in pairs(listKeys) do " +
            "    redis.call('zrem', KEYS[1], listKeys[i]) " +
            "end " +
            "return rlt ";

    public void push(String topic, int partition, String message, long timestamp) {
        String zsetKey = prefix + topic + key_separator + partition;
        String score = Long.toString(timestamp);
        String listKey = prefix + topic + key_separator + partition + key_separator + score;

        try (Jedis jedis = pool.getResource()) {
            jedis.eval(push_lua, 2, zsetKey, score, message, listKey);
        }
    }

    public List<String> query(String topic, int partition, long timestamp) {
        String zsetKey = prefix + topic + key_separator + partition;
        String score = Long.toString(timestamp);

        try (Jedis jedis = pool.getResource()) {
            return (List) jedis.eval(query_lua, 1, zsetKey, score);
        }
    }
}
