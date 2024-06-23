package logic;
import java.io.Serializable;

public class myfile implements Serializable {
	private String WhatToDo;
    private String fileName;
    private String path;
    private String type;
    private byte[] fileData;

  

    public myfile(String whatToDo, String fileName, String path, String type, byte[] fileData) {
		this.WhatToDo = whatToDo;
		this.fileName = fileName;
		this.path = path;
		this.type = type;
		this.fileData = fileData;
	}



	public String getWhatToDo() {
		return WhatToDo;
	}



	public void setWhatToDo(String whatToDo) {
		WhatToDo = whatToDo;
	}



	public String getFileName() {
		return fileName;
	}



	public void setFileName(String fileName) {
		this.fileName = fileName;
	}



	public String getPath() {
		return path;
	}



	public void setPath(String path) {
		this.path = path;
	}



	public String getType() {
		return type;
	}



	public void setType(String type) {
		this.type = type;
	}



	public byte[] getFileData() {
		return fileData;
	}



	public void setFileData(byte[] fileData) {
		this.fileData = fileData;
	}


}
