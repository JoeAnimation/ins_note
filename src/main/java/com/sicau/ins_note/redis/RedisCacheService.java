package com.sicau.ins_note.redis;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * Redis缓存服务
 *
 */
@Slf4j
@Component
public class RedisCacheService {

	private JedisPool jedisPool;
	 
    public void setJedisPool(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }
 
    /**
     * 获取Jedis实例
     *
     * @return
     */
    public synchronized Jedis getJedis() {
        try {
            if (jedisPool != null) {
                Jedis resource = jedisPool.getResource();
                return resource;
            } else {
                return null;
            }
        } catch (Exception e) {
        	if (e.getMessage() != null) {
        		log.error("Redis缓存获取Jedis实例 出错！", e.getLocalizedMessage());
			}
            return null;
        }
    }
 
    /**
     * 释放jedis资源
     * 每次连接必须使用
     *
     * @param jedis
     */
    public void returnResource(final Jedis jedis) {
        if (jedis != null) {
            jedis.close();
        }
    }
 
    //=============================common============================
 
    /**
     * 指定缓存失效时间
     * @param key 键
     * @param time 时间(秒)
     * @return
     */
    public boolean expire(String key,Long time){
        boolean result = false;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            if (null != jedis && time > 0) {
                jedis.expire(key, Integer.valueOf(String.valueOf(time)));
            }
        } catch (Exception e) {
        	if (e.getMessage() != null) {
        		log.error("redis键过期设置出错--键值" + key, e.getLocalizedMessage());
			}
        } finally {
            returnResource(jedis);
        }
        return result;
    }
 
    /**
     * 根据key 获取过期时间
     * @param key 键 不能为null
     * @return 时间(秒) 返回0代表为永久有效
     */
    public long getExpire(String key){
        Long time = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            if (jedis != null) {
                time = jedis.ttl(key);
            }
        } catch (Exception e) {
        	if (e.getMessage() != null) {
        		log.error("获取" + key + "的过期时间出错！", e.getLocalizedMessage());
			}
        } finally {
            returnResource(jedis);
        }
        if(null == time) {
            return -2;
        }
        return time;
    }
 
    /**
     * 判断key是否存在
     * @param key 键
     * @return true 存在 false不存在
     */
    public boolean hasKey(String key){
        Jedis jedis = null;
        try {
            jedis = getJedis();
            if (jedis != null) {
                return jedis.exists(key);
            }
        } catch (Exception e) {
        	if (e.getMessage() != null) {
        		log.error("获取" + key + "的过期时间出错！", e.getLocalizedMessage());
			}
        } finally {
            returnResource(jedis);
        }
        return false;
    }
 
    /**
     * 删除缓存
     * @param key 可以传一个值 或多个
     */
    @SuppressWarnings("unchecked")
    public void del(String ... key){
        if(null == key) {
            return;
        }
        Jedis jedis = null;
        try {
            jedis = getJedis();
            if(null == jedis) {
                return;
            }
            jedis.del(key);
        } catch (Exception e) {
        	if (e.getMessage() != null) {
        		log.error("删除" + key + "出错！", e.getLocalizedMessage());
			}
        } finally {
            returnResource(jedis);
        }
    }
 
    //============================String=============================
    /**
     * 普通缓存获取
     * @param key 键
     * @return 值
     */
    public Object get(String key){
        Jedis jedis = null;
        Object obj = null;
        try {
            jedis = jedisPool.getResource();
            String str = jedis.get(key);
            obj = JSONObject.parse(str);
        } catch (Exception e) {
        	if (e.getMessage() != null) {
        		log.error("获取" + key + "出错！", e.getLocalizedMessage());
			}
        } finally {
            returnResource(jedis);
        }
        return obj;
    }
 
    /**
     * 普通缓存放入
     * @param key 键
     * @param value 值
     * @return true成功 false失败
     */
    public boolean set(String key,Object value) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            if (jedis != null) {
                jedis.set(key, JSONObject.toJSONString(value));
            }
            return true;
        } catch (Exception e) {
        	if (e.getMessage() != null) {
        		log.error("Redis缓存设置" + key + "-" + value + "出错！", e.getLocalizedMessage());
			}
            return false;
        } finally {
            returnResource(jedis);
        }
    }
 
    /**
     * 普通缓存放入并设置时间
     * @param key 键
     * @param value 值
     * @param time 时间(秒) time要大于0 如果time小于等于0 将设置无限期
     * @return true成功 false 失败
     */
    public boolean set(String key,Object value,Long time){
        Jedis jedis = null;
        try {
            jedis = getJedis();
            if (jedis != null) {
                jedis.set(key, JSONObject.toJSONString(value));
                jedis.expire(key, Integer.valueOf(String.valueOf(time)));
            }
            return true;
        } catch (Exception e) {
        	if (e.getMessage() != null) {
        		log.error("Redis缓存设置" + key + "-" + value + "出错！", e.getLocalizedMessage());
			}
            return false;
        } finally {
            returnResource(jedis);
        }
    }
 
    //============================set=============================
    /**
     * 根据key获取Set中的所有值
     * @param key 键
     * @return
     */
    public Set<Object> sGet(String key){
        Jedis jedis = null;
        Set<Object> set = null;
        try {
            jedis = jedisPool.getResource();
            Set<String> strSet = jedis.smembers(key);
            if (strSet !=null && !strSet.isEmpty()) {
                set = new HashSet<Object>();
                for (String str : strSet) {
                    Object obj = JSONObject.parse(str);
                    set.add(obj);
                }
            }
        } catch (Exception e) {
        	if (e.getMessage() != null) {
        		log.error("获取" + key + "出错！", e.getLocalizedMessage());
			}
        } finally {
            returnResource(jedis);
        }
        return set;
    }
 
    /**
     * 将set数据放入缓存
     * @param key 键
     * @param time 时间(秒)
     * @param values 值 可以是多个
     * @return 成功个数
     */
    public long sSetAndTime(String key,long time,Object...values) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            if (values != null && values.length>0) {
                String[] strarrays = new String[values.length];
                for (int i = 0; i < values.length; i++) {
                    strarrays[i] = JSONObject.toJSONString(values[i]);
                }
                Long count = jedis.sadd(key, strarrays);
                if(time>0) expire(key, time);
                return count;
            }
        } catch (Exception e) {
            return 0;
        } finally {
            returnResource(jedis);
        }
        return 0;
    }
 
    //================================zSet================================
 
    /**
     * 将数据放入set缓存
     * @param key 键
     * @param value 值
     * @param score 排序分数
     */
    public <T> boolean zSet(String key, T value ,double score) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            if (jedis != null) {
                jedis.zadd(key, score, JSONObject.toJSONString(value));
            }
            return true;
        } catch (Exception e) {
        	if (e.getMessage() != null) {
        		log.error("Redis缓存设置" + key + "-" + value + "出错！", e.getLocalizedMessage());
			}
            return false;
        } finally {
            returnResource(jedis);
        }
    }
    /**
     *
    * 返回键k的从start-end之间的数据。（从大到小排序从0开始）
    * @param
    * @return
    * @auther yaotenghui
    * @date 2018/5/17 15:22
    */
    public <T> Set<T> reverseRange(String key, long start, long end, Class<T> resultType){
        Jedis jedis = null;
        Set<T> set = null;
        try {
            jedis = jedisPool.getResource();
            Set<String> strSet = jedis.zrevrange(key,start,end);
            if (strSet !=null && !strSet.isEmpty()) {
                set = new HashSet<T>();
                for (String str : strSet) {
                    set.add(JSON.toJavaObject(JSON.parseObject(str), resultType));
                }
            }
        } catch (Exception e) {
        	if (e.getMessage() != null) {
        		log.error("获取" + key + "出错！", e.getLocalizedMessage());
			}
        } finally {
            returnResource(jedis);
        }
        return set;
    }
 
    /**
     *
     * 返回键k的从start-end之间的数据。（从大到小排序从0开始）
     * @param
     * @return
     * @auther wanjiadong
     * @date 2018/6/23 15:22
     */
    public <T> List<T> reverseRangeToList(String key, Long start, Long end, Class<T> resultType){
        Jedis jedis = null;
        List<T> list = null;
        try {
            jedis = jedisPool.getResource();
            Set<String> strSet = jedis.zrevrange(key,start,end);
            if (strSet !=null && !strSet.isEmpty()) {
                list = new ArrayList<T>(strSet.size());
                for (String str : strSet) {
                    list.add(JSON.toJavaObject(JSON.parseObject(str), resultType));
                }
            }
        } catch (Exception e) {
        	if (e.getMessage() != null) {
        		log.error("获取" + key + "出错！", e.getLocalizedMessage());
			}
        } finally {
            returnResource(jedis);
        }
        return list;
       }
}
