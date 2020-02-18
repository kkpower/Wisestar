package com.tensquare.service;

import com.tensquare.dao.ArticleSearchDao;
import com.tensquare.pojo.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import util.IdWorker;

/**
 * @ClassName ArticleSearchService
 * @Description: TODO
 * @Author codemi@aliyun.com
 * @Date 2020/1/29
 **/

@Service
public class ArticleSearchService {

    @Autowired
    private ArticleSearchDao articleSearchDao;

   /* @Autowired
    private IdWorker idWorker;*/

    /**
     * 增加文章
     * @param article
     */
    public void add(Article article){
//        article.setId(idWorker.nextId()+"");
        articleSearchDao.save(article);
    }

    public Page<Article> findByTitleLike(String keyWords,int page,int size){
        PageRequest pageRequest = PageRequest.of(page-1,size);
        return articleSearchDao.findByTitleOrContentLike(keyWords,keyWords,pageRequest);
    }
}
