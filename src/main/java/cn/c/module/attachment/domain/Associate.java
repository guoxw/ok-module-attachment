package cn.c.module.attachment.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

import cn.c.core.domain.IdEntity;

@Entity
@Table(name="sys_AttachmentEntityAssociate")
public class Associate extends IdEntity{
	
	private static final long serialVersionUID = 1L;
	
	private Long entityId;
	private String distinguish;
	private String attachmentField;
	private String uuid;
	
	
	public Long getEntityId() {
		return entityId;
	}
	public void setEntityId(Long entityId) {
		this.entityId = entityId;
	}
	public String getDistinguish() {
		return distinguish;
	}
	public void setDistinguish(String distinguish) {
		this.distinguish = distinguish;
	}
	public String getAttachmentField() {
		return attachmentField;
	}
	public void setAttachmentField(String attachmentField) {
		this.attachmentField = attachmentField;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (null == obj) {
			return false;
		}

		if (this == obj) {
			return true;
		}

		if (!getClass().equals(obj.getClass())) {
			return false;
		}

		Associate that = (Associate) obj;
		
		if(null != this.getId() && this.getId().equals(that.getId())) {
			return true;
		}
		
		if(null != this.getUuid() && null != this.getAttachmentField() 
				&& null != this.getDistinguish() && null != this.getEntityId()
				&& this.getUuid().equals(that.getUuid()) && this.getAttachmentField().equals(that.getAttachmentField())
				&& this.getDistinguish().equals(that.getDistinguish()) && this.getEntityId().equals(that.getEntityId())) {
			return true;
		}
		

		return false;
		
		
	}
	
}
