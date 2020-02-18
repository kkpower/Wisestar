package com.tensquare.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @ClassName UserClient
 * @Description: TODO
 * @Author codemi@aliyun.com
 * @Date 2020/2/10
 **/
@FeignClient("tensquare-user")
public interface UserClient {
    @RequestMapping(value = "/user/{userid}/{friendid}/{x}",method = RequestMethod.PUT)
    void updatefanscountandfollowcount(@PathVariable("userid") String userid, @PathVariable("friendid") String friendid, @PathVariable("x") int x);
}
