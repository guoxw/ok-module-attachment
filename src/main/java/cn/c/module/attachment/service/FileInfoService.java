package cn.c.module.attachment.service;

import cn.c.core.service.SimpleService;
import cn.c.module.attachment.domain.FileInfo;

public interface FileInfoService extends SimpleService<FileInfo> {

	public FileInfo save(FileInfo file);
	public FileInfo findByUuid(String uuid);
	public boolean updateAccessCount(Long id);
	public boolean isCanSecondUpload(String md5);
	public FileInfo findByUuid(String uuid, boolean eager);

}
