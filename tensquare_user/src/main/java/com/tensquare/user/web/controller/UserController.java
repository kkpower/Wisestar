package com.tensquare.user.web.controller;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import com.tensquare.user.po.User;
import com.tensquare.user.service.UserService;
import util.JwtUtil;


/**
 * 控制器层
 * @author BoBoLaoShi
 *
 */
@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private RedisTemplate redisTemplate;

	@Autowired
	private JwtUtil jwtUtil;

	/**
	 * 更新好友粉丝数和用户关注数
	 * @param userid
	 * @param friendid
	 * @param x
	 */
	@RequestMapping(value = "/{userid}/{friendid}/{x}",method = RequestMethod.PUT)
	public void updatefanscountandfollowcount(@PathVariable String userid,@PathVariable String friendid,@PathVariable int x){
		userService.updatefanscountandfollowcount(userid,friendid,x);
	}
	@RequestMapping(value = "/login",method = RequestMethod.POST)
	public Result login(@RequestBody User user){
		user = userService.findUserByMobile(user.getMobile(),user.getPassword());
		if (user == null){
			return new Result(false,StatusCode.ERROR,"登录失败");
		}
		String token = jwtUtil.createJWT(user.getId(),user.getNickname(),"user");
		Map<String,String> map = new HashMap<>(2);
		map.put("roles","user");
		map.put("token",token);
		return new Result(true,StatusCode.OK,"登录成功",map);
	}

	/**
	 * 发送短信验证码
	 * @param mobile
	 * @return
	 */
	@RequestMapping(value = "/sendSms/{mobile}",method = RequestMethod.POST)
	public Result sendSms(@PathVariable String mobile){
		userService.sendSms(mobile);
		return new Result(true,StatusCode.OK,"发送成功");
	}

	@RequestMapping(value = "/register/{code}",method = RequestMethod.POST)
	public Result regist(@PathVariable String code,@RequestBody User user){
		String checkcode = (String) redisTemplate.opsForValue().get("checkcode:"+user.getMobile());
		if (checkcode.isEmpty()){
			return new Result(false,StatusCode.ERROR,"请先获取验证码");
		}
		if (!code.equals(checkcode)){
			return new Result(false,StatusCode.ERROR,"验证码错误");
		}
		userService.saveUser(user);
		return new Result(true, StatusCode.OK,"注册成功");
	}

	/**
	 * 增加
	 * @param user
	 */
	@PostMapping
	public Result add(@RequestBody User user  ){
		userService.saveUser(user);
		return new Result(true, StatusCode.OK,"增加成功");
	}
	
	/**
	 * 修改
	 * @param user
	 */
	@PutMapping("/{id}")
	public Result edit(@RequestBody User user, @PathVariable String id ){
		user.setId(id);
		userService.updateUser(user);		
		return new Result(true,StatusCode.OK,"修改成功");
	}
	
	/**
	 * 删除
	 * @param id
	 */
	@DeleteMapping("/{id}")
	public Result remove(@PathVariable String id ){
		userService.deleteUserById(id);
		return new Result(true,StatusCode.OK,"删除成功");
	}

	/**
	 * 查询全部数据
	 * @return
	 */
	@GetMapping
	public Result list(){
		return new Result(true,StatusCode.OK,"查询成功",userService.findUserList());
	}
	
	/**
	 * 根据ID查询
	 * @param id ID
	 * @return
	 */
	@GetMapping("/{id}")
	public Result listById(@PathVariable String id){
		return new Result(true,StatusCode.OK,"查询成功",userService.findUserById(id));
	}

	/**
     * 根据条件查询
     * @param searchMap
     * @return
     */
    @PostMapping("/search")
    public Result list( @RequestBody Map searchMap){
        return new Result(true,StatusCode.OK,"查询成功",userService.findUserList(searchMap));
    }

	/**
	 * 分页+多条件查询
	 * @param searchMap 查询条件封装
	 * @param page 页码
	 * @param size 页大小
	 * @return 分页结果
	 */
	@PostMapping("/search/{page}/{size}")
	public Result listPage(@RequestBody Map searchMap , @PathVariable int page, @PathVariable int size){
		Page<User> pageResponse = userService.findUserListPage(searchMap, page, size);
		return  new Result(true,StatusCode.OK,"查询成功",  new PageResult<User>(pageResponse.getTotalElements(), pageResponse.getContent()) );
	}
	
}
