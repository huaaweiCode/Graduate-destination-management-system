package model;
/**
 * 
* @author ���÷
* @ClassName java.mjm.model.Admin
* @Description: ����Ա��ı�����get() set()����
* @date 2019��3��26�� ����3:51:08
 */
public class Admin {
	private int  adminId=-1;//����ԱId
	private String adminName;//����Ա����
	private String adminPassword;//����Ա����
	public int userTypeId=3;
	private int isDelete=0;
	public Admin() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Admin(String adminName, String adminPassword) {
		super();
		this.adminName = adminName;
		this.adminPassword = adminPassword;
	}
	//��ȡ����ԱId
	public int getAdminId() {
		return adminId;
	}
	//���ù���ԱId
	public void setAdminId(int adminId) {
		this.adminId = adminId;
	}
	//��ȡ����Ա����
	public String getAdminName() {
		return adminName;
	}
	//���ù���Ա����
	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}
	//��ȡ����Ա����
	public String getAdminPassword() {
		return adminPassword;
	}
	//���ù���Ա����
	public void setAdminPassword(String adminPassword) {
		this.adminPassword = adminPassword;
	}
	public int getIsDelete() {
		return isDelete;
	}
	public void setIsDelete(int isDelete) {
		this.isDelete = isDelete;
	}
	
}
