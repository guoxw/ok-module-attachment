package cn.c.module.attachment.aspect;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cn.c.core.domain.IdEntity;
import cn.c.core.util.EntityUtil;
import cn.c.core.util.JSONUtil;
import cn.c.core.util.StringUtil;
import cn.c.module.attachment.annotation.Attachment;
import cn.c.module.attachment.annotation.AttachmentField;
import cn.c.module.attachment.domain.Associate;
import cn.c.module.attachment.util.AttachmentUtil;

public class FileAspect {
	public void associateEntity(IdEntity entity) {
		
		Class<?> clazz = entity.getClass();
		if(clazz.isAnnotationPresent(Attachment.class)){
			Field[] fields = clazz.getDeclaredFields();
			Map<String, Set<String>> attachmentFields = new HashMap<String, Set<String>>();
			List<Associate> associates = new ArrayList<Associate>();
			for(Field field : fields) {
				if(field.isAnnotationPresent(AttachmentField.class)) {
					Set<String> fileUuids = null;
					
					String fileUuidJson = (String) EntityUtil.invoke(entity, EntityUtil.getGeterMethod(clazz, field.getName()));
					if(StringUtil.isNullOrEmpty(fileUuidJson)) {
						fileUuidJson = "[]";
					}
					fileUuids = JSONUtil.toBean(fileUuidJson, Set.class);
					
					if(!fileUuids.isEmpty()) {
						AttachmentField attachmentField = field.getAnnotation(AttachmentField.class);
						String attachmentFieldName =  attachmentField.value();
						if(StringUtil.isNullOrEmpty(attachmentFieldName)) {
							attachmentFieldName = field.getName();
						}
						attachmentFields.put(attachmentFieldName, fileUuids);
					}
					
				}
			}
			String distinguish = clazz.getName();
			Long entityId = entity.getId();
			
			Set<String> attachmentFieldKeys = attachmentFields.keySet();
			for(String attachmentFieldKey : attachmentFieldKeys) {
				for(String uuid : attachmentFields.get(attachmentFieldKey)) {
					Associate associate = new Associate();
					associate.setEntityId(entityId);
					associate.setDistinguish(distinguish);
					associate.setAttachmentField(attachmentFieldKey);
					associate.setUuid(uuid);
					associates.add(associate);
					
					/*
					System.out.print(distinguish + ",");
					System.out.print(entityId + ",");
					System.out.print(attachmentFieldKey + ",");
					System.out.println(uuid);
					*/
				}
			}

			AttachmentUtil.associateEntity(associates, false);
			
		}
		 
	}
}
