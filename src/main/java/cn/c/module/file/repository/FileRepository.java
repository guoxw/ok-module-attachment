package cn.c.module.file.repository;

import org.springframework.data.repository.CrudRepository;

import cn.c.module.file.domain.File;

public interface FileRepository extends CrudRepository<File, Long>{

	File findByMd5(String md5);
	
}
