package org.ehuacui.bbs.cron4j;

import com.jfinal.kit.LogKit;
import com.jfinal.plugin.redis.Cache;
import com.jfinal.plugin.redis.Redis;

/**
 * Created by ehuacui.
 * Copyright (c) 2016, All Rights Reserved.
 * http://www.ehuacui.org
 */
public class ClearCacheJob implements Runnable {
    @Override
    public void run() {
        LogKit.info("开始清理缓存");
        Cache cache = Redis.use();
        if (cache != null) {
            cache.getJedis().flushDB();
        }
        LogKit.info("缓存清理完成");
    }
}
