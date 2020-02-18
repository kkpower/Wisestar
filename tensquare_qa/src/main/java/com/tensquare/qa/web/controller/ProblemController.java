package com.tensquare.qa.web.controller;
import java.util.List;
import java.util.Map;

import com.tensquare.qa.client.BaseClient;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import com.tensquare.qa.po.Problem;
import com.tensquare.qa.service.ProblemService;

import javax.servlet.http.HttpServletRequest;


/**
 * 控制器层
 * @author BoBoLaoShi
 *
 */
@RestController
@CrossOrigin
@RequestMapping("/problem")
public class ProblemController {

	@Autowired
	private ProblemService problemService;

	@Autowired
	private HttpServletRequest request;

	@Autowired
	private BaseClient baseClient;

	@RequestMapping(value = "/label/{labelId}",method = RequestMethod.GET)
	public Result findLabelById(@PathVariable String labelId){
		Result result = baseClient.findById(labelId);
		return result;
	}
	@RequestMapping(value = "/newlist/{labelid}/{page}/{size}",method = RequestMethod.GET)
	public Result newlist(@PathVariable String labelid,@PathVariable int page,@PathVariable int size){
		Page<Problem> problemPage = problemService.newlist(labelid,page,size);
		return new Result(true,StatusCode.OK,"查询成功",new PageResult<Problem>(problemPage.getTotalElements(),problemPage.getContent()));
	}

	@RequestMapping(value = "/hotlist/{labelid}/{page}/{size}",method = RequestMethod.GET)
	public Result hotlist(@PathVariable String labelid,@PathVariable int page,@PathVariable int size){
		Page<Problem> problemPage = problemService.hotlist(labelid,page,size);
		return new Result(true,StatusCode.OK,"查询成功",new PageResult<Problem>(problemPage.getTotalElements(),problemPage.getContent()));
	}

	@RequestMapping(value = "/waitlist/{labelid}/{page}/{size}",method = RequestMethod.GET)
	public Result waitlist(@PathVariable String labelid,@PathVariable int page,@PathVariable int size){
		Page<Problem> problemPage = problemService.waitlist(labelid,page,size);
		return new Result(true,StatusCode.OK,"查询成功",new PageResult<Problem>(problemPage.getTotalElements(),problemPage.getContent()));
	}
	
	/**
	 * 增加
	 * @param problem
	 */
	@PostMapping
	public Result add(@RequestBody Problem problem  ){
		String token = (String) request.getAttribute("claims_user");
		if (StringUtils.isBlank(token)){
			return new Result(false,StatusCode.ACCESSERROR,"权限不足");
		}
		problemService.saveProblem(problem);
		return new Result(true, StatusCode.OK,"增加成功");
	}
	
	/**
	 * 修改
	 * @param problem
	 */
	@PutMapping("/{id}")
	public Result edit(@RequestBody Problem problem, @PathVariable String id ){
		problem.setId(id);
		problemService.updateProblem(problem);		
		return new Result(true,StatusCode.OK,"修改成功");
	}
	
	/**
	 * 删除
	 * @param id
	 */
	@DeleteMapping("/{id}")
	public Result remove(@PathVariable String id ){
		problemService.deleteProblemById(id);
		return new Result(true,StatusCode.OK,"删除成功");
	}

	/**
	 * 查询全部数据
	 * @return
	 */
	@GetMapping
	public Result list(){
		return new Result(true,StatusCode.OK,"查询成功",problemService.findProblemList());
	}
	
	/**
	 * 根据ID查询
	 * @param id ID
	 * @return
	 */
	@GetMapping("/{id}")
	public Result listById(@PathVariable String id){
		return new Result(true,StatusCode.OK,"查询成功",problemService.findProblemById(id));
	}

	/**
     * 根据条件查询
     * @param searchMap
     * @return
     */
    @PostMapping("/search")
    public Result list( @RequestBody Map searchMap){
        return new Result(true,StatusCode.OK,"查询成功",problemService.findProblemList(searchMap));
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
		Page<Problem> pageResponse = problemService.findProblemListPage(searchMap, page, size);
		return  new Result(true,StatusCode.OK,"查询成功",  new PageResult<Problem>(pageResponse.getTotalElements(), pageResponse.getContent()) );
	}
	
}
