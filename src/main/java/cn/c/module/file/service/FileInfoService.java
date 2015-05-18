package cn.c.module.file.service;

import cn.c.core.service.SimpleService;
import cn.c.module.file.domain.FileInfo;

public interface FileInfoService extends SimpleService<FileInfo> {

	public FileInfo save(FileInfo file);
	public FileInfo findByUuid(String uuid);
	public boolean updateAccessCount(Long id);

}
