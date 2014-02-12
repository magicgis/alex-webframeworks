package platform.framework.orm.mybatis;

public interface Dialect {

	 public  String getLimitString(String querySqlString,int pageNum,int pageSize);
	
}
