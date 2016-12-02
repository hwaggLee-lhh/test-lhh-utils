package com.crawler.spider;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class SpiderUrlReader implements Runnable {
	URL site;
	String s;

	public SpiderUrlReader(URL u) {
		site = u;
		s = null;
	}

	public void run() {
		try {
			String ret = new String();
			URLConnection u = site.openConnection();
			String type = u.getContentType();
			if (type.indexOf("text") == -1 && type.indexOf("txt") == -1
					&& type.indexOf("HTM") == -1 && type.indexOf("htm") == -1) {
				// System.err.println("bad content type "+type+" at site"+site);
				System.out.println("bad content type " + type + " at site"
						+ site);
				ret = "Not html";
				return;
			}
			InputStream in = u.getInputStream();
			BufferedInputStream bufIn = new BufferedInputStream(in);
			int data;
			while (true) {
				data = bufIn.read();
				// Check for EOF
				if (data == -1)
					break;
				else
					ret += ((char) data);
			}
			s = ret;
		} catch (Exception e) {
			s = null;
		}
	}

	public String poll() {
		return s;
	}


}
