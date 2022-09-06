package web;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DownloadServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		//��ǰ�˻�ȡҪ���ص��ļ���
		request.setCharacterEncoding("utf-8");
		//��ȡ��Ҫ���ص��ļ�����
		String fileName=request.getParameter("fileId");
		fileName=new String(fileName.getBytes("iso8859-1"),"utf-8");
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
	}
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.doPost(request, response);
	}
	
}
