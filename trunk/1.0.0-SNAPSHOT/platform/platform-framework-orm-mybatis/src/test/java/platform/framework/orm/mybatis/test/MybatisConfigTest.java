package platform.framework.orm.mybatis.test;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration(locations={
		"classpath:platform/framework/appctx-dao-framework.xml",
		"classpath:platform/framework/appctx-orm-mybatis-framework.xml",
		"classpath:platform/framework/orm/mybatis/test/mybatisTestConfig.xml",		
		"classpath:platform/framework/appctx-service-framework.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
public class MybatisConfigTest {

	@Autowired
	private MybatisConfigTestDao mybatisConfigTest;
	
	@Test
	public void configTest(){
		
		Assert.assertEquals(mybatisConfigTest.getDual(),1);
		
	}
	
}
