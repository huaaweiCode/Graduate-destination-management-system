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
* @author 马金梅
* @ClassName web.UploadFileServlet
* @Description: 简历文件上传
* @date 2019年4月1日 下午11:26:41
实现文件上传功能,依靠Apache组织的Commons-io-2.0.1.jar,
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
		//得到文件的保存目录。将上传的文件存放在WEB-INF目录下，不允许外界直接访问,确保安全。wtpwebapps /WebContent/upload
		String savePath=this.getServletContext().getRealPath("/WebContent/upload");
		File file=new File(savePath);
		//判断上传的文件的保存目录是否存在？
		if(!file.exists() && !file.isDirectory()){
			/*System.out.println(savePath+"目录不存在，需要创建啊！");*/
			//创建目录
			file.mkdirs();
		}
		//消息提示：
		String message="";
		//使用apache文件上传组价处理文件上传步骤；导入commons-fileupload-1.2.2.jar
		//commons-fileupload 的原理是将请求体解析后,存入到FileItem的集合里面去.
		//① 创建一个解析器DiskfileItemFactory工厂。
		 DiskFileItemFactory factory=new DiskFileItemFactory();
		//② 创建一个文件上传解析器
		ServletFileUpload upload= new ServletFileUpload(factory);
		//解决文件名上传乱码问题
		upload.setHeaderEncoding("utf-8");
		//③ 判断提交上来的数据是否是上传表单的数据
		if(!ServletFileUpload.isMultipartContent(request)){
			//按照传统的方式获取数据
			return;
		}
			/*数据库设计*/
		
		Connection con=null;
		//④ 使用ServletFileUpload解析器解析上传数据，解析结果返回的是一个List<FileItem>集合，每一个FileItem对应一个Form表单的输入项
		try {
			List<FileItem> list=upload.parseRequest(request);
			String jobId="";
			for(FileItem item:list){
				//如果fileitem中封装的是普通输入项的数据
				if(item.isFormField()){
					//获取输入的文件名
					String name=item.getFieldName();//获取当前表单项的名称
					//解决普通输入项数据的中文乱码问题
					String value=item.getString("utf-8");
					System.out.println(name+"="+value);
					jobId=value;
				}else{
					//如果file中封装的是上传文件
					//得到上传文件的名称
					String filename=item.getName();
					System.out.println(filename);
					//如果文件名为空或者带有空格，tab 等 去除之后还是为空
					if(filename==null || filename.trim().equals("")){
						continue;
					}
/*
 * 不同的浏览器传上来的文件名不一定一样，有些浏览器提交上来的文件名称是带路径的，如：c:\a\b\1.txt 而有些只是单纯的文件名，如：1.txt
*/				
					/*处理获取到的上传文件名的路径部分，只保留文件名部分*/
					  //从后往前找 \ 的索引位置，然后从这个地方开始取
					filename=filename.substring(filename.lastIndexOf("\\")+1);
					//获取Item中为上传文件输入流
					InputStream in=item.getInputStream();
					//创建一个文件输出流
					FileOutputStream out=new FileOutputStream(savePath+"\\"+jobId+filename);
					//创建一个缓冲区
					byte buffer[]=new byte[1024];
					//判断输入流中的数据是否已经读完的标识
					int len=0;
					//循环将输入流读入到缓冲区当中，(len=in.read(buffer))>0就表示in里面还有数据
					while((len=in.read(buffer))>0){
						//使用FileOutputStream输出流将缓冲区的数据写入到指定的目录（savePath+"\\"+filename）中
						out.write(buffer,0,len);
					}
					
					//设计数据 将数据写入数据库
					con=dbUtil.getCon();
					//文件名存入t_file
					String filetitle=jobId+filename;
					//获取当前用户session
					Student stu=(Student)request.getSession().getAttribute("activeUser");
					Files files=new Files(filetitle,stu.getStuId());
					fileDao.fileadd(con,files);
					//获取插入简历的fileId
					int fileId=fileDao.fileBytitle(con, filetitle).getFileId();
					Apply apply=new Apply(Integer.parseInt(jobId),stu.getStuId(),fileId);
					//申请表插入数据
					applyDao.applyadd(con, apply);
					//关闭输入流
					in.close();
					//关闭输出流
					out.close();
					//删除处理文件上传时产生的临时文件
					item.delete();
					message="文件上传成功！";
				}
				
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			message="文件上传失败！";
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
