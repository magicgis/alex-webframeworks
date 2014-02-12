package platform.framework.orm.mybatis.dialect;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import platform.framework.orm.mybatis.Dialect;

public  class  SQLServer2005Dialect implements Dialect {

	private static final String SELECT = "select";
	
	private static final String FROM = "from";
	
	private static final String DISTINCT = "distinct";
	
	private static final Pattern ALIAS_PATTERN = Pattern.compile( "\\sas\\s[^,]+(,?)" );

	
	@Override public  String getLimitString(String querySqlString, int pageNum,
			int pageSize) {
		
		StringBuilder sb = new StringBuilder( querySqlString.trim().toLowerCase() );

		int orderByIndex = sb.indexOf( "order by" );
		CharSequence orderby = orderByIndex > 0 ? sb.subSequence( orderByIndex, sb.length() )
				: "ORDER BY CURRENT_TIMESTAMP";

		if ( orderByIndex > 0 ) {
			sb.delete( orderByIndex, orderByIndex + orderby.length() );
		}

		replaceDistinctWithGroupBy( sb );

		insertRowNumberFunction( sb, orderby );

		// Wrap the query within a with statement:
		sb.insert( 0, "WITH query AS (" ).append( ") SELECT * FROM query " );
		sb.append( "WHERE __vstone_row_nr__ >= ");
		sb.append(((pageNum-1)*pageSize)+1);
		sb.append( " AND __vstone_row_nr__ < " );
		sb.append((pageNum*pageSize)+1);

		return sb.toString();
	}
	
	
	private void replaceDistinctWithGroupBy(StringBuilder sql) {
		int distinctIndex = sql.indexOf( DISTINCT );
		int selectEndIndex = sql.indexOf( FROM );
		if (distinctIndex > 0 && distinctIndex < selectEndIndex) {
			sql.delete( distinctIndex, distinctIndex + DISTINCT.length() + 1 );
			sql.append( " group by" ).append( getSelectFieldsWithoutAliases( sql ) );
		}
	}
	
	private void insertRowNumberFunction(StringBuilder sql, CharSequence orderby) {
		
		int selectEndIndex = sql.indexOf( FROM );

		
		sql.insert( selectEndIndex - 1, ", ROW_NUMBER() OVER (" + orderby + ") as __vstone_row_nr__" );
	}
	
	private CharSequence getSelectFieldsWithoutAliases(StringBuilder sql) {
		String select = sql.substring( sql.indexOf( SELECT ) + SELECT.length(), sql.indexOf( FROM ) );

		return stripAliases( select );
	}
	
	private String stripAliases(String str) {
		Matcher matcher = ALIAS_PATTERN.matcher( str );
		return matcher.replaceAll( "$1" );
	}
	
	
	

}
