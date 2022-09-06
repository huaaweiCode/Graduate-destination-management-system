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
* @author ���÷
* @ClassName web.StudentServlet
* @Description: ����ѧ�����������񣡣� ����ѧ����Ϣ���޸ģ�ѧ���ϴ��������޸ļ������鿴��Ϣ��
* @date 2019��3��29�� ����9:12:23
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
	//�����ѯ
	protected void searchNotice(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		HttpSession session=request.getSession();
		Student student=(Student)session.getAttribute("activeUser");//ת��Ϊѧ������
		String content=request.getParameter("content");
		String type="2";//������Ϣ
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
	
	//��Ƹ��ѯ
	protected void searchJob(HttpServletRequest request, HttpServletResponse response) 
				throws ServletException, IOException {
			// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		HttpSession session=request.getSession();
		Student student=(Student)session.getAttribute("activeUser");//ת��Ϊѧ������
		String content=request.getParameter("content");
		String type="1";//��Ƹ��Ϣ
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
	//����չʾ
	protected void fileList(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		HttpSession session=request.getSession();
		Student student=(Student)session.getAttribute("activeUser");//ת��Ϊѧ������
		Connection con=null;
		try {
			//��ȡ���ݿ�����
			con=dbUtil.getCon();
			session.setAttribute("activeUser", student);	
			//�����ݿ��е��ļ���ȫ��ȡ���洢��filetitles��
			List<Files> filelists=fileDao.fileBystuId(con,student.getStuId());
			/*//��ȡ�ϴ��ļ���Ŀ¼
				String uploadFilePath=this.getServletContext().getRealPath("/WEB-INF/upload");*/
			//�洢Ҫ���ص��ļ���
			Map<String,String>fileNameMap=new HashMap<String,String>();
			//�ݹ����filepathĿ¼�µ������ļ���Ŀ¼�����ļ����ļ����洢��map������
			for(Files files:filelists){
				fileNameMap.put(files.getFileTitle(),files.getFileTitle().substring(files.getFileTitle().indexOf("\\")+1));
			}
			//��MapĿ¼���͵�listfile.jspҳ�����չʾ
			request.setAttribute("fileNameMap",fileNameMap);
			request.setAttribute("mainPages", "/pages/student/studentfile.jsp");
			request.getRequestDispatcher("pages/student/studentIndex.jsp").forward(request, response);
		} catch (Exception e) {
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
	protected void noticeOrJobsList(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		HttpSession session=request.getSession();
		Student student=(Student)session.getAttribute("activeUser");//ת��Ϊѧ������
		
		//����typeId���ж����ռ�������Ϣ�ػ��ǹ�����Ϣ�� typeId=1 ������Ϣ     typeId=2������Ϣ
		String type=request.getParameter("type");
		Connection con=null;
		try {
			//��ȡ���ݿ�����
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
			//���� �ر����ݿ�����
			try {
				dbUtil.closeCon(con);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	//������Ϣ
	private void modifyInfo(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
			//��ȡ��ǰ��¼���û���Ϣ
		HttpSession session=request.getSession();
		Student student=(Student)session.getAttribute("activeUser");//ת��Ϊѧ������
		Connection con=null;
		try {
			//��ȡ���ݿ�����
			con=dbUtil.getCon();
			session.setAttribute("activeUser", student);
			request.setAttribute("student", student);
			request.setAttribute("mainPages", "/pages/student/studentInfoUpdate.jsp");
			request.getRequestDispatcher("pages/student/studentIndex.jsp").forward(request, response);
		} catch (Exception e) {
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
	//��Ϣ����
	private void save(HttpServletRequest request, HttpServletResponse response) {
			// TODO Auto-generated method stub
		//��ȡ Id  ����  ���� �绰  �Ա�  �꼶  רҵ ����
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
	//����չʾ
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
		
		
		//����չʾ     ����
	protected void jobApplyList(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException {
			// TODO Auto-generated method stub
			request.setCharacterEncoding("utf-8");       //���ñ����ʽ
			String jobId=request.getParameter("jobId");  //�õ�����ID
			HttpSession session=request.getSession();     //�õ�һ��session����
			Student student=(Student)session.getAttribute("activeUser");//ת��Ϊѧ������
			Connection con=null;
			List<Apply>applyList=null;
			try {
				//��ȡ���ݿ�����
				con=dbUtil.getCon();
				session.setAttribute("activeUser", student);
				//Ŀǰ��Ҫ�����Ǹ�����
				applyList=applyDao.applyselect(con, Integer.parseInt(jobId));
				boolean isApply=false;
				Iterator<Apply> it=applyList.iterator(); //��������Iterator��ģʽ
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
				//���� �ر����ݿ�����
				try {
					dbUtil.closeCon(con);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
}
