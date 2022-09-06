package web;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.CompanyDao;
import dao.FileDao;
import dao.JobDao;
import dao.StudentDao;
import model.Admin;
import model.Apply;
import model.Company;
import model.Jobs;
import model.Notice;
import model.Student;
import net.sf.json.JSONObject;
/**
 * 
* @author ���÷
* @ClassName web.CompanyServlet
* @Description:  ��˾����
* @date 2019��3��31�� ����2:25:02
 */
import util.DataBaseUtil;
public class CompanyServlet extends HttpServlet{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	DataBaseUtil dbUtil=new DataBaseUtil();
	CompanyDao companyDao=new CompanyDao();
	JobDao jobDao=new JobDao();
	StudentDao stuDao=new StudentDao();
	FileDao fileDao=new FileDao();
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		String way=request.getParameter("way");
		if("Modifyinfo".equals(way)){
			this.Modifyinfo(request,response);
		}else if("save".equals(way)){
			this.SaveInfo(request,response);
		}else if("listjobs".equals(way)){
			this.jobLists(request,response);
		}else if("jobPresave".equals(way)){
			this.jobPresave(request,response);
		}else if("jobSave".equals(way)){
			this.jobSave(request, response);
		}else if("apply".equals(way)){
			this.applyList(request,response);
		}else if("downLoad".equals(way)){
			this.downLoad(request,response);
		}else if("jobDelete".equals(way)){
			this.jobDelete(request,response);
		}
	}
	//ɾ������
	protected void jobDelete(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		String jobId=request.getParameter("jobId");
		Connection con=null;
		try {
			con=dbUtil.getCon();
			JSONObject re=new JSONObject();
			int del=jobDao.jobsDelete(con,Integer.parseInt(jobId));
			if(del>0){
				re.put("success", true);
			}else{
				re.put("errorMsg", "ɾ��ʧ��");
			}
			request.setAttribute("re", re);
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out=response.getWriter();
			out.print(re.toString());
			out.flush();
			out.close();
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				dbUtil.closeCon(con);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	//���ؼ���
	protected void downLoad(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		//��ǰ�˻�ȡҪ���ص��ļ���
				request.setCharacterEncoding("utf-8");
				String fileId=request.getParameter("fileId");
				Connection con=null;
				try {
					con=dbUtil.getCon();
					String fileName=fileDao.filetitle(con, Integer.parseInt(fileId));
					//�ϴ����ļ����Ǳ�����/WEB-INF/uploadĿ¼�µ���Ŀ¼��
					String fileSaveRootPath=this.getServletContext().getRealPath("/WebContent/upload");
					//ͨ���ļ����ҵ��ļ�������Ŀ¼
					/*String path=findFileSavePathByFileName(fileName,fileSaveRootPath);*/
					//�õ�Ҫ���ص��ļ�
					File file=new File(fileSaveRootPath+"\\"+fileName);
					//����ļ�������
					if(!file.exists()){
						request.setAttribute("message", "��Ҫ���ص������Ѿ���ɾ���ˣ�");
						request.getRequestDispatcher("/message.jsp").forward(request, response);
						return;
					}
					//�����ļ���
					String realname=fileName.substring(fileName.indexOf("\\")+1);
					//������Ӧͷ��������������ظ��ļ�
					response.setHeader("content-disposition", "attachment;filename="+URLEncoder.encode(realname,"UTF-8"));
					//��ȡҪ���ص��ļ������浽�ļ�������
					FileInputStream in=new FileInputStream(fileSaveRootPath+"\\"+fileName);
					//���������
					OutputStream out=response.getOutputStream();
					//����������
					byte buffer[]=new byte[1024];
					int len=0;
					//ѭ�����������е����ݶ�ȡ������������
					while((len=in.read(buffer))>0){
						out.write(buffer,0,len);
					}
					//�ر��ļ�������
					in.close();
					//�ر��ļ������
					out.close();
					//ͨ���ļ����ʹ洢�ļ��ĸ�Ŀ¼�ҵ�Ҫ���ص��ļ�������·��
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}finally{
					try {
						dbUtil.closeCon(con);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
	}
	//��Ƹ��ʾ
	protected void applyList(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		Connection con=null;
		HttpSession session=request.getSession();
		Company  company=(Company)session.getAttribute("activeUser");//ת��Ϊcompany����
		List<Apply>applyLists=new ArrayList<Apply>();
		int jobCompanyId=company.getCompanyId();
		try {
			con=dbUtil.getCon();
			applyLists=companyDao.getApplyList(con, jobCompanyId);
			List<Student>studentlist=stuDao.studentList(con);
			List<Jobs>jobsLists=jobDao.jobsList(con);
			request.setAttribute("jobsLists", jobsLists);
			request.setAttribute("applyLists", applyLists);
			request.setAttribute("studentlist", studentlist);
			session.setAttribute("activeUser", company);
			request.setAttribute("company", company);
			request.setAttribute("mainPages","/pages/company/companyApply.jsp");
			request.getRequestDispatcher("pages/company/companyIndex.jsp").forward(request, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				dbUtil.closeCon(con);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	//������Ϣ����
	protected void jobPresave(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		Connection con=null;
		HttpSession session=request.getSession();
		Company  company=(Company)session.getAttribute("activeUser");//ת��Ϊcompany����
		String jobId=request.getParameter("jobId");
		try {
			//��ȡ���ݿ�����
			con=dbUtil.getCon();
			if(jobId!=null&&!jobId.equals("")){
				Jobs job=jobDao.jobListById(con, jobId);
				request.setAttribute("job", job);
			}
			session.setAttribute("activeUser", company);
			request.setAttribute("company", company);
			request.setAttribute("mainPages","/pages/company/companyJobSave.jsp");
			request.getRequestDispatcher("pages/company/companyIndex.jsp").forward(request, response);
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			//���� �ر����ݿ�����
			try {
				dbUtil.closeCon(con);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}	
	}
	protected void jobSave(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		HttpSession session=request.getSession();
		Company  company=(Company)session.getAttribute("activeUser");//ת��Ϊcompany����
		String jobId=request.getParameter("jobId");
		String jobtitle=request.getParameter("jobtitle");
		String jobContent=request.getParameter("jobContent");
		int jobCompanyId=company.getCompanyId();
		Jobs job=new Jobs(jobtitle, jobCompanyId, jobContent);
		if(jobId!=null&&!jobId.equals("")){
			job.setJobId(Integer.parseInt(jobId));
		}
		Connection con=null;
		try {
			con=dbUtil.getCon();
			if(jobId!=null&&!jobId.equals("")){
				//����
				jobDao.jobUpdate(con, job);
			}else{
				//���
				jobDao.jobAdd(con, job);
			}
			List<Jobs>jobLists=companyDao.jobSelect(con, company.getCompanyId());
			session.setAttribute("company", company);
			request.setAttribute("activeUser", company);
			request.setAttribute("jobLists", jobLists);
			request.setAttribute("mainPages", "/pages/company/companyJobsLists.jsp");
			request.getRequestDispatcher("pages/company/companyIndex.jsp").forward(request, response);
				
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				dbUtil.closeCon(con);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	//��ʾ���е����ڱ���˾��job
	protected void jobLists(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		HttpSession session=request.getSession();
		Company company=(Company)session.getAttribute("activeUser");
		Connection con=null;
		try {
			con=dbUtil.getCon();
			List<Jobs>jobLists=companyDao.jobSelect(con, company.getCompanyId());
			session.setAttribute("company", company);
			request.setAttribute("activeUser", company);
			request.setAttribute("jobLists", jobLists);
			request.setAttribute("mainPages", "/pages/company/companyJobsLists.jsp");
			request.getRequestDispatcher("pages/company/companyIndex.jsp").forward(request, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	//������Ϣ
	protected void Modifyinfo(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		HttpSession session=request.getSession();
		Company company=(Company)session.getAttribute("activeUser");
		Connection con=null;
		try {
			con=dbUtil.getCon();
			request.setAttribute("company", company);
			session.setAttribute("company", company);
			request.setAttribute("activeUser", company);
			request.setAttribute("mainPages", "/pages/company/companyInfoUpdate.jsp");
			request.getRequestDispatcher("pages/company/companyIndex.jsp").forward(request, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				dbUtil.closeCon(con);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	//������Ϣ
		protected void SaveInfo(HttpServletRequest request, HttpServletResponse response) {
			String companyId=request.getParameter("companyId");
			String companyName=request.getParameter("companyName");
			String companyPassword=request.getParameter("companyPassword");
			String companyContent=request.getParameter("companyContent");
			Company company=new Company(companyName,companyPassword,companyContent);
			Connection con=null;
			if(companyId!=null&&!companyId.equals("")){
				company.setCompanyId(Integer.parseInt(companyId));
			}
			try {
				con=dbUtil.getCon();
				int result=0;
				if(companyId!=null&&!companyId.equals("")){
					result=companyDao.companyUpdate(con, company);
				}
				if(result>0){
					response.setContentType("text/html;charset=utf-8");
					PrintWriter out=response.getWriter();
					out.print("�޸ĳɹ��� ��<a href='login.jsp'>�������</a>�������µ�¼");
					out.flush();
					out.close();
					HttpSession se=request.getSession();
					se.removeAttribute("activeUser");
				}else{
					response.setContentType("text/html;charset=utf-8");
					PrintWriter out=response.getWriter();
					out.print("�޸�ʧ�ܣ�");
					out.flush();
					out.close();
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				try {
					dbUtil.closeCon(con);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}
}
