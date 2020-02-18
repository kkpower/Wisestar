package com.tensquare.controller;

import com.tensquare.pojo.Spit;
import com.tensquare.service.SpitService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName SpitController
 * @Description: TODO
 * @Author codemi@aliyun.com
 * @Date 2020/1/25
 **/

@RestController
@CrossOrigin
@RequestMapping("/spit")
public class SpitController {

    @Autowired
    private SpitService spitService;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 查询全部数据
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public Result findAll(){
        return new Result(true, StatusCode.OK,"查询成功",spitService.findAll());
    }

    /**
     * 根据id查询单条记录
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public Result findOne(@PathVariable String id){
        return new Result(true,StatusCode.OK,"查询成功",spitService.findById(id));
    }

    /**
     * 增加
     * @param spit
     * @return
     */
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public Result add(@RequestBody Spit spit){
        spitService.save(spit);
        return new Result(true,StatusCode.OK,"增加成功");
    }

    /**
     * 修改
     * @param id
     * @param spit
     * @return
     */
    @RequestMapping(value = "/update/{id}",method = RequestMethod.PUT)
    public Result update(@PathVariable String id,@RequestBody Spit spit){
        spit.set_id(id);
        spitService.update(spit);
        return new Result(true,StatusCode.OK,"修改成功");
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @RequestMapping(value = "/delete/{id}")
    public Result delete(@PathVariable String id){
        spitService.delete(id);
        return new Result(true,StatusCode.OK,"删除成功");
    }

    /**
     * 根据父级id查询
     * @param parentid
     * @param page
     * @param size
     * @return
     */
    @RequestMapping(value = "/comment/{parentid}/{page}/{size}",method = RequestMethod.GET)
    public Result findByParentid(@PathVariable String parentid,@PathVariable int page,@PathVariable int size){
        Page<Spit> spitPage = spitService.findByParentid(parentid, page, size);
        return new Result(true,StatusCode.OK,"查询成功",new PageResult<Spit>(spitPage.getTotalElements(),spitPage.getContent()));
    }

    @RequestMapping(value = "/thumbup/{id}",method = RequestMethod.PUT)
    public Result thumbup(@PathVariable String id){
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        String userid = "111";
        if (redisTemplate.opsForValue().get("thumbup:"+id+"user:"+userid) != null){
            return new Result(false,StatusCode.REPERROR,"不能重复点赞");
        }
        spitService.thumbup(id);
        redisTemplate.opsForValue().set("thumbup:"+id+"user:"+userid,1);
        return new Result(true,StatusCode.OK,"点赞成功");
    }
}
