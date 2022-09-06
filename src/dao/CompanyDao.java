package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import model.Admin;
import model.Apply;
import model.Company;
import model.Jobs;

/**
 * 
* @author ���÷
* @ClassName dao.CompanyDao
* @Description: ��˾���ݿⷽ��
* @date 2019��3��27�� ����7:35:31
 */
public class CompanyDao {
			//��ȡ��˾       ��˾��֤��¼
	public Company companySelect(Connection con,String companyName,String companyPassword)
			throws Exception{
		String sql="select * from t_company where companyName=? and companyPassword=? and isDelete=0 ";
		PreparedStatement ppt=con.prepareStatement(sql);
		ppt.setString(1, companyName);
		ppt.setString(2, companyPassword);
		ResultSet rs=ppt.executeQuery();
		Company company=null;
		if(rs.next()){
			company=new Company();
			company.setCompanyId(rs.getInt("companyId"));
			company.setCompanyName(rs.getString("companyName"));
			company.setCompanyContent(rs.getString("companyContent"));
			company.setCompanyPassword(rs.getString("companyPassword"));
		}
		return company;
	}
		/* ��˾ע�Ṧ�ܣ�         ��ӹ�˾*/
	public int companyAdd(Connection con,Company company)throws Exception{
		String sql="insert into t_company values(null,?,?,?,3,0)";
		PreparedStatement ppt=con.prepareStatement(sql);
		ppt.setString(1, company.getCompanyName());
		ppt.setString(2, company.getCompanyPassword());
		ppt.setString(3, company.getCompanyContent());
		return ppt.executeUpdate();
	}
	//��˾�޸��Լ�����Ϣ
	public int companyUpdate(Connection con,Company company)throws Exception{
		String sql="update t_company set companyName=?,companyPassword=?,companyContent=? where companyId=? ";
		PreparedStatement ppt=con.prepareStatement(sql);
		ppt.setString(1, company.getCompanyName());
		ppt.setString(2, company.getCompanyPassword());
		ppt.setString(3, company.getCompanyContent());
		ppt.setInt(4, company.getCompanyId());
		return ppt.executeUpdate();
	}
	//��װ��˾����Ϣ
	public List<Company> companyList(Connection con)throws Exception{
		String sql="SELECT * FROM t_company WHERE isDelete=0 ";
		PreparedStatement ppt=con.prepareStatement(sql);
		ResultSet rs=ppt.executeQuery();
		List<Company> companylists=new ArrayList<Company>();
		while(rs.next()){
			Company company=new Company();
			company=new Company();
			company.setCompanyId(rs.getInt("companyId"));
			company.setCompanyName(rs.getString("companyName"));
			company.setCompanyContent(rs.getString("companyContent"));
			company.setCompanyPassword(rs.getString("companyPassword"));
			companylists.add(company);
		}
		return companylists;
	}
	//ɾ����˾
	public int companyDelete(Connection con,int companyId)throws Exception{
		String sql="update t_company set isDelete=1 where companyId=?";
		PreparedStatement ppt=con.prepareStatement(sql);
		ppt.setInt(1, companyId);
		return ppt.executeUpdate();
	}
	//��װ��������Ϣ
	public List<Jobs> jobSelect(Connection con,int companyId)throws Exception{
		String sql="select * from t_jobs where jobCompanyId=? and isDelete=0 ";
		PreparedStatement ppt=con.prepareStatement(sql);
		ppt.setInt(1, companyId);
		ResultSet rs=ppt.executeQuery();
		List<Jobs>joblists =new ArrayList<Jobs>();
		while(rs.next()){
			Jobs job=new Jobs();
			job.setJobId(rs.getInt("jobId"));
			job.setJobtitle(rs.getString("jobtitle"));
			job.setJobDate(rs.getDate("jobDate"));
			job.setJobCompanyId(rs.getInt("jobCompanyId"));
			job.setJobContent(rs.getString("jobContent"));
			job.setIsPass(rs.getInt("isPass"));
			joblists.add(job);
		}
		return joblists;
	}
	public List<Jobs> jobAdminSelect(Connection con)throws Exception{
		String sql="select * from t_jobs where isDelete=0 ";
		PreparedStatement ppt=con.prepareStatement(sql);
		ResultSet rs=ppt.executeQuery();
		List<Jobs>joblists =new ArrayList<Jobs>();
		while(rs.next()){
			Jobs job=new Jobs();
			job.setJobId(rs.getInt("jobId"));
			job.setJobtitle(rs.getString("jobtitle"));
			job.setJobDate(rs.getDate("jobDate"));
			job.setJobCompanyId(rs.getInt("jobCompanyId"));
			job.setJobContent(rs.getString("jobContent"));
			job.setIsPass(rs.getInt("isPass"));
			joblists.add(job);
		}
		return joblists;
	}
	//��ѯ����δ��ɾ����ӦƸ��Ϣ
	public List<Apply> getApplyLists(Connection con)throws Exception{
		String sql="SELECT * FROM t_apply WHERE applyJobId IN(SELECT jobId FROM t_jobs) and isDelete=0 ";
		List<Apply>applyLists=new ArrayList<Apply>();
		PreparedStatement ppt=con.prepareStatement(sql);
		ResultSet rs=ppt.executeQuery();
		while(rs.next()){
			Apply apply =new Apply();
			apply.setApplyId(rs.getInt("applyId"));
			apply.setApplyDate(rs.getDate("applyDate"));
			apply.setApplyFileId(rs.getInt("applyFileId"));
			apply.setApplyJobId(rs.getInt("applyJobId"));
			apply.setApplyStuId(rs.getInt("applyStuId"));
			applyLists.add(apply);
		}
		return applyLists;
	}
	
	
	
