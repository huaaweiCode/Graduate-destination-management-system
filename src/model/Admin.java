package model;
/**
 * 
* @author 马金梅
* @ClassName java.mjm.model.Admin
* @Description: 管理员类的变量和get() set()方法
* @date 2019年3月26日 下午3:51:08
 */
public class Admin {
	private int  adminId=-1;//管理员Id
	private String adminName;//管理员姓名
	private String adminPassword;//管理员密码
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
	//获取管理员Id
	public int getAdminId() {
		return adminId;
	}
	//设置管理员Id
	public void setAdminId(int adminId) {
		this.adminId = adminId;
	}
	//获取管理员姓名
	public String getAdminName() {
		return adminName;
	}
	//设置管理员姓名
	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}
	//获取管理员密码
	public String getAdminPassword() {
		return adminPassword;
	}
	//设置管理员密码
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
