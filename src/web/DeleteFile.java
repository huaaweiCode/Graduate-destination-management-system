package web;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class DeleteFile {
	//删除简历
	public int deleteFile(Connection con,int fileId)throws Exception{
		String sql="delete t_file where fileId=?";
		PreparedStatement ppt=con.prepareStatement(sql);
		ppt.setInt(1, fileId);
		return ppt.executeUpdate();
	}
	//删除简历的时候要把申请去掉 applyFileId 
	public int deleteApply(Connection con,int fileId)throws Exception{
		String sql="delete t_apply where applyFileId=?";
		PreparedStatement ppt=con.prepareStatement(sql);
		ppt.setInt(1, fileId);
		return ppt.executeUpdate();
	}
	
}
