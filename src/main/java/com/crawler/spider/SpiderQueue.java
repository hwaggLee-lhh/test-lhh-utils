package com.crawler.spider;



public class SpiderQueue extends SpiderList {
	public SpiderQueue() {
		super();
	}

	public void insert(Object o) {
		insert(o, true);
	}

	public String peek() {
		if (tail == null)
			return "";
		return tail.getNext().getData().toString();
	}

	public Object pop() {
		if (tail == null)
			return null;
		Object ret = tail.getNext().getData();
		if (tail.getNext() == tail) {
			tail = ptr = null;
		} else {
			if (tail.getNext() == ptr)
				ptr = ptr.getNext();
			tail.setNext(tail.getNext().getNext());
		}
		return ret;
	}

}
