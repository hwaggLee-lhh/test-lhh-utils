package com.crawler.spider;

import java.net.URL;

public class Spider implements Runnable {

	public SpiderQueue todo;
	public SpiderStack done;
	public SpiderStack errors;
	public SpiderStack omittions;
	private SpiderHashtable allsites;
	private String last = "";
	int maxsites;
	int visitedsites;
	int TIMEOUT;
	String base;
	String[] badEndings2 = { "ps", "gz" };
	String[] badEndings3 = { "pdf", "txt", "zip", "jpg", "mpg", "gif", "mov",
			"tut", "req", "abs", "swf", "tex", "dvi", "bin", "exe", "rpm" };
	String[] badEndings4 = { "jpeg", "mpeg" };

	public Spider(String starturl, int max, String b) {
		TIMEOUT = 5000;
		base = b;
		allsites = new SpiderHashtable();
		todo = new SpiderQueue();
		done = new SpiderStack();
		errors = new SpiderStack();
		omittions = new SpiderStack();
		try {
			URL u = new URL(starturl);
			todo.insert(u);
		} catch (Exception e) {
			System.out.println(e);
			errors.insert("bad starting url " + starturl + "," + e.toString());
		}
		maxsites = max;
		visitedsites = 0;
	}

	/*
	 * how many millisec to wait for each page
	 */
	public void setTimer(int amount) {
		TIMEOUT = amount;
	}

	/*
	 * strips the '#' anchor off a url
	 */
	private URL stripRef(URL u) {
		try {
			return new URL(u.getProtocol(), u.getHost(), u.getPort(),
					u.getFile());
		} catch (Exception e) {
			return u;
		}
	}

	/*
	 * adds a url for future processing
	 */
	public void addSite(URL toadd) {
		if (null != toadd.getRef())
			toadd = stripRef(toadd);
		if (!allsites.contains(toadd)) {
			allsites.insert(toadd);
			if (!toadd.toString().startsWith(base)) {
				omittions.insert("foreign URL: " + toadd.toString());
				return;
			}
			if (!toadd.toString().startsWith("http")
					&& !toadd.toString().startsWith("HTTP")) {
				omittions.insert("ignoring URL: " + toadd.toString());
				return;
			}

			String s = toadd.getFile();
			String last = "";
			String[] comp = {};
			if (s.charAt(s.length() - 3) == '.') {
				last = s.substring(s.length() - 2);
				comp = badEndings2;
			} else if (s.charAt(s.length() - 4) == '.') {
				last = s.substring(s.length() - 3);
				comp = badEndings3;
			} else if (s.charAt(s.length() - 5) == '.') {
				last = s.substring(s.length() - 4);
				comp = badEndings4;
			}
			for (int i = 0; i < comp.length; i++) {
				if (last.equalsIgnoreCase(comp[i])) {// loop through all bad
														// extensions
					omittions.insert("ignoring URL:" + toadd.toString());
					return;
				}
			}

			todo.insert(toadd);
		}
	}

	/*
	 * true if there are pending urls and the maximum hasn't beenreached
	 */
	public boolean hasMore() {
		return !todo.isEmpty() && visitedsites < maxsites;
	}

	/*
	 * returns the next site, works like enumeration, will return newvalues each
	 * time
	 */
	private URL getNextSite() {
		last = todo.peek();
		visitedsites++;
		return (URL) todo.pop();
	}

	/*
	 * Just to see what we are doing now...
	 */
	public String getCurrent() {
		return last;
	}

	/*
	 * process the next site
	 */
	public void doNextSite() {
		URL current = getNextSite();
		if (current == null)
			return;
		try {
			// System.err.println("Processing #"+visitedsites+":"+current);
			parse(current);
			done.insert(current);
		} catch (Exception e) {
			errors.insert("Bad site: " + current.toString() + ","
					+ e.toString());
		}
	}

	public void run() {
		while (hasMore())
			doNextSite();
	}

	/*
	 * to print out the internal data structures
	 */
	public String toString() {
		return getCompleted() + getErrors();
	}

	private String getErrors() {
		if (errors.isEmpty())
			return "No errors\n";
		else
			return "Errors:\n" + errors.toString() + "\nEnd oferrors\n";
	}

	private String getCompleted() {
		return "Completed Sites:\n" + done.toString()
				+ "\nEnd of completedsites\n";
	}

