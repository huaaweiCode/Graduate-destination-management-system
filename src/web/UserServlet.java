package web;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import dao.AdminDao;
import dao.CompanyDao;
import dao.StudentDao;
import model.Admin;
import model.Company;
import model.Student;
/**
 * 
* @author 马金梅
* @ClassName web.InintServlet
* @Description:     用户信息处理       包括内容：管理员，学生，公司 系统登录注册退出修改信息
* @date 2019年3月27日 下午7:37:06
 */
import util.DataBaseUtil;
public class UserServlet extends HttpServlet{
	AdminDao adminDao=new AdminDao();
	StudentDao studentDao=new StudentDao();
	CompanyDao companyDao=new CompanyDao();
	DataBaseUtil dbUtil=new DataBaseUtil();
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
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
					/*设置编码 防止出现乱码*/
		request.setCharacterEncoding("utf-8");
		String way=request.getParameter("way");
		if("login".equals(way)){//登录
			this.userlogin(request,response);
		}else if("registe".equals(way)){//注册
			this.userRegiste(request,response);
		}else if("logout".equals(way)){
			this.userLogout(request,response);
		}
	}
	//退出系统
	protected void userLogout(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		  //清空sessin 将session中的activeUser去除掉
		request.getSession().removeAttribute("activeUser");
		request.getRequestDispatcher("login.jsp").forward(request, response);
	}

	//用户登录 根据用户类别不同转发到不同的页面 adminIndex.jsp   studentIndex.jsp   companyIndex.jsp
	protected void userlogin(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");   //设置编码格式 
			//   为了判断用户是否登录，设置session;     将前用户塞到session中
		HttpSession session=request.getSession();
			//获取用户类别
		String userTypeId=request.getParameter("userTypeId");
			//获取用户姓名
		String userName=request.getParameter("userName");
			//获取密码
		String password=request.getParameter("password");
		Connection con=null;
		try {
			con=dbUtil.getCon();
			//管理员登录
			if(userTypeId.equals("1")){
				Admin admin=adminDao.AdminSelect(con,userName, password);
				if(admin!=null){
					session.setAttribute("activeUser", admin);
					request.setAttribute("activeUser", admin);
					request.getRequestDispatcher("/pages/admin/adminIndex.jsp").forward(request, response);
				}else{
					request.setAttribute("error", "管理员姓名或者密码错误！");
					request.getRequestDispatcher("login.jsp").forward(request, response);
				}
				//学生登录
			}else if(userTypeId.equals("2")){
				Student stu=studentDao.StuSelect(con, userName, password);
				if(stu!=null&&!stu.equals("")){
					session.setAttribute("activeUser", stu);
					request.setAttribute("activeUser", stu);
					request.getRequestDispatcher("/pages/student/studentIndex.jsp").forward(request, response);
				}else{
					request.setAttribute("error", "学生姓名或者密码错误！");
					request.getRequestDispatcher("login.jsp").forward(request, response);
				}
				//公司登录
			}else if(userTypeId.equals("3")){
				Company comp=companyDao.companySelect(con, userName, password);				
				if(comp!=null){
					session.setAttribute("activeUser", comp);
					request.setAttribute("activeUser", comp);
					request.getRequestDispatcher("/pages/company/companyIndex.jsp").forward(request, response);
				}else{
					request.setAttribute("error", "企业名称或者密码错误！");
					request.getRequestDispatcher("login.jsp").forward(request, response);
				}
				
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
	//用户注册
	protected void userRegiste(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		//获取登录用户的类别
		String userTypeId=request.getParameter("userTypeId");
		String userName=request.getParameter("userName");
		String password=request.getParameter("password");
		Student student=null;
		Company company=null;
		if(userTypeId.equals("2")){
			//学生专属！
			String stuSex=request.getParameter("stuSex");
			String stuAge=request.getParameter("stuAge");
			String stuTel=request.getParameter("stuTel");
			String stuGrade=request.getParameter("stuGrade");
			String stuMajor=request.getParameter("stuMajor");
			student=new Student(userName, password, stuSex, Integer.parseInt(stuAge), stuTel, stuGrade, stuMajor);
		}else{
			//公司信息
			String companyContent=request.getParameter("companyContent");
			company=new Company(userName, password, companyContent);
		}
		Connection con=null;
		if(userTypeId!=null&&!userTypeId.equals("")){
			try {
				con=dbUtil.getCon();
				if(userTypeId.equals("2")){
					//用户名存在 报错！
					if(studentDao.stuSelect(con, userName)){
						request.setAttribute("error", "用户名已存在！");
						request.setAttribute("student",student);
						request.getRequestDispatcher("/registe.jsp").forward(request, response);
					}else{//用户名不报错！
						int result=studentDao.StundetAdd(con, student);
						if(result>0){
							response.setContentType("text/html;charset=utf-8");
							PrintWriter out=response.getWriter();
							out.print("注册成功！ 请<a href='javascript:history.back()'>点击这里</a>返回登录");
							out.flush();
							out.close();
						}else{
							response.setContentType("text/html;charset=utf-8");
							PrintWriter out=response.getWriter();
							out.print("注册失败！");
							out.flush();
							out.close();
						}
					}
				}else{
					if(companyDao.companyexist(con, userName)){
						request.setAttribute("error", "用户名已存在！");
						request.setAttribute("company",company);
						request.getRequestDispatcher("/registe.jsp").forward(request, response);
					}else{
						int result=companyDao.companyAdd(con, company);
						if(result>0){
							response.setContentType("text/html;charset=utf-8");
							PrintWriter out=response.getWriter();
							out.print("注册成功！ 请<a href='login.jsp'>点击这里</a>返回登录");
							out.flush();
							out.close();
						}else{
							response.setContentType("text/html;charset=utf-8");
							PrintWriter out=response.getWriter();
							out.print("注册失败！");
							out.flush();
							out.close();
						}
					}	
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
}
