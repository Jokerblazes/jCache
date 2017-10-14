package com.joker.jcache.rule;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.joker.jcache.bean.Bean;
import com.joker.jcache.bean.BeanContainer;
import com.joker.jcache.common.Invocation;
import com.joker.jcache.common.ReflectionUtils;
import com.joker.jcache.condition.Condition;
import com.joker.jcache.factory.CacheSingleton;
import com.joker.jcache.list.ListBeanContainer;
import com.joker.support.connection.TransactionUtil;

/**
 * 插入规则
 * @author joker
 * {@link https://github.com/Jokerblazes/jcache.git}
 * @param <T>
 */
public class InsertRule<T> implements StrategyRule {
	
	private static final Logger logger = LoggerFactory.getLogger(InsertRule.class); 
	
	@Override
	public Object saveOrGetFromCache(Invocation invocation, CacheSingleton cacheSingleton, Condition condition) {
//		ListBeanContainer listBean = cacheSingleton.getListBeanContainer(invocation.getController().getClass());
		Object dao = invocation.getController();
		Method method = invocation.getMethod();
		BeanContainer<T> beanContainer = cacheSingleton.getBeanContainer(dao.getClass());
		ListBeanContainer<T> listBeanContainer = cacheSingleton.getListBeanContainer(dao.getClass());
		List<Condition> conditions = listBeanContainer.getAllCondition();
		Object[] args = invocation.getArgs();
		Object result = null;
		//执行数据库操作插入到数据库
		try {
			result = method.invoke(dao, args);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
		//执行sql语句查询刚刚插入的数据
		Object key = doSelectLastInsertId();
		logger.info("bean主键值 {}",key);
		int number = ReflectionUtils.getCacheObjectNumberFromMethod(method);
		if (number == -1) 
			throw new RuntimeException("未设置@CacheBean");
		ReflectionUtils.setObjectKey(key, args[number]);
		//放入到BeanContainer容器中
		Bean<T> bean = new Bean<T>();
		bean.setBean((T) args[number]);
		logger.info("开始将对象分类到对应条件链中！");
		for (Condition temp : conditions) {
			if (ReflectionUtils.compareComditionAndObject(args[number], temp)) {
				logger.info("符合条件 {}",temp);
				bean.setCondition(temp);
				beanContainer.setBean(bean,key);
				beanContainer.setResourceName(result.getClass().getName());
			}
		}
		TransactionUtil.commit();
		TransactionUtil.release();
		return result;
	}
	
	private int doSelectLastInsertId() {
		String sql = "SELECT LAST_INSERT_ID()";
		Connection conn = TransactionUtil.getConnection();
		PreparedStatement pst;
		try {
			pst = conn.prepareStatement(sql);
			ResultSet set = pst.executeQuery();
			if (set.next()) {
				return set.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
}
