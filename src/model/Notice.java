package model;

import java.util.Date;

/**
 * 
* @author 马金梅
* @ClassName java.mjm.model.Notice
* @Description: 公告类的变量和get() set()方法
* @date 2019年3月26日 下午3:52:53
 */
public class Notice {
	private int noticeId=-1;//公告Id
	private String noticeTitle;//公告的标题
	private Date noticeDate;//公告的日期
	private String noticeContent;//公告的内容
	private int noticeAdminId=-1;//公告的管理员Id
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
