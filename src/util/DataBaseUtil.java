package util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DataBaseUtil{
	//连接数据库
	public Connection getCon()throws Exception{
		//对外 提供数据库连接方法
		Class.forName("com.mysql.cj.jdbc.Driver"); //加载驱动
		Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/db_job?characterEncoding=utf8&useSSL=false&serverTimezone=UTC&rewriteBatchedStatements=true","root","root");
		//使用DriverManager获取数据库连接
		return con;
	}
	//关闭数据库
	public void closeCon(Connection con)throws Exception{
		if(con!=null){
			con.close();
		}
	}
}
