package com.tasly.gxx.service.impl;



import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.miemiedev.mybatis.paginator.domain.Order;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.tasly.gxx.cache.RedisCache;
import com.tasly.gxx.dao.IUserDao;
import com.tasly.gxx.domain.User;
import com.tasly.gxx.enums.ResultEnum;
import com.tasly.gxx.exception.BizException;
import com.tasly.gxx.service.IUserService;

@Service("userService") 
public class UserServiceImpl implements IUserService {
	private final Logger LOG = LoggerFactory.getLogger(this.getClass());
	@Autowired
    private IUserDao userDaoImpl;  
	
	@Autowired
	private RedisCache cache;
	
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
	
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public boolean delUserByArray(String[] userIds){
		boolean isDel=true;
		try{
			for(String userId:userIds){
				boolean currentDelStatus=this.userDaoImpl.delUserById(userId);
				if(currentDelStatus==false){
					isDel=false;
				}
			}
		}catch(BizException e){
			LOG.error(ResultEnum.INNER_ERROR.getMsg()+":删除用户出错",e.getMessage());
			isDel=false;
		}finally{
			if(isDel){
				cache.deleteCacheWithPattern(RedisCache.CAHCENAME + "|getUserList|*");
			}
			return isDel;
		}
	}
	
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public PageList<User> findUserForPage(int curPageSize, int limit) {
		PageList<User> pageList=null;
		if(curPageSize!=0&&limit!=0){
			String cache_key=RedisCache.CAHCENAME+"|getUserList|"+curPageSize+"|"+limit;
			
			List<User> result_cache=cache.getListCache(cache_key, User.class);
			if(result_cache==null){

				String sortString = "userid.asc";
				PageBounds pageBounds = new PageBounds(curPageSize, limit , Order.formString(sortString));  

				pageList = (PageList<User>)this.userDaoImpl.findUserByCondition(pageBounds);  
//				System.out.println("totalCount: "+ pageList.getPaginator().getTotalCount());
				cache.putListCacheWithExpireTime(cache_key, pageList, RedisCache.CAHCETIME);
				LOG.info("put cache with key:"+cache_key);
			}else{
				pageList=new PageList(result_cache);			
				LOG.info("get cache with key:"+cache_key);
			}
		}
		return pageList;
	}

	@Override
	public boolean addUser(String userName, String userPass) {
		boolean isAdd=false;
		try{
			if(StringUtils.isNotEmpty(userName)&&StringUtils.isNotEmpty(userPass)){
				isAdd=this.userDaoImpl.insertUser(userName,userPass);
			}
		}catch(BizException e){
			LOG.error(ResultEnum.INNER_ERROR.getMsg()+":新增用户出错",e.getMessage());
			isAdd=false;
		}finally{
			if(isAdd){
				cache.deleteCacheWithPattern(RedisCache.CAHCENAME + "|getUserList|*");
			}
			return isAdd;
		}
	}

}
