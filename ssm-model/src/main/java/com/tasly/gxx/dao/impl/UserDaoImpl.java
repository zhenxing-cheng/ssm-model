package com.tasly.gxx.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.tasly.gxx.client.IUserMapper;
import com.tasly.gxx.dao.IUserDao;
import com.tasly.gxx.domain.User;
import com.tasly.gxx.exception.BizException;

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
	
	public List<User> findUserByCondition(PageBounds pageBounds){  
	    Map<String, Object> params =new HashMap<String, Object>();  
	    return sqlSession.selectList(IUserMapper.class.getName()+".userListForPage", params, pageBounds);  
	}  
	
	public boolean delUserById(String userId) throws BizException{
		IUserMapper userMapper = sqlSession.getMapper(IUserMapper.class);
		int count=userMapper.deleteByPrimaryKey(Integer.parseInt(userId));
		
		if(count>0){
			return true;
		}
		
		return false;
	}

	@Override
	public boolean insertUser(String userName, String userPass) {
		IUserMapper userMapper = sqlSession.getMapper(IUserMapper.class);
		
		User user= new User();
		user.setUsername(userName);
		user.setPassword(userPass);
		
		int count=userMapper.insert(user);
		return count>0?true:false;
	}
}
