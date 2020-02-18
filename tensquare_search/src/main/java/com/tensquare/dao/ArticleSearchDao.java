package com.tensquare.dao;

import com.tensquare.pojo.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @ClassName ArticleSearchDao
 * @Description: TODO
 * @Author codemi@aliyun.com
 * @Date 2020/1/29
 **/
public interface ArticleSearchDao extends ElasticsearchRepository<Article,String> {
    /**
     * 检索
     * @param title
     * @param content
     * @param pageable
     * @return
     */
    Page<Article> findByTitleOrContentLike(String title, String content, Pageable pageable);
}
