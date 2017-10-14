package com.joker.jcache;

import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.sql.DataSource;

import org.junit.Test;

import com.joker.jcache.bean.Bean;
import com.joker.jcache.bean.BeanContainer;
import com.joker.jcache.condition.Condition;
import com.joker.jcache.factory.CacheSingleton;
import com.joker.jcache.list.ListBeanContainer;
import com.joker.support.connection.DataSourceFactory;
import com.joker.support.connection.TransactionUtil;



/**
 * Unit test for simple App.
 */
public class AppTest {
//	@Test
//	public void test() {
//		//数据库操作
//	}
	
	@Test
	public void test() {
		DataSource dataSource = DruidDataSourceFactory.getDataSource();
		DataSourceFactory dataSourceFactory = DataSourceFactory.getInstance();
		dataSourceFactory.setDataSource(dataSource);
		//1
		User user = new User();
		user.setUserid(19);
		user.setPassword("345456768");
	}
	
	@Test
	public void testHashCode() {
		Integer a = 1;
		Integer b = 1;
		System.out.println(a.hashCode());
		System.out.println(b.hashCode());
	}
	
	
	@Test
	public void testsss() {
		DataSource dataSource = DruidDataSourceFactory.getDataSource();
		DataSourceFactory dataSourceFactory = DataSourceFactory.getInstance();
		dataSourceFactory.setDataSource(dataSource);
		TransactionUtil.startTransaction();
		CacheSingleton cache = CacheSingleton.getInstance();
		ListBeanContainer<User> listBean = cache.getListBeanContainer(User.class);
		UserDao userDao = new UserDaoImpl();
		CacheInvokeHandler handler = new CacheInvokeHandler();
		handler.setUserDao(userDao);
		UserDao userDaoProxy = (UserDao) Proxy.newProxyInstance(UserDaoImpl.class.getClassLoader(), userDao.getClass().getInterfaces(),handler );
		User user = new User();
		user.setPassword("82222");
		userDaoProxy.insert(user);
		
		
	}
	@Test
	public void select() {
		DataSource dataSource = DruidDataSourceFactory.getDataSource();
		DataSourceFactory dataSourceFactory = DataSourceFactory.getInstance();
		dataSourceFactory.setDataSource(dataSource);
		CacheSingleton cache = CacheSingleton.getInstance();
		ListBeanContainer<User> listBean = cache.getListBeanContainer(User.class);
		UserDao userDao = new UserDaoImpl();
		CacheInvokeHandler handler = new CacheInvokeHandler();
		handler.setUserDao(userDao);
		UserDao userDaoProxy = (UserDao) Proxy.newProxyInstance(UserDaoImpl.class.getClassLoader(), userDao.getClass().getInterfaces(),handler );
//		List<User> users = userDaoProxy.selectUserByPassword("123456");
		User user = new User();
		user.setPassword("123456");
		List<User> users = userDaoProxy.selectUserByUser(user);
		System.out.println(users);
	}
	
	@Test
	public void delete() {
		DataSource dataSource = DruidDataSourceFactory.getDataSource();
		DataSourceFactory dataSourceFactory = DataSourceFactory.getInstance();
		dataSourceFactory.setDataSource(dataSource);
		CacheSingleton cache = CacheSingleton.getInstance();
		ListBeanContainer<User> listBean = cache.getListBeanContainer(User.class);
		UserDao userDao = new UserDaoImpl();
		CacheInvokeHandler handler = new CacheInvokeHandler();
		handler.setUserDao(userDao);
		UserDao userDaoProxy = (UserDao) Proxy.newProxyInstance(UserDaoImpl.class.getClassLoader(), userDao.getClass().getInterfaces(),handler );
		userDaoProxy.delete(70);
	}
	
