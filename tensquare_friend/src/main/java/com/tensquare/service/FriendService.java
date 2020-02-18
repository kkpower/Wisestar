package com.tensquare.service;

import com.tensquare.dao.FriendDao;
import com.tensquare.po.Friend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @ClassName FriendService
 * @Description: TODO
 * @Author codemi@aliyun.com
 * @Date 2020/2/10
 **/
@Service
public class FriendService {


    @Autowired
    private FriendDao friendDao;

    @Transactional
    public boolean addFriend(String uid, String friendid) {

        //如果用户已经添加这个好友，则不能进行任何操作，返回false
        int count = friendDao.selectCount(uid,friendid);
        if (count > 0){
            return false;
        }else{
            //判断对方是否喜欢你，如果喜欢，将islike设置为1
            int flag = friendDao.selectCount(friendid,uid);
            if (flag == 0){
                //向喜欢表中添加记录
                Friend friend = new Friend();
                friend.setUserid(uid);
                friend.setFriendid(friendid);
                friend.setIslike("0");
                friendDao.save(friend);
            }else if (flag == 1){
                //向喜欢表中添加记录(互相喜欢)
                Friend friend = new Friend();
                friend.setUserid(uid);
                friend.setFriendid(friendid);
                friend.setIslike("1");
                friendDao.save(friend);
                friendDao.updateLike(friendid,uid);
            }

        }
        return true;
    }
}
