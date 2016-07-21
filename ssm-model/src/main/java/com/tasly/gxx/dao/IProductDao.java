package com.tasly.gxx.dao;

import com.tasly.gxx.domain.Product;

public interface IProductDao {
	 /** 
     * @param uid 
     * @param address 
     */  
    void save(Product product);  
  
    /** 
     * @param uid 
     * @return 
     */  
    Product read(String skuId);  
  
    /** 
     * @param uid 
     */  
    void delete(String skuId);  
}
