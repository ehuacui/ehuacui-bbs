package org.ehuacui.bbs.service.impl;

import org.ehuacui.bbs.mapper.CollectMapper;
import org.ehuacui.bbs.model.Collect;
import org.ehuacui.bbs.service.CollectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by ehuacui.
 * Copyright (c) 2016, All Rights Reserved.
 * http://www.ehuacui.org
 */
@Service
public class CollectServiceImpl implements CollectService {

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

    @Override
    public void save(Collect collect) {
        collectMapper.insert(collect);
    }

    @Override
    public void delete(Integer id) {
        collectMapper.deleteByPrimaryKey(id);
    }
}
