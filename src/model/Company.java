package model;
/**
 * 
* @author 马金梅
* @ClassName java.mjm.model.Company
* @Description: 公司类的变量和get() set()方法
* @date 2019年3月26日 下午3:51:30
 */
public class Company {
	private int companyId;//公司Id
	private String companyName;//公司名称
	private String companyPassword;//公司登录密码
	private String companyContent;//公司的描述信息
	private int isDelete=0;
	
	
	
	public int getIsDelete() {
		return isDelete;
	}
	public void setIsDelete(int isDelete) {
		this.isDelete = isDelete;
	}
	public Company() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Company(String companyName, String companyPassword, String companyContent) {
		super();
		this.companyName = companyName;
		this.companyPassword = companyPassword;
		this.companyContent = companyContent;
	}
	//获取公司的Id
	public int getCompanyId() {
		return companyId;
	}
	//设置公司的Id
	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}
	//获取公司的名称
	public String getCompanyName() {
		return companyName;
	}
	//设置公司的名称
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	//获取公司的的登录密码
	public String getCompanyPassword() {
		return companyPassword;
	}
	//设置公司的的登录密码
	public void setCompanyPassword(String companyPassword) {
		this.companyPassword = companyPassword;
	}
	//获取公司的描述信息
	public String getCompanyContent() {
		return companyContent;
	}
	//设置公司的描述信息
	public void setCompanyContent(String companyContent) {
		this.companyContent = companyContent;
	}
	
}
