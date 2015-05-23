package cn.c.module.attachment.repository;

import org.springframework.data.repository.CrudRepository;

import cn.c.module.attachment.domain.File;

public interface FileRepository extends CrudRepository<File, Long>{

	File findByMd5(String md5);
	
}
