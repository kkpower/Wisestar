package com.tensquare.pojo;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

import java.io.Serializable;

/**
 * @ClassName Article
 * @Description: TODO
 * @Author codemi@aliyun.com
 * @Date 2020/1/29
 **/

@Document(indexName = "tensquare",type = "article")
@Data
public class Article implements Serializable {
    @Id
    private String id;//ID

    @Field(index= true
            ,analyzer="ik_max_word",searchAnalyzer="ik_max_word")
    private String title;//标题

    @Field(index= true
            ,analyzer="ik_max_word",searchAnalyzer="ik_max_word")
    private String content;//文章正文

    private String state;//审核状态
}
