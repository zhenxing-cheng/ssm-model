package com.tasly.gxx.dao.impl;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.tasly.gxx.client.IUserMapper;
import com.tasly.gxx.dao.IUserDao;
import com.tasly.gxx.domain.User;

@Repository
public class UserDaoImpl implements IUserDao {

	@Resource(name = "sqlSession")
	private SqlSession sqlSession;

	public User getEntityByUserId(Integer id) {
		IUserMapper userMapper = sqlSession.getMapper(IUserMapper.class);
		User user= userMapper.selectByPrimaryKey(id);
		return user;
	}

	public User findUserByName(String name) {
		IUserMapper userMapper = sqlSession.getMapper(IUserMapper.class);
		User user= userMapper.findUserByUserName(name);
		return user;
	}
	
}
