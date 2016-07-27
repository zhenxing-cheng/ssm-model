package com.tasly.gxx.service.impl;



import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.miemiedev.mybatis.paginator.domain.Order;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.tasly.gxx.cache.RedisCache;
import com.tasly.gxx.dao.IUserDao;
import com.tasly.gxx.domain.User;
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

	public PageList<User> findUserForPage(int curPageSize, int limit) {
		PageList<User> pageList=null;
		if(curPageSize!=0&&limit!=0){
			String cache_key=RedisCache.CAHCENAME+"|getUserList|"+curPageSize+"|"+limit;
			//先去缓存中取
			List<User> result_cache=cache.getListCache(cache_key, User.class);
			if(result_cache==null){
				//缓存中没有再去数据库取，并插入缓存（缓存时间为60秒）
				String sortString = "userid.asc";//如果你想排序的话逗号分隔可以排序多列  
				PageBounds pageBounds = new PageBounds(curPageSize, limit , Order.formString(sortString));  
				//获得结果集条总数  
				pageList = (PageList<User>)this.userDaoImpl.findUserByCondition(pageBounds);  
				System.out.println("totalCount: "+ pageList.getPaginator().getTotalCount());
				cache.putListCacheWithExpireTime(cache_key, pageList, RedisCache.CAHCETIME);
				LOG.info("put cache with key:"+cache_key);
			}else{
				pageList=new PageList(result_cache);			
				LOG.info("get cache with key:"+cache_key);
			}
		}
		return pageList;
	}

}