	//���ݹ�˾�������е���������
	public List<Apply> getApplyList(Connection con,int jobCompanyId)throws Exception{
		String sql="SELECT * FROM t_apply WHERE applyJobId IN(SELECT jobId FROM t_jobs WHERE jobCompanyId=?) and isDelete=0 ";
		 //�Ȳ�����Ĺ�����λ����ȥ��������������Ǹ���˾������
		List<Apply>applyLists=new ArrayList<Apply>();
		PreparedStatement ppt=con.prepareStatement(sql);
		ppt.setInt(1, jobCompanyId);
		ResultSet rs=ppt.executeQuery();
		while(rs.next()){
			Apply apply =new Apply();
			apply.setApplyId(rs.getInt("applyId"));
			apply.setApplyDate(rs.getDate("applyDate"));
			apply.setApplyFileId(rs.getInt("applyFileId"));
			apply.setApplyJobId(rs.getInt("applyJobId"));
			apply.setApplyStuId(rs.getInt("applyStuId"));
			applyLists.add(apply);
		}
		return applyLists;
	}
	public boolean companyexist(Connection con,String companyName)throws Exception{
		PreparedStatement ppt=con.prepareStatement("select * from t_company where isDelete=0 and companyName= '"+companyName+"' ");
		ResultSet rs=ppt.executeQuery();
		if(rs.next()){
			return true;
		}else{
			return false;
		}
	}
	public Company companySelectById(Connection con,int companyId)throws Exception{
		PreparedStatement ppt=con.prepareStatement("select * from t_company where isDelete=0 and companyId= ?");
		ppt.setInt(1, companyId);
		ResultSet rs=ppt.executeQuery();
		Company company=new Company();
		if(rs.next()){
			company.setCompanyId(rs.getInt("companyId"));
			company.setCompanyName(rs.getString("companyName"));
			company.setCompanyContent(rs.getString("companyContent"));
			company.setCompanyPassword(rs.getString("companyPassword"));
			company.setIsDelete(rs.getInt("isDelete"));
		}
		return company;
	}
	
	public Jobs jobInfoById(Connection con,String jobId)throws Exception{
		String sql="select * from t_jobs where jobId=? and isDelete=0 ";
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
			job.setIsPass(rs.getInt("isPass"));
		}
		return job;
	}
}
