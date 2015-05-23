package cn.c.module.attachment.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.c.core.excepion.BusinessException;
import cn.c.core.excepion.NecessaryParametersNotSatisfiedException;
import cn.c.core.service.SimpleServiceImpl;
import cn.c.core.util.MD5Util;
import cn.c.core.util.StringUtil;
import cn.c.module.attachment.domain.File;
import cn.c.module.attachment.repository.FileRepository;

@Service
public class FileServiceImpl extends SimpleServiceImpl<File> implements FileService {
	private FileRepository fileRepository;

	@Override
	public File save(File file) {
		if(file.getFileByte() == null){
			throw new BusinessException("没有文件需要保存!!!");
		}
		file.setMd5(MD5Util.MD5(file.getFileByte()));
		
		// TODO 判断系统配置,是保存在数据库中还是文件夹中
		
		return this.fileRepository.save(file);
	}

	@Override
	public File findByMd5(String md5) {
		if(StringUtil.isNullOrEmpty(md5)){
			throw new NecessaryParametersNotSatisfiedException("md5");
		}
		File file = this.fileRepository.findByMd5(md5);
		return file;
	}

	public FileRepository getFileRepository() {
		return fileRepository;
	}
	@Autowired
	public void setFileRepository(FileRepository fileRepository) {
		this.fileRepository = fileRepository;
	}
	
}
