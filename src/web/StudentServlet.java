package web;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.ApplyDao;
import dao.FileDao;
import dao.JobDao;
import dao.NoticeDao;
import dao.StudentDao;
import model.Apply;
import model.Files;
import model.Jobs;
import model.Notice;
import model.Student;
import util.DataBaseUtil;
/**
 * 
* @author 马金梅
* @ClassName web.StudentServlet
* @Description: 处理学生的所有事务！！ 包括学生信息的修改，学生上传简历，修改简历，查看信息。
* @date 2019年3月29日 下午9:12:23
 */
public class StudentServlet extends HttpServlet{
	/**
	 * 
	 */
	DataBaseUtil dbUtil=new DataBaseUtil();
	StudentDao stuDao=new StudentDao();
	UserServlet userServlet=new UserServlet();
	NoticeDao noticeDao=new NoticeDao();
	JobDao jobDao=new JobDao();
	ApplyDao applyDao=new ApplyDao();
	FileDao fileDao=new FileDao();
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
		if("Modifyinfo".equals(way)){
			this.modifyInfo(request,response);
		}else if("save".equals(way)){
			this.save(request,response);
		}else if("noticeOrJobList".equals(way)){
			this.noticeOrJobsList(request,response);
		} else if("jobApplyList".equals(way)){
			this.jobApplyList(request,response);
		}else if("noticeListInfo".equals(way)){
			this.noticeListInfo(request,response);
		}else if("filelist".equals(way)){
			this.fileList(request, response);
		}else if("searchNotice".equals(way)){
			this.searchNotice(request,response);
		}else if("searchJob".equals(way)){
			this.searchJob(request,response);
		}
	}
	//公告查询
	protected void searchNotice(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		HttpSession session=request.getSession();
		Student student=(Student)session.getAttribute("activeUser");//转换为学生对象
		String content=request.getParameter("content");
		String type="2";//公告信息
		Connection con=null;
		List<Notice> noticeLists=null;
		try {
			con=dbUtil.getCon();
			noticeLists=noticeDao.noticeSearch(con,content);
			session.setAttribute("activeUser", student);
			request.setAttribute("noticeLists", noticeLists);
			request.setAttribute("typeId", type);
			request.setAttribute("mainPages", "/pages/student/studentNotice.jsp");
			request.getRequestDispatcher("pages/student/studentIndex.jsp").forward(request, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//招聘查询
	protected void searchJob(HttpServletRequest request, HttpServletResponse response) 
				throws ServletException, IOException {
			// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		HttpSession session=request.getSession();
		Student student=(Student)session.getAttribute("activeUser");//转换为学生对象
		String content=request.getParameter("content");
		String type="1";//招聘信息
		Connection con=null;
		List<Jobs> jobLists=null;
		try {
			con=dbUtil.getCon();
			jobLists=jobDao.jobSearch(con,content);
			session.setAttribute("activeUser", student);
			request.setAttribute("jobLists", jobLists);
			request.setAttribute("typeId", type);
			request.setAttribute("mainPages", "/pages/student/studentNotice.jsp");
			request.getRequestDispatcher("pages/student/studentIndex.jsp").forward(request, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		}
	//简历展示
	protected void fileList(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		HttpSession session=request.getSession();
		Student student=(Student)session.getAttribute("activeUser");//转换为学生对象
		Connection con=null;
		try {
			//获取数据库连接
			con=dbUtil.getCon();
			session.setAttribute("activeUser", student);	
			//将数据库中的文件名全部取出存储到filetitles中
			List<Files> filelists=fileDao.fileBystuId(con,student.getStuId());
			/*//获取上传文件的目录
				String uploadFilePath=this.getServletContext().getRealPath("/WEB-INF/upload");*/
			//存储要下载的文件名
			Map<String,String>fileNameMap=new HashMap<String,String>();
			//递归遍历filepath目录下的所有文件和目录，将文件的文件名存储到map集合中
			for(Files files:filelists){
				fileNameMap.put(files.getFileTitle(),files.getFileTitle().substring(files.getFileTitle().indexOf("\\")+1));
			}
			//将Map目录发送到listfile.jsp页面进行展示
			request.setAttribute("fileNameMap",fileNameMap);
			request.setAttribute("mainPages", "/pages/student/studentfile.jsp");
			request.getRequestDispatcher("pages/student/studentIndex.jsp").forward(request, response);
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
	protected void noticeOrJobsList(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		HttpSession session=request.getSession();
		Student student=(Student)session.getAttribute("activeUser");//转换为学生对象
		
		//根据typeId来判断是收集工作信息呢还是公告信息。 typeId=1 工作信息     typeId=2公告信息
		String type=request.getParameter("type");
		Connection con=null;
		try {
			//获取数据库连接
			con=dbUtil.getCon();
			session.setAttribute("activeUser", student);	
			if(type.equals("1")){
				List<Jobs> jobLists=jobDao.noticeList(con);
				request.setAttribute("jobLists", jobLists);
			}else if(type.equals("2")){
				List<Notice> noticeLists= noticeDao.noticeList(con);
				request.setAttribute("noticeLists",noticeLists);
			}
			request.setAttribute("typeId", type);
			request.setAttribute("mainPages", "/pages/student/studentNotice.jsp");
			request.getRequestDispatcher("pages/student/studentIndex.jsp").forward(request, response);
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

	//更新信息
	private void modifyInfo(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
			//获取当前登录的用户信息
		HttpSession session=request.getSession();
		Student student=(Student)session.getAttribute("activeUser");//转换为学生对象
		Connection con=null;
		try {
			//获取数据库连接
			con=dbUtil.getCon();
			session.setAttribute("activeUser", student);
			request.setAttribute("student", student);
			request.setAttribute("mainPages", "/pages/student/studentInfoUpdate.jsp");
			request.getRequestDispatcher("pages/student/studentIndex.jsp").forward(request, response);
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
	//信息保存
	private void save(HttpServletRequest request, HttpServletResponse response) {
			// TODO Auto-generated method stub
		//获取 Id  姓名  年龄 电话  性别  年级  专业 密码
				String stuId=request.getParameter("stuId");
				String stuName=request.getParameter("stuName");
				String stuPassword=request.getParameter("stuPassword");
				String stuAge=request.getParameter("stuAge");
				String stuTel=request.getParameter("stuTel");
				String stuSex=request.getParameter("stuSex");
				String stuGrade=request.getParameter("stuGrade");
				String stuMajor=request.getParameter("stuMajor");
				Student stu=new Student(Integer.parseInt(stuId),stuName, stuPassword, stuSex, Integer.parseInt(stuAge), stuTel, stuGrade, stuMajor);
				Connection con=null;
				try {
					con=dbUtil.getCon();
					int result=0;
					if(stuId!=null&&!stuId.equals("")){
						result=stuDao.studentUpdate(con, stu);
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
	//公告展示
	protected void noticeListInfo(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
			// TODO Auto-generated method stub
			request.setCharacterEncoding("utf-8");
			String noticeId=request.getParameter("noticeId");
			HttpSession session=request.getSession();
			Student student=(Student)session.getAttribute("activeUser");
			Connection con=null;
			try {
				con=dbUtil.getCon();
				Notice notice=noticeDao.noticeSelect(con, noticeId);
				request.setAttribute("notice", notice);
				session.setAttribute("activeUser", student);
				request.setAttribute("mainPages", "/pages/student/noticeId.jsp");
				request.getRequestDispatcher("pages/student/studentIndex.jsp").forward(request, response);
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
	}
		
		
		//工作展示     申请
	protected void jobApplyList(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException {
			// TODO Auto-generated method stub
			request.setCharacterEncoding("utf-8");       //设置编码格式
			String jobId=request.getParameter("jobId");  //得到工作ID
			HttpSession session=request.getSession();     //得到一个session对象
			Student student=(Student)session.getAttribute("activeUser");//转换为学生对象
			Connection con=null;
			List<Apply>applyList=null;
			try {
				//获取数据库连接
				con=dbUtil.getCon();
				session.setAttribute("activeUser", student);
				//目前想要申请那个工作
				applyList=applyDao.applyselect(con, Integer.parseInt(jobId));
				boolean isApply=false;
				Iterator<Apply> it=applyList.iterator(); //迭代器（Iterator）模式
				while(it.hasNext()){
					if(it.next().getApplyStuId()==student.getStuId()){
						isApply=true;
						break;
					};
				}
				request.setAttribute("isApply",isApply);
				Jobs jobList=jobDao.jobListById(con, jobId);
				request.setAttribute("jobList", jobList);
				request.setAttribute("mainPages", "/pages/student/studentjob.jsp");
				request.getRequestDispatcher("pages/student/studentIndex.jsp").forward(request, response);
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
}
