package com.tasly.gxx.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.tasly.gxx.domain.User;

@Repository
public interface IUserDao {
    
	public User getEntityByUserId(Integer id);
	
	public User findUserByName(String name);
	
	public List<User> findUserByCondition(PageBounds pageBounds);
}