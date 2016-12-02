package com.crawler.spider;


public class SpiderLinkedlist {
	SpiderNode head;
	SpiderNode tail;

	public SpiderLinkedlist() {
		tail = head = null;
	}

	public String toString() {
		if (head == null)
			return "Empty list";
		return head.toString();
	}

	public void insert(Object o) {
		if (tail == null) {
			head = tail = new SpiderNode(o);
		} else {
			SpiderNode nn = new SpiderNode(o);
			tail.setNext(nn);
			tail = nn;
		}
	}

	public boolean contains(Object o) {
		for (SpiderNode n = head; n != null; n = n.getNext()) {
			if (o.equals(n.getData()))
				return true;
		}
		return false;
	}

	public Object pop() {
		if (head == null)
			return null;
		Object ret = head.getData();
		head = head.getNext();
		if (head == null)
			tail = null;
		return ret;
	}

	public boolean isEmpty() {
		return head == null;
	}
}
