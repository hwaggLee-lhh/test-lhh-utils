package com.crawler.spider;

import java.util.Vector;

public class SpiderHashtable {
	private Vector table;
	private int size;

	public SpiderHashtable() {
		size = 991;
		table = new Vector();
		for (int i = 0; i < size; i++) {
			table.add(new SpiderLinkedlist());
		}
	}

	public void insert(Object o) {
		int index = o.hashCode();
		index = index % size;
		if (index < 0)
			index += size;
		SpiderLinkedlist ol = (SpiderLinkedlist) table.get(index);
		ol.insert(o);
	}

	public boolean contains(Object o) {
		int index = o.hashCode();
		index = index % size;
		if (index < 0)
			index += size;
		return ((SpiderLinkedlist) (table.get(index))).contains(o);
	}

	public String toString() {
		String ret = "";
		for (int i = 0; i < size; i++) {
			if (!((SpiderLinkedlist) (table.get(i))).isEmpty()) {
				ret += "\n";
				ret += table.get(i).toString();
			}
		}
		return ret;
	}
}
