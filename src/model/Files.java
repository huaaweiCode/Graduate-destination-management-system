package model;
/**
 * 
* @author ���÷
* @ClassName model.File
* @Description: ������
* @date 2019��4��2�� ����10:29:42
 */
public class Files {
	 private int fileId;
	 private String fileTitle;
	 private int stuId;
	 
	 private int isDelete=0;
	 
	 
	public int getIsDelete() {
		return isDelete;
	}
	public void setIsDelete(int isDelete) {
		this.isDelete = isDelete;
	}
	public Files(String fileTitle, int stuId) {
		super();
		this.fileTitle = fileTitle;
		this.stuId = stuId;
	}
	public Files() {
		super();
		// TODO Auto-generated constructor stub
	}
	public int getFileId() {
		return fileId;
	}
	public void setFileId(int fileId) {
		this.fileId = fileId;
	}
	public String getFileTitle() {
		return fileTitle;
	}
	public void setFileTitle(String fileTitle) {
		this.fileTitle = fileTitle;
	}
	public int getStuId() {
		return stuId;
	}
	public void setStuId(int stuId) {
		this.stuId = stuId;
	}
	 
	 
}
