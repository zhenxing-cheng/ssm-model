package com.tasly.gxx.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

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
        return "/user/userInfo";  
    }
	
	@RequestMapping(value="/listUser")  
    public String userList(HttpServletRequest request){  
		User currentUser = (User) SecurityUtils.getSubject()
				.getSession().getAttribute("currentUser");  
        System.out.println("当前登录的用户为[" + currentUser.toString() + "]");  
        request.setAttribute("currUser", currentUser.getUsername());  
        return "/user/listUser";  
    }
}
