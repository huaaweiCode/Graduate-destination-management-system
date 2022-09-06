package model;

import java.util.Date;

/**
 * 
* @author 马金梅
* @ClassName java.mjm.model.Apply
* @Description: 申请类的变量和get() set()方法
* @date 2019年3月26日 下午3:52:15
 */
public class Apply {
	private int applyId;//申请的Id
	private int applyJobId;//申请的工作Id
	private Date applyDate;//申请的日期
	private int applyStuId;//申请的学生Id
	private int applyFileId;//简历Id
	private int isDelete=0;

	public Apply() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Apply(int applyJobId, int applyStuId, int applyFileId) {
		super();
		this.applyJobId = applyJobId;
		this.applyStuId = applyStuId;
		this.applyFileId = applyFileId;
	}
	public int getApplyFileId() {
		return applyFileId;
	}
	public void setApplyFileId(int applyFileId) {
		this.applyFileId = applyFileId;
	}
	//获取申请的Id
	public int getApplyId() {
		return applyId;
	}
	//设置申请的Id
	public void setApplyId(int applyId) {
		this.applyId = applyId;
	}
	//获取申请工作的Id
	public int getApplyJobId() {
		return applyJobId;
	}
	//设置申请工作的Id
	public void setApplyJobId(int applyJobId) {
		this.applyJobId = applyJobId;
	}
	//获取申请的日期
	public Date getApplyDate() {
		return applyDate;
	}
	//设置申请的日期
	public void setApplyDate(Date applyDate) {
		this.applyDate = applyDate;
	}
	//获取申请学生的Id
	public int getApplyStuId() {
		return applyStuId;
	}
	//设置申请的学生Id
	public void setApplyStuId(int applyStuId) {
		this.applyStuId = applyStuId;
	}
	public int getIsDelete() {
		return isDelete;
	}
	public void setIsDelete(int isDelete) {
		this.isDelete = isDelete;
	}
		
}
