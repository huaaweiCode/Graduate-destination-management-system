package model;
/**
 * 
* @author ���÷
* @ClassName java.mjm.model.Company
* @Description: ��˾��ı�����get() set()����
* @date 2019��3��26�� ����3:51:30
 */
public class Company {
	private int companyId;//��˾Id
	private String companyName;//��˾����
	private String companyPassword;//��˾��¼����
	private String companyContent;//��˾��������Ϣ
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
	//��ȡ��˾��Id
	public int getCompanyId() {
		return companyId;
	}
	//���ù�˾��Id
	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}
	//��ȡ��˾������
	public String getCompanyName() {
		return companyName;
	}
	//���ù�˾������
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	//��ȡ��˾�ĵĵ�¼����
	public String getCompanyPassword() {
		return companyPassword;
	}
	//���ù�˾�ĵĵ�¼����
	public void setCompanyPassword(String companyPassword) {
		this.companyPassword = companyPassword;
	}
	//��ȡ��˾��������Ϣ
	public String getCompanyContent() {
		return companyContent;
	}
	//���ù�˾��������Ϣ
	public void setCompanyContent(String companyContent) {
		this.companyContent = companyContent;
	}
	
}
