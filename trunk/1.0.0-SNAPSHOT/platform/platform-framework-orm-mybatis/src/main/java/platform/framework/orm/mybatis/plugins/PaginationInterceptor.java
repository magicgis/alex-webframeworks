package platform.framework.orm.mybatis.plugins;

import java.sql.Connection;
import java.util.Properties;

import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.factory.DefaultObjectFactory;
import org.apache.ibatis.reflection.factory.ObjectFactory;
import org.apache.ibatis.reflection.wrapper.DefaultObjectWrapperFactory;
import org.apache.ibatis.reflection.wrapper.ObjectWrapperFactory;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.RowBounds;

import platform.framework.orm.mybatis.Dialect;

@Intercepts({@Signature(type=StatementHandler.class,method="prepare",args={Connection.class})})
public class PaginationInterceptor implements Interceptor {

	public static final ObjectWrapperFactory DEFAULT_OBJECT_WRAPPER_FACTORY = new DefaultObjectWrapperFactory();
	
	public static final ObjectFactory DEFAULT_OBJECT_FACTORY = new DefaultObjectFactory();

	
	@Override public Object intercept(Invocation invocation) throws Throwable {
		
		 StatementHandler statementHandler = (StatementHandler)invocation.getTarget();

	     BoundSql boundSql = statementHandler.getBoundSql();

	     MetaObject metaStatementHandler = MetaObject.forObject(statementHandler,DEFAULT_OBJECT_FACTORY, DEFAULT_OBJECT_WRAPPER_FACTORY);

	     RowBounds rowBounds = (RowBounds)metaStatementHandler.getValue("delegate.rowBounds");
		
	     if (rowBounds.getLimit() > 0
	                && rowBounds.getLimit() < RowBounds.NO_ROW_LIMIT)
	        {
	           
	    	 	String dialectString = ((Configuration)metaStatementHandler.getValue("delegate.configuration")).getVariables().getProperty("dialect");
	    	 	
	    	 	if(dialectString == null)

	    	           throw new IllegalArgumentException("the value of the dialect property in configuration.xml is not defined ");


	    	 	String sql = ((Dialect)Class.forName(dialectString).newInstance()).getLimitString(boundSql.getSql(), rowBounds.getOffset(), rowBounds.getLimit());
	    	 	
	    	 	metaStatementHandler.setValue("delegate.boundSql.sql", sql );

	            metaStatementHandler.setValue("delegate.rowBounds.offset", RowBounds.NO_ROW_OFFSET );

	            metaStatementHandler.setValue("delegate.rowBounds.limit", RowBounds.NO_ROW_LIMIT );
	           
	        }
	     
	        return invocation.proceed();

	}

	@Override public Object plugin(Object target) {
		
		return Plugin.wrap(target, this);
	}

	@Override public void setProperties(Properties arg0) {
		
	}

}
