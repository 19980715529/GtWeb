package com.smallchill.db.config.controller;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.smallchill.common.base.BaseController;
import com.smallchill.core.annotation.DoControllerLog;
import com.smallchill.core.annotation.Json;
import com.smallchill.core.annotation.Permission;
import com.smallchill.core.constant.ConstShiro;
import com.smallchill.core.plugins.dao.Blade;
import com.smallchill.core.shiro.ShiroKit;
import com.smallchill.core.toolbox.CMap;
import com.smallchill.core.toolbox.ajax.AjaxResult;
import com.smallchill.core.toolbox.kit.DateFormatKit;
import com.smallchill.core.toolbox.kit.HttpKit;
import com.smallchill.core.toolbox.kit.StrKit;
import com.smallchill.core.toolbox.support.Convert;
import com.smallchill.core.utils.MD5;
import com.smallchill.core.utils.SerialNumberGeneratorCommonUtil;
import com.smallchill.db.config.model.request.LivcardBuildStreamRequest;
import com.smallchill.game.model.LivcardAssociator;
import com.smallchill.game.model.LivcardBuildStream;
import com.smallchill.game.service.CommonService;

@Controller
@RequestMapping("/livcard")
public class GlobalLivcardController extends BaseController implements ConstShiro {
	private static String BASE_PATH = "/db/livcard/";
	private static String CODE = "livcard";
	private static String PREFIX = "livcard";
	private static String LIST_SOURCE = "db_livcard.list";
	
	@Autowired
	private CommonService commonService;
	
	@DoControllerLog(name="进入实卡配置列表页面")
	@RequestMapping("/")
	@Permission(ADMINISTRATOR)
	public String index(ModelMap mm) {
		mm.put("code", CODE);
		mm.put("ConfigKey", "livcardconfig");
		return BASE_PATH + "livcard.html";
	}
	
	@RequestMapping("/membercard")
	@Permission(ADMINISTRATOR)
	public String membercard(ModelMap mm) {
		mm.put("code", CODE);
		mm.put("ConfigKey", "membercard");
		return BASE_PATH + "membercard.html";
	}
	@Json
	@RequestMapping("/membercardlist")
	@Permission({ ADMINISTRATOR})
	public Object membercardlist() {
		Object gird = paginateBySelf(LIST_SOURCE);
		return gird;
	}
	@Json
	@RequestMapping("/stocklist")
	@Permission({ ADMINISTRATOR})
	public Object stocklist() {
		Object gird = commonService.getInfoList("db_livcard.statistics_list", null);
		return gird;
	}
	
	@RequestMapping("/gen")
	@Permission(ADMINISTRATOR)
	public String gen(ModelMap mm) {
		mm.put("code", CODE);
		return BASE_PATH + "membercard_add.html";
	}

	@RequestMapping("/stock")
	@Permission(ADMINISTRATOR)
	public String stock(ModelMap mm) {
		mm.put("code", CODE);
		return BASE_PATH + "livcard_statistics.html";
	}
	
	@RequestMapping(KEY_EDIT + "/{id}")
	@Permission(ADMINISTRATOR)
	public String edit(@PathVariable String id, ModelMap mm) {
		mm.put("code", CODE);
		mm.put("BuildID", id);
		return BASE_PATH + "membercard_edit.html";
	}
	