	@Test
	public void update() {
		DataSource dataSource = DruidDataSourceFactory.getDataSource();
		DataSourceFactory dataSourceFactory = DataSourceFactory.getInstance();
		dataSourceFactory.setDataSource(dataSource);
		CacheSingleton cache = CacheSingleton.getInstance();
		ListBeanContainer<User> listBean = cache.getListBeanContainer(User.class);
		UserDao userDao = new UserDaoImpl();
		CacheInvokeHandler handler = new CacheInvokeHandler();
		handler.setUserDao(userDao);
		UserDao userDaoProxy = (UserDao) Proxy.newProxyInstance(UserDaoImpl.class.getClassLoader(), userDao.getClass().getInterfaces(),handler );
		User user = new User();
		user.setUserid(70);
		userDaoProxy.update(user);
	}
	
	@Test
	public void testCache() {
		DataSource dataSource = DruidDataSourceFactory.getDataSource();
		DataSourceFactory dataSourceFactory = DataSourceFactory.getInstance();
		dataSourceFactory.setDataSource(dataSource);
		CacheSingleton cache = CacheSingleton.getInstance();
		ListBeanContainer<User> listBean = cache.getListBeanContainer(User.class);
		UserDao userDao = new UserDaoImpl();
		CacheInvokeHandler handler = new CacheInvokeHandler();
		handler.setUserDao(userDao);
		UserDao userDaoProxy = (UserDao) Proxy.newProxyInstance(UserDaoImpl.class.getClassLoader(), userDao.getClass().getInterfaces(),handler );
		
		System.out.println(userDaoProxy.selectAllUser());
		User user = new User();
		user.setUserid(4);
		user.setPassword("1578987");
		userDaoProxy.update(user);
		System.out.println(userDaoProxy.selectAllUser());
		System.out.println(userDaoProxy.selectAllUser());
		userDaoProxy.delete(46);
		System.out.println(userDaoProxy.selectAllUser());
		User user1 = new User();
		user1.setPassword("nihao");
		TransactionUtil.startTransaction();
		userDaoProxy.insert(user1);
		System.out.println(userDaoProxy.selectAllUser());
	}
	
	@Test
	public void testCacheThread() {
		DataSource dataSource = DruidDataSourceFactory.getDataSource();
		DataSourceFactory dataSourceFactory = DataSourceFactory.getInstance();
		dataSourceFactory.setDataSource(dataSource);
		CacheSingleton cache = CacheSingleton.getInstance();
		ListBeanContainer<User> listBean = cache.getListBeanContainer(User.class);
		UserDao userDao = new UserDaoImpl();
		CacheInvokeHandler handler = new CacheInvokeHandler();
		handler.setUserDao(userDao);
		UserDao userDaoProxy = (UserDao) Proxy.newProxyInstance(UserDaoImpl.class.getClassLoader(), userDao.getClass().getInterfaces(),handler );
		System.out.println(userDaoProxy.selectAllUser());
		ThreadPoolExecutor executor = new ThreadPoolExecutor(5, 10, 10, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>());
		int[] nums = { 1, 2, 3,4,5};
		int[] nums2 = { 4, 5, 8};
		int[] nums3 = { 80, 81, 82,83,84};
		Runnable runnable1 = new Runnable() {
			@Override
			public void run() {
				System.out.println("select:"+userDaoProxy.selectAllUser());
				
			}
		};
		
		Runnable runnable2 = new Runnable() {
			@Override
			public void run() {
				int j = (int) (Math.random() * nums2.length);
				User user = new User();
				user.setUserid(nums2[j]);
				System.out.println("update:"+userDaoProxy.update(user));
			}
		};
		
		Runnable runnable3 = new Runnable() {
			@Override
			public void run() {
				TransactionUtil.startTransaction();
				User user = new User();
				user.setPassword("nishishui");
				System.out.println("insert:"+userDaoProxy.insert(user));
			}
		};
		
		Runnable runnable4 = new Runnable() {
			@Override
			public void run() {
				int j = (int) (Math.random() * nums3.length);
				System.out.println("delete:"+userDaoProxy.delete(nums3[j]));
			}
		};
		
		Runnable runnable5 = new Runnable() {
			@Override
			public void run() {
				String[] psw = {"123456","98765","444444"};
				int j = (int) (Math.random() * psw.length);
				System.out.println("selectbyPassword:"+userDaoProxy.selectUserByPassword(psw[j]));
//				System.out.println("selectbyPassword:"+userDaoProxy.selectUserByPassword("123456"));
			}
		};
		Map<Integer,Runnable> map = new HashMap<>();
		map.put(1, runnable1);
		map.put(2, runnable2);
		map.put(3, runnable3);
		map.put(4, runnable4);
		map.put(5, runnable5);
		for (int i = 0 ; i < 10 ; i ++) {
			int j = (int) (Math.random() * nums.length);
			executor.execute(map.get(nums[j]));
//			executor.execute(runnable5);
//			executor.execute(runnable3);
		}
//		executor.execute(runnable5);
//		executor.execute(runnable3);
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("end:"+userDaoProxy.selectUserByPassword("123456"));
	}
	
