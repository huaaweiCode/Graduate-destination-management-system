package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import model.Admin;
import model.Student;

/**
 * 
* @author 马金梅
* @ClassName dao.AdminDao
* @Description: TODO
* @date 2019年3月27日 下午7:35:48
*   按照数据库表对应的字段编写实体类。
 */
public class AdminDao {
	     //管理员登录验证
	public Admin AdminSelect(Connection con,String adminName,String adminPassword)
			throws Exception{
		String sql="select * from t_admin where adminName=? and adminPassword=?";
		PreparedStatement ppt=con.prepareStatement(sql);
		ppt.setString(1, adminName);
		ppt.setString(2, adminPassword);
		ResultSet rs=ppt.executeQuery();   //执行sql语句
		Admin admin=null;
		if(rs.next()){       
			admin=new Admin();
			admin.setAdminId(rs.getInt("adminId"));
			admin.setAdminName(rs.getString("adminName"));
			admin.setAdminPassword(rs.getString("adminPassword"));
		}
		return admin;
	}
	//管理员修改信息
	public int adminUpdate(Connection con,Admin admin)throws Exception{
		String sql="UPDATE t_admin SET adminName=?,adminPassword=? WHERE adminId=?";
		PreparedStatement ppt=con.prepareStatement(sql);
		ppt.setString(1, admin.getAdminName());
		ppt.setString(2, admin.getAdminPassword());
		ppt.setInt(3, admin.getAdminId());
		return ppt.executeUpdate();
	}
	//管理员审核工作通过
	public int jobsPass(Connection con,String jobId)throws Exception{
		String sql="UPDATE t_jobs SET isPass=1 WHERE jobId=?";   //将对应企业发布的工作ispass变为1 即为审核通过
		PreparedStatement ppt=con.prepareStatement(sql);
		ppt.setInt(1, Integer.parseInt(jobId));
		return ppt.executeUpdate();
	}
	//管理员审核工作不通过
	public int jobsNotPass(Connection con,String jobId)throws Exception{
		String sql="UPDATE t_jobs SET isPass=0 WHERE jobId=?";
		PreparedStatement ppt=con.prepareStatement(sql);
		ppt.setInt(1, Integer.parseInt(jobId));
		return ppt.executeUpdate();
	}
}
