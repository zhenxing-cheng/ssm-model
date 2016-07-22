package com.tasly.gxx.dao;

import org.springframework.stereotype.Repository;

import com.tasly.gxx.domain.User;

@Repository
public interface IUserDao {
    
	public User getEntityByUserId(Integer id);
	
	public User findUserByName(String name);
}