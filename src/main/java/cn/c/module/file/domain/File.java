package cn.c.module.file.domain;

import cn.c.core.domain.IdEntity;
import cn.c.core.util.MD5Util;

public class File extends IdEntity{
	
	private static final long serialVersionUID = 1L;

	private String md5;
	private byte[] fileByte;
	private String filePath;
	
	
	public String getMd5() {
		return md5;
	}
	public void setMd5(String md5) {
		this.md5 = md5;
	}
	public byte[] getFileByte() {
		return fileByte;
	}
	public void setFileByte(byte[] fileByte) {
		this.fileByte = fileByte;
		this.md5 = MD5Util.MD5(this.fileByte);
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	
	
}
