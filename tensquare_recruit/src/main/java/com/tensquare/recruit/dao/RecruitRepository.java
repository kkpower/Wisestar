package com.tensquare.recruit.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.tensquare.recruit.po.Recruit;

import java.util.List;

/**
 * 数据访问接口
 * @author Administrator
 *
 */
public interface RecruitRepository extends JpaRepository<Recruit,String>,JpaSpecificationExecutor<Recruit>{
	List<Recruit> findTop4ByStateOrderByCreatetimeDesc(String state);

	List<Recruit> findTop12ByStateNotOrderByCreatetimeDesc(String state);
}
