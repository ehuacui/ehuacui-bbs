package org.ehuacui.service.impl;

import org.ehuacui.common.Page;
import org.ehuacui.mapper.CollectMapper;
import org.ehuacui.model.Collect;
import org.ehuacui.service.ICollectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by ehuacui.
 * Copyright (c) 2016, All Rights Reserved.
 * http://www.ehuacui.org
 */
@Service
public class CollectService implements ICollectService {

    @Autowired
    private CollectMapper collectMapper;

    /**
     * 根据话题id与用户查询收藏记录
     *
     * @param tid
     * @param uid
     * @return
     */
    @Override
    public Collect findByTidAndUid(Integer tid, Integer uid) {
        return collectMapper.selectByTidAndUid(tid, uid);
    }

    /**
     * 查询话题被收藏的数量
     *
     * @param tid
     * @return
     */
    @Override
    public Long countByTid(Integer tid) {
        return collectMapper.countByTid(tid);
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
        List<Collect> list = collectMapper.selectByUid(uid, pageNumber, pageSize);
        long total = collectMapper.countByUid(uid);
        return new Page<>(list, pageNumber, pageSize, total);
    }

    /**
     * 查询用户收藏的数量
     *
     * @param uid
     * @return
     */
    @Override
    public Long countByUid(Integer uid) {
        return collectMapper.countByUid(uid);
    }

}
