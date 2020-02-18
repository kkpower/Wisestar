package com.tensquare.user.web.controller;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import com.tensquare.user.po.Admin;
import com.tensquare.user.service.AdminService;
import util.JwtUtil;


/**
 * 控制器层
 * @author BoBoLaoShi
 *
 */
@RestController
@CrossOrigin
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private AdminService adminService;


	@Autowired
	private JwtUtil jwtUtil;

	@PostMapping(value = "/login")
	public Result login(@RequestBody Admin admin){
		Admin loginname = adminService.findAdminByNameAndPassword(admin);
		if (loginname == null){
			return new Result(false,StatusCode.ERROR,"登录失败");
		}
		Map<String,String> map = new HashMap<>(2);
		String token = jwtUtil.createJWT(loginname.getId(),loginname.getLoginname(),"admin");
		map.put("token",token);
		map.put("roles","admin");
		return new Result(true, StatusCode.OK,"登录成功",map);
	}
	
	/**
	 * 增加
	 * @param admin
	 */
	@PostMapping
	public Result add(@RequestBody Admin admin  ){
		adminService.saveAdmin(admin);
		return new Result(true, StatusCode.OK,"增加成功");
	}
	
	/**
	 * 修改
	 * @param admin
	 */
	@PutMapping("/{id}")
	public Result edit(@RequestBody Admin admin, @PathVariable String id ){
		admin.setId(id);
		adminService.updateAdmin(admin);		
		return new Result(true,StatusCode.OK,"修改成功");
	}
	
	/**
	 * 删除
	 * @param id
	 */
	@DeleteMapping("/{id}")
	public Result remove(@PathVariable String id ){
		adminService.deleteAdminById(id);
		return new Result(true,StatusCode.OK,"删除成功");
	}

	/**
	 * 查询全部数据
	 * @return
	 */
	@GetMapping
	public Result list(){
		return new Result(true,StatusCode.OK,"查询成功",adminService.findAdminList());
	}
	
	/**
	 * 根据ID查询
	 * @param id ID
	 * @return
	 */
	@GetMapping("/{id}")
	public Result listById(@PathVariable String id){
		return new Result(true,StatusCode.OK,"查询成功",adminService.findAdminById(id));
	}

	/**
     * 根据条件查询
     * @param searchMap
     * @return
     */
    @PostMapping("/search")
    public Result list( @RequestBody Map searchMap){
        return new Result(true,StatusCode.OK,"查询成功",adminService.findAdminList(searchMap));
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
		Page<Admin> pageResponse = adminService.findAdminListPage(searchMap, page, size);
		return  new Result(true,StatusCode.OK,"查询成功",  new PageResult<Admin>(pageResponse.getTotalElements(), pageResponse.getContent()) );
	}
	
}
