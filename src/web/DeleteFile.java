package web;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class DeleteFile {
	//ɾ������
	public int deleteFile(Connection con,int fileId)throws Exception{
		String sql="delete t_file where fileId=?";
		PreparedStatement ppt=con.prepareStatement(sql);
		ppt.setInt(1, fileId);
		return ppt.executeUpdate();
	}
	//ɾ��������ʱ��Ҫ������ȥ�� applyFileId 
	public int deleteApply(Connection con,int fileId)throws Exception{
		String sql="delete t_apply where applyFileId=?";
		PreparedStatement ppt=con.prepareStatement(sql);
		ppt.setInt(1, fileId);
		return ppt.executeUpdate();
	}
	
}
