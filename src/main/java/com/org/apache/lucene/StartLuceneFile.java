package com.org.apache.lucene;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.TextField;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.util.Version;

import com.utils.UtilsFile;

public class StartLuceneFile  extends BaseLucene{

	public static void main(String[] args) throws Exception {
		StartLuceneFile utils = new StartLuceneFile();
		utils.createIndexFile();
		utils.findByKeyWords();
	}
	

	private static final String QUERY_STR = "四川";
	private static final String FILE_TARGET = "D:\\lucene\\luceneData";
	private static final String FILE_INDEX = "D:\\lucene\\luceneIndex";

	/**
	 * 创建索引
	 * 
	 * @throws Exception
	 */
	public void createIndexFile() throws Exception {
		Long startTime = System.currentTimeMillis();
		createIndex(FILE_INDEX, FILE_TARGET,Version.LUCENE_44);
		Long endTime = System.currentTimeMillis();
		System.out.println("花费了" + (endTime - startTime) + "毫秒来创建索引文件");
	}


	public void findByKeyWords() throws Exception {
		Long startTime = System.currentTimeMillis();
		findByKeyWords("content",QUERY_STR, FILE_INDEX, Version.LUCENE_44);
		Long endTime = System.currentTimeMillis();
		System.out.println("花费了" + (endTime - startTime) + "毫秒来检索文件");
	}
	

	/**
	 * 创建索引
	 * @param indexPath：索引存放地址
	 * @param parserpath：需要创建索引的文件地址
	 * @throws Exception
	 */
	public void createIndex(String indexPath,String parserpath,Version type) throws Exception {
		IndexWriter indexWriter = super.getIndexWriter(indexPath, type);
		List<String> fileList = new ArrayList<String>();
		UtilsFile.listFile(new File(parserpath), fileList);
		for (String filePath : fileList) {
			System.out.println("文件:" + filePath + "正在被索引....");
			String content = UtilsFile.readFile(filePath);
			Document doc = new Document();
			doc.add(new TextField("content", content.toString(), Store.YES));
			doc.add(new TextField("path", filePath, Store.YES));
			indexWriter.addDocument(doc);
		}
		indexWriter.close();
	}
	

	public void findByKeyWords(String keyName,String keyWords,String indexPath,Version type) throws Exception {
		IndexReader reader = super.getIndexReader(indexPath);
		IndexSearcher searcher = super.getIndexSearcher(reader);
		Query query = super.getQuery(keyName, keyWords, indexPath, type);
		List<Document> list = super.getDocuments(searcher, query, 1000);
		if(list != null)
		for (int i = 0; i < list.size(); i++) {
			try {
				Document doc = list.get(i);
				System.out.print("这是第" + i + "个检索到的结果，文件名为：");
				System.out.println(doc.get("path"));
				System.out.println("内容:\n" + doc.get("content"));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
