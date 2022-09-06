package model;

import java.util.Date;

/**
 * 
* @author ���÷
* @ClassName java.mjm.model.Jobs
* @Description: ������ı�����get() set()����
* @date 2019��3��26�� ����3:51:53
 */
public class Jobs {
	private int jobId;//������Id
	private String jobtitle;//��������
	private int jobCompanyId;//������˾Id
	private String jobContent;//��������
	private String companyName;//��˾����
	private Date jobDate;
	private int isDelete=0;
	private int isPass=0;//�Ƿ����ͨ��
	
	public int getIsPass() {
		return isPass;
	}
	public void setIsPass(int isPass) {
		this.isPass = isPass;
	}
	public int getIsDelete() {
		return isDelete;
	}
	public void setIsDelete(int isDelete) {
		this.isDelete = isDelete;
	}
	public Jobs() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Jobs(String jobtitle, int jobCompanyId, String jobContent) {
		super();
		this.jobtitle = jobtitle;
		this.jobCompanyId = jobCompanyId;
		this.jobContent = jobContent;
	}
	//��ȡjob
	public int getJobId() {
		return jobId;
	}
	//����job
	public void setJobId(int jobId) {
		this.jobId = jobId;
	}
	public Date getJobDate() {
		return jobDate;
	}
	public void setJobDate(Date jobDate) {
		this.jobDate = jobDate;
	}
	//��ȡ��������
	public String getJobtitle() {
		return jobtitle;
	}
	//���ù�������
	public void setJobtitle(String jobtitle) {
		this.jobtitle = jobtitle;
	}
	//��ȡ��˾Id
	public int getJobCompanyId() {
		return jobCompanyId;
	}
	//���ù�˾id
	public void setJobCompanyId(int jobCompanyId) {
		this.jobCompanyId = jobCompanyId;
	}
	//��ȡ��������
	public String getJobContent() {
		return jobContent;
	}
	//���ù�������
	public void setJobContent(String jobContent) {
		this.jobContent = jobContent;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	
}
