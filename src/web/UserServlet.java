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
* @author ���÷
* @ClassName web.InintServlet
* @Description:     �û���Ϣ����       �������ݣ�����Ա��ѧ������˾ ϵͳ��¼ע���˳��޸���Ϣ
* @date 2019��3��27�� ����7:37:06
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
					/*���ñ��� ��ֹ��������*/
		request.setCharacterEncoding("utf-8");
		String way=request.getParameter("way");
		if("login".equals(way)){//��¼
			this.userlogin(request,response);
		}else if("registe".equals(way)){//ע��
			this.userRegiste(request,response);
		}else if("logout".equals(way)){
			this.userLogout(request,response);
		}
	}
	//�˳�ϵͳ
	protected void userLogout(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		  //���sessin ��session�е�activeUserȥ����
		request.getSession().removeAttribute("activeUser");
		request.getRequestDispatcher("login.jsp").forward(request, response);
	}

	//�û���¼ �����û����ͬת������ͬ��ҳ�� adminIndex.jsp   studentIndex.jsp   companyIndex.jsp
	protected void userlogin(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");   //���ñ����ʽ 
			//   Ϊ���ж��û��Ƿ��¼������session;     ��ǰ�û�����session��
		HttpSession session=request.getSession();
			//��ȡ�û����
		String userTypeId=request.getParameter("userTypeId");
			//��ȡ�û�����
		String userName=request.getParameter("userName");
			//��ȡ����
		String password=request.getParameter("password");
		Connection con=null;
		try {
			con=dbUtil.getCon();
			//����Ա��¼
			if(userTypeId.equals("1")){
				Admin admin=adminDao.AdminSelect(con,userName, password);
				if(admin!=null){
					session.setAttribute("activeUser", admin);
					request.setAttribute("activeUser", admin);
					request.getRequestDispatcher("/pages/admin/adminIndex.jsp").forward(request, response);
				}else{
					request.setAttribute("error", "����Ա���������������");
					request.getRequestDispatcher("login.jsp").forward(request, response);
				}
				//ѧ����¼
			}else if(userTypeId.equals("2")){
				Student stu=studentDao.StuSelect(con, userName, password);
				if(stu!=null&&!stu.equals("")){
					session.setAttribute("activeUser", stu);
					request.setAttribute("activeUser", stu);
					request.getRequestDispatcher("/pages/student/studentIndex.jsp").forward(request, response);
				}else{
					request.setAttribute("error", "ѧ�����������������");
					request.getRequestDispatcher("login.jsp").forward(request, response);
				}
				//��˾��¼
			}else if(userTypeId.equals("3")){
				Company comp=companyDao.companySelect(con, userName, password);				
				if(comp!=null){
					session.setAttribute("activeUser", comp);
					request.setAttribute("activeUser", comp);
					request.getRequestDispatcher("/pages/company/companyIndex.jsp").forward(request, response);
				}else{
					request.setAttribute("error", "��ҵ���ƻ����������");
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
	//�û�ע��
	protected void userRegiste(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		//��ȡ��¼�û������
		String userTypeId=request.getParameter("userTypeId");
		String userName=request.getParameter("userName");
		String password=request.getParameter("password");
		Student student=null;
		Company company=null;
		if(userTypeId.equals("2")){
			//ѧ��ר����
			String stuSex=request.getParameter("stuSex");
			String stuAge=request.getParameter("stuAge");
			String stuTel=request.getParameter("stuTel");
			String stuGrade=request.getParameter("stuGrade");
			String stuMajor=request.getParameter("stuMajor");
			student=new Student(userName, password, stuSex, Integer.parseInt(stuAge), stuTel, stuGrade, stuMajor);
		}else{
			//��˾��Ϣ
			String companyContent=request.getParameter("companyContent");
			company=new Company(userName, password, companyContent);
		}
		Connection con=null;
		if(userTypeId!=null&&!userTypeId.equals("")){
			try {
				con=dbUtil.getCon();
				if(userTypeId.equals("2")){
					//�û������� ����
					if(studentDao.stuSelect(con, userName)){
						request.setAttribute("error", "�û����Ѵ��ڣ�");
						request.setAttribute("student",student);
						request.getRequestDispatcher("/registe.jsp").forward(request, response);
					}else{//�û���������
						int result=studentDao.StundetAdd(con, student);
						if(result>0){
							response.setContentType("text/html;charset=utf-8");
							PrintWriter out=response.getWriter();
							out.print("ע��ɹ��� ��<a href='javascript:history.back()'>�������</a>���ص�¼");
							out.flush();
							out.close();
						}else{
							response.setContentType("text/html;charset=utf-8");
							PrintWriter out=response.getWriter();
							out.print("ע��ʧ�ܣ�");
							out.flush();
							out.close();
						}
					}
				}else{
					if(companyDao.companyexist(con, userName)){
						request.setAttribute("error", "�û����Ѵ��ڣ�");
						request.setAttribute("company",company);
						request.getRequestDispatcher("/registe.jsp").forward(request, response);
					}else{
						int result=companyDao.companyAdd(con, company);
						if(result>0){
							response.setContentType("text/html;charset=utf-8");
							PrintWriter out=response.getWriter();
							out.print("ע��ɹ��� ��<a href='login.jsp'>�������</a>���ص�¼");
							out.flush();
							out.close();
						}else{
							response.setContentType("text/html;charset=utf-8");
							PrintWriter out=response.getWriter();
							out.print("ע��ʧ�ܣ�");
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
