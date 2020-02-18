package com.tensquare.dao;

import com.tensquare.po.Friend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * @ClassName FriendDao
 * @Description: TODO
 * @Author codemi@aliyun.com
 * @Date 2020/2/10
 **/
public interface FriendDao extends JpaRepository<Friend,String> {

    @Query(value = "SELECT COUNT(tb_friend.userid) FROM tb_friend WHERE tb_friend.userid = ?1 AND tb_friend.friendid = ?2",nativeQuery = true)
    int selectCount(String uid, String friendid);

    @Modifying
    @Query(value = "update tb_friend set tb_friend.islike = 1 where tb_friend.userid = ?1 and tb_friend.friendid = ?2",nativeQuery = true)
    void updateLike(String friendid, String uid);
}
