package cn.c.module.attachment.service;

import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.c.core.excepion.NecessaryParametersNotSatisfiedException;
import cn.c.core.service.SimpleServiceImpl;
import cn.c.core.util.MD5Util;
import cn.c.core.util.StringUtil;
import cn.c.module.attachment.domain.File;
import cn.c.module.attachment.domain.FileInfo;
import cn.c.module.attachment.repository.FileInfoRepository;

@Service
public class FileInfoServiceImpl extends SimpleServiceImpl<FileInfo> implements FileInfoService {
	private FileInfoRepository fileInfoRepository;
	private FileService fileService;
	
	@Override
	public FileInfo save(FileInfo fileInfo) {
		if(fileInfo.isNew()) {
			fileInfo.setUuid(UUID.randomUUID().toString());
			fileInfo.setAccessPath(fileInfo.getAccessPath()+"/"+fileInfo.getUuid());
			fileInfo.setAccessCount(0);
			fileInfo.setCreateTime(new Date());
		} else {
			fileInfo.setUpdateTime(new Date());
		}
		
		if(StringUtil.isNullOrEmpty(fileInfo.getFileMd5())) {
			String fileMd5 = MD5Util.MD5(fileInfo.getFileByte());
			File fileByFind = this.fileService.findByMd5(fileMd5);
			if(fileByFind==null) {
				File file = new File();
				file.setFileByte(fileInfo.getFileByte());
				file.setMd5(fileMd5);
				this.fileService.save(file);
			}
			fileInfo.setFileMd5(fileMd5);
		}
		
		fileInfo = this.fileInfoRepository.save(fileInfo);
		return fileInfo;
	}
	

	@Override
	public FileInfo findByUuid(String uuid) {
		return this.findByUuid(uuid, false);
	}
	
	@Override
	public FileInfo findByUuid(String uuid, boolean eager) {
		if(StringUtil.isNullOrEmpty(uuid)) {
			throw new NecessaryParametersNotSatisfiedException("uuid");
		}
		
		FileInfo fileInfo = this.fileInfoRepository.findByUuid(uuid);
		if(eager && fileInfo!=null && !StringUtil.isNullOrEmpty(fileInfo.getFileMd5())) {
			File file = this.fileService.findByMd5(fileInfo.getFileMd5());
			fileInfo.setFileByte(file.getFileByte());
		}
		return fileInfo;
	}
	
	@Override
	public boolean updateAccessCount(Long id) {
		int count = this.fileInfoRepository.updateAccessCount(id, new Date());
		if(count>0) {
			return true;
		}
		return false;
	}
	
	@Override
	public boolean isCanSecondUpload(String md5) {
		File file = this.fileService.findByMd5(md5);
		return file != null;
	}

	public FileInfoRepository getFileInfoRepository() {
		return fileInfoRepository;
	}
	@Autowired
	public void setFileInfoRepository(FileInfoRepository fileInfoRepository) {
		this.fileInfoRepository = fileInfoRepository;
	}
	
	public FileService getFileService() {
		return fileService;
	}
	@Autowired
	public void setFileService(FileService fileService) {
		this.fileService = fileService;
	}

	
}
