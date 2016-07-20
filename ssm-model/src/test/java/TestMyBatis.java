import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.tasly.gxx.domain.User;
import com.tasly.gxx.service.IUserService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/applicationContext.xml" })
public class TestMyBatis {

	private static Logger logger = Logger.getLogger(TestMyBatis.class);
	@Resource
	private IUserService userService = null;

	@Test
	public void test1() {
		User user = userService.getUserId("1");
		 System.out.println(user.getUserName());
		 logger.info("Öµ£º"+user.getUserName());
//		logger.info(JSON.toJSONString(user));
	}

}
