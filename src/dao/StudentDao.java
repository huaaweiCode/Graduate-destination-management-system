package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import model.Student;

/**                                                
 * ���ݿ�t_student stuId(int ����) stuName stuPassword userTypeId(int) stuSex stuAge(int) stuTel stuGrade stuMajor
* @author ���÷
* @ClassName dao.StudentDao
* @Description: TODO
* @date 2019��3��27�� ����7:35:16
 */
public class StudentDao {
	/*ѧ��ע�Ṧ�ܣ���   ���ѧ���û� Id�Զ�����  �û����Ϊ2*/
	public int StundetAdd(Connection con,Student stu)  //���ѧ��
			throws Exception{
		String sql="insert into t_student values(null,?,?,2,?,?,?,?,?,0)"; ////��ӵ�SQL���
		PreparedStatement ppt=con.prepareStatement(sql);  //��Ԥ�����,����Ԥ����
		ppt.setString(1, stu.getStuName());
		ppt.setString(2, stu.getStuPassword());
		ppt.setString(3, stu.getStuSex());
		ppt.setInt(4, stu.getStuAge());
		ppt.setString(5, stu.getStuTel());
		ppt.setString(6, stu.getStuGrade());
		ppt.setString(7, stu.getStuMajor());
		return ppt.executeUpdate();  //ִ����䣬������Ӱ��ļ�¼����
	}				
				//����ѧ�����������ѯ�Ƿ�������û��� ѧ����֤��¼
	public Student StuSelect(Connection con,String stuName,String stuPassword)
			throws Exception{
		String sql="select * from t_student where stuName=? and stuPassword=? and isDelete=0 ";
		PreparedStatement ppt=con.prepareStatement(sql);
		ppt.setString(1, stuName);
		ppt.setString(2, stuPassword);
		ResultSet rs=ppt.executeQuery();
		Student stu=null;
		if(rs.next()){
			stu=new Student();
			stu.setStuId(Integer.parseInt(rs.getString("stuId")));
			stu.setStuName(rs.getString("stuName"));
			stu.setStuPassword(rs.getString("stuPassword"));
			stu.setStuSex(rs.getString("stuSex"));
			stu.setStuAge(rs.getInt("stuAge"));
			stu.setStuTel(rs.getString("stuTel"));
			stu.setStuGrade(rs.getString("stuGrade"));
			stu.setStuMajor(rs.getString("stuMajor"));
		}
		return stu;
	}
	public Student StuSelectById(Connection con,String stuId)
			throws Exception{
		String sql="select * from t_student where stuId=? and isDelete=0 ";
		PreparedStatement ppt=con.prepareStatement(sql);
		ppt.setString(1, stuId);
		ResultSet rs=ppt.executeQuery();
		Student stu=null;
		if(rs.next()){
			stu=new Student();
			stu.setStuId(Integer.parseInt(rs.getString("stuId")));
			stu.setStuName(rs.getString("stuName"));
			stu.setStuPassword(rs.getString("stuPassword"));
			stu.setStuSex(rs.getString("stuSex"));
			stu.setStuAge(rs.getInt("stuAge"));
			stu.setStuTel(rs.getString("stuTel"));
			stu.setStuGrade(rs.getString("stuGrade"));
			stu.setStuMajor(rs.getString("stuMajor"));
		}
		return stu;
	}
	public int studentUpdate(Connection con,Student stu)throws Exception{
		String sql="UPDATE t_student SET stuName=?,stuPassword=?,stuSex=?,stuAge=?,stuTel=?,stuGrade=?,stuMajor=? WHERE stuId=?";
		PreparedStatement ppt=con.prepareStatement(sql);    //Ԥ����sql��� 
		                                                   //prepareStatement��������ὫSQL�����ص���������conn���ɳ����У����ǲ���ֱ��ִ��
		//�ȶ�ӦSQL��䣬��SQL��䴫�ݲ���
		ppt.setString(1, stu.getStuName());
		ppt.setString(2, stu.getStuPassword());
		ppt.setString(3, stu.getStuSex());
		ppt.setInt(4, stu.getStuAge());
		ppt.setString(5, stu.getStuTel());
		ppt.setString(6, stu.getStuGrade());
		ppt.setString(7, stu.getStuMajor());
		ppt.setInt(8, stu.getStuId());
		return ppt.executeUpdate();  // �޸ķ�����Ӱ�������
	}
	
	public List<Student> studentList(Connection con)throws Exception{
		String sql="select * from t_student where isDelete=0";
		PreparedStatement ppt=con.prepareStatement(sql);
		ResultSet rs=ppt.executeQuery();
		List<Student> stulists=new ArrayList<Student>();  //�½�һ������Ϊstulists�������б�����Ԫ�ؾ���Student����������ɡ�
		while(rs.next()){
			Student stu=new Student();
			stu.setStuId(Integer.parseInt(rs.getString("stuId")));
			stu.setStuName(rs.getString("stuName"));
			stu.setStuPassword(rs.getString("stuPassword"));
			stu.setStuSex(rs.getString("stuSex"));
			stu.setStuAge(rs.getInt("stuAge"));
			stu.setStuTel(rs.getString("stuTel"));
			stu.setStuGrade(rs.getString("stuGrade"));
			stu.setStuMajor(rs.getString("stuMajor"));
			stulists.add(stu);
		}
		return stulists;
	}
	public int stuDelete(Connection con,int stuId)throws Exception{
		 //�߼�ɾ�� #��Ҫɾ���Ķ�������ǣ��ɻָ�����һ���ֶ�����ʾ ������Ϣ�Ƿ��Ѿ�������ʹ���ˣ�
		 //��Ҫ��students�����һ�� isdelete �ֶ� bit ���Ͳ��ܽ����߼�ɾ��
		 //isdelete=1 ���Ǵ���ɾ����ǣ�is_delete=0 ���ǻָ� #�ö�����0��1��ʾ
		// update t_students set isdelete=1 where id=? ;
		PreparedStatement ppt=con.prepareStatement("update t_student set isDelete=1 where stuId=?");  
		//SQL ��䱻Ԥ���벢�Ҵ洢�� PreparedStatement�����С�
		ppt.setInt(1, stuId);
		return ppt.executeUpdate();   //ִ��sql���
	}
	//��ѯ�û����Ƿ���ڣ�
	public boolean stuSelect(Connection con,String stuName)throws Exception{
		PreparedStatement ppt=con.prepareStatement("select * from t_student where isDelete=0 and stuName= '"+stuName+"' ");
		ResultSet rs=ppt.executeQuery();
		if(rs.next()){
			return true;
		}else{
			return false;
		}
	}
}