	/*
	 * Parses a web page at (site) and adds all the urls it sees
	 */
	private void parse(URL site) throws Exception {
		String source = getText(site);
		String title = getTitle(source);
		if (title.indexOf("404") != -1 || title.indexOf("Error") != -1
				|| title.indexOf("Not Found") != -1) {
			throw new Exception(("404, Not Found: " + site));
		}
		int loc, beg;
		boolean hasLT = false;
		boolean hasSp = false;
		boolean hasF = false;
		boolean hasR = false;
		boolean hasA = false;
		boolean hasM = false;
		boolean hasE = false;
		for (loc = 0; loc < source.length(); loc++) {
			char c = source.charAt(loc);
			if (!hasLT) {
				hasLT = (c == '<');
			}

			// search for "<a "
			else if (hasLT && !hasA && !hasF) {
				if (c == 'a' || c == 'A')
					hasA = true;
				else if (c == 'f' || c == 'F')
					hasF = true;
				else
					hasLT = false;
			} else if (hasLT && hasA && !hasF && !hasSp) {
				if (c == ' ' || c == '\t' || c == '\n')
					hasSp = true;
				else
					hasLT = hasA = false;
			}

			// search for "<frame "
			else if (hasLT && hasF && !hasA && !hasR) {
				if (c == 'r' || c == 'R')
					hasR = true;
				else
					hasLT = hasF = false;
			} else if (hasLT && hasF && hasR && !hasA) {
				if (c == 'a' || c == 'A')
					hasA = true;
				else
					hasLT = hasF = hasR = false;
			} else if (hasLT && hasF && hasR && hasA && !hasM) {
				if (c == 'm' || c == 'M')
					hasM = true;
				else
					hasLT = hasF = hasR = hasA = false;
			} else if (hasLT && hasF && hasR && hasA && hasM && !hasE) {
				if (c == 'e' || c == 'E')
					hasE = true;
				else
					hasLT = hasF = hasR = hasA = hasM = false;
			} else if (hasLT && hasF && hasR && hasA && hasM && hasE && !hasSp) {
				if (c == ' ' || c == '\t' || c == '\n')
					hasSp = true;
				else
					hasLT = hasF = hasR = hasA = hasM = hasE = false;
			}

			// found "<frame "
			else if (hasLT && hasF && hasR && hasA && hasM && hasE && hasSp) {
				hasLT = hasF = hasR = hasA = hasM = hasE = hasSp = false;
				beg = loc;
				loc = source.indexOf(">", loc);
				if (loc == -1) {
					errors.insert("malformed frame at" + site.toString());
					loc = beg;
				} else {
					try {
						parseFrame(site, source.substring(beg, loc));
					} catch (Exception e) {
						errors.insert("while parsing " + site.toString()
								+ ",error parsing frame: " + e.toString());
					}
				}
			}

			// found "<a "
			else if (hasLT && hasA && hasSp && !hasF) {
				hasLT = hasA = hasSp = false;
				beg = loc;
				loc = source.indexOf(">", loc);
				if (loc == -1) {
					errors.insert("malformed linked at" + site.toString());
					loc = beg;
				} else {
					try {
						parseLink(site, source.substring(beg, loc));
					} catch (Exception e) {
						errors.insert("while parsing " + site.toString()
								+ ",error parsing link: " + e.toString());
					}
				}
			}
		}
	}

