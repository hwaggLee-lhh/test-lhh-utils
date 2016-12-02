package com.org.apache.lucene;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.queryparser.flexible.standard.QueryParserUtil;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;

public abstract class BaseLucene extends UtilsLucene{
	
	/**
	 * 根据key和value查询-唯一匹配
	 * @param keyname
	 * @param valuename
	 * @return
	 */
	public List<Map<String,String>> queryDirectoryField(String[] keyname,String[] valuename){
		List<Map<String,String>> list = new ArrayList<Map<String,String>>();
		if(keyname == null || keyname.length == 0 	) return list;
		if(valuename == null || keyname.length == 0 	) return list;
		IndexReader reader = null;
		try {
			reader = DirectoryReader.open(FSDirectory.open(super.getDirectoryFile()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		if( reader == null) return list;
		IndexSearcher searcher = new IndexSearcher(reader);
		TopDocs topDocs = null;
		try {
			/*StandardAnalyzer analyzer = new StandardAnalyzer(this.getVersion());
			QueryParser parser = new QueryParser(this.getVersion(),keyname[0],analyzer) ;
			topDocs = searcher.search(parser.parse(valuename[0]), reader.maxDoc());*/
			topDocs = searcher.search(QueryParserUtil.parse(valuename, keyname, new StandardAnalyzer(super.getVersion())), reader.maxDoc());
		} catch (Exception e) {
			e.printStackTrace();
		}
		if( topDocs == null )return list;
		ScoreDoc[] scoreDocs = topDocs.scoreDocs;
		if(scoreDocs==null || scoreDocs.length==0) return list;
		for (ScoreDoc d : scoreDocs) {
			if( d == null )continue;
			Map<String, String> map = null;
			try {
				map = super.convertMap(searcher.doc(d.doc));
			} catch (IOException e) {
				e.printStackTrace();
				continue;
			}
			if( map == null || map.isEmpty())continue;
			list.add(map);
		}
		super.closeReader(reader);
		return list;
	}
	
	/**
	 * 查询getDirectoryFile下的所有索引内容
	 * @return
	 */
	public List<Map<String,String>> queryDirectoryAllField(){
		List<Map<String,String>> list = new ArrayList<Map<String,String>>();
		IndexReader reader = null;
		try {
			reader = DirectoryReader.open(FSDirectory.open(super.getDirectoryFile()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		if( reader == null) return list;
		IndexSearcher searcher = new IndexSearcher(reader);
		int maxdoc = reader.maxDoc();
		for (int i = 0; i < maxdoc; i++) {
			try {
				Document doc = searcher.doc(i);
				if( doc == null )continue;
				Map<String,String> map = super.convertMap(doc);
				if( map == null || map.isEmpty())continue;
				list.add(map);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		super.closeReader(reader);
		return list;
	}
	
	
	/**
	 * 创建索引
	 * @param list
	 */
	public void insertIndex(List<Map<String,String>> list){
		IndexWriter writer = super.getIndexWriter();
		List<Document> listDocument = new ArrayList<Document>();
		for (Map<String, String> map : list) {
			Document doc = this.addDocuments(super.convertField(map));
			if( doc == null || doc.getFields().isEmpty())continue;
			listDocument.add(doc);
		}
		try {
			writer.addDocuments(listDocument);
		} catch (IOException e) {
			e.printStackTrace();
		}
		super.closeWriter(writer);
	}
	

	
}
