package model;

import java.util.Date;

/**
 * 
* @author ���÷
* @ClassName java.mjm.model.Notice
* @Description: ������ı�����get() set()����
* @date 2019��3��26�� ����3:52:53
 */
public class Notice {
	private int noticeId=-1;//����Id
	private String noticeTitle;//����ı���
	private Date noticeDate;//���������
	private String noticeContent;//���������
	private int noticeAdminId=-1;//����Ĺ���ԱId
	private int isDelete=0;
	
	
	
	
	public int getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(int isDelete) {
		this.isDelete = isDelete;
	}

	public Notice(String noticeTitle, String noticeContent, int noticeAdminId) {
		super();
		this.noticeTitle = noticeTitle;
		this.noticeContent = noticeContent;
		this.noticeAdminId = noticeAdminId;
	}

	public Notice() {
		super();
		// TODO Auto-generated constructor stub
	}
	public int getNoticeId() {
		return noticeId;
	}
	public void setNoticeId(int noticeId) {
		this.noticeId = noticeId;
	}
	public String getNoticeTitle() {
		return noticeTitle;
	}
	public void setNoticeTitle(String noticeTitle) {
		this.noticeTitle = noticeTitle;
	}
	public Date getNoticeDate() {
		return noticeDate;
	}
	public void setNoticeDate(Date noticeDate) {
		this.noticeDate = noticeDate;
	}
	public String getNoticeContent() {
		return noticeContent;
	}
	public void setNoticeContent(String noticeContent) {
		this.noticeContent = noticeContent;
	}
	public int getNoticeAdminId() {
		return noticeAdminId;
	}
	public void setNoticeAdminId(int noticeAdminId) {
		this.noticeAdminId = noticeAdminId;
	}
	
}
