package com.tensquare.listener;

import com.aliyuncs.exceptions.ClientException;
import com.tensquare.uitis.SmsUtils;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.sound.midi.Soundbank;
import java.util.Map;

/**
 * @ClassName SmsListener 短信监听
 * @Description: TODO
 * @Author codemi@aliyun.com
 * @Date 2020/2/6
 **/

@Component
@RabbitListener(queues = "sms")
public class SmsListener {


    @Autowired
    private SmsUtils smsUtils;

    @Value("${aliyun.sms.sign_name}")
    private String sign_name;//签名

    @Value("${aliyun.sms.template_code}")
    private String template_code;//签名

    @RabbitHandler
    public void sendSms(Map<String,String> map){
        System.out.println("手机号:"+map.get("mobile"));
        System.out.println("验证码："+map.get("checkcode"));
        String mobile = map.get("mobile");
        String checkcode = map.get("checkcode");
        try {
            smsUtils.sendSms(mobile,sign_name,template_code,"{\"code\":\""+checkcode+"\"}","");
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }
}
