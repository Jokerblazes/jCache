package com.joker.jcache.factory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.joker.jcache.bean.BeanContainer;
import com.joker.jcache.list.ListBeanContainer;

/**
 * 总的容器对象-单例模式
 * @author joker
 * {@link https://github.com/Jokerblazes/jcache.git}
 * @param <T>
 */
public class CacheSingleton<T> {
	private static final Logger logger = LoggerFactory.getLogger(CacheSingleton.class); 
	public static CacheSingleton cacheSingleton = new CacheSingleton();
	private CacheSingleton() {}
	@SuppressWarnings("rawtypes")
	private static Map<String,ListBeanContainer> listMap = new ConcurrentHashMap<String,ListBeanContainer>();
	private static Map<String,BeanContainer> beanMap = new ConcurrentHashMap<String,BeanContainer>();
	
	/**
	 * 获取单例对象
	 * @return
	 * @author joker
	 * {@link https://github.com/Jokerblazes/jcache.git}
	 */
	public static CacheSingleton getInstance() {
		logger.info("获取CacheSingleton对象！");
		return cacheSingleton;
	}
	
//	private Map<String,>
	
	/**
	 * 获取ListBeanContainer容器
	 * @param beanClass
	 * @return
	 * @author joker
	 * {@link https://github.com/Jokerblazes/jcache.git}
	 */
	public ListBeanContainer<T> getListBeanContainer(Class beanClass) {
		@SuppressWarnings("unchecked")
		ListBeanContainer<T> listContainer = listMap.get(beanClass.getName());
		if (listContainer == null) {
			logger.info("{} 对应的ListBeanContainer对象不存在！",beanClass.getName());
			listContainer = new ListBeanContainer<>();
			listMap.put(beanClass.getName(), listContainer);
		}
		logger.info("返回ListBeanContainer,资源名{}",beanClass.getName());
		return listContainer;
	}
	
	/**
	 * 获取bean容器
	 * @param beanClass
	 * @return
	 * @author joker
	 * {@link https://github.com/Jokerblazes/jcache.git}
	 */
	public BeanContainer<T> getBeanContainer(Class beanClass) {
		@SuppressWarnings("unchecked")
		BeanContainer<T> beanContainer = beanMap.get(beanClass.getName());
		if (beanContainer == null) {
			logger.info("{} 对应的BeanContainer对象不存在！",beanClass.getName());
			beanContainer = new BeanContainer<T>();
//			ListBeanContainer<T> listBeanContainer = new ListBeanContainer<T>();
//			listMap.put(beanClass.getName(), listBeanContainer);
//			beanContainer.addObserver(listBeanContainer);
			beanMap.put(beanClass.getName(), beanContainer);
		}
		logger.info("返回BeanContainer,资源名{}",beanClass.getName());
		return beanContainer;
	}
	
	
	
}
