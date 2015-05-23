package cn.c.module.attachment.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import cn.c.module.attachment.domain.Associate;

public interface AssociateRepository extends CrudRepository<Associate, Long>{

	@Query("from Associate t where t.uuid=:uuid and t.attachmentField=:attachmentField and t.distinguish=:distinguish and t.entityId=:entityId")
	public Associate findOne(@Param("entityId")Long entityId, @Param("distinguish")String distinguish, @Param("attachmentField")String attachmentField, @Param("uuid")String uuid);

	public Iterable<Associate> findByAttachmentFieldAndDistinguishAndEntityId(String attachmentField, String distinguish, Long entityId);
}
