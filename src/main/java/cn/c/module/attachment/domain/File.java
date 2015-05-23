package cn.c.module.attachment.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

import cn.c.core.domain.IdEntity;

@Entity
@Table(name="sys_File")
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
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	
	
}
