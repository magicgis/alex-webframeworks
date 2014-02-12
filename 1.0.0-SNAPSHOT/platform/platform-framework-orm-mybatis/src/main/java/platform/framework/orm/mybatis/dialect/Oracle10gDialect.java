package platform.framework.orm.mybatis.dialect;

import platform.framework.orm.mybatis.Dialect;

public class Oracle10gDialect implements Dialect {

	@Override public String getLimitString(String querySqlString, int pageNum,
			int pageSize) {
		
		querySqlString = querySqlString.trim();
		String forUpdateClause = null;
		boolean isForUpdate = false;
		final int forUpdateIndex = querySqlString.toLowerCase().lastIndexOf( "for update") ;
		if ( forUpdateIndex > -1 ) {
			// save 'for update ...' and then remove it
			forUpdateClause = querySqlString.substring( forUpdateIndex );
			querySqlString = querySqlString.substring( 0, forUpdateIndex-1 );
			isForUpdate = true;
		}

		StringBuilder pagingSelect = new StringBuilder( querySqlString.length() + 100 );
		
		pagingSelect.append("select * from ( select row_.*, rownum rownum_ from ( ");
		
		pagingSelect.append(querySqlString);
		
		pagingSelect.append(" ) row_ where rownum <= ");
		
		pagingSelect.append(pageNum*pageSize);
		
		pagingSelect.append(") where rownum_ > ");
		
		pagingSelect.append((pageNum-1)*pageSize);
		

		if ( isForUpdate ) {
			pagingSelect.append( " " );
			pagingSelect.append( forUpdateClause );
		}

		return pagingSelect.toString();
	}
	
	public static void main(String[] args) {
		
		Dialect dialect=new Oracle10gDialect();
		
		
		System.out.println(dialect.getLimitString("select * from absapool", 2, 1));
		
	}

}
