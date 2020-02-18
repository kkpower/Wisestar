package com.tensquare.qa.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.tensquare.qa.po.Problem;
import org.springframework.data.jpa.repository.Query;


/**
 * 数据访问接口
 * @author Administrator
 *
 */
public interface ProblemRepository extends JpaRepository<Problem,String>,JpaSpecificationExecutor<Problem>{

    @Query(value = "SELECT * FROM tb_problem INNER JOIN tb_pl ON tb_problem.id = tb_pl.problemid WHERE labelid = ? ORDER BY replytime DESC",nativeQuery = true)
    Page<Problem> newlist(String labelid, Pageable pageable);

    @Query(value = "SELECT * FROM tb_problem INNER JOIN tb_pl ON tb_problem.id = tb_pl.problemid WHERE labelid = ? ORDER BY reply DESC",nativeQuery = true)
    Page<Problem> hotlist(String labelid, Pageable pageable);

    @Query(value = "SELECT * FROM tb_problem INNER JOIN tb_pl ON tb_problem.id = tb_pl.problemid WHERE labelid = ? AND reply = 0 ORDER BY createtime DESC",nativeQuery = true)
    Page<Problem> waitlist(String labelid, Pageable pageable);
}
