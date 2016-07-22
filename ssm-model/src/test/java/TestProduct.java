import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.tasly.gxx.dao.IProductDao;
import com.tasly.gxx.domain.Product;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/applicationContext.xml" })
public class TestProduct {
	
	private static Logger logger = Logger.getLogger(TestProduct.class);
	@Resource
	private IProductDao productDao = null;
	
	@Test  
    public void crud() {  
        // -------------- Create ---------------  
        String skuId = "dddsss";  
        String skuName = "测试产品";  
        Product product = new Product();  
        product.setSkuId(skuId);
        product.setSkuName(skuName);
        productDao.save(product);  
  
        // ---------------Read ---------------  
        product = productDao.read(skuId);  
  
        assertEquals(skuName, product.getSkuName());  
  
        // --------------Update ------------  
        String skuName2= "测试产品二";  
        product.setSkuName(skuName2);
        
        productDao.save(product);  
  
        product = productDao.read(skuId);
  
        assertEquals(skuName2, product.getSkuName());  
  
        // --------------Delete ------------  
        productDao.delete(skuId);  
        product = productDao.read(skuId);  
        assertNull(product);  
    }  
}
