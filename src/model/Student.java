package model;
/**
 * 
* @author ���÷
* @ClassName java.mjm.model.Student
* @Description: ѧ����ı�����get() set()����
* @date 2019��3��26�� ����3:50:29
 */
public class Student {
	private int stuId=-1;//ѧ��ѧ��
	private String stuName;//ѧ������
	private String stuPassword;//ѧ������
	private String stuSex;//ѧ���Ա�
	private int stuAge;//ѧ������
	private String stuTel;//ѧ���绰
	private String stuGrade;//ѧ���꼶
	private String stuMajor;//ѧ��רҵ
	private int userTypeId=2;//ѧ�����Id
	private int isDelete=0;
	
	
	
	
	
	
	
	
	/*�޲����Ĺ��췽��*/
	public Student() {
		super();
		// TODO Auto-generated constructor stub
	}
	/*�������Ĺ��췽��*/
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
	//��ȡѧ��
	public int getStuId() {
		return stuId;
	}
	//����ѧ��
	public void setStuId(int stuId) {
		this.stuId = stuId;
	}
	//��ȡ����
	public String getStuName() {
		return stuName;
	}
	//��������
	public void setStuName(String stuName) {
		this.stuName = stuName;
	}
	//��ȡ����
	public String getStuPassword() {
		return stuPassword;
	}
	//��������
	public void setStuPassword(String stuPassword) {
		this.stuPassword = stuPassword;
	}
	//��ȡ�Ա�
	public String getStuSex() {
		return stuSex;
	}
	//�����Ա�
	public void setStuSex(String stuSex) {
		this.stuSex = stuSex;
	}
	//��ȡ����
	public int getStuAge() {
		return stuAge;
	}
	//��������
	public void setStuAge(int stuAge) {
		this.stuAge = stuAge;
	}
	//��ȡ�绰
	public String getStuTel() {
		return stuTel;
	}
	//���õ绰
	public void setStuTel(String stuTel) {
		this.stuTel = stuTel;
	}
	//��ȡ�꼶
	public String getStuGrade() {
		return stuGrade;
	}
	//�����꼶
	public void setStuGrade(String stuGrade) {
		this.stuGrade = stuGrade;
	}
	//��ȡרҵ
	public String getStuMajor() {
		return stuMajor;
	}
	//����רҵ
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
