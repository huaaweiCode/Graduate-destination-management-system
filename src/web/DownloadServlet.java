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
		//从前端获取要下载的文件名
		request.setCharacterEncoding("utf-8");
		//获取所要下载的文件名称
		String fileName=request.getParameter("fileId");
		fileName=new String(fileName.getBytes("iso8859-1"),"utf-8");
		//上传的文件都是保存在/WEB-INF/upload目录下的子目录中
		String fileSaveRootPath=this.getServletContext().getRealPath("/WebContent/upload");
		//通过文件名找到文件的所在目录
		/*String path=findFileSavePathByFileName(fileName,fileSaveRootPath);*/
		//得到要下载的文件
		File file=new File(fileSaveRootPath+"\\"+fileName);
		//如果文件不存在
		if(!file.exists()){
			request.setAttribute("message", "您要下载的资料已经被删除了！");
			request.getRequestDispatcher("/message.jsp").forward(request, response);
			return;
		}
		//处理文件名
		String realname=fileName.substring(fileName.indexOf("\\")+1);
		//设置响应头，控制浏览器下载该文件
		response.setHeader("content-disposition", "attachment;filename="+URLEncoder.encode(realname,"UTF-8"));
		//读取要下载的文件，保存到文件输入流
		FileInputStream in=new FileInputStream(fileSaveRootPath+"\\"+fileName);
		//创建输出流
		OutputStream out=response.getOutputStream();
		//创建缓冲区
		byte buffer[]=new byte[1024];
		int len=0;
		//循环将输入流中的内容读取到缓冲区当中
		while((len=in.read(buffer))>0){
			out.write(buffer,0,len);
		}
		//关闭文件输入流
		in.close();
		//关闭文件输出流
		out.close();
		//通过文件名和存储文件的根目录找到要下载的文件的所在路径
	}
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.doPost(request, response);
	}
	
}
