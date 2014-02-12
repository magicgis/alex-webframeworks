package platform.framework.test;

import javax.sql.DataSource;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@ContextConfiguration(locations={
		"classpath:platform/framework/appctx-service-framework.xml",
		"classpath:platform/framework/appctx-dao-framework.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
public class ServiceConfig {
	
	@Autowired
	private DataSource dataSource;
	
	private JdbcTemplate jdbcTemplate;
	
	@Before
	public void beforeTest(){
		
		jdbcTemplate=new JdbcTemplate(dataSource);
		
		jdbcTemplate.update("insert into TESTTABLE values('D')");
	}
	
	@Test
	public void configTest(){
		
		Assert.assertEquals(jdbcTemplate.queryForList("select * from TESTTABLE").size(), 1);
				
	}	
	
}
