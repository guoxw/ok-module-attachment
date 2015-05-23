package cn.c.module.attachment.service;

import cn.c.core.service.SimpleService;
import cn.c.module.attachment.domain.Associate;
import cn.c.module.attachment.domain.FileInfo;

public interface AssociateService extends SimpleService<Associate> {
	public boolean exists(Long id);
	public Associate findOne(Long id);
	public Associate findOne(Long entityId, String distinguish, String attachmentField, String uuid);
	public Iterable<Associate> findByEntityIdAndDistinguishAndAttachmentField(Long entityId, String distinguish, String attachmentField);
	public Iterable<FileInfo> findAttachmentsByAssociate(Associate associates);
	/**
	 * 通过可变参数找到实体附件
	 * @param associates 可变参数顺序, entityId > distinguish(默认是类名) > attachmentField
	 * @return
	 */
	public Iterable<FileInfo> findAttachmentsByParams(Object... params);
	public Associate save(Associate associate);
	public Iterable<Associate> save(Iterable<Associate> associates);
	public void delete(Long id);
	public void delete(Associate associate);
	public void delete(Iterable<Associate> associates);
}
