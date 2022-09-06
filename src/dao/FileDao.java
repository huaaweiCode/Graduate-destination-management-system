package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import model.Files;

/**
 * 
* @author 马金梅
* @ClassName dao.FileDao
* @Description: TODO
* @date 2019年4月2日 上午10:31:49
 */
public class FileDao {
	public int fileadd(Connection con,Files file)throws Exception{
		String sql="insert into t_file values(null,?,?,0)";
		PreparedStatement ppt=con.prepareStatement(sql);
		ppt.setString(1, file.getFileTitle());
		ppt.setInt(2, file.getStuId());
		return ppt.executeUpdate();
	}
	public Files fileBytitle(Connection con,String filetitle)throws Exception{
		String sql="SELECT * FROM t_file WHERE isDelete=0 and fileTitle LIKE  '"+filetitle+"%'";
		PreparedStatement ppt=con.prepareStatement(sql);
		ResultSet rs=ppt.executeQuery();
		Files files=new Files();
		if(rs.next()){
			files.setFileId(rs.getInt("fileId"));
			files.setFileTitle(rs.getString("fileTitle"));
			files.setStuId(rs.getInt("stuId"));
		}
		return files;
	}
	public List<Files> fileBystuId(Connection con,int stuId)throws Exception{
		String sql="SELECT * FROM t_file WHERE stuId=? and isDelete=0";
		PreparedStatement ppt=con.prepareStatement(sql);
		ppt.setInt(1, stuId);
		ResultSet rs=ppt.executeQuery();
		List<Files>fileLists=new ArrayList<Files>();
		while(rs.next()){
			Files files=new Files();
			files.setFileId(rs.getInt("fileId"));
			files.setFileTitle(rs.getString("fileTitle"));
			files.setStuId(rs.getInt("stuId"));
			fileLists.add(files);
		}
		return fileLists;
	}
	public String filetitle(Connection con,int fileId)throws Exception{
		String sql="SELECT fileTitle FROM t_file WHERE fileId=? and isDelete=0";
		PreparedStatement ppt=con.prepareStatement(sql);
		ppt.setInt(1,fileId);
		ResultSet rs=ppt.executeQuery();
		String  filetitle="";
		if(rs.next()){
			filetitle=rs.getString("fileTitle");
		}
		return filetitle;
	}
}
