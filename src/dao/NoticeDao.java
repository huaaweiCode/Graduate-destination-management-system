package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import model.Notice;

/**
 * 
* @author ���÷
* @ClassName dao.NoticeDao
* @Description:������  
* @date 2019��3��30�� ����4:34:27
 */
public class NoticeDao {
	public List<Notice> noticeList(Connection con)throws Exception{
		PreparedStatement ppt=con.prepareStatement("SELECT * FROM t_notice where isDelete=0 ORDER BY noticeDate DESC");//����ʱ�併���ȡ���еĹ��棡
		List<Notice>noticeLists=new ArrayList<Notice>();
		ResultSet  rs=ppt.executeQuery();
		while(rs.next()){
			Notice notice=new Notice();
			notice.setNoticeId(Integer.parseInt(rs.getString("noticeId")));
			notice.setNoticeTitle(rs.getString("noticeTitle"));
			notice.setNoticeDate(rs.getDate("noticeDate"));
			notice.setNoticeContent(rs.getString("noticeContent"));
			notice.setNoticeAdminId(Integer.parseInt(rs.getString("noticeAdminId")));
			noticeLists.add(notice);
		}
		return noticeLists;
	}
	public List<Notice> noticeByAdminIdList(Connection con, int adminId)throws Exception{
		PreparedStatement ppt=con.prepareStatement("SELECT * FROM t_notice where noticeAdminId=? and isDelete=0 ORDER BY noticeDate DESC");//����ʱ�併���ȡ���еĹ��棡
		List<Notice>noticeLists=new ArrayList<Notice>();
		ppt.setInt(1,adminId);
		ResultSet  rs=ppt.executeQuery();
		while(rs.next()){
			Notice notice=new Notice();
			notice.setNoticeId(Integer.parseInt(rs.getString("noticeId")));
			notice.setNoticeTitle(rs.getString("noticeTitle"));
			notice.setNoticeDate(rs.getDate("noticeDate"));
			notice.setNoticeContent(rs.getString("noticeContent"));
			notice.setNoticeAdminId(Integer.parseInt(rs.getString("noticeAdminId")));
			noticeLists.add(notice);
		}
		return noticeLists;
	}
	public Notice noticeSelect(Connection con,String noticeId)throws Exception{
		PreparedStatement ppt=con.prepareStatement("select * from t_notice where noticeId=? and isDelete=0 ");
		ppt.setString(1,noticeId);
		ResultSet rs=ppt.executeQuery();
		Notice notice=new Notice();
		if(rs.next()){
			notice.setNoticeId(Integer.parseInt(noticeId));
			notice.setNoticeTitle(rs.getString("noticeTitle"));
			notice.setNoticeDate(rs.getDate("noticeDate"));
			notice.setNoticeContent(rs.getString("noticeContent"));
			notice.setNoticeAdminId(rs.getInt("noticeAdminId"));
		}
		return notice;
	}
	public int noticeAdd(Connection con,Notice notice)throws Exception{
		PreparedStatement ppt=con.prepareStatement("insert into t_notice values(null,?,now(),?,?,0)");
		ppt.setString(1, notice.getNoticeTitle());
		ppt.setString(2, notice.getNoticeContent());
		ppt.setInt(3, notice.getNoticeAdminId());
		return ppt.executeUpdate();
	}
	public int noticeUpdate(Connection con,Notice notice)throws Exception{
		PreparedStatement ppt=con.prepareStatement("update t_notice set noticeTitle=?,noticeContent=?,noticeDate=now() where noticeId=?");
		ppt.setString(1, notice.getNoticeTitle());
		ppt.setString(2, notice.getNoticeContent());
		ppt.setInt(3, notice.getNoticeId());
		return ppt.executeUpdate();
	}
	public int noticeDelete(Connection con,int noticeId)throws Exception{
		PreparedStatement ppt=con.prepareStatement("update t_notice set isDelete=1 where noticeId=?");
		ppt.setInt(1, noticeId);
		return ppt.executeUpdate();
	}
	
	// ģ����ѯ
	public List<Notice> noticeSearch(Connection con,String content)throws Exception{
		PreparedStatement ppt=con.prepareStatement("SELECT * FROM t_notice where noticeContent like '%"+content+"%' and isDelete=0 ORDER BY noticeDate DESC");//
		////% ����ʾ����0�������ַ�����ƥ���������ͺͳ��ȵ��ַ�����Щ������������ģ���ʹ�������ٷֺţ�%%����ʾ��
		List<Notice>noticeLists=new ArrayList<Notice>();
		ResultSet  rs=ppt.executeQuery();
		while(rs.next()){
			Notice notice=new Notice();
			notice.setNoticeId(Integer.parseInt(rs.getString("noticeId")));
			notice.setNoticeTitle(rs.getString("noticeTitle"));
			notice.setNoticeDate(rs.getDate("noticeDate"));
			notice.setNoticeContent(rs.getString("noticeContent"));
			notice.setNoticeAdminId(Integer.parseInt(rs.getString("noticeAdminId")));
			noticeLists.add(notice);
		}
		return noticeLists;
	}
	
	
}
