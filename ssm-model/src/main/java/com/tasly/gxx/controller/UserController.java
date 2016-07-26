package com.tasly.gxx.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

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
    public String getUserInfo(HttpServletRequest request){  
		User currentUser = (User) SecurityUtils.getSubject()
				.getSession().getAttribute("currentUser");
        System.out.println("当前登录的用户为[" + currentUser.toString() + "]");  
        request.setAttribute("currUser", currentUser.getUsername());  
        request.setAttribute("selectItem", "user");
        return "/user/userInfo";  
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
}
