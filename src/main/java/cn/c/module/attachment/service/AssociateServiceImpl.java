package cn.c.module.attachment.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.c.core.service.SimpleServiceImpl;
import cn.c.module.attachment.domain.Associate;
import cn.c.module.attachment.domain.FileInfo;
import cn.c.module.attachment.repository.AssociateRepository;

@Service
public class AssociateServiceImpl extends SimpleServiceImpl<Associate> implements AssociateService {
	private AssociateRepository associateRepository;

	@Override
	public boolean exists(Long id) {
		return this.associateRepository.exists(id);
	}
	@Override
	public Associate findOne(Long id) {
		return this.associateRepository.findOne(id);
	}
	@Override
	public Associate findOne(Long entityId, String distinguish,
			String attachmentField, String uuid) {
		return this.associateRepository.findOne(entityId, distinguish, attachmentField, uuid);
	}
	

	
	@Override
	public Iterable<FileInfo> findAttachmentsByAssociate(Associate associates) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Iterable<FileInfo> findAttachmentsByParams(Object... params) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Associate save(Associate associate) {
		return this.associateRepository.save(associate);
	}
	@Override
	public Iterable<Associate> save(Iterable<Associate> associates) {
		return this.associateRepository.save(associates);
	}
	@Override
	public void delete(Long id) {
		Associate associate = this.associateRepository.findOne(id);
		this.delete(associate);
	}
	@Override
	public void delete(Associate associate) {
		if(associate!=null && !associate.isNew() ) {
			this.delete(associate);
		}
	}
	@Override
	public void delete(Iterable<Associate> associates) {
		this.associateRepository.delete(associates);;
	}

	public AssociateRepository getAssociateRepository() {
		return associateRepository;
	}
	@Autowired
	public void setAssociateRepository(AssociateRepository associateRepository) {
		this.associateRepository = associateRepository;
	}
	@Override
	public Iterable<Associate> findByEntityIdAndDistinguishAndAttachmentField(
			Long entityId, String distinguish, String attachmentField) {
		return this.associateRepository.findByAttachmentFieldAndDistinguishAndEntityId(attachmentField, distinguish, entityId);
	}

	
}
