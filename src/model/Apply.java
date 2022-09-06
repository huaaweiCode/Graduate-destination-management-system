package model;

import java.util.Date;

/**
 * 
* @author ���÷
* @ClassName java.mjm.model.Apply
* @Description: ������ı�����get() set()����
* @date 2019��3��26�� ����3:52:15
 */
public class Apply {
	private int applyId;//�����Id
	private int applyJobId;//����Ĺ���Id
	private Date applyDate;//���������
	private int applyStuId;//�����ѧ��Id
	private int applyFileId;//����Id
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
	//��ȡ�����Id
	public int getApplyId() {
		return applyId;
	}
	//���������Id
	public void setApplyId(int applyId) {
		this.applyId = applyId;
	}
	//��ȡ���빤����Id
	public int getApplyJobId() {
		return applyJobId;
	}
	//�������빤����Id
	public void setApplyJobId(int applyJobId) {
		this.applyJobId = applyJobId;
	}
	//��ȡ���������
	public Date getApplyDate() {
		return applyDate;
	}
	//�������������
	public void setApplyDate(Date applyDate) {
		this.applyDate = applyDate;
	}
	//��ȡ����ѧ����Id
	public int getApplyStuId() {
		return applyStuId;
	}
	//���������ѧ��Id
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
