package org.ehuacui.service.impl;

import com.jfinal.plugin.activerecord.Page;
import org.ehuacui.common.DaoHolder;
import org.ehuacui.module.Collect;
import org.ehuacui.service.ICollectService;

/**
 * Created by ehuacui.
 * Copyright (c) 2016, All Rights Reserved.
 * http://www.ehuacui.org
 */
public class CollectService implements ICollectService {

    /**
     * 根据话题id与用户查询收藏记录
     *
     * @param tid
     * @param uid
     * @return
     */
    @Override
    public Collect findByTidAndUid(Integer tid, Integer uid) {
        return DaoHolder.collectDao.findByTidAndUid(tid, uid);
    }

    /**
     * 查询话题被收藏的数量
     *
     * @param tid
     * @return
     */
    @Override
    public Long countByTid(Integer tid) {
        return DaoHolder.collectDao.countByTid(tid);
    }

    /**
     * 收藏话题列表
     *
     * @param pageNumber
     * @param pageSize
     * @param uid
     * @return
     */
    @Override
    public Page<Collect> findByUid(Integer pageNumber, Integer pageSize, Integer uid) {
        return DaoHolder.collectDao.findByUid(pageNumber, pageSize, uid);
    }

    /**
     * 查询用户收藏的数量
     *
     * @param uid
     * @return
     */
    @Override
    public Long countByUid(Integer uid) {
        return DaoHolder.collectDao.countByUid(uid);
    }

}
