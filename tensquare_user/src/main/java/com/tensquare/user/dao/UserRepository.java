package com.tensquare.user.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.tensquare.user.po.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * 数据访问接口
 * @author Administrator
 *
 */
public interface UserRepository extends JpaRepository<User,String>,JpaSpecificationExecutor<User>{

    User findByMobile(String mobile);

    @Modifying
    @Query(value = "update tb_user set fanscount = fanscount+?2 where id = ?1",nativeQuery = true)
    void incfanscount(String friendid, int x);

    @Modifying
    @Query(value = "update tb_user set followcount = followcount+?2 where id = ?1",nativeQuery = true)
    void incfollowcount(String userid, int x);
}
