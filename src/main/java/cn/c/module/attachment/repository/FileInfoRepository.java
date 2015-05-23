package cn.c.module.attachment.repository;

import java.util.Date;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import cn.c.module.attachment.domain.FileInfo;

public interface FileInfoRepository extends CrudRepository<FileInfo, Long>{

	FileInfo findByUuid(String uuid);

	@Modifying
	@Query("update FileInfo f set f.accessCount=f.accessCount+1, f.lastAccessDate=:date where f.id=:id")
	int updateAccessCount(@Param("id")Long id, @Param("date")Date date);
	
}
