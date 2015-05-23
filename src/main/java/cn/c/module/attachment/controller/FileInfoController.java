package cn.c.module.attachment.controller;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import cn.c.core.controller.SimpleController;
import cn.c.module.attachment.domain.FileInfo;
import cn.c.module.attachment.service.FileInfoService;

@Controller
@RequestMapping("/file")
public class FileInfoController extends SimpleController<FileInfo, FileInfoService>{
	
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> uploadByAjax(@RequestParam(value="file", required=false) MultipartFile[] files, HttpServletResponse response) throws IOException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<Map<String, Object>> items = new ArrayList<Map<String, Object>>();
		if(files != null) {
			for(MultipartFile file : files) {
				
				if (!file.isEmpty()) {
					Map<String, Object> item = new HashMap<String, Object>();
					
					String fileName = file.getOriginalFilename();
					int fileSuffixIndex = fileName.lastIndexOf('.');
					
		            FileInfo fileByNew = new FileInfo(file.getBytes());
		            fileByNew.setFileName(fileName);
		            fileByNew.setFileSuffix(fileSuffixIndex>-1 ? fileName.substring(fileSuffixIndex) : "");
		            fileByNew.setFileContentType(file.getContentType());
		            fileByNew.setFileSize(file.getSize());
		            fileByNew.setAccessPath(this.getUriDirector());
		            this.getService().save(fileByNew);
		            
		            item.put("uuid", fileByNew.getUuid());
		            item.put("fileName", fileByNew.getFileName());
		            items.add(item);
				}
			}
		}

		resultMap.put("items", items);
		return resultMap;
    }
	
	
	@RequestMapping(value = "/secondUpload", method = RequestMethod.POST, consumes="application/json")
	@ResponseBody
    public Map<String, Object> secondUploadByAjax(@RequestBody List<Map<String, Object>> requestData) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<Map<String, Object>> items = new ArrayList<Map<String, Object>>();
		for(Map<String, Object> params : requestData) {
			Map<String, Object> item = new HashMap<String, Object>();
			String md5 = params.get("md5").toString().toUpperCase();
			String fileName = (String)params.get("fileName");
			boolean isCan = this.getService().isCanSecondUpload(md5);
			item.put("canSecondUpload", isCan);
			item.put("md5", md5);
			item.put("fileName", fileName);
			item.put("fileElementId", params.get("fileElementId").toString());
			
			if(isCan) {
				int fileSuffixIndex = fileName.lastIndexOf('.');
				FileInfo fileByNew = new FileInfo();
	            fileByNew.setFileName(fileName);
	            fileByNew.setFileSuffix(fileSuffixIndex>-1 ? fileName.substring(fileSuffixIndex) : "");
	            fileByNew.setFileContentType(params.get("contentType").toString());
	            fileByNew.setFileSize(Long.parseLong(params.get("size").toString()));
	            fileByNew.setAccessPath(this.getUriDirector());
	            fileByNew.setFileMd5(md5);
	            this.getService().save(fileByNew);
	            
	            item.put("uuid", fileByNew.getUuid());
			}
			items.add(item);
		}
		
		resultMap.put("items", items);
		return resultMap;
	}
	@RequestMapping(value = "/{uuid}", method = RequestMethod.GET)
	public void getFile(@PathVariable("uuid") String uuid, HttpServletResponse response) throws IOException{
		FileInfo fileInfo = this.getService().findByUuid(uuid, true);
		this.getService().updateAccessCount(fileInfo.getId());
		String fileName = URLEncoder.encode(fileInfo.getFileName(), "UTF-8");
		response.addHeader("Content-Disposition","filename=" + fileName);
		response.setContentType(fileInfo.getFileContentType());
		ServletOutputStream out = response.getOutputStream();
		out.write(fileInfo.getFileByte());
		out.flush();
		
	}
}
