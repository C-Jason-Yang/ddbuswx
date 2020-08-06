/**
 * Copyright (C), 2015-2019, Jason-Yang
 * FileName: LineCache
 * Author:   Jason-Yang
 * Date:     2019-06-12 15:31
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * Jason-Yang        2019-06-12 15:31         1.0.0         〈〉
 */
package com.evcas.ddbuswx.config.cache;

import cn.hutool.cache.CacheUtil;
import cn.hutool.cache.impl.TimedCache;

/**
 * 〈简述:〉<br>
 * 定时缓存，对被缓存的对象定义一个过期时间，当对象超过过期时间会被清理。此缓存没有容量限制，对象只有在过期后才会被移除。
 *
 * @author Jason-Yang
 * @Date: 2019-06-12 15:31
 * @since 1.0.0
 */
public class SysCache<V> {
    public static final String BUS_LINE = "bus_line_";

    private static final long FOUR_HOURS = 4 * 60 * 60 * 1000;
    private static final long FIVE_HOURS = 5 * 60 * 60 * 1000;

    private TimedCache<String, V> TIMED_CACHE;

    private SysCache() {
        //启动 4小时 定时清理
        TIMED_CACHE = CacheUtil.newTimedCache(FIVE_HOURS);
        TIMED_CACHE.schedulePrune(FOUR_HOURS);
    }

    private static class LineCacheInstance {
        private static final SysCache INSTANCE = new SysCache();
    }

    public static SysCache getInstance() {
        return LineCacheInstance.INSTANCE;
    }


    /**
     * 添加缓存
     *
     * @param key   key
     * @param value value
     * @return
     */
    public void addCache(String key, V value) {
        TIMED_CACHE.put(key, value, FIVE_HOURS);
    }

    /**
     * 获取缓存
     *
     * @param key
     * @return
     */
    public V getCache(String key) {
        return TIMED_CACHE.get(key);
    }
}