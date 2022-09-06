package model;

import java.util.Date;

/**
 * 
* @author 马金梅
* @ClassName java.mjm.model.Jobs
* @Description: 工作类的变量和get() set()方法
* @date 2019年3月26日 下午3:51:53
 */
public class Jobs {
	private int jobId;//工作的Id
	private String jobtitle;//工作标题
	private int jobCompanyId;//工作公司Id
	private String jobContent;//工作内容
	private String companyName;//公司名称
	private Date jobDate;
	private int isDelete=0;
	private int isPass=0;//是否审核通过
	
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
	//获取job
	public int getJobId() {
		return jobId;
	}
	//设置job
	public void setJobId(int jobId) {
		this.jobId = jobId;
	}
	public Date getJobDate() {
		return jobDate;
	}
	public void setJobDate(Date jobDate) {
		this.jobDate = jobDate;
	}
	//获取工作标题
	public String getJobtitle() {
		return jobtitle;
	}
	//设置工作标题
	public void setJobtitle(String jobtitle) {
		this.jobtitle = jobtitle;
	}
	//获取公司Id
	public int getJobCompanyId() {
		return jobCompanyId;
	}
	//设置公司id
	public void setJobCompanyId(int jobCompanyId) {
		this.jobCompanyId = jobCompanyId;
	}
	//获取工作内容
	public String getJobContent() {
		return jobContent;
	}
	//设置工作内容
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
