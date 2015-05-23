package cn.c.module.attachment.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import cn.c.core.util.ApplicationContextUtil;
import cn.c.module.attachment.domain.Associate;
import cn.c.module.attachment.service.AssociateService;

public class AttachmentUtil {
	private static AssociateService associateService = null;
	
	static {
		associateService = (AssociateService) ApplicationContextUtil.getBean("associateServiceImpl");
	}
	
	public static void associateEntity(Associate associate) {
		Associate associateByFind = null;
		if(associate.isNew()) {
			associateByFind = associateService.findOne(associate.getEntityId(), associate.getDistinguish(), associate.getAttachmentField(), associate.getUuid());
		} else {
			associateByFind = associateService.findOne(associate.getId());
		}
		if(associateByFind == null) {
			associateService.save(associate);
		}
	}
	
	public static void associateEntity(List<Associate> associates) {
		associateEntity(associates, true);
	}
	
	public static void associateEntity(List<Associate> associates, boolean incremental) {
		Set<String> keys = new HashSet<String>();
		Set<Associate> associateByFind = new HashSet<Associate>();
		
		for(Associate associate : associates) {
			String key = associate.getEntityId() + "_" + associate.getDistinguish() + "_" + associate.getAttachmentField();
			keys.add(key);
		}
		
		for(String key : keys) {
			String[] params = key.split("_");
			associateByFind.addAll((ArrayList<Associate>)associateService.findByEntityIdAndDistinguishAndAttachmentField(Long.parseLong(params[0]), params[1], params[2]));
		}
		
		//associateByFind.addAll(associates);
		
		boolean has = false;
		List<Associate> associateAdd = new ArrayList<Associate>();
		associateAdd.addAll(associateByFind);
		for(Associate associate : associates) {
			has = false;
			for(Associate associate1 : associateByFind) {
				if(associate.equals(associate1)) {
					has = true;
					break;
				}
			}
			if(!has) {
				associateAdd.add(associate);
			}
		}
		
		associateService.save(associateAdd);
		
		if(!incremental) {
			List<Associate> associateDel = new ArrayList<Associate>();
			for(Associate associate1 : associateByFind) {
				if(!associates.contains(associate1)) {
					associateDel.add(associate1);
				}
			}
			associateService.delete(associateDel);
		}
		
	
		
	}
	
	
}
