package com.joker.jcache;

import java.util.List;

import com.joker.jcache.annoation.Cache;
import com.joker.jcache.annoation.Cache.OperateType;
import com.joker.jcache.annoation.CacheBean;

public interface UserDao {
	@Cache(operateType=OperateType.INSERT)
	int insert(@CacheBean User user);
	
	@Cache(operateType=OperateType.UPDATE)
	int update(@CacheBean User user);
	
	@Cache(operateType=OperateType.DELETE)
	int delete(@CacheBean int userId);
	
	@Cache(operateType=OperateType.SELECT,value= {"password"})
	List<User> selectUserByPassword(String password);
	
	@Cache(value = {"user.password"},operateType=OperateType.SELECT)
	List<User> selectUserByUser(@CacheBean(value="user") User user);
	
	@Cache(operateType=OperateType.SELECT)
	List<User> selectAllUser();
}
