package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import model.Jobs;
import model.Notice;
public class JobDao {
	public List<Jobs> noticeList(Connection con)throws Exception{
		PreparedStatement ppt=con.prepareStatement("SELECT companyName,jobId,jobtitle,jobContent,jobDate,isPass FROM t_jobs t1,t_company t2 WHERE t1.`jobCompanyId`=t2.`companyId` and t2.isDelete=0 and t1.isPass=1 and t1.isDelete=0 ORDER BY jobDate DESC;");
		//查询companyId jobId jobtitle jobContent 
		//查询出来那个公司发布的招聘信息
		List<Jobs> jobsLists =new ArrayList<Jobs>();  //定义泛型List容器
		ResultSet  rs=ppt.executeQuery();
		while(rs.next()){
			Jobs job=new Jobs();
			job.setJobId(Integer.parseInt(rs.getString("jobId")));
			job.setJobtitle(rs.getString("jobtitle"));
			job.setJobContent(rs.getString("jobContent"));
			job.setCompanyName(rs.getString("companyName"));
			job.setJobDate(rs.getDate("jobDate"));
			job.setIsPass(rs.getInt("isPass"));
			jobsLists.add(job);
		}
		return jobsLists;
	}
	
	public Jobs jobListById(Connection con,String jobId)throws Exception{
		String sql="select * from t_jobs where jobId=? and isDelete=0 "; 
		//把这个工作选择出来
		PreparedStatement ppt=con.prepareStatement(sql);
		ppt.setInt(1, Integer.parseInt(jobId));
		ResultSet rs=ppt.executeQuery();
		Jobs job=new Jobs();
		if(rs.next()){
			job.setJobtitle(rs.getString("jobtitle"));
			job.setJobCompanyId(rs.getInt("jobCompanyId"));
			job.setJobContent(rs.getString("jobContent"));
			job.setJobDate(rs.getDate("jobDate"));
			job.setJobId(rs.getInt("jobId"));
		}
		return job;
	}
	public int jobUpdate(Connection con,Jobs job)throws Exception{
		String sql="update t_jobs set jobContent=?,jobtitle=? where jobId=?";
		PreparedStatement ppt=con.prepareStatement(sql);
		ppt.setString(1, job.getJobContent());
		ppt.setString(2, job.getJobtitle());
		ppt.setInt(3, job.getJobId());
		return ppt.executeUpdate();
	}
	public int jobAdd(Connection con,Jobs job)throws Exception{
		String sql="insert into t_jobs values(null,?,now(),?,?,0,0)";
		PreparedStatement ppt=con.prepareStatement(sql);
		ppt.setString(1, job.getJobtitle());
		ppt.setInt(2, job.getJobCompanyId());
		ppt.setString(3, job.getJobContent());
		return ppt.executeUpdate();
	}
	public List<Jobs> jobsList(Connection con)throws Exception{
		String sql="select * from t_jobs where isDelete=0 and isPass=1 ";
		PreparedStatement ppt=con.prepareStatement(sql);
		List<Jobs>jobLists=new ArrayList<Jobs>();
		ResultSet rs=ppt.executeQuery();
		while(rs.next()){
			Jobs job=new Jobs();
			job.setJobtitle(rs.getString("jobtitle"));
			job.setJobCompanyId(rs.getInt("jobCompanyId"));
			job.setJobContent(rs.getString("jobContent"));
			job.setJobDate(rs.getDate("jobDate"));
			job.setJobId(rs.getInt("jobId"));
			jobLists.add(job);
		}
		return jobLists;
	}
	public int jobsDelete(Connection con,int jobId)throws Exception{
		String sql="update t_jobs set isDelete=1 where jobId=?";
		PreparedStatement ppt=con.prepareStatement(sql);
		ppt.setInt(1, jobId);
		return ppt.executeUpdate();
	}
	public List<Jobs> jobSearch(Connection con,String content)throws Exception{
		PreparedStatement ppt=con.prepareStatement("SELECT companyName,jobId,jobtitle,jobContent,jobDate FROM t_jobs t1,t_company t2 WHERE t1.`jobCompanyId`=t2.`companyId` and jobtitle like '%"+content+"%' and t2.isDelete=0 ORDER BY jobDate DESC;");
		//查询companyId jobId jobtitle jobContent 
		List<Jobs> jobsLists =new ArrayList<Jobs>();
		ResultSet  rs=ppt.executeQuery();
		while(rs.next()){
			Jobs job=new Jobs();
			job.setJobId(Integer.parseInt(rs.getString("jobId")));
			job.setJobtitle(rs.getString("jobtitle"));
			job.setJobContent(rs.getString("jobContent"));
			job.setCompanyName(rs.getString("companyName"));
			job.setJobDate(rs.getDate("jobDate"));
			jobsLists.add(job);
		}
		return jobsLists;
	}
}
