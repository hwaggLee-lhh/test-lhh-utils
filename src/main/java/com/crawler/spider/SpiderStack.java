package com.crawler.spider;


public class SpiderStack  extends SpiderList {
	public SpiderStack() {
		super();
	}

	public void insert(Object o) {
		insert(o, false);
	}

}
