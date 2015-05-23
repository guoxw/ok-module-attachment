package cn.c.module.attachment.service;

import cn.c.core.service.SimpleService;
import cn.c.module.attachment.domain.File;

public interface FileService extends SimpleService<File> {

	public File save(File file);
	public File findByMd5(String md5);

}
