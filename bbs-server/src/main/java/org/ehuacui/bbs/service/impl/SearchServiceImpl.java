package org.ehuacui.bbs.service.impl;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.ehuacui.bbs.dto.PageDataBody;
import org.ehuacui.bbs.model.Topic;
import org.ehuacui.bbs.model.TopicAppend;
import org.ehuacui.bbs.service.SearchService;
import org.ehuacui.bbs.service.TopicAppendService;
import org.ehuacui.bbs.service.TopicService;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by ehuacui.
 * Copyright (c) 2016, All Rights Reserved.
 * http://www.ehuacui.org
 */
@Service
public class SearchServiceImpl implements SearchService {

    @Value("${solr.url}")
    private String solrURL;
    @Value("${solr.pageSize}")
    private Integer solrPageSize;

    @Autowired
    private TopicService topicService;
    @Autowired
    private TopicAppendService topicAppendService;

    /**
     * 将所有的topic都索引
     *
     * @return
     */
    @Override
    public boolean indexAll() {
        try {
            List<Topic> topics = topicService.findAll();
            List<SolrInputDocument> docs = new ArrayList<>();
            for (Topic topic : topics) {
                SolrInputDocument doc = new SolrInputDocument();
                doc.addField("id", topic.getId());
                doc.addField("title", topic.getTitle());
                doc.addField("in_time", topic.getInTime());
                //话题内容与追加内容拼接作为一个整体索引
                StringBuffer content = new StringBuffer(topic.getContent());
                for (TopicAppend ta : topic.getTopicAppends()) {
                    content.append("\n")//换行
                            .append(ta.getContent());
                }
                doc.addField("content", content.toString());
                docs.add(doc);
            }
            SolrClient client = new HttpSolrClient(solrURL);
            client.add(docs);
            client.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 索引单个话题
     *
     * @param topic
     * @return
     */
    @Override
    public boolean indexTopic(Topic topic) {
        try {
            SolrInputDocument doc = new SolrInputDocument();
            doc.addField("id", topic.getId());
            doc.addField("title", topic.getTitle());
            doc.addField("in_time", topic.getInTime());
            List<TopicAppend> topicAppends = topicAppendService.findByTid(topic.getId());
            StringBuffer content = new StringBuffer(topic.getContent());
            for (TopicAppend ta : topicAppends) {
                content.append("\n")//换行
                        .append(ta.getContent());
            }
            doc.addField("content", content.toString());
            SolrClient client = new HttpSolrClient(solrURL);
            client.add(doc);
            client.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 根据
     *
     * @param pageNumber
     * @param q
     * @return
     */
    @Override
    public PageDataBody indexQuery(Integer pageNumber, String q) {
        try {
            SolrClient solrClient = new HttpSolrClient(solrURL);
            SolrQuery query = new SolrQuery(q);
            query.setStart((pageNumber - 1) * solrPageSize);
            query.setRows(solrPageSize);
            query.setSort("in_time", SolrQuery.ORDER.desc);
            query.setHighlight(true);
            query.addHighlightField("title");
            query.addHighlightField("content");
            query.setHighlightSimplePre("<font color='red'><b>");
            query.setHighlightSimplePost("</b></font>");
            query.setHighlightSnippets(1);
            query.setHighlightFragsize(150);
            QueryResponse res = solrClient.query(query);
            Map<String, Map<String, List<String>>> highlightMap = res.getHighlighting();
            SolrDocumentList docs = res.getResults();
            List<Topic> list = new ArrayList<>();
            for (SolrDocument doc : docs) {
                Topic topic = new Topic();
                String id = doc.getFieldValue("id").toString();
                topic.setId(Integer.valueOf(id));
                List<String> titleList = highlightMap.get(id).get("title");
                if (titleList != null && titleList.size() > 0) {
                    topic.setTitle(titleList.get(0));
                } else {
                    topic.setTitle(doc.getFieldValueMap().get("title").toString());
                }
                List<String> contentList = highlightMap.get(id).get("content");
                if (contentList != null && contentList.size() > 0) {
                    topic.setContent(Jsoup.clean(contentList.get(0) + "...", Whitelist.none().addTags("b").addAttributes("font", "color")));
                }
                list.add(topic);
            }
            int totalCount = (int) docs.getNumFound();
            return new PageDataBody<>(list, pageNumber, solrPageSize, totalCount);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 删除索引
     *
     * @param id
     */
    @Override
    public void indexDelete(String id) {
        try {
            SolrClient client = new HttpSolrClient(solrURL);
            client.deleteById(id);
            client.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteAll() {
        try {
            SolrClient client = new HttpSolrClient(solrURL);
            client.deleteByQuery("*");
            client.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
