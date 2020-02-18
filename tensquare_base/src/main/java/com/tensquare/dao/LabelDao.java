package com.tensquare.dao;

import com.tensquare.pojo.Label;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @ClassName LabelDao
 * @Description: TODO
 * @Author codemi@aliyun.com
 * @Date 2020/1/19
 **/
public interface LabelDao extends JpaRepository<Label,String>, JpaSpecificationExecutor<Label> {
}
