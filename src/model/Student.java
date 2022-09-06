package model;
/**
 * 
* @author 马金梅
* @ClassName java.mjm.model.Student
* @Description: 学生类的变量和get() set()方法
* @date 2019年3月26日 下午3:50:29
 */
public class Student {
	private int stuId=-1;//学生学号
	private String stuName;//学生姓名
	private String stuPassword;//学生密码
	private String stuSex;//学生性别
	private int stuAge;//学生年龄
	private String stuTel;//学生电话
	private String stuGrade;//学生年级
	private String stuMajor;//学生专业
	private int userTypeId=2;//学生类别Id
	private int isDelete=0;
	
	
	
	
	
	
	
	
	/*无参数的构造方法*/
	public Student() {
		super();
		// TODO Auto-generated constructor stub
	}
	/*带参数的构造方法*/
	public Student(int stuId,String stuName, String stuPassword, String stuSex, int stuAge, String stuTel,
			String stuGrade, String stuMajor) {
		super();
		this.stuId=stuId;
		this.stuName = stuName;
		this.stuPassword = stuPassword;
		this.stuSex = stuSex;
		this.stuAge = stuAge;
		this.stuTel = stuTel;
		this.stuGrade = stuGrade;
		this.stuMajor = stuMajor;
	}
	public Student(String stuName, String stuPassword, String stuSex, int stuAge, String stuTel, String stuGrade,
			String stuMajor) {
		super();
		this.stuName = stuName;
		this.stuPassword = stuPassword;
		this.stuSex = stuSex;
		this.stuAge = stuAge;
		this.stuTel = stuTel;
		this.stuGrade = stuGrade;
		this.stuMajor = stuMajor;
	}
	//获取学号
	public int getStuId() {
		return stuId;
	}
	//设置学号
	public void setStuId(int stuId) {
		this.stuId = stuId;
	}
	//获取姓名
	public String getStuName() {
		return stuName;
	}
	//设置姓名
	public void setStuName(String stuName) {
		this.stuName = stuName;
	}
	//获取密码
	public String getStuPassword() {
		return stuPassword;
	}
	//设置密码
	public void setStuPassword(String stuPassword) {
		this.stuPassword = stuPassword;
	}
	//获取性别
	public String getStuSex() {
		return stuSex;
	}
	//设置性别
	public void setStuSex(String stuSex) {
		this.stuSex = stuSex;
	}
	//获取年龄
	public int getStuAge() {
		return stuAge;
	}
	//设置年龄
	public void setStuAge(int stuAge) {
		this.stuAge = stuAge;
	}
	//获取电话
	public String getStuTel() {
		return stuTel;
	}
	//设置电话
	public void setStuTel(String stuTel) {
		this.stuTel = stuTel;
	}
	//获取年级
	public String getStuGrade() {
		return stuGrade;
	}
	//设置年级
	public void setStuGrade(String stuGrade) {
		this.stuGrade = stuGrade;
	}
	//获取专业
	public String getStuMajor() {
		return stuMajor;
	}
	//设置专业
	public void setStuMajor(String stuMajor) {
		this.stuMajor = stuMajor;
	}
	public int getIsDelete() {
		return isDelete;
	}
	public void setIsDelete(int isDelete) {
		this.isDelete = isDelete;
	}
}
