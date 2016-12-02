package com.crawler.spider;



public class SpiderList {
	protected SpiderNode tail;
	protected SpiderNode ptr;
	private boolean stop;

	public SpiderList() {
		ptr = tail = null;
		stop = false;
	}

	public boolean isEmpty() {
		return tail == null;
	}

	public void reset() {
		stop = false;
		ptr = tail;
	}

	public String toString() {
		if (tail == null)
			return "Empty list";
		String ret = "";
		for (SpiderNode n = tail.getNext(); n != tail; n = n.getNext())
			ret += n.getData().toString() + "\n";
		ret += tail.getData().toString();
		return ret;
	}

	public Object get() {
		if (ptr == null)
			return null;
		ptr = ptr.getNext();
		if (ptr == tail.getNext()) {
			if (stop)
				return null;
			stop = true;
			return tail.getNext().getData();
		}
		return ptr.getData();
	}

	public void insert(Object o, boolean attail) {
		SpiderNode nn = new SpiderNode(o);
		if (tail == null) {
			nn.setNext(nn);
			nn.setPrev(nn);
			ptr = tail = nn;
			return;
		}
		if (attail) {
			tail.getNext().setPrev(nn);
			nn.setNext(tail.getNext());
			tail.setNext(nn);
			nn.setPrev(tail);
			tail = nn;
		} else {
			nn.setNext(tail.getNext());
			nn.setPrev(tail);
			tail.setNext(nn);
			nn.getNext().setPrev(nn);
		}
	}

	public void insert(Object o) {
	}
}
