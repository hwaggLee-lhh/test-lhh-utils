package com.jdbc;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;


public class UtilsJDBCCreateJavaClassFile {
	private String url = "jdbc:mysql://192.168.0.245:3306/nsanban0924";
	public static void main(String[] args) {
		String[] names= new String[]{"guided_page"
				
		};
		UtilsJDBCCreateJavaClassFile createBean = new UtilsJDBCCreateJavaClassFile();
		for(int i=0;i<names.length;i++){
			createBean.setTableName(names[i]);
			createBean.createBeanMethod();
			createBean.createManager();
			createBean.createService();
			createBean.createServiceImpl();
		}
		System.out.println("生成成功！");
	}
	
	
	
	
	
	
	
	
	
	
	
	private String tablename = "advertisement_image";
	
	public void setTableName(String name){
		this.tablename = name;
	}

	private String[] colnames; // 列名数组

	private String[] colTypes; // 列名类型数组

	private int[] colSizes; // 列名大小数组

	@SuppressWarnings("unused")
	private boolean f_util = false; // 是否需要导入包java.util.*
	@SuppressWarnings("unused")
	private boolean f_sql = false; // 是否需要导入包java.sql.*
	

	private String user = "root";
	private String pwd = "123456";

	public Connection getConnection() {
		Connection conn = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(url, user, pwd);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}
	
