package org.ehuacui.bbs.service;

import org.ehuacui.bbs.dto.PageDataBody;
import org.ehuacui.bbs.model.Topic;

/**
 * 搜索服务
 * Created by jianwei.zhou on 2016/10/12.
 */
public interface SearchService {
    /**
     * 将所有的topic都索引
     *
     * @return
     */
    boolean indexAll();

    /**
     * 索引单个话题
     *
     * @param topic
     * @return
     */
    boolean indexTopic(Topic topic);

    /**
     * 根据
     *
     * @param pageNumber
     * @param q
     * @return
     */
    PageDataBody indexQuery(Integer pageNumber, String q);

    /**
     * 删除索引
     *
     * @param id
     */
    void indexDelete(String id);

    void deleteAll();
}
