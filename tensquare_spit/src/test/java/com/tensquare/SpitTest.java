package com.tensquare;

import com.tensquare.pojo.Spit;
import com.tensquare.service.SpitService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Iterator;
import java.util.List;

/**
 * @ClassName SpitTest
 * @Description: TODO
 * @Author codemi@aliyun.com
 * @Date 2020/1/25
 **/
@SpringBootTest
@RunWith(SpringRunner.class)
public class SpitTest {

    @Autowired
    private SpitService spitService;

    @Test
    public void findAll(){
        List<Spit> list = spitService.findAll();
        for (Spit spit : list) {
            System.out.println(spit);
        }
    }
}