	public void createManager(){
		Connection conn = getConnection(); // 得到数据库连接
	    //myDB为数据库名
		String strsql = "select * from " + tablename;
		PreparedStatement pstmt = null;
		ResultSetMetaData rsmd = null;
		try {
			pstmt = conn.prepareStatement(strsql);
			rsmd = pstmt.getMetaData();
			int size = rsmd.getColumnCount(); // 共有多少列
			colnames = new String[size];
			colTypes = new String[size];
			colSizes = new int[size];
			for (int i = 0; i < rsmd.getColumnCount(); i++) {
				colnames[i] = rsmd.getColumnName(i + 1);
				colTypes[i] = rsmd.getColumnTypeName(i + 1);
				if (colTypes[i].equalsIgnoreCase("datetime")) {
					f_util = true;
				}
				if (colTypes[i].equalsIgnoreCase("image")
						|| colTypes[i].equalsIgnoreCase("text")) {
					f_sql = true;
				}
				colSizes[i] = rsmd.getColumnDisplaySize(i + 1);
			}
			String content = parseManager(colnames, colTypes, colSizes);
			try {
				FileWriter fw = new FileWriter(managertitle(initcapTitle(tablename)) + ".java");
				PrintWriter pw = new PrintWriter(fw);
				pw.println(content);
				pw.flush();
				pw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
		
	public String resourcetitle(String str){
		char[] chars=new char[1];  
        chars[0]=str.charAt(0);  
        String temp=new String(chars);  
        return str.replaceFirst(temp,temp.toLowerCase());  
	}
	
	public String managertitle(String str){
		return str+"Manager";
	}
	
	public void createService(){
		Connection conn = getConnection(); // 得到数据库连接
	    //myDB为数据库名
		String strsql = "select * from " + tablename;
		PreparedStatement pstmt = null;
		ResultSetMetaData rsmd = null;
		try {
			pstmt = conn.prepareStatement(strsql);
			rsmd = pstmt.getMetaData();
			int size = rsmd.getColumnCount(); // 共有多少列
			colnames = new String[size];
			colTypes = new String[size];
			colSizes = new int[size];
			for (int i = 0; i < rsmd.getColumnCount(); i++) {
				colnames[i] = rsmd.getColumnName(i + 1);
				colTypes[i] = rsmd.getColumnTypeName(i + 1);
				if (colTypes[i].equalsIgnoreCase("datetime")) {
					f_util = true;
				}
				if (colTypes[i].equalsIgnoreCase("image")
						|| colTypes[i].equalsIgnoreCase("text")) {
					f_sql = true;
				}
				colSizes[i] = rsmd.getColumnDisplaySize(i + 1);
			}
			String content = parseService(colnames, colTypes, colSizes);
			try {
				FileWriter fw = new FileWriter(initcapTitle(tablename) + "Service.java");
				PrintWriter pw = new PrintWriter(fw);
				pw.println(content);
				pw.flush();
				pw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void createServiceImpl(){
		Connection conn = getConnection(); // 得到数据库连接
	    //myDB为数据库名
		String strsql = "select * from " + tablename;
		PreparedStatement pstmt = null;
		ResultSetMetaData rsmd = null;
		try {
			pstmt = conn.prepareStatement(strsql);
			rsmd = pstmt.getMetaData();
			int size = rsmd.getColumnCount(); // 共有多少列
			colnames = new String[size];
			colTypes = new String[size];
			colSizes = new int[size];
			for (int i = 0; i < rsmd.getColumnCount(); i++) {
				colnames[i] = rsmd.getColumnName(i + 1);
				colTypes[i] = rsmd.getColumnTypeName(i + 1);
				if (colTypes[i].equalsIgnoreCase("datetime")) {
					f_util = true;
				}
				if (colTypes[i].equalsIgnoreCase("image")
						|| colTypes[i].equalsIgnoreCase("text")) {
					f_sql = true;
				}
				colSizes[i] = rsmd.getColumnDisplaySize(i + 1);
			}
			String content = parseServiceImpl(colnames, colTypes, colSizes);
			try {
				FileWriter fw = new FileWriter(initcapTitle(tablename) + "ServiceImpl.java");
				PrintWriter pw = new PrintWriter(fw);
				pw.println(content);
				pw.flush();
				pw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public void createBeanMethod() {
		Connection conn = getConnection(); // 得到数据库连接
	    //myDB为数据库名
		String strsql = "select * from " + tablename;
		PreparedStatement pstmt = null;
		ResultSetMetaData rsmd = null;
		try {
			pstmt = conn.prepareStatement(strsql);
			rsmd = pstmt.getMetaData();
			int size = rsmd.getColumnCount(); // 共有多少列
			colnames = new String[size];
			colTypes = new String[size];
			colSizes = new int[size];
			for (int i = 0; i < rsmd.getColumnCount(); i++) {
				colnames[i] = rsmd.getColumnName(i + 1);
				colTypes[i] = rsmd.getColumnTypeName(i + 1);
				if (colTypes[i].equalsIgnoreCase("datetime")) {
					f_util = true;
				}
				if (colTypes[i].equalsIgnoreCase("image")
						|| colTypes[i].equalsIgnoreCase("text")) {
					f_sql = true;
				}
				colSizes[i] = rsmd.getColumnDisplaySize(i + 1);
			}
			String content = parse(colnames, colTypes, colSizes);
			try {
				FileWriter fw = new FileWriter(initcapTitle(tablename) + ".java");
				PrintWriter pw = new PrintWriter(fw);
				pw.println(content);
				pw.flush();
				pw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	private String parseManager(String[] colNames, String[] colTypes, int[] colSizes){
		StringBuffer sb = new StringBuffer();
		sb.append("package com.qinghuainvest.tsportal.sec.service;\r\n");
		sb.append("\r\n");
		sb.append("import com.qinghuainvest.cmndd.util.service.BaseService;\r\n");
		sb.append("import com.qinghuainvest.cmndd.util.hibernate.BaseManager;\r\n");
		sb.append("import com.qinghuainvest.tsportal.sec.model.");
		sb.append(initcapTitle(tablename));	
		sb.append(";\r\n");	
		sb.append("\r\n");
		sb.append("@Repository(\""+resourcetitle(initcapTitle(tablename))+"Manager\")\r\n");
		sb.append("public class "+managertitle(initcapTitle(tablename))+" extends BaseManager<"+initcapTitle(tablename)+">{\r\n");
		sb.append("\r\n");
		sb.append("\t@Override\r\n");
		sb.append("\tpublic Class<"+initcapTitle(tablename)+"> getModelClass() {\r\n");
		sb.append("\t\treturn "+initcapTitle(tablename)+".class;\r\n");
		sb.append("\t}\r\n");
		sb.append("\r\n");
		sb.append("\r\n");
		sb.append("}\r\n");
		return sb.toString();
	}
	
	private String parseService(String[] colNames, String[] colTypes, int[] colSizes){
		StringBuffer sb = new StringBuffer();
		sb.append("package com.qinghuainvest.tsportal.sec.service;\r\n");
		sb.append("\r\n");
		sb.append("import com.qinghuainvest.cmndd.util.service.BaseService;\r\n");
		sb.append("import com.qinghuainvest.tsportal.weibo.model.");
		sb.append(initcapTitle(tablename));	
		sb.append(";\r\n");	
		sb.append("\r\n");
		sb.append("public interface "+initcapTitle(tablename)+"Service extends BaseService<"+initcapTitle(tablename)+">{\r\n");
		sb.append("\r\n");
		sb.append("\r\n");
		sb.append("\r\n");
		sb.append("}\r\n");
		return sb.toString();
	}
	
	private String parseServiceImpl(String[] colNames, String[] colTypes, int[] colSizes){
		StringBuffer sb = new StringBuffer();
		sb.append("package com.qinghuainvest.tsportal.sec.service.impl;\r\n");
		sb.append("\r\n");
		sb.append("import javax.annotation.Resource;\r\n");
		sb.append("import org.springframework.stereotype.Service;\r\n");
		sb.append("import com.qinghuainvest.cmndd.util.hibernate.BaseManager;\r\n");
		sb.append("import com.qinghuainvest.cmndd.util.service.impl.BaseServiceImpl;\r\n");
		sb.append("import com.qinghuainvest.tsportal.sec.service.");
		sb.append(initcapTitle(tablename));	
		sb.append("Service;\r\n");
		sb.append("import com.qinghuainvest.tsportal.sec.manager.");
		sb.append(initcapTitle(tablename));	
		sb.append("Manager;\r\n");
		sb.append("import com.qinghuainvest.tsportal.sec.model.");
		sb.append(initcapTitle(tablename));	
		sb.append(";\r\n");	
		sb.append("\r\n");
		sb.append("@Service(\""+resourcetitle(initcapTitle(tablename))+"Service\")\r\n");
		sb.append("public class "+initcapTitle(tablename)+"ServiceImpl extends BaseServiceImpl<"+initcapTitle(tablename)+"> implements "+initcapTitle(tablename)+"Service{\r\n");
		sb.append("\r\n");
		sb.append("\t@Resource\r\n");
		sb.append("\tprivate "+initcapTitle(tablename)+"Manager "+resourcetitle(initcapTitle(tablename))+"Manager;\r\n");
		sb.append("\r\n");
		sb.append("\t@Override\r\n");
		sb.append("\tprotected BaseManager<"+initcapTitle(tablename)+"> getBaseManager() {\r\n");
		sb.append("\t\treturn "+resourcetitle(initcapTitle(tablename))+"Manager;\r\n");
		sb.append("\t}\r\n");
		sb.append("\r\n");
		sb.append("\r\n");
		sb.append("}\r\n");
		return sb.toString();
	}

	/**
	 * 解析处理(生成实体类主体代码)
	 */
	private String parse(String[] colNames, String[] colTypes, int[] colSizes) {
		StringBuffer sb = new StringBuffer();
		
		sb.append("package com.qinghuainvest.tsportal.sec.model;\r\n");
		sb.append("\r\n");
		sb.append("\timport java.sql.Timestamp;\r\n");
		sb.append("\timport javax.persistence.Column;\r\n");
		sb.append("\timport javax.persistence.Entity;\r\n");
		sb.append("\timport javax.persistence.GeneratedValue;\r\n");
		sb.append("\timport javax.persistence.Id;\r\n");
		sb.append("\timport javax.persistence.Table;\r\n");
		sb.append("\timport org.hibernate.annotations.GenericGenerator;\r\n");
		sb.append("\timport com.qinghuainvest.cmndd.util.hibernate.BaseModel;\r\n");
		sb.append("\r\n");
		sb.append("@Entity\r\n");
		sb.append("@Table(name=\""+tablename+"\")\r\n");
		sb.append("public class " + initcapTitle(tablename) + " extends BaseModel{\r\n");
		sb.append("\r\n");
		sb.append("\tprivate static final long serialVersionUID = 1L;");
		sb.append("\r\n");
		sb.append("\t@Id\r\n");
		sb.append("\t@GeneratedValue(generator=\"paymentableGenerator\")\r\n");
		sb.append("\t@GenericGenerator(name=\"paymentableGenerator\",strategy=\"uuid\")\r\n");
		processAllAttrs(sb);
		processAllMethod(sb);
		sb.append("}\r\n");
		return sb.toString();

	}

	/**
	 * 生成所有的方法
	 * 
	 * @param sb
	 */
	private void processAllMethod(StringBuffer sb) {
		for (int i = 0; i < colnames.length; i++) {
			sb.append("\tpublic void set" + initcap(colnames[i]) + "("
					+ sqlType2JavaType(colTypes[i]) + " " + colnames[i]
					+ "){\r\n");
			sb.append("\t\tthis." + colnames[i] + "=" + colnames[i] + ";\r\n");
			sb.append("\t}\r\n");

			sb.append("\tpublic " + sqlType2JavaType(colTypes[i]) + " get"
					+ initcap(colnames[i]) + "(){\r\n");
			sb.append("\t\treturn " + colnames[i] + ";\r\n");
			sb.append("\t}\r\n");
		}
	}

	/**
	 * 解析输出属性
	 * 
	 * @return
	 */
	private void processAllAttrs(StringBuffer sb) {
		for (int i = 0; i < colnames.length; i++) {
			sb.append("\t@Column(name=\"" + colnames[i] + "\")\r\n");
			sb.append("\tprivate " + sqlType2JavaType(colTypes[i]) + " "
					//+ resourcetitle(initcapTitle(colnames[i])) + ";\r\n");
					+ colnames[i] + ";\r\n");

		}
	}

	/**
	 * 把输入字符串的首字母改成大写
	 * 
	 * @param str
	 * @return
	 */
	private String initcap(String str) {
        char[] ch = str.toCharArray();
        if (ch[0] >= 'a' && ch[0] <= 'z') {
            ch[0] = (char) (ch[0] - 32);
        }
        return new String(ch);
    }
	
	private String initcapTitle(String str){
		StringBuffer sbf = new StringBuffer();
		String[] arr = str.split("_");
		for (String string : arr) {
			char[] ch = string.toCharArray();
	        if (ch[0] >= 'a' && ch[0] <= 'z') {
	            ch[0] = (char) (ch[0] - 32);
	        }
	        sbf.append(new String(ch));
		}
		return sbf.toString();
	}

	private String sqlType2JavaType(String sqlType) {
		if (sqlType.equalsIgnoreCase("bit")) {
			return "bool";
		} else if (sqlType.equalsIgnoreCase("tinyint")) {
			return "byte";
		} else if (sqlType.equalsIgnoreCase("smallint")) {
			return "short";
		} else if (sqlType.equalsIgnoreCase("int")) {
			return "Integer";
		} else if (sqlType.equalsIgnoreCase("bigint")) {
			return "Long";
		} else if (sqlType.equalsIgnoreCase("float")) {
			return "Fload";
		} else if (sqlType.equalsIgnoreCase("decimal")
				|| sqlType.equalsIgnoreCase("numeric")
				|| sqlType.equalsIgnoreCase("real")) {
			return "BigDecimal";
		} else if (sqlType.equalsIgnoreCase("money")
				|| sqlType.equalsIgnoreCase("smallmoney")) {
			return "Double";
		} else if (sqlType.equalsIgnoreCase("varchar")
				|| sqlType.equalsIgnoreCase("char")
				|| sqlType.equalsIgnoreCase("nvarchar")
				|| sqlType.equalsIgnoreCase("nchar")) {
			return "String";
		} else if (sqlType.equalsIgnoreCase("datetime")) {
			return "Date";
		}

		else if (sqlType.equalsIgnoreCase("image")) {
			return "Blob";
		} else if (sqlType.equalsIgnoreCase("text")) {
			return "Clob";
		} else if (sqlType.equalsIgnoreCase("date")) {
			return "Date";
		} else if(sqlType.equalsIgnoreCase("timestamp")){
			return "Timestamp";
		}
		return null;
	}
}
