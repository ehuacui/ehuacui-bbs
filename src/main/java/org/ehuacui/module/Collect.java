package org.ehuacui.module;

import org.ehuacui.common.BaseModel;
import org.ehuacui.common.Constants.CacheEnum;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.redis.Cache;
import com.jfinal.plugin.redis.Redis;

/**
 * Created by ehuacui.
 * Copyright (c) 2016, All Rights Reserved.
 * http://www.ehuacui.org
 */
public class Collect extends BaseModel<Collect> {

    public static final Collect me = new Collect();

    /**
     * 根据话题id与用户查询收藏记录
     * @param tid
     * @param uid
     * @return
     */
    public Collect findByTidAndUid(Integer tid, Integer uid) {
        Cache cache = Redis.use();
        Collect collect = cache.get(CacheEnum.collect.name() + tid + "_" + uid);
        if(collect == null) {
            collect = super.findFirst(
                    "select * from ehuacui_collect where tid = ? and uid = ?",
                    tid,
                    uid
            );
            cache.set(CacheEnum.collect.name() + tid + "_" + uid, collect);
        }
        return collect;
    }

    /**
     * 查询话题被收藏的数量
     * @param tid
     * @return
     */
    public Long countByTid(Integer tid) {
        Cache cache = Redis.use();
        Long count = cache.get(CacheEnum.collectcount.name() + tid);
        if(count == null) {
            count = super.findFirst(
                    "select count(1) as count from ehuacui_collect where tid = ?",
                    tid
            ).getLong("count");
            cache.set(CacheEnum.collectcount.name() + tid, count);
        }
        return count;
    }

    /**
     * 收藏话题列表
     * @param pageNumber
     * @param pageSize
     * @param uid
     * @return
     */
    public Page<Collect> findByUid(Integer pageNumber, Integer pageSize, Integer uid) {
        Cache cache = Redis.use();
        Page<Collect> page = cache.get(CacheEnum.collects.name() + uid);
        if(page == null) {
            page = super.paginate(
                    pageNumber,
                    pageSize,
                    "select c.*, t.* ",
                    " from ehuacui_collect c left join ehuacui_topic t on c.tid = t.id where t.is_delete = ? and c.uid = ?",
                    false,
                    uid);
            cache.set(CacheEnum.collects.name() + uid, page);
        }
        return page;
    }

    /**
     * 查询用户收藏的数量
     * @param uid
     * @return
     */
    public Long countByUid(Integer uid) {
        Cache cache = Redis.use();
        Long count = cache.get(CacheEnum.usercollectcount.name() + uid);
        if(count == null) {
            count = super.findFirst(
                    "select count(1) as count from ehuacui_collect where uid = ?",
                    uid
            ).getLong("count");
            cache.set(CacheEnum.usercollectcount.name() + uid, count);
        }
        return count;
    }

}