	@Json
	@RequestMapping(KEY_SAVE)
	@Permission(ADMINISTRATOR)
	public AjaxResult save() {
		LivcardBuildStreamRequest configInfo = mapping(PREFIX, LivcardBuildStreamRequest.class);
		LivcardBuildStream buildStream = new LivcardBuildStream();
		buildStream.setAdminname(ShiroKit.getUser().getName());
		buildStream.setBuildaddr(HttpKit.getRequest().getRemoteAddr());
		buildStream.setBuildcardpacket("0".getBytes());
		buildStream.setBuildcount(configInfo.getBuildcount());
		Date now = new Date();
		buildStream.setBuilddate(now);
		buildStream.setCardprice(configInfo.getCardprice());
		buildStream.setCardtypeid(configInfo.getCardtypeid());
		buildStream.setCurrency(configInfo.getCurrency());
		buildStream.setDownloadcount(0);
		buildStream.setGold(configInfo.getGold());
		buildStream.setNoteinfo(configInfo.getNoteinfo());
		int temp = Blade.create(LivcardBuildStream.class).saveRtId(buildStream);
		
		List<LivcardAssociator> associators = new ArrayList<LivcardAssociator>();
		LivcardAssociator associator = null;
		int slen = configInfo.getBuildcount();
		for (int i = 0; i < slen; i++) {
			associator = new LivcardAssociator();
			associator.setBuilddate(now);
			associator.setSerialid(genSerialID(configInfo.getPrefix(), configInfo.getSerialLength()));
			associator.setBuildid(temp);
			associator.setCardtypeid(configInfo.getCardtypeid());
			associator.setCardprice(configInfo.getCardprice());
			associator.setCurrency(configInfo.getCurrency());
			associator.setValiddate(configInfo.getValiddate());
			associator.setUserange(configInfo.getUserange());
			associator.setSalesperson(configInfo.getSalesperson());
			associator.setNullity(0);
			associator.setGold(configInfo.getGold());
			try {
				associator.setPassword(MD5.EncoderByMd5("123456"));
				associators.add(associator);
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		Blade.create(LivcardAssociator.class).saveBatch(associators);
		if (temp > 0) {
			return success(SAVE_SUCCESS_MSG);
		} else {
			return error(SAVE_FAIL_MSG);
		}
	}
	
	@Json
	@RequestMapping(KEY_UPDATE)
	@Permission(ADMINISTRATOR)
	public AjaxResult update() {
		LivcardAssociator configInfo = mapping(PREFIX, LivcardAssociator.class);
		String setup = "";
		DateFormatKit format = new DateFormatKit();
		if(configInfo.getValiddate() != null) {
			setup += "ValidDate ='" + format.getTime(configInfo.getValiddate())+"'";
			if(StrKit.notBlank(configInfo.getPassword())) {
				setup += ",Password = " + configInfo.getPassword();
			}
		} else if(configInfo.getPassword() != null) {
			setup += "Password = " + configInfo.getPassword();
		}
		CMap updateMap = CMap.init().set("BuildID", Convert.toIntArray(configInfo.getBuildid().toString()));
		boolean temp = Blade.create(LivcardAssociator.class).updateBy(setup, "BuildID in (#{join(BuildID)})", updateMap);
		if (temp) {
			return success(UPDATE_SUCCESS_MSG);
		} else {
			return error(UPDATE_FAIL_MSG);
		}
	}
	
	@RequestMapping(KEY_VIEW + "/{id}")
	@Permission(ADMINISTRATOR)
	public String view(@PathVariable Integer id, ModelMap mm) {
		mm.put("BuildID", id);
		mm.put("code", CODE);
		return BASE_PATH + "membercard_detail.html";
	}
	@Json
	@RequestMapping("/mcdlist")
	@Permission({ ADMINISTRATOR})
	public Object mcdlist() {
		Object gird = paginateBySelf("db_livcardassociator.list");
		return gird;
	}
	// 禁用
	@Json
	@RequestMapping("/nullity")
	@Permission(ADMINISTRATOR)
	public AjaxResult nullity() {
		String ids = getParameter("ids");
		CMap updateMap = CMap.init().set("ids", Convert.toIntArray(ids));
		boolean temp = Blade.create(LivcardAssociator.class).updateBy("Nullity = 1", "CardID in (#{join(ids)})", updateMap);
		if (temp) {
			return success("禁用成功!");
		} else {
			return error("禁用失败!");
		}
	}

	// 启用
	@Json
	@RequestMapping("/unNullity")
	@Permission(ADMINISTRATOR)
	public AjaxResult unNullity() {
		String ids = getParameter("ids");
		CMap updateMap = CMap.init().set("ids", Convert.toIntArray(ids));
		// 启用当前条
		boolean temp = Blade.create(LivcardAssociator.class).updateBy("Nullity = 0", "CardID in (#{join(ids)})", updateMap);
		if (temp) {
			return success("启用成功!");
		} else {
			return error("启用失败!");
		}
	}
	
	private String genSerialID(String prefix,Integer length) {
		String serialId = prefix;
		DateFormatKit format = new DateFormatKit();
		serialId = serialId + format.getYearDays();
		serialId = serialId + SerialNumberGeneratorCommonUtil.generateRandom(length-serialId.length());
		return serialId;
	}
}