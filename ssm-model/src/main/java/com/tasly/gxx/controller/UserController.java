package com.tasly.gxx.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.ArrayUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.tasly.gxx.domain.User;
import com.tasly.gxx.service.IUserService;
import com.wordnik.swagger.annotations.ApiOperation;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Resource
	private IUserService userService;

	@RequestMapping("/showUser")
	public String toIndex(HttpServletRequest request, Model model) {
		String userId = request.getParameter("id");
		User user = this.userService.getUserId(userId);
		model.addAttribute("user", user);
		return "showUser";
	}
	
	@RequestMapping(value="/getUserInfo")  
	@ApiOperation(value="测试数据：获取用户列表", httpMethod ="POST", response=String.class, notes ="获取用户列表")  
    public String getUserInfo(HttpServletRequest request){  
		User currentUser = (User) SecurityUtils.getSubject()
				.getSession().getAttribute("currentUser");
        request.setAttribute("currUser", currentUser.getUsername());  
        request.setAttribute("selectItem", "user");
        return "/user/userList";  
    }
	
	@RequestMapping(value="/listUser",method = RequestMethod.GET)  
    public @ResponseBody List<User> userList(HttpServletRequest request,Model model,
			@RequestParam(required = false, value = "pageSize", defaultValue = "1") int curPageSize,
			@RequestParam(required = false, value = "pageNumber", defaultValue = "10") int limit){  

		PageList<User> userList=userService.findUserForPage(curPageSize,limit);
		if(!CollectionUtils.isEmpty(userList)){
			return userList;
		}
        return null;  
    }
	
	@ResponseBody
	@RequestMapping(value = "/delUser", method = RequestMethod.POST)
	public boolean delUser(@RequestParam("userList") final String[] userList) {
		boolean delResult=false;
		if(ArrayUtils.isNotEmpty(userList)){
			delResult=this.userService.delUserByArray(userList);
		}
		return delResult;
	}
	
	@ResponseBody
	@RequestMapping(value = "/addUser", method = RequestMethod.POST)
	public boolean addUser(@RequestParam("userName") final String userName,@RequestParam("userPass") final String userPass) {
		boolean addResult=this.userService.addUser(userName,userPass);
		return addResult;
	}
}
