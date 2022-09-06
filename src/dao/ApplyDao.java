package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import model.Apply;

public class ApplyDao {
	public int applyadd(Connection con,Apply apply)throws Exception{
		String sql="insert into t_apply values(null,?,now(),?,?,0)";
		PreparedStatement ppt=con.prepareStatement(sql);
		ppt.setInt(1, apply.getApplyJobId());
		ppt.setInt(2, apply.getApplyStuId());
		ppt.setInt(3, apply.getApplyFileId());
		return ppt.executeUpdate();
	}
	public List<Apply> applyselect(Connection con,int applyJobId)throws Exception{
		PreparedStatement ppt=con.prepareStatement("SELECT * FROM t_apply WHERE applyJobId=? and isDelete=0 ");
		//要申请那个工作的id选择出来
		ppt.setInt(1,applyJobId);
		ResultSet  rs=ppt.executeQuery();
		List<Apply>applyList=new ArrayList<Apply>();
		while(rs.next()){
			Apply apply=new Apply();
			apply.setApplyId(rs.getInt("applyId"));
			apply.setApplyDate(rs.getDate("applyDate"));
			apply.setApplyFileId(rs.getInt("applyFileId"));
			apply.setApplyJobId(rs.getInt("applyJobId"));
			apply.setApplyStuId(rs.getInt("applyStuId"));
			applyList.add(apply);
		}
		return applyList;
	}
	public int applyDelete(Connection con, int applyId) throws Exception{
		String sql="update t_apply set isDelete=1 where applyId=?";
		PreparedStatement ppt=con.prepareStatement(sql);
		ppt.setInt(1, applyId);
		return ppt.executeUpdate();
	}
	
}
