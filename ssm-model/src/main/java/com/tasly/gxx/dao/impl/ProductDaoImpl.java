package com.tasly.gxx.dao.impl;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.tasly.gxx.dao.IProductDao;
import com.tasly.gxx.domain.Product;

@Service("productDao") 
public class ProductDaoImpl implements IProductDao {

	@Autowired
	private RedisTemplate<Serializable, Serializable> redisTemplate;

	public void save(final Product product) {
		redisTemplate.execute(new RedisCallback<Object>() {

			public Object doInRedis(RedisConnection conn)
					throws DataAccessException {
				conn.set(
						redisTemplate.getStringSerializer().serialize(
								"product.skuid."+ product.getSkuId()),
						redisTemplate.getStringSerializer().serialize(
								product.getSkuName()));
				return null;
			}

		});
	}

	public Product read(final String skuId) {
		return redisTemplate.execute(new RedisCallback<Product>() {

			public Product doInRedis(RedisConnection conn)
					throws DataAccessException {
				byte[] key=redisTemplate.getStringSerializer().serialize("product.skuid."+skuId);
				
				if(conn.exists(key)){
					byte[] value=conn.get(key);
					String skuName=redisTemplate.getStringSerializer().deserialize(value);
					
					Product product=new Product();
					product.setSkuId(skuId);
					product.setSkuName(skuName);
					
					return product;
				}
				
				return null;
			}
		});
	}

	public void delete(final String skuId) {
		redisTemplate.execute(new RedisCallback<Object>() {  
	        public Object doInRedis(RedisConnection connection) {  
	            connection.del(redisTemplate.getStringSerializer().serialize(  
	                    "product.skuid." + skuId));  
	            return null;  
	        }  
	    });  
	}

}
