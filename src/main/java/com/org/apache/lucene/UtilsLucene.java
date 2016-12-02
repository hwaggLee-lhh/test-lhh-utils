package com.org.apache.lucene;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.FieldType;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexableField;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;


public abstract class UtilsLucene{
	

	/**
	 * 关闭写入流
	 * @param writer
	 */
	public void closeWriter(IndexWriter writer){
		try {
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

	/**
	 * 关闭读取流
	 * @param writer
	 */
	public void closeReader(IndexReader reader){
		try {
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

	
	/**
	 * 将文档存储索引
	 * @param map
	 * @return
	 */
	public Document addDocuments(List<IndexableField> listField){
		if( listField == null || listField.isEmpty())return null;
		Document doc = new Document();
		for (IndexableField f : listField) {
			doc.add(f);
		}
		return doc;
	}
	
	/**
	 * 将文档存储索引
	 * @param map
	 * @return
	 */
	public Document addDocument(IndexableField field){
		if( field == null )return null;
		Document doc = new Document();
		doc.add(field);
		return doc;
	}
	
	/**
	 * map转换成List<TextField>
	 * @param map
	 * @return
	 */
	public List<IndexableField> convertField(Map<String, String> map){
		List<IndexableField> list = new ArrayList<IndexableField>();
		if( map == null || map.isEmpty())return list;
		for (String key : map.keySet()) {
			String ob = map.get(key);
			if( ob == null || ob.toString().trim().length() == 0)continue;
			IndexableField field = convertField(key, ob, new FieldType());
			if( field == null )continue;
			list.add(field);
		}
		return list;
	}

	/**
	 * document转换成Map<String, String>
	 * @param doc
	 * @return
	 */
	public Map<String, String> convertMap(Document doc){
		if( doc == null || doc.getFields().size() == 0 )return null;
		Map<String,String> map = new HashMap<String, String>();
		for (IndexableField field : doc.getFields()) {
			if( field instanceof Field){
				Field f = (Field)field;
				map.put(f.name(), f.stringValue());
			}
		}
		if( map.isEmpty()) return null;
		return map;
	}
	
	/**
	 * List<document>转换成List<Map<String, String>>
	 * @param doc
	 * @return
	 */
	public List<Map<String, String>> convertMaps(List<Document> listDoc){
		if( listDoc == null || listDoc.size() == 0 )return null;
		List<Map<String,String>> list = new ArrayList<Map<String,String>>();
		for (Document doc : listDoc) {
			Map<String,String> map = this.convertMap(doc);
			if( map ==  null || map.isEmpty())continue;
			list.add(map);
		}
		return list;
	}
	
	/**
	 * key-value转换成List<TextField>
	 * key-value:不允许为空，
	 * store=null时使用默认的this.getStore();
	 * @param key
	 * @param value
	 * @param store
	 * @return
	 */
	public IndexableField convertField(String key,String value,FieldType type){
		if(key == null || key.trim().length() == 0) return null;
		if(value == null || value.trim().length() == 0) return null;
		if(type == null ) return null;
		type.setIndexed(true);
		type.setStored(true);
		type.setStoreTermVectors(true);
		return new Field(key, value,type);
	}
	
	
	/**
	 * 获取读写文件（索引文件）
	 * @return
	 */
	protected IndexWriter getIndexWriter() {
		try {
			IndexWriterConfig config = new IndexWriterConfig(getVersion(),new StandardAnalyzer(getVersion()));
			config.setOpenMode(this.getOpenMode());
			return new IndexWriter(FSDirectory.open(getDirectoryFile()), config);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 索引文件存放目录
	 * @return
	 */
	public File getDirectoryFile(){
		File file = new File(getIndexPath());
		if( !file.exists()){
			file.mkdirs();
		}
		return file;
	}
	
	/**
	 * 索引存储方式，创建新的，或者一直后面追加
	 * @return
	 */
	public OpenMode getOpenMode(){
		return OpenMode.CREATE_OR_APPEND;
	}
	
	/**
	 * 索引存储数据格式
	 * @return
	 */
	public Store getStore(){
		return Store.YES;
	}
	/**
	 * 创建版本
	 * @return
	 */
	public Version getVersion(){
		return Version.LUCENE_44;
	}
	
	/**
	 * 存放地址
	 * @return
	 */
	public String getIndexPath(){
		return "D:\\lucene\\luceneIndex\\base";
	}
	
	
	
	
	/****************************/


	public IndexReader getIndexReader(String indexPath){
		try {
			return DirectoryReader.open(FSDirectory.open(new File(indexPath)));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	

	public IndexSearcher getIndexSearcher(IndexReader reader){
		if( reader == null)return null;
		return new IndexSearcher(reader);
	}
	


	public Query getQuery(String keyName,String keyWords,String indexPath,Version type){
		Analyzer analyzer = new StandardAnalyzer(type);
		QueryParser parser = new QueryParser(type, keyName,analyzer);
		try {
			return parser.parse(keyWords);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public List<Document> getDocuments(IndexSearcher searcher,Query query,int num){
		TopDocs results = null;
		try {
			results = searcher.search(query, num);
		} catch (IOException e) {
			e.printStackTrace();
		}
		if( results != null ){
			ScoreDoc[] score = results.scoreDocs;
			if(score != null ){
				List<Document> list = new ArrayList<Document>(); 
				for (int i = 0; i < score.length; i++) {
					Document doc = getDocument(searcher,score[i]);
					list.add(doc);
				}
				return list;
			}
		}
		return null;
	}
	
	public Document getDocument(IndexSearcher searcher,ScoreDoc scoreDoc){
		try {
			return searcher.doc(scoreDoc.doc);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public IndexWriter getIndexWriter(String indexPath,Version type){
		IndexWriterConfig config = new IndexWriterConfig(type,new StandardAnalyzer(type));
		config.setOpenMode(OpenMode.CREATE);
		Directory directory = null;
		try {
			directory = FSDirectory.open(new File(indexPath));
			return new IndexWriter(directory, config);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	
}
