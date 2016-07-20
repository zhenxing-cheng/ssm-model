package com.tasly.gxx.service.impl;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.tasly.gxx.dao.IUserDao;
import com.tasly.gxx.domain.User;
import com.tasly.gxx.service.IUserService;

@Service("userService") 
public class UserServiceImpl implements IUserService {

	@Resource
    private IUserDao userDaoImpl;  
	
	public User getUserId(String id) {
		if(StringUtils.hasText(id)){
			return this.userDaoImpl.getEntityByUserId(Integer.parseInt(id));
		}
		return null;
	}

}