	@Test
	public void testCacheTssss() {
		DataSource dataSource = DruidDataSourceFactory.getDataSource();
		DataSourceFactory dataSourceFactory = DataSourceFactory.getInstance();
		dataSourceFactory.setDataSource(dataSource);
		CacheSingleton cache = CacheSingleton.getInstance();
		ListBeanContainer<User> listBean = cache.getListBeanContainer(User.class);
		UserDao userDao = new UserDaoImpl();
		CacheInvokeHandler handler = new CacheInvokeHandler();
		handler.setUserDao(userDao);
		UserDao userDaoProxy = (UserDao) Proxy.newProxyInstance(UserDaoImpl.class.getClassLoader(), userDao.getClass().getInterfaces(),handler );
//		System.out.println(userDaoProxy.selectAllUser());
//		System.out.println("end:"+userDaoProxy.selectUserByPassword("123456"));
		System.out.println("end:"+userDaoProxy.selectUserByPassword("123456"));
		System.out.println(userDaoProxy.selectAllUser());
		User user = new User();
		user.setPassword("123456");
//		List<User> users = userDaoProxy.selectUserByUser(user);
		List<User> users2 = userDaoProxy.selectUserByUser(user);
	}
	
	@Test
	public void testHashCodess() {
		ThreadPoolExecutor executor = new ThreadPoolExecutor(5, 10, 10, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>());
		DataSource dataSource = DruidDataSourceFactory.getDataSource();
		DataSourceFactory dataSourceFactory = DataSourceFactory.getInstance();
		dataSourceFactory.setDataSource(dataSource);
		CacheSingleton cache = CacheSingleton.getInstance();
		ListBeanContainer<User> listBean = cache.getListBeanContainer(User.class);
		UserDao userDao = new UserDaoImpl();
//		CacheInvokeHandler handler = new CacheInvokeHandler();
//		handler.setUserDao(userDao);
//		UserDao userDaoProxy = (UserDao) Proxy.newProxyInstance(UserDaoImpl.class.getClassLoader(), userDao.getClass().getInterfaces(),handler );
		Runnable runnable3 = new Runnable() {
			@Override
			public void run() {
				System.out.println(1);
				TransactionUtil.startTransaction();
				User user = new User();
				user.setPassword("nishishui");
				System.out.println("insert:"+userDao.insert(user));
			}
		};
		for (int i =0;i<10;i++) {
			executor.execute(runnable3);
		}
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testsfdfgdfgfd() {
		CacheSingleton<User> cacheSingleton = CacheSingleton.getInstance();
		BeanContainer<User> beanContainer = cacheSingleton.getBeanContainer(User.class);
		User user = new User();
		Bean<User> bean = new Bean<>();
		bean.setBean(user);
		beanContainer.setBean(bean, user.getUserid());
	}
	
}
