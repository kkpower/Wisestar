package com.tensquare;

import javafx.application.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @ClassName MqProvider
 * @Description: TODO
 * @Author codemi@aliyun.com
 * @Date 2020/2/5
 **/

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RabbitmqApplication.class)
public class MqProvider {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    public void testSend(){
        rabbitTemplate.convertAndSend("itcast","直接模式测试");
    }

    @Test
    public void testSend2(){
        rabbitTemplate.convertAndSend("chuanzhi","","分裂模式测试");
    }


    @Test
    public void testSend3(){
        rabbitTemplate.convertAndSend("topic33","good.log","主题模式测试");
    }
}
