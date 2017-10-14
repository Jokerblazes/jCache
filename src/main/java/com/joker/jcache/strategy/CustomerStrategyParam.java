package com.joker.jcache.strategy;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.joker.jcache.common.Invocation;
import com.joker.jcache.common.Node;
import com.joker.jcache.common.ReflectionUtils;
import com.joker.jcache.common.Utils;
import com.joker.jcache.condition.Condition;
import com.joker.jcache.exception.NumberException;

/**
 * 查询自定义参数处理方式
 * @author joker
 * {@link https://github.com/Jokerblazes/jcache.git}
 */
public class CustomerStrategyParam implements StrategyParam {
	private static final Logger logger = LoggerFactory.getLogger(CustomerStrategyParam.class);
	@Override
	public Condition handleParamToCondition(Object[] args,Invocation invocation,String[] values) {
		Condition condition = new Condition();
		int number = ReflectionUtils.getCacheObjectNumberFromMethod(invocation.getMethod());
		if (number != -1) {
			Map<String,Node> nodeMap = new LinkedHashMap<>();
			for (String value : values) {
				handleValue(value,nodeMap);
			}
			logger.info("当前执行的入参是一个用户自定义对象 {}",nodeMap);
			//		Map<String,Object> valueMap = getParams(invocation, values);
			Object param = args[number];
			Object[] results = new Object[values.length];
			String[] names = new String[values.length];
			int i = 0;
			for (String key : nodeMap.keySet()) {
				Object object = null;
				Node parentNode = nodeMap.get(key);
				if (parentNode.getNode() != null) {
					Node childNode = parentNode.getNode();
					Method method = null;
					try {
						method = param.getClass().getMethod("get" + Utils.captureName(childNode.getValue()),null);
					} catch (NoSuchMethodException | SecurityException e) {
						e.printStackTrace();
					}
					try {
						object = method.invoke(param, null);
						results[i] = object;
						names[i] = childNode.getValue();
					} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
						e.printStackTrace();
					}
				}  else {
					results[i] = param;
					names[i] = parentNode.getValue();
				}
				condition.setArgs(results);
				condition.setArgNames(names);
			}
		} else {
			logger.info("当前入参是一个基本类型!");
			condition.setArgs(args);
			condition.setArgNames(values);
		}
		condition.setMethodName(invocation.getMethod().getName());
		logger.info("参数处理后 {}",condition);
		return condition;
	}

	//	private Map<String,Object> getParams(Invocation invocation,Object[] values) {
	//		Map<String,Object> map = new LinkedHashMap<String, Object>();
	//		Method method = invocation.getMethod();
	//		Class clazz = invocation.getController().getClass();
	//		int number = ReflectionUtils.getCacheObjectNumberFromMethod(method);
	////		String[] pns = Utils.getParameterNamesByAsm5(clazz, method);  
	//		for (int i = 0 ; i < pns.length ; i++) {
	//			map.put(pns[i], values[i]);
	//		}
	//		return map;
	//	}

	private void handleValue(String value,Map<String,Node> nodeMap) {
		String[] valueNodes = value.split("\\.");
		if (valueNodes.length > 2) 
			throw new NumberException("只允许一个.当前出现"+(valueNodes.length-1)+"个");
		Node node = null;
		//		if (valueNodes.length == 1) {
		//			node = new Node();
		//			node.setValue(value);
		//		} else {
		Node exitNode = nodeMap.get(valueNodes[0]);
		if (exitNode == null) {
			node = new Node();
			node.setValue(valueNodes[0]);
			nodeMap.put(valueNodes[0], node);

		} else 
			node = exitNode;
		Node chileNode = new Node();
		chileNode.setValue(valueNodes[1]);
		node.setNode(chileNode);
		//		}
	}


}
