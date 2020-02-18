package com.tensquare.service;

import com.tensquare.pojo.Label;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @ClassName BaseServiceImpl
 * @Description: TODO
 * @Author codemi@aliyun.com
 * @Date 2020/1/19
 **/
public interface LabelService {

    List<Label> findAll();

    Label findById(String labelId);

    void save(Label label);

    void update(Label label);

    void delete(String labelId);

    List<Label> findSearch(Label label);

    Page<Label> pageQuery(Label label, int page, int size);
}
