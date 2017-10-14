# jcache

## 功能

`针对数据访问层进行缓存，以减少数据库操作。提供了当今流程的注解方式进行简单的缓存配置。`

## 特色

* 缓存广（增、删、改操作都直接操作内存对象，无需重新数据库获取对象）
* 用户使用方便（通过注解则可使用缓存）
* 灵活性高，框架同时提供了API可由用户手动缓存
* 查询高效，框架保存了条件对象链

## Demo

### 注解方式

```java
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
```

### API接口

例：缓存对象

```java
CacheSingleton<User> cacheSingleton = CacheSingleton.getInstance();
		BeanContainer<User> beanContainer = cacheSingleton.getBeanContainer(User.class);
		User user = new User();
		Bean<User> bean = new Bean<>();
		bean.setBean(user);
		beanContainer.setBean(bean, user.getUserid());
```



