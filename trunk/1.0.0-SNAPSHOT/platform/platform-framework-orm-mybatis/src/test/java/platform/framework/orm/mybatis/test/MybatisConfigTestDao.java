package platform.framework.orm.mybatis.test;

import org.apache.ibatis.annotations.Select;


public interface MybatisConfigTestDao {

	@Select("select count(*) from dual")
	public int getDual();
	
}
