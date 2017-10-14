package com.joker.jcache.common;

public class Node {
	private String value;
	private Node node;
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public Node getNode() {
		return node;
	}
	public void setNode(Node node) {
		this.node = node;
	}
	@Override
	public String toString() {
		return "Node [value=" + value + ", node=" + node + "]";
	}
	
	
}
