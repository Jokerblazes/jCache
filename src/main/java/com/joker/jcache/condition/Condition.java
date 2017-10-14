package com.joker.jcache.condition;

import java.util.Arrays;

public class Condition {
	private Object[] args;
	private String methodName;
	private String[] argNames;
	
	@Override
	public boolean equals(Object obj) {
		Condition otherCondition = (Condition) obj;
		Object[] otherObjects = otherCondition.getArgs();
		if (!methodName.equals(otherCondition.getMethodName()))
			return false;
		if ((otherObjects == null && this.args != null) || (args == null && otherObjects != null))
			return false;
		if (otherCondition != null && this.args != null) {
			if (otherObjects.length != this.args.length && !methodName.equals(otherCondition.getMethodName())) 
				return false;
			for (int i = 0 ; i < this.args.length ; i++) 
				if (!otherObjects[i].equals(this.args[i])) 
					return false;
		}
		return true;
	}
	
	@Override
	public int hashCode() {
		int sum = methodName.hashCode();
		if (args == null)
			return sum;
		for (Object object : args) {
			sum += object.hashCode();
		}
		return sum;
	}


	public Object[] getArgs() {
		return args;
	}

	public void setArgs(Object[] args) {
		this.args = args;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public String[] getArgNames() {
		return argNames;
	}

	public void setArgNames(String[] argNames) {
		this.argNames = argNames;
	}

	@Override
	public String toString() {
		return "Condition [args=" + Arrays.toString(args) + ", methodName=" + methodName + ", argNames="
				+ Arrays.toString(argNames) + "]";
	}
	
	
	
	
	
}