	/*
	 * parses a frame
	 */
	private void parseFrame(URL at_page, String s) throws Exception{ 
		int beg=s.indexOf("src"); 
		if(beg==-1)beg=s.indexOf("SRC"); 
		if(beg==-1)return;//doesn't have a src, ignore 
		beg = s.indexOf("=", beg); 
		if(beg==-1)throw new Exception("while parsing"+at_page.toString()+", bad frame, missing \'=\' after src:"+s);
		int start = beg; 
		for(;beg<s.length();beg++){ 
		   if(s.charAt(beg)=='\'')break; 
		   if(s.charAt(beg)=='\"')break; 
		} 
		int end=beg+1; 
		for(;end<s.length();end++){ 
		   if(s.charAt(beg)==s.charAt(end))break; 
		} 
		beg++; 
		if(beg>=end){//missing quotes... just take the first token after"src=" 
		   for(beg=start+1;beg<s.length() && (s.charAt(beg)==' ');beg++){} 
		   for(end=beg+1;end<s.length() && (s.charAt(beg)!=' ')&& (s.charAt(beg)!='>');end++){}
		} 
		   
		if(beg>=end){ 
		   errors.insert("while parsing "+at_page.toString()+",bad frame: "+s); 
		   return; 
		} 
		   
		String linkto=s.substring(beg,end); 
		if(linkto.startsWith("mailto:")||linkto.startsWith("Mailto:"))return;
		if(linkto.startsWith("javascript:")||linkto.startsWith("Javascript:"))return;
		if(linkto.startsWith("news:")||linkto.startsWith("Javascript:"))return;
		try{ 
		   addSite(new URL(at_page, linkto)); 
		   return; 
		}catch(Exception e1){} 
		try{ 
		   addSite(new URL(linkto)); 
		   return; 
		}catch(Exception e2){} 
		try{ 
		   URL cp = new URL(at_page.toString()+"/index.html"); 
		   System.out.println("attemping to use "+cp); 
		   addSite(new URL(cp, linkto)); 
		   return; 
		}catch(Exception e3){} 
		errors.insert("while parsing "+at_page.toString()+", bad frame:"+linkto+", formed from: "+s);
		   }

	/*
	 * given a link at a URL, will parse it and add it to the list ofsites to do
	 */
	private void parseLink(URL at_page, String s) throws Exception{ 
		//System.out.println("parsing link "+s); 
		int beg=s.indexOf("href"); 
		if(beg==-1)beg=s.indexOf("HREF"); 
		if(beg==-1)return;//doesn't have a href, must be an anchor 
		beg = s.indexOf("=", beg); 
		if(beg==-1)throw new Exception("while parsing"+at_page.toString()+", bad link, missing \'=\' after href:"+s);
		int start = beg; 
		for(;beg<s.length();beg++){ 
		   if(s.charAt(beg)=='\'')break; 
		   if(s.charAt(beg)=='\"')break; 
		} 
		int end=beg+1; 
		for(;end<s.length();end++){ 
		   if(s.charAt(beg)==s.charAt(end))break; 
		} 
		beg++; 
		if(beg>=end){//missing quotes... just take the first token after"href=" 
		   //for(beg=start+1;beg<s.length() && (s.charAt(beg)==' ');beg++){} 
		   for(end=beg+1;end<s.length() && (s.charAt(beg)!=' ')&& (s.charAt(beg)!='>');end++){}
		} 
		   
		if(beg>=end){ 
		   errors.insert("while parsing"+at_page.toString()+", bad href: "+s); 
		   return; 
		} 
		   
		String linkto=s.substring(beg,end); 
		if(linkto.startsWith("mailto:")||linkto.startsWith("Mailto:"))return;
		if(linkto.startsWith("javascript:")||linkto.startsWith("Javascript:"))return;
		if(linkto.startsWith("news:")||linkto.startsWith("Javascript:"))return;
		   
		try{ 
		   addSite(new URL(at_page, linkto)); 
		   return; 
		}catch(Exception e1){} 
		try{ 
		   addSite(new URL(linkto)); 
		   return; 
		}catch(Exception e2){} 
		try{ 
		   addSite(new URL(new URL(at_page.toString()+"/index.html"), linkto)); 
		   return; 
		}catch(Exception e3){} 
		errors.insert("while parsing "+at_page.toString()+", bad link:"+linkto+", formed from: "+s);
		   }

	/*
	 * gets the title of a web page with content s
	 */
	private String getTitle(String s) {
		try {
			int beg = s.indexOf("<title>");
			if (beg == -1)
				beg = s.indexOf("<TITLE>");
			int end = s.indexOf("</title>");
			if (end == -1)
				end = s.indexOf("</TITLE>");
			return s.substring(beg, end);
		} catch (Exception e) {
			return "";
		}
	}

	/*
	 * gets the text of a web page, times out after 10s
	 */
	private String getText(URL site) throws Exception {
		SpiderUrlReader u = new SpiderUrlReader(site);
		Thread t = new Thread(u);
		t.setDaemon(true);
		t.start();
		t.join(TIMEOUT);
		String ret = u.poll();
		if (ret == null) {
			throw new Exception("connection timed out");
		} else if (ret.equals("Not html")) {
			throw new Exception("Not an HTML document");
		}
		return ret;
	}

	/*
	 * returns how many sites have been visited so far
	 */
	public int Visited() {
		return visitedsites;
	}
}

