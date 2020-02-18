package com.tensquare;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @ClassName Customer1
 * @Description: TODO
 * @Author codemi@aliyun.com
 * @Date 2020/2/5
 **/

@Component
@RabbitListener(queues = "heima")
public class Customer1 {
    @RabbitHandler
    public void showMessage(String msg){
        System.out.println("heima接收消息"+msg);
    }
}
