package com.tasly.gxx.service.impl;



import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.github.miemiedev.mybatis.paginator.domain.Order;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
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

	public PageList<User> findUserForPage(int curPageSize, int limit) {
		if(curPageSize!=0&&limit!=0){
			String sortString = "userid.asc";//如果你想排序的话逗号分隔可以排序多列  
			PageBounds pageBounds = new PageBounds(curPageSize, limit , Order.formString(sortString));  
			//获得结果集条总数  
			PageList<User> pageList = (PageList<User>)this.userDaoImpl.findUserByCondition(pageBounds);  
			System.out.println("totalCount: "+ pageList.getPaginator().getTotalCount());
			return pageList;
		}
		return null;
	}

}
