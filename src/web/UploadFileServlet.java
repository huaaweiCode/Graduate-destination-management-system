package web;

import javax.servlet.http.HttpServlet;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
/**
 * 
* @author ���÷
* @ClassName web.UploadFileServlet
* @Description: �����ļ��ϴ�
* @date 2019��4��1�� ����11:26:41
ʵ���ļ��ϴ�����,����Apache��֯��Commons-io-2.0.1.jar,
 */

import dao.ApplyDao;
import dao.FileDao;
import model.Apply;
import model.Files;
import model.Student;
import util.DataBaseUtil;
public class UploadFileServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	DataBaseUtil dbUtil=new DataBaseUtil();
	FileDao fileDao=new FileDao();
	ApplyDao applyDao=new ApplyDao();
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//�õ��ļ��ı���Ŀ¼�����ϴ����ļ������WEB-INFĿ¼�£����������ֱ�ӷ���,ȷ����ȫ��wtpwebapps /WebContent/upload
		String savePath=this.getServletContext().getRealPath("/WebContent/upload");
		File file=new File(savePath);
		//�ж��ϴ����ļ��ı���Ŀ¼�Ƿ���ڣ�
		if(!file.exists() && !file.isDirectory()){
			/*System.out.println(savePath+"Ŀ¼�����ڣ���Ҫ��������");*/
			//����Ŀ¼
			file.mkdirs();
		}
		//��Ϣ��ʾ��
		String message="";
		//ʹ��apache�ļ��ϴ���۴����ļ��ϴ����裻����commons-fileupload-1.2.2.jar
		//commons-fileupload ��ԭ���ǽ������������,���뵽FileItem�ļ�������ȥ.
		//�� ����һ��������DiskfileItemFactory������
		 DiskFileItemFactory factory=new DiskFileItemFactory();
		//�� ����һ���ļ��ϴ�������
		ServletFileUpload upload= new ServletFileUpload(factory);
		//����ļ����ϴ���������
		upload.setHeaderEncoding("utf-8");
		//�� �ж��ύ�����������Ƿ����ϴ���������
		if(!ServletFileUpload.isMultipartContent(request)){
			//���մ�ͳ�ķ�ʽ��ȡ����
			return;
		}
			/*���ݿ����*/
		
		Connection con=null;
		//�� ʹ��ServletFileUpload�����������ϴ����ݣ�����������ص���һ��List<FileItem>���ϣ�ÿһ��FileItem��Ӧһ��Form����������
		try {
			List<FileItem> list=upload.parseRequest(request);
			String jobId="";
			for(FileItem item:list){
				//���fileitem�з�װ������ͨ�����������
				if(item.isFormField()){
					//��ȡ������ļ���
					String name=item.getFieldName();//��ȡ��ǰ���������
					//�����ͨ���������ݵ�������������
					String value=item.getString("utf-8");
					System.out.println(name+"="+value);
					jobId=value;
				}else{
					//���file�з�װ�����ϴ��ļ�
					//�õ��ϴ��ļ�������
					String filename=item.getName();
					System.out.println(filename);
					//����ļ���Ϊ�ջ��ߴ��пո�tab �� ȥ��֮����Ϊ��
					if(filename==null || filename.trim().equals("")){
						continue;
					}
/*
 * ��ͬ����������������ļ�����һ��һ������Щ������ύ�������ļ������Ǵ�·���ģ��磺c:\a\b\1.txt ����Щֻ�ǵ������ļ������磺1.txt
*/				
					/*�����ȡ�����ϴ��ļ�����·�����֣�ֻ�����ļ�������*/
					  //�Ӻ���ǰ�� \ ������λ�ã�Ȼ�������ط���ʼȡ
					filename=filename.substring(filename.lastIndexOf("\\")+1);
					//��ȡItem��Ϊ�ϴ��ļ�������
					InputStream in=item.getInputStream();
					//����һ���ļ������
					FileOutputStream out=new FileOutputStream(savePath+"\\"+jobId+filename);
					//����һ��������
					byte buffer[]=new byte[1024];
					//�ж��������е������Ƿ��Ѿ�����ı�ʶ
					int len=0;
					//ѭ�������������뵽���������У�(len=in.read(buffer))>0�ͱ�ʾin���滹������
					while((len=in.read(buffer))>0){
						//ʹ��FileOutputStream�������������������д�뵽ָ����Ŀ¼��savePath+"\\"+filename����
						out.write(buffer,0,len);
					}
					
					//������� ������д�����ݿ�
					con=dbUtil.getCon();
					//�ļ�������t_file
					String filetitle=jobId+filename;
					//��ȡ��ǰ�û�session
					Student stu=(Student)request.getSession().getAttribute("activeUser");
					Files files=new Files(filetitle,stu.getStuId());
					fileDao.fileadd(con,files);
					//��ȡ���������fileId
					int fileId=fileDao.fileBytitle(con, filetitle).getFileId();
					Apply apply=new Apply(Integer.parseInt(jobId),stu.getStuId(),fileId);
					//������������
					applyDao.applyadd(con, apply);
					//�ر�������
					in.close();
					//�ر������
					out.close();
					//ɾ�������ļ��ϴ�ʱ��������ʱ�ļ�
					item.delete();
					message="�ļ��ϴ��ɹ���";
				}
				
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			message="�ļ��ϴ�ʧ�ܣ�";
			e.printStackTrace();
		}
		request.setAttribute("message", message);
		request.getRequestDispatcher("/message.jsp").forward(request, response);
	}
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.doGet(request, response);
	}
}
