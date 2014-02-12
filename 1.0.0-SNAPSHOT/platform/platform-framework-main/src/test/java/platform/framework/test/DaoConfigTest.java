package platform.framework.test;

import java.sql.SQLException;

import javax.sql.DataSource;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration(locations={
		"classpath:platform/framework/appctx-dao-framework.xml"
		})
@RunWith(SpringJUnit4ClassRunner.class)
public class DaoConfigTest {

	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private DataSourceTransactionManager txManager;
	
	@Test
	public void configTest() throws SQLException{
		
		Assert.assertNotNull(dataSource);
		Assert.assertNotNull(txManager);
		
		Assert.assertNotNull(dataSource.getConnection());
		Assert.assertNotNull(txManager.getDataSource());
		
		JdbcTemplate jdbcTemplate=new JdbcTemplate(dataSource);
		
		Assert.assertEquals(jdbcTemplate.queryForList("select * from dual").size(),1);
		
	}
	
}
