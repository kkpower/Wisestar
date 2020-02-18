package com.tensquare.controller;

import com.tensquare.client.UserClient;
import com.tensquare.service.FriendService;
import entity.Result;
import entity.StatusCode;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName FriendController
 * @Description: TODO
 * @Author codemi@aliyun.com
 * @Date 2020/2/10
 **/
@RestController
@RequestMapping("/friend")
public class FriendController {

    @Autowired
    private FriendService friendService;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private UserClient userClient;

    /**
     * 添加好友或者添加非好友
     * @param friendid 对方用户id
     * @param type “1”喜欢、“0”不喜欢
     * @return
     */
    @RequestMapping(value = "/like/{friendid}/{type}",method = RequestMethod.PUT)
    public Result addFriend(@PathVariable String friendid,@PathVariable String type){
        boolean flag = false;
        //验证是否登录，并且拿到当前登录的用户id
        Claims claims = (Claims) request.getAttribute("claims_user");
        if (claims == null){
            return new Result(false,StatusCode.ERROR,"权限不足!");
        }
            //拿到当前用户id
        String uid = claims.getId();
        //判断是添加好友还是添加非好友
        if (type != null){
            if ("1".equals(type)){
                //添加好友
                flag = friendService.addFriend(uid,friendid);
                if (flag){
                    userClient.updatefanscountandfollowcount(uid,friendid,1);
                    return new Result(true,StatusCode.OK,"添加成功");
                }
            }else if ("0".equals(type)){
                //添加非好友
                //friendService.addNoFriend(uid,friendid);
            }else {
                return new Result(false,StatusCode.ERROR,"参数异常");
            }
            return new Result(false,StatusCode.ERROR,"请勿重复添加好友");
        }else {
            return new Result(false,StatusCode.ERROR,"参数异常");
        }

    }
}
