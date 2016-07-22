package com.tasly.gxx.service.impl;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tasly.gxx.dao.IUserDao;
import com.tasly.gxx.domain.User;
import com.tasly.gxx.service.IUserService;

@Service("userService") 
public class UserServiceImpl implements IUserService {

	@Resource
    private IUserDao userDaoImpl;  
	
	public User getUserId(String id) {
		if(StringUtils.isNotEmpty(id)){
			return this.userDaoImpl.getEntityByUserId(Integer.parseInt(id));
		}
		return null;
	}

	public User findUserByLoginName(String loginName) {
		if(StringUtils.isNotEmpty(loginName)){
			return this.userDaoImpl.findUserByName(loginName);
		}
		return null;
	}

}
