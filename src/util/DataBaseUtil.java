package util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DataBaseUtil{
	//�������ݿ�
	public Connection getCon()throws Exception{
		//���� �ṩ���ݿ����ӷ���
		Class.forName("com.mysql.cj.jdbc.Driver"); //��������
		Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/db_job?characterEncoding=utf8&useSSL=false&serverTimezone=UTC&rewriteBatchedStatements=true","root","root");
		//ʹ��DriverManager��ȡ���ݿ�����
		return con;
	}
	//�ر����ݿ�
	public void closeCon(Connection con)throws Exception{
		if(con!=null){
			con.close();
		}
	}
}
