package com.tensquare;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @ClassName MqConsumer
 * @Description: TODO
 * @Author codemi@aliyun.com
 * @Date 2020/2/5
 **/

@Component
@RabbitListener(queues = "itcast")
public class MqConsumer {
    @RabbitHandler
    public void showMessage(String msg){
        System.out.println("itcast接收消息:"+msg);
    }
}
