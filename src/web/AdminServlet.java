package web;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.AdminDao;
import dao.ApplyDao;
import dao.CompanyDao;
import dao.JobDao;
import dao.NoticeDao;
import dao.StudentDao;
import model.Admin;
import model.Apply;
import model.Company;
import model.Jobs;
import model.Notice;
import model.Student;
import net.sf.json.JSONObject;
import util.DataBaseUtil;

public class AdminServlet extends HttpServlet{
	DataBaseUtil dbUtil=new DataBaseUtil();
	AdminDao adminDao=new AdminDao();
	NoticeDao noticeDao=new NoticeDao();
	StudentDao studentDao=new StudentDao();
	CompanyDao companyDao=new CompanyDao();
	JobDao jobDao=new JobDao();
	ApplyDao applyDao=new ApplyDao();
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		String way=request.getParameter("way");
		if("adminUpdateInfo".equals(way)){
			this.adminUpdateInfo(request,response);
		}else if("save".equals(way)){
			this.userSave(request,response);
		}else if("noticelist".equals(way)){
			this.noticeList(request,response);
		}else if("noticePresave".equals(way)){
			this.noticePresave(request,response);
		}else if("noticeSave".equals(way)){
			this.noticeSave(request,response);
		}else if("noticeDelete".equals(way)){
			this.noticeDelete(request,response);
		}else if("studentlist".equals(way)){
			this.studentList(request,response);
		}else if("studentPresave".equals(way)){
			this.studentPresave(request,response);
		}else if("studentSave".equals(way)){
			this.studentSave(request,response);
		}else if("studentDelete".equals(way)){
			this.studentDelete(request,response);
		}else if("companyList".equals(way)){
			this.companyList(request,response);
		}else if("companyPresave".equals(way)){
			this.companyPresave(request,response);
		}else if("companySave".equals(way)){
			this.companySave(request,response);
		}else if("companyDelete".equals(way)){
			this.companyDelete(request,response);
		}else if("applylist".equals(way)){
			this.applylist(request,response);
		}else if("applyDelete".equals(way)){
			this.applyDelete(request,response);
		}else if("jobslist".equals(way)){
			this.jobslist(request,response);
		}else if("jobsPass".equals(way)){
			this.jobsPass(request,response);
		}else if("jobsNotPass".equals(way)){
			this.jobsNotPass(request,response);
		}else if("jobInfo".equals(way)){
			this.jobInfo(request,response);
		}else if("jobDelete".equals(way)){
			this.jobDelete(request,response);
		}
	}
	//??????????
		protected void jobsNotPass(HttpServletRequest request, HttpServletResponse response) 
				throws ServletException, IOException {
			request.setCharacterEncoding("utf-8");
			String jobId=request.getParameter("jobId");
			HttpSession session=request.getSession();
			Admin admin=(Admin)session.getAttribute("activeUser");
			Connection con=null;
			session.setAttribute("activeUser", admin);
			try {
				con=dbUtil.getCon();
				int result=0;
				if(jobId!=null&&!jobId.equals("")){
					result=adminDao.jobsNotPass(con, jobId);
				}
				if(result>0){
					response.setContentType("text/html;charset=utf-8");
					PrintWriter out=response.getWriter();
					out.print("????????<a href='admin?way=jobslist' onclick='javascript:location.replace(this.href);event.returnValue=false; '>????????</a>???? ");
					out.flush();
					out.close();
				}else{
					response.setContentType("text/html;charset=utf-8");
					PrintWriter out=response.getWriter();
					out.print("??????????");
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
	
	
	
	
	//????????
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
					re.put("errorMsg", "????????");
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
	//????????
	protected void jobInfo(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		Connection con=null;
		HttpSession session=request.getSession();
		Admin  admin=(Admin)session.getAttribute("activeUser");//??????company????
		String jobId=request.getParameter("jobId");
		try {
			//??????????????
			con=dbUtil.getCon();
			if(jobId!=null&&!jobId.equals("")){
				Jobs job=companyDao.jobInfoById(con, jobId);
				request.setAttribute("job", job);
			}
			session.setAttribute("activeUser", admin);
			request.setAttribute("mainPages","/pages/admin/adminJobSave.jsp");
			request.getRequestDispatcher("pages/admin/adminIndex.jsp").forward(request, response);
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			//???? ??????????????
			try {
				dbUtil.closeCon(con);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}	
	}
	
	
	//??????????
	protected void jobsPass(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String jobId=request.getParameter("jobId");
		HttpSession session=request.getSession();
		Admin admin=(Admin)session.getAttribute("activeUser");
		Connection con=null;
		session.setAttribute("activeUser", admin);
		try {
			con=dbUtil.getCon();
			int result=0;
			if(jobId!=null&&!jobId.equals("")){
				result=adminDao.jobsPass(con, jobId);
			}
			if(result>0){
				response.setContentType("text/html;charset=utf-8");
				PrintWriter out=response.getWriter(); //??????????????????
				out.print("????????<a href='admin?way=jobslist' onclick='javascript:location.replace(this.href);event.returnValue=false; '>????????</a>???? ");
				out.flush();
				out.close();
			}else{
				response.setContentType("text/html;charset=utf-8");
				PrintWriter out=response.getWriter();
				out.print("??????????");
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
	
	//????????????
	protected void jobslist(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		HttpSession session=request.getSession();////????request??????getSession()????session??????session????????????????
		Admin admin=(Admin)session.getAttribute("activeUser");
		Connection con=null;
		try {
			con=dbUtil.getCon();
			List<Jobs>jobLists=companyDao.jobAdminSelect(con);
			session.setAttribute("activeUser", admin);
			request.setAttribute("jobLists", jobLists);
			request.setAttribute("mainPages", "/pages/admin/adminJobsLists.jsp");
			request.getRequestDispatcher("pages/admin/adminIndex.jsp").forward(request, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//???????????? ????????????????????
	protected void studentList(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		Connection con=null;
		HttpSession session=request.getSession();
		Admin admin=(Admin)session.getAttribute("activeUser");//??????admin????
		try {
			//??????????????
			con=dbUtil.getCon();
			List<Student> stuLists=studentDao.studentList(con);
			request.setAttribute("stuLists", stuLists);
			request.setAttribute("mainPages", "/pages/admin/adminStudentList.jsp");
			session.setAttribute("activeUser", admin);
			request.getRequestDispatcher("pages/admin/adminIndex.jsp").forward(request, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			//???? ??????????????
			try {
				dbUtil.closeCon(con);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	//??????????????
	protected void  studentPresave(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException {
			Connection con=null;       //??????????????
			HttpSession session=request.getSession();  // 
			Admin  admin=(Admin)session.getAttribute("activeUser");//??????admin????
			String stuId=request.getParameter("stuId");
			try {
				//??????????????
				con=dbUtil.getCon();
				session.setAttribute("activeUser", admin);
				request.setAttribute("admin", admin);
				if(stuId!=null&&!stuId.equals("")){
					Student stu=studentDao.StuSelectById(con, stuId);
					request.setAttribute("student", stu);
				}
				request.setAttribute("mainPages","/pages/admin/adminStuSave.jsp");
				request.getRequestDispatcher("pages/admin/adminIndex.jsp").forward(request, response);
			}catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				//???? ??????????????
				try {
					dbUtil.closeCon(con);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}	
		}
	
		/*??????????????????*/
	protected void studentSave(HttpServletRequest request, HttpServletResponse response) 
				throws ServletException, IOException {
			// TODO Auto-generated method stub
			Connection con=null;     //??????????????
			HttpSession session=request.getSession(); // //????request??????getSession()????session??????session????????????????
			Admin admin=(Admin)session.getAttribute("activeUser");//??????admin????
			String stuId=request.getParameter("stuId");     //??????????????????value
			String stuName=request.getParameter("stuName");
			String stuPassword=request.getParameter("stuPassword");
			String stuSex=request.getParameter("stuSex");
			String stuAge=request.getParameter("stuAge");
			String stuTel=request.getParameter("stuTel");
			String stuGrade=request.getParameter("stuGrade");
			String stuMajor=request.getParameter("stuMajor");
			Student student=new Student(stuName, stuPassword, stuSex, Integer.parseInt(stuAge), stuTel, stuGrade, stuMajor);
			if(stuId!=null&&!stuId.equals("")){
				student.setStuId(Integer.parseInt(stuId));
			}
			try {
				con=dbUtil.getCon();
				if(stuId!=null&&!stuId.equals("")){
						studentDao.studentUpdate(con, student);			
				}else{
					if(studentDao.stuSelect(con, stuName)){
						request.setAttribute("error", "??????????????");
						response.setContentType("text/html;charset=utf-8");
						PrintWriter out=response.getWriter();
						out.print("<p style='color:red;'>??????????????????</p>??<a href='javascript:history.back()'>????????</a>????????????");
						out.flush();
						out.close();
					}else{
						studentDao.StundetAdd(con, student);	
					}
				}
				List<Student> stuLists=studentDao.studentList(con);  //
				request.setAttribute("stuLists", stuLists);  //
				request.setAttribute("mainPages", "/pages/admin/adminStudentList.jsp");
				session.setAttribute("activeUser", admin);
				request.getRequestDispatcher("pages/admin/adminIndex.jsp").forward(request, response);  //forward(request,response)??????????????????adminIndex.jsp.
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
		//????????
	protected void studentDelete(HttpServletRequest request, HttpServletResponse response) 
				throws ServletException, IOException {
			// TODO Auto-generated method stub
			String stuId=request.getParameter("stuId");    
			Connection con=null;
			try {
				con=dbUtil.getCon();              //????????????????????
				JSONObject re=new JSONObject();   //????????????????????????JSONObject????
				                                  //put()??????????????????key??????????String??????????????????value????????boolean??double??int??long??Object??Map????Collection????
				                                  //int????????????Java??????????json????????????????Number??????????
				int del=studentDao.stuDelete(con,Integer.parseInt(stuId));
				if(del>0){
					re.put("success", true);      //????put()??????????????????
				}else{
					re.put("errorMsg", "????????");
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
	
	//????????????
	protected void companyList(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Connection con=null;
		HttpSession session=request.getSession();
		Admin admin=(Admin)session.getAttribute("activeUser");//??????admin????
		try {
			//??????????????
			con=dbUtil.getCon();
			List<Company> companyLists=companyDao.companyList(con);
			request.setAttribute("companyLists", companyLists);
			request.setAttribute("mainPages", "/pages/admin/adminCompanyList.jsp");
			session.setAttribute("activeUser", admin);
			request.getRequestDispatcher("pages/admin/adminIndex.jsp").forward(request, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			//???? ??????????????
			try {
				dbUtil.closeCon(con);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	//????????????????
	protected void companyPresave(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException {
		Connection con=null;
		HttpSession session=request.getSession();
		Admin  admin=(Admin)session.getAttribute("activeUser");//??????admin????
		String companyId=request.getParameter("companyId");
		try {
			//??????????????
			con=dbUtil.getCon();
			session.setAttribute("activeUser", admin);
			request.setAttribute("admin", admin);
			if(companyId!=null&&!companyId.equals("")){
				Company company=companyDao.companySelectById(con, Integer.parseInt(companyId));
				request.setAttribute("company", company);
			}
			request.setAttribute("mainPages","/pages/admin/adminCompanySave.jsp");
			request.getRequestDispatcher("pages/admin/adminIndex.jsp").forward(request, response);
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			//???? ??????????????
			try {
				dbUtil.closeCon(con);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}	
	}
	/*??????????????????*/
	protected void companySave(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		Connection con=null;
		HttpSession session=request.getSession();
		Admin admin=(Admin)session.getAttribute("activeUser");//??????admin????
		String companyId=request.getParameter("companyId");
		String companyName=request.getParameter("companyName");
		String companyContent=request.getParameter("companyContent");
		String companyPassword=request.getParameter("companyPassword");
		Company company=new Company(companyName, companyPassword, companyContent);
		if(companyId!=null&&!companyId.equals("")){
			company.setCompanyId(Integer.parseInt(companyId));
		}
		try {
			con=dbUtil.getCon();
			if(companyId!=null&&!companyId.equals("")){
						companyDao.companyUpdate(con, company);
				}else{
					if(companyDao.companyexist(con, companyName)){
						request.setAttribute("error", "??????????????");
						response.setContentType("text/html;charset=utf-8");
						PrintWriter out=response.getWriter();
						out.print("<p style='color:red;'>??????????????????</p>??<a href='javascript:history.back()'>????????</a>????????????");
						out.flush();
						out.close();
					}else{
						companyDao.companyAdd(con, company);
					}
			}
			List<Company> companyLists=companyDao.companyList(con);
			request.setAttribute("companyLists", companyLists);
			request.setAttribute("mainPages", "/pages/admin/adminCompanyList.jsp");
			session.setAttribute("activeUser", admin);
			request.getRequestDispatcher("pages/admin/adminIndex.jsp").forward(request, response);
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
	//????????
	public void companyDelete(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		String companyId=request.getParameter("companyId");
		Connection con=null;
		try {
			con=dbUtil.getCon();
			JSONObject re=new JSONObject();
			int del=companyDao.companyDelete(con, Integer.parseInt(companyId));
			if(del>0){
				re.put("success", true);
			}else{
				re.put("errorMsg", "????????");
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

		/*??????????????*/
	protected void noticeList(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException {
			// TODO Auto-generated method stub
			Connection con=null;
			HttpSession session=request.getSession();
			Admin admin=(Admin)session.getAttribute("activeUser");//??????admin????
			try {
				//??????????????
				con=dbUtil.getCon();
				List<Notice> noticeLists=noticeDao.noticeByAdminIdList(con,admin.getAdminId());
				request.setAttribute("noticeLists", noticeLists);
				request.setAttribute("mainPages", "/pages/admin/adminNoticeList.jsp");
				session.setAttribute("activeUser", admin);
				request.getRequestDispatcher("pages/admin/adminIndex.jsp").forward(request, response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				//???? ??????????????
				try {
					dbUtil.closeCon(con);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	//????????????????
	protected void noticePresave(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		// TODO Auto-generated method stub
		Connection con=null;
		HttpSession session=request.getSession();
		Admin  admin=(Admin)session.getAttribute("activeUser");//??????admin????
		String noticeId=request.getParameter("noticeId");
		try {
			//??????????????
			con=dbUtil.getCon();
			session.setAttribute("activeUser", admin);
			request.setAttribute("admin", admin);
			if(noticeId!=null&&!noticeId.equals("")){
				Notice notice=noticeDao.noticeSelect(con,noticeId);
				request.setAttribute("notice", notice);
			}
			request.setAttribute("mainPages","/pages/admin/adminNoticeSave.jsp");
			request.getRequestDispatcher("pages/admin/adminIndex.jsp").forward(request, response);
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			//???? ??????????????
			try {
				dbUtil.closeCon(con);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}	
	}
	/*??????????????????*/
	protected void noticeSave(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		Connection con=null;
		HttpSession session=request.getSession();
		Admin admin=(Admin)session.getAttribute("activeUser");//??????admin????
		String noticeId=request.getParameter("noticeId");
		String noticeTitle=request.getParameter("noticeTitle");
		String noticeContent=request.getParameter("noticeContent");
		Notice notice=new Notice(noticeTitle, noticeContent,admin.getAdminId());
		if(noticeId!=null&&!noticeId.equals("")){
			notice.setNoticeId(Integer.parseInt(noticeId));
		}
		try {
			con=dbUtil.getCon();
			if(noticeId!=null&&!noticeId.equals("")){
					noticeDao.noticeUpdate(con,notice);
				}else{
					noticeDao.noticeAdd(con, notice);
			}
			List<Notice> noticeLists=noticeDao.noticeByAdminIdList(con,admin.getAdminId());
			request.setAttribute("noticeLists", noticeLists);
			request.setAttribute("mainPages", "/pages/admin/adminNoticeList.jsp");
			session.setAttribute("activeUser", admin);
			request.getRequestDispatcher("pages/admin/adminIndex.jsp").forward(request, response);
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
	//????????
	public void noticeDelete(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		String noticeId=request.getParameter("noticeId");
		Connection con=null;
		try {
			con=dbUtil.getCon();
			JSONObject re=new JSONObject();
			int del=noticeDao.noticeDelete(con, Integer.parseInt(noticeId));
			if(del>0){
				re.put("success", true);
			}else{
				re.put("errorMsg", "????????");
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
	protected void userSave(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String adminId=request.getParameter("adminId");
		String adminName=request.getParameter("adminName");
		String adminPassword=request.getParameter("adminPassword");
		Admin admin=new Admin(adminName,adminPassword);
		Connection con=null;
		if(adminId!=null&&!adminId.equals("")){
			admin.setAdminId(Integer.parseInt(adminId));
		}
		try {
			con=dbUtil.getCon();
			int result=0;
			if(adminId!=null&&!adminId.equals("")){
				result=adminDao.adminUpdate(con, admin);
			}
			if(result>0){
				response.setContentType("text/html;charset=utf-8");
				PrintWriter out=response.getWriter();
				out.print("?????????? ??<a href='login.jsp'>????????</a>????????????");
				out.flush();
				out.close();
				HttpSession se=request.getSession();
				se.removeAttribute("activeUser");
			}else{
				response.setContentType("text/html;charset=utf-8");
				PrintWriter out=response.getWriter();
				out.print("??????????");
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
	protected void adminUpdateInfo(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		HttpSession session=request.getSession();   //????????session????
		Admin admin=(Admin)session.getAttribute("activeUser");//??????admin????
		Connection con=null;
		try {
			//??????????????
			con=dbUtil.getCon();
			session.setAttribute("activeUser", admin);
			request.setAttribute("admin", admin);
			request.setAttribute("mainPages", "/pages/admin/adminInfoUpdate.jsp");
			request.getRequestDispatcher("pages/admin/adminIndex.jsp").forward(request, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			//???? ??????????????
			try {
				dbUtil.closeCon(con);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	protected void applylist(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		Connection con=null;
		HttpSession session=request.getSession();
		Admin  admin=(Admin)session.getAttribute("activeUser");//??????company????
		List<Apply>applyLists=new ArrayList<Apply>();
		try {
			con=dbUtil.getCon();
			applyLists=companyDao.getApplyLists(con);
			List<Student>studentlist=studentDao.studentList(con);
			List<Jobs>jobsLists=jobDao.jobsList(con);
			request.setAttribute("jobsLists", jobsLists);
			request.setAttribute("applyLists",applyLists);
			request.setAttribute("studentlist", studentlist);
			session.setAttribute("activeUser", admin);
			request.setAttribute("admin", admin);
			request.setAttribute("mainPages","/pages/admin/adminApply.jsp");
			request.getRequestDispatcher("pages/admin/adminIndex.jsp").forward(request, response);
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
	
	public void applyDelete(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		   String applyId=request.getParameter("applyId");
		Connection con=null;
		try {
			con=dbUtil.getCon();
			JSONObject re=new JSONObject();
			int del=applyDao.applyDelete(con, Integer.parseInt(applyId));
			if(del>0){
				re.put("success", true);
			}else{
				re.put("errorMsg", "????????");
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

}



