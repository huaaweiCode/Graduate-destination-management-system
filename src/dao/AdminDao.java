package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import model.Admin;
import model.Student;

/**
 * 
* @author ���÷
* @ClassName dao.AdminDao
* @Description: TODO
* @date 2019��3��27�� ����7:35:48
*   �������ݿ���Ӧ���ֶα�дʵ���ࡣ
 */
public class AdminDao {
	     //����Ա��¼��֤
	public Admin AdminSelect(Connection con,String adminName,String adminPassword)
			throws Exception{
		String sql="select * from t_admin where adminName=? and adminPassword=?";
		PreparedStatement ppt=con.prepareStatement(sql);
		ppt.setString(1, adminName);
		ppt.setString(2, adminPassword);
		ResultSet rs=ppt.executeQuery();   //ִ��sql���
		Admin admin=null;
		if(rs.next()){       
			admin=new Admin();
			admin.setAdminId(rs.getInt("adminId"));
			admin.setAdminName(rs.getString("adminName"));
			admin.setAdminPassword(rs.getString("adminPassword"));
		}
		return admin;
	}
	//����Ա�޸���Ϣ
	public int adminUpdate(Connection con,Admin admin)throws Exception{
		String sql="UPDATE t_admin SET adminName=?,adminPassword=? WHERE adminId=?";
		PreparedStatement ppt=con.prepareStatement(sql);
		ppt.setString(1, admin.getAdminName());
		ppt.setString(2, admin.getAdminPassword());
		ppt.setInt(3, admin.getAdminId());
		return ppt.executeUpdate();
	}
	//����Ա��˹���ͨ��
	public int jobsPass(Connection con,String jobId)throws Exception{
		String sql="UPDATE t_jobs SET isPass=1 WHERE jobId=?";   //����Ӧ��ҵ�����Ĺ���ispass��Ϊ1 ��Ϊ���ͨ��
		PreparedStatement ppt=con.prepareStatement(sql);
		ppt.setInt(1, Integer.parseInt(jobId));
		return ppt.executeUpdate();
	}
	//����Ա��˹�����ͨ��
	public int jobsNotPass(Connection con,String jobId)throws Exception{
		String sql="UPDATE t_jobs SET isPass=0 WHERE jobId=?";
		PreparedStatement ppt=con.prepareStatement(sql);
		ppt.setInt(1, Integer.parseInt(jobId));
		return ppt.executeUpdate();
	}
}
