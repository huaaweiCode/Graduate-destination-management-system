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
	//禁止发布、
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
					out.print("操作成功<a href='admin?way=jobslist' onclick='javascript:location.replace(this.href);event.returnValue=false; '>点击这里</a>返回 ");
					out.flush();
					out.close();
				}else{
					response.setContentType("text/html;charset=utf-8");
					PrintWriter out=response.getWriter();
					out.print("操作失败！");
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
	
	
	
	
	//删除工作
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
					re.put("errorMsg", "删除失败");
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
	//信息展示
	protected void jobInfo(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		Connection con=null;
		HttpSession session=request.getSession();
		Admin  admin=(Admin)session.getAttribute("activeUser");//转换为company对象
		String jobId=request.getParameter("jobId");
		try {
			//获取数据库连接
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
			//用完 关闭数据库连接
			try {
				dbUtil.closeCon(con);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}	
	}
	
	
	//同意审核、
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
				PrintWriter out=response.getWriter(); //构造一个标准输出流
				out.print("审核成功<a href='admin?way=jobslist' onclick='javascript:location.replace(this.href);event.returnValue=false; '>点击这里</a>返回 ");
				out.flush();
				out.close();
			}else{
				response.setContentType("text/html;charset=utf-8");
				PrintWriter out=response.getWriter();
				out.print("审核失败！");
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
	
	//招聘信息维护
	protected void jobslist(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		HttpSession session=request.getSession();////使用request对象的getSession()获取session，如果session不存在则创建一个
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
	//学生信息维护 查询出所有的学生信息
	protected void studentList(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		Connection con=null;
		HttpSession session=request.getSession();
		Admin admin=(Admin)session.getAttribute("activeUser");//转换为admin对象
		try {
			//获取数据库连接
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
			//用完 关闭数据库连接
			try {
				dbUtil.closeCon(con);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	//更新或者是修改
	protected void  studentPresave(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException {
			Connection con=null;       //定义数据库连接
			HttpSession session=request.getSession();  // 
			Admin  admin=(Admin)session.getAttribute("activeUser");//转换为admin对象
			String stuId=request.getParameter("stuId");
			try {
				//获取数据库连接
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
				//用完 关闭数据库连接
				try {
					dbUtil.closeCon(con);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}	
		}
	
		/*更新或者是添加保存*/
	protected void studentSave(HttpServletRequest request, HttpServletResponse response) 
				throws ServletException, IOException {
			// TODO Auto-generated method stub
			Connection con=null;     //定义数据库连接
			HttpSession session=request.getSession(); // //使用request对象的getSession()获取session，如果session不存在则创建一个
			Admin admin=(Admin)session.getAttribute("activeUser");//转换为admin对象
			String stuId=request.getParameter("stuId");     //获取前端页面表单的value
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
						request.setAttribute("error", "用户名已存在！");
						response.setContentType("text/html;charset=utf-8");
						PrintWriter out=response.getWriter();
						out.print("<p style='color:red;'>用户名已经存在了！</p>请<a href='javascript:history.back()'>点击这里</a>返回重新输入");
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
				request.getRequestDispatcher("pages/admin/adminIndex.jsp").forward(request, response);  //forward(request,response)作用是将请求转发到adminIndex.jsp.
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
		//学生删除
	protected void studentDelete(HttpServletRequest request, HttpServletResponse response) 
				throws ServletException, IOException {
			// TODO Auto-generated method stub
			String stuId=request.getParameter("stuId");    
			Connection con=null;
			try {
				con=dbUtil.getCon();              //首先拿到数据库的连接
				JSONObject re=new JSONObject();   //直接构建即直接实例化一个JSONObject对象
				                                  //put()方法的第一个参数为key值，必须为String类型，第二个参数为value，可以为boolean、double、int、long、Object、Map以及Collection等。
				                                  //int等类型只是在Java中，写入到json中时，统一都会以Number类型存储。
				int del=studentDao.stuDelete(con,Integer.parseInt(stuId));
				if(del>0){
					re.put("success", true);      //调用put()方法，将数据写入。
				}else{
					re.put("errorMsg", "删除失败");
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
	
	//企业信息展示
	protected void companyList(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Connection con=null;
		HttpSession session=request.getSession();
		Admin admin=(Admin)session.getAttribute("activeUser");//转换为admin对象
		try {
			//获取数据库连接
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
			//用完 关闭数据库连接
			try {
				dbUtil.closeCon(con);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	//企业信息信息添加
	protected void companyPresave(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException {
		Connection con=null;
		HttpSession session=request.getSession();
		Admin  admin=(Admin)session.getAttribute("activeUser");//转换为admin对象
		String companyId=request.getParameter("companyId");
		try {
			//获取数据库连接
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
			//用完 关闭数据库连接
			try {
				dbUtil.closeCon(con);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}	
	}
	/*更新或者是添加保存*/
	protected void companySave(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		Connection con=null;
		HttpSession session=request.getSession();
		Admin admin=(Admin)session.getAttribute("activeUser");//转换为admin对象
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
						request.setAttribute("error", "用户名已存在！");
						response.setContentType("text/html;charset=utf-8");
						PrintWriter out=response.getWriter();
						out.print("<p style='color:red;'>用户名已经存在了！</p>请<a href='javascript:history.back()'>点击这里</a>返回重新输入");
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
	//删除公司
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
				re.put("errorMsg", "删除失败");
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

		/*所有的公告信息*/
	protected void noticeList(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException {
			// TODO Auto-generated method stub
			Connection con=null;
			HttpSession session=request.getSession();
			Admin admin=(Admin)session.getAttribute("activeUser");//转换为admin对象
			try {
				//获取数据库连接
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
				//用完 关闭数据库连接
				try {
					dbUtil.closeCon(con);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	//添加或者更新公告
	protected void noticePresave(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		// TODO Auto-generated method stub
		Connection con=null;
		HttpSession session=request.getSession();
		Admin  admin=(Admin)session.getAttribute("activeUser");//转换为admin对象
		String noticeId=request.getParameter("noticeId");
		try {
			//获取数据库连接
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
			//用完 关闭数据库连接
			try {
				dbUtil.closeCon(con);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}	
	}
	/*更新或者是添加保存*/
	protected void noticeSave(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		Connection con=null;
		HttpSession session=request.getSession();
		Admin admin=(Admin)session.getAttribute("activeUser");//转换为admin对象
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
	//删除公告
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
				re.put("errorMsg", "删除失败");
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
				out.print("修改成功！ 请<a href='login.jsp'>点击这里</a>返回重新登录");
				out.flush();
				out.close();
				HttpSession se=request.getSession();
				se.removeAttribute("activeUser");
			}else{
				response.setContentType("text/html;charset=utf-8");
				PrintWriter out=response.getWriter();
				out.print("修改失败！");
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
		HttpSession session=request.getSession();   //创建一个session对话
		Admin admin=(Admin)session.getAttribute("activeUser");//转换为admin对象
		Connection con=null;
		try {
			//获取数据库连接
			con=dbUtil.getCon();
			session.setAttribute("activeUser", admin);
			request.setAttribute("admin", admin);
			request.setAttribute("mainPages", "/pages/admin/adminInfoUpdate.jsp");
			request.getRequestDispatcher("pages/admin/adminIndex.jsp").forward(request, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			//用完 关闭数据库连接
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
		Admin  admin=(Admin)session.getAttribute("activeUser");//转换为company对象
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
				re.put("errorMsg", "删除失败");
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



