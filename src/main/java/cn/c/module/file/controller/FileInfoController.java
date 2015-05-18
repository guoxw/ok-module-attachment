package cn.c.module.file.controller;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import cn.c.core.controller.SimpleController;
import cn.c.module.file.domain.FileInfo;
import cn.c.module.file.service.FileInfoService;

@Controller
@RequestMapping("/file")
public class FileInfoController extends SimpleController<FileInfo, FileInfoService>{
	
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> uploadByAjax(@RequestParam(value="file", required=false) MultipartFile[] files) throws IOException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<Map<String, Object>> items = new ArrayList<Map<String, Object>>();
		
		for(MultipartFile file : files) {
			
			if (!file.isEmpty()) {
				Map<String, Object> item = new HashMap<String, Object>();
				
				String fileName = file.getOriginalFilename();
				int fileSuffixIndex = fileName.lastIndexOf('.');
				
	            FileInfo fileByNew = new FileInfo(file.getBytes());
	            fileByNew.setFileName(file.getOriginalFilename());
	            fileByNew.setFileSuffix(fileSuffixIndex>-1 ? file.getOriginalFilename().substring(fileSuffixIndex) : "");
	            fileByNew.setFileContentType(file.getContentType());
	            fileByNew.setFileSize(file.getSize());
	            fileByNew.setAccessPath(this.getUriDirector());
	            this.getService().save(fileByNew);
	            
	            item.put(fileByNew.getUuid(), fileByNew.getFileName());
	            items.add(item);
			}
		}
		resultMap.put("items", items);
		return resultMap;
    }
	
	@RequestMapping(value = "/{uuid}", method = RequestMethod.GET)
	public void getFile(@PathVariable("uuid") String uuid, HttpServletResponse response) throws IOException{
		FileInfo file = this.getService().findByUuid(uuid);
		this.getService().updateAccessCount(file.getId());
		String fileName = URLEncoder.encode(file.getFileName(), "UTF-8");
		response.addHeader("Content-Disposition","filename=" + fileName);
		response.setContentType(file.getFileContentType());
		ServletOutputStream out = response.getOutputStream();
		out.write(file.getFileByte());
		out.flush();
		
	}
}
