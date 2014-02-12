package com.bigtail.platform.framework.orm.dao;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration(locations={
		"classpath:platform/framework/appctx-dao-framework.xml",
		"classpath:platform/framework/appctx-jpa-framework.xml",
		"classpath:platform/framework/appctx-service-framework.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
public class RunTest {

	@Autowired
	private TestCURDDao testCURDDao;
	
	@Test
	public void run() {
		
		Assert.assertEquals(testCURDDao.count(),1);
			
	}
	
}
