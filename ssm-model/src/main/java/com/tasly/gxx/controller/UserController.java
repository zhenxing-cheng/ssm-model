package com.tasly.gxx.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

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
}
