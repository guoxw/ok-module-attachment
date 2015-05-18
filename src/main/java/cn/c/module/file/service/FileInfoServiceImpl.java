package cn.c.module.file.service;

import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.c.core.excepion.EntityNotFoundException;
import cn.c.core.service.SimpleServiceImpl;
import cn.c.core.util.StringUtils;
import cn.c.module.file.domain.File;
import cn.c.module.file.domain.FileInfo;
import cn.c.module.file.repository.FileInfoRepository;
import cn.c.module.file.repository.FileRepository;

@Service
public class FileInfoServiceImpl extends SimpleServiceImpl<FileInfo> implements FileInfoService {
	private FileInfoRepository repository;
	private FileRepository fileRepository;
	
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
		
		if(StringUtils.isNullOrEmpty(fileInfo.getFileMd5())) {
			File file = new File();
			file.setFileByte(fileInfo.getFileByte());
			File fileByFind = this.fileRepository.findByMd5(file.getMd5());
			if(fileByFind==null) {
				this.fileRepository.save(file);
			}
			fileInfo.setFileMd5(file.getMd5());
		}
		
		fileInfo = this.repository.save(fileInfo);
		return fileInfo;
	}
	

	@Override
	public FileInfo findByUuid(String uuid) {
		FileInfo file = this.repository.findByUuid(uuid);
		if(file == null) {
			throw new EntityNotFoundException();
		}
		return file;
	}
	
	@Override
	public boolean updateAccessCount(Long id) {
		int count = this.repository.updateAccessCount(id, new Date());
		if(count>0) {
			return true;
		}
		return false;
	}

	public FileInfoRepository getRepository() {
		return repository;
	}
	@Autowired
	public void setRepository(FileInfoRepository repository) {
		this.repository = repository;
	}
	
	public FileRepository getFileRepository() {
		return fileRepository;
	}
	@Autowired
	public void setFileRepository(FileRepository fileRepository) {
		this.fileRepository = fileRepository;
	}
	
}
