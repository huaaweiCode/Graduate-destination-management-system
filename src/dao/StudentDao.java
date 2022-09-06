package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import model.Student;

/**                                                
 * 数据库t_student stuId(int 主键) stuName stuPassword userTypeId(int) stuSex stuAge(int) stuTel stuGrade stuMajor
* @author 马金梅
* @ClassName dao.StudentDao
* @Description: TODO
* @date 2019年3月27日 下午7:35:16
 */
public class StudentDao {
	/*学生注册功能！！   添加学生用户 Id自动生成  用户类别为2*/
	public int StundetAdd(Connection con,Student stu)  //添加学生
			throws Exception{
		String sql="insert into t_student values(null,?,?,2,?,?,?,?,?,0)"; ////添加的SQL语句
		PreparedStatement ppt=con.prepareStatement(sql);  //是预编译的,进行预处理
		ppt.setString(1, stu.getStuName());
		ppt.setString(2, stu.getStuPassword());
		ppt.setString(3, stu.getStuSex());
		ppt.setInt(4, stu.getStuAge());
		ppt.setString(5, stu.getStuTel());
		ppt.setString(6, stu.getStuGrade());
		ppt.setString(7, stu.getStuMajor());
		return ppt.executeUpdate();  //执行语句，返回受影响的记录条数
	}				
				//根据学生名和密码查询是否有这个用户。 学生验证登录
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
		PreparedStatement ppt=con.prepareStatement(sql);    //预编译sql语句 
		                                                   //prepareStatement这个方法会将SQL语句加载到驱动程序conn集成程序中，但是并不直接执行
		//先对应SQL语句，给SQL语句传递参数
		ppt.setString(1, stu.getStuName());
		ppt.setString(2, stu.getStuPassword());
		ppt.setString(3, stu.getStuSex());
		ppt.setInt(4, stu.getStuAge());
		ppt.setString(5, stu.getStuTel());
		ppt.setString(6, stu.getStuGrade());
		ppt.setString(7, stu.getStuMajor());
		ppt.setInt(8, stu.getStuId());
		return ppt.executeUpdate();  // 修改返回受影响的行数
	}
	
	public List<Student> studentList(Connection con)throws Exception{
		String sql="select * from t_student where isDelete=0";
		PreparedStatement ppt=con.prepareStatement(sql);
		ResultSet rs=ppt.executeQuery();
		List<Student> stulists=new ArrayList<Student>();  //新建一个名字为stulists的数组列表，数组元素均由Student类型数据组成。
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
		 //逻辑删除 #对要删除的对象做标记，可恢复（用一个字段来表示 这条信息是否已经不能再使用了）
		 //需要给students表添加一个 isdelete 字段 bit 类型才能进行逻辑删除
		 //isdelete=1 就是代表删除标记；is_delete=0 就是恢复 #用二进制0和1表示
		// update t_students set isdelete=1 where id=? ;
		PreparedStatement ppt=con.prepareStatement("update t_student set isDelete=1 where stuId=?");  
		//SQL 语句被预编译并且存储在 PreparedStatement对象中。
		ppt.setInt(1, stuId);
		return ppt.executeUpdate();   //执行sql语句
	}
	//查询用户名是否存在！
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
