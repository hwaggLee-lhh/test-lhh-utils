package com.crawler.spider;


public class SpiderNode {
	private Object data;
	private SpiderNode next;
	private SpiderNode prev;

	public SpiderNode(Object o) {
		data = o;
		prev = next = null;
	}

	public String toString() {
		if (next != null)
			return data.toString() + "\n" + next.toString();
		return data.toString();
	}

	public SpiderNode getNext() {
		return next;
	}

	public void setNext(SpiderNode n) {
		next = n;
	}

	public SpiderNode getPrev() {
		return prev;
	}

	public void setPrev(SpiderNode n) {
		prev = n;
	}

	public Object getData() {
		return data;
	}
}
