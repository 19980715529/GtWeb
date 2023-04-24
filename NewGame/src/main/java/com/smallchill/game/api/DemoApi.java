package com.smallchill.game.api;

import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.smallchill.common.base.BaseController;
import com.smallchill.core.annotation.DoControllerLog;
import com.smallchill.core.annotation.Json;
import com.smallchill.core.constant.ConstConfig;
import com.smallchill.core.constant.ConstShiro;
import com.smallchill.core.plugins.dao.Blade;
import com.smallchill.core.toolbox.CMap;
import com.smallchill.core.toolbox.ajax.AjaxResult;
import com.smallchill.core.toolbox.file.BladeFile;
import com.smallchill.core.toolbox.file.BladeFileKit;
import com.smallchill.game.newmodel.logindb.Notice;
import com.smallchill.game.newmodel.logindb.Noticedetail;

@RestController
@RequestMapping("/api")
public class DemoApi extends BaseController implements ConstShiro {
	
	@DoControllerLog(name="进入测试API")
	@PostMapping("/test")
	public AjaxResult doErrorLog(String id,String msg) {
		
		return success(SAVE_SUCCESS_MSG);
	}
	
	@Json
	@RequestMapping("/upload_json")
	public CMap upload_json(@RequestParam("imgFile") MultipartFile file) {
		CMap cmap = CMap.init();
		if (null == file) {
			cmap.set("error", 1);
			cmap.set("message", "请选择要上传的图片");
			return cmap;
		}
		String originalFileName = file.getOriginalFilename();
		String dir = getParameter("dir", "image");
		// 测试后缀
		boolean ok = BladeFileKit.testExt(dir, originalFileName);
		if (!ok) {
			cmap.set("error", 1);
			cmap.set("message", "上传文件的类型不允许");
			return cmap;
		}
		BladeFile bf = getFile(file, dir);
		bf.transfer();
		Object fileId = bf.getFileId();	
		String url = ConstConfig.DOMAIN + bf.getUploadVirtualPath();
		cmap.set("error", 0);
		cmap.set("title", fileId);
		cmap.set("url", url);
		cmap.set("vurl", bf.getUploadVirtualPath());
		cmap.set("name", originalFileName);
		return cmap;	
	}

	@Json
	@RequestMapping("/createData")
	public AjaxResult createData() {
		List<Notice> notices = Blade.create(Notice.class).findAll();
		for (Notice notice : notices) {
			System.out.println(notice.getClientid()+"---"+notice.getNotice());
			List<Noticedetail> parseArray = JSON.parseArray(notice.getNotice(), Noticedetail.class);
			for (Noticedetail noticedetail : parseArray) {
				noticedetail.setClientid(notice.getClientid());
			}
			Blade.create(Noticedetail.class).saveBatch(parseArray);
		}
		return success("成功");	
	}
}