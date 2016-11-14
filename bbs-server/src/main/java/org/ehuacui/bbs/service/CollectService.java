package org.ehuacui.bbs.service;

import org.ehuacui.bbs.model.Collect;

/**
 * Created by jianwei.zhou on 2016/8/15.
 */
public interface CollectService {
    /**
     * 根据话题id与用户查询收藏记录
     *
     * @param tid
     * @param uid
     * @return
     */
    Collect findByTidAndUid(Integer tid, Integer uid);

    /**
     * 查询话题被收藏的数量
     *
     * @param tid
     * @return
     */
    Long countByTid(Integer tid);

    void save(Collect collect);

    void delete(Integer id);
}
