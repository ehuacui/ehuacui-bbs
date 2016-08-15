package org.ehuacui.dao;

import com.jfinal.plugin.activerecord.Page;
import org.ehuacui.module.Collect;

/**
 * Created by jianwei.zhou on 2016/8/15.
 */
public interface ICollectDao {
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

    /**
     * 收藏话题列表
     *
     * @param pageNumber
     * @param pageSize
     * @param uid
     * @return
     */
    Page<Collect> findByUid(Integer pageNumber, Integer pageSize, Integer uid);

    /**
     * 查询用户收藏的数量
     *
     * @param uid
     * @return
     */
    Long countByUid(Integer uid);
}
