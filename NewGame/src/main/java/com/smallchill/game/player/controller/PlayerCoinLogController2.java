package com.smallchill.game.player.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.smallchill.core.plugins.dao.Db;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.smallchill.common.base.BaseController;
import com.smallchill.core.annotation.Json;
import com.smallchill.core.constant.ConstConfig;
import com.smallchill.core.constant.ConstShiro;
import com.smallchill.core.toolbox.kit.CharsetKit;
import com.smallchill.core.toolbox.kit.StrKit;
import com.smallchill.core.toolbox.kit.URLKit;
import com.smallchill.game.service.CommonService;

@Controller
@RequestMapping("/player")
public class PlayerCoinLogController2 extends BaseController implements ConstShiro {

    @Autowired
    private HttpServletRequest request;
    @Autowired
    private CommonService commonService;

	
    @SuppressWarnings({"rawtypes"})
    @Json
    @RequestMapping("/bolerllist")
    public Object bolerllist() {
    	Object gird = new Object();
    	String parameter = request.getParameter("where");
    	Map paras = null;
    	if (StrKit.isBlank(parameter)) {
    		return gird;
    	} else {
    		if (parameter.contains("%")) {
    			parameter = URLKit.decode(parameter, CharsetKit.UTF_8);
    		}
    		paras = JSON.parseObject(parameter, Map.class);
    	}
    	
    	List<Map> xml3 = commonService.getInfoList("player_gold_change_log.3_gold_change",paras);
    	List<Map> xml5 = commonService.getInfoList("player_gold_change_log.5_gold_change",paras);
    	List<Map> xml7 = commonService.getInfoList("player_gold_change_log.7_gold_change",paras);
    	List<Map> xml8 = commonService.getInfoList("player_gold_change_log.8_gold_change",paras);
    	List<Map> xml9 = commonService.getInfoList("player_gold_change_log.9_gold_change",paras);
    	List<Map> xml13 = commonService.getInfoList("player_gold_change_log.13_gold_change",paras);
    	List<Map> xml14 = commonService.getInfoList("player_gold_change_log.14_gold_change",paras);
    	List<Map> xml16 = commonService.getInfoList("player_gold_change_log.16_gold_change",paras);
    	List<Map> xml17 = commonService.getInfoList("player_gold_change_log.17_gold_change",paras);
    	List<Map> xml18 = commonService.getInfoList("player_gold_change_log.18_gold_change",paras);
    	List<Map> xml19 = commonService.getInfoList("player_gold_change_log.19_gold_change",paras);
    	List<Map> xml20 = commonService.getInfoList("player_gold_change_log.20_gold_change",paras);
    	List<Map> xml21 = commonService.getInfoList("player_gold_change_log.21_gold_change",paras);
    	List<Map> xml22 = commonService.getInfoList("player_gold_change_log.22_gold_change",paras);
    	List<Map> xml25 = commonService.getInfoList("player_gold_change_log.25_gold_change",paras);
    	List<Map> xml27 = commonService.getInfoList("player_gold_change_log.27_gold_change",paras);
    	List<Map> xml28 = commonService.getInfoList("player_gold_change_log.28_gold_change",paras);
    	List<Map> xml32 = commonService.getInfoList("player_gold_change_log.32_gold_change",paras);
//    	List<Map> xml39 = commonService.getInfoList("player_gold_change_log.39_gold_change",paras);
    	xml3.addAll(xml28);
    	xml3.addAll(xml27);
    	xml3.addAll(xml25);
    	xml3.addAll(xml20);
    	xml3.addAll(xml21);
    	xml3.addAll(xml22);
    	xml3.addAll(xml19);
    	xml3.addAll(xml18);
    	xml3.addAll(xml17);
    	xml3.addAll(xml16);
    	xml3.addAll(xml14);
    	xml3.addAll(xml13);
    	xml3.addAll(xml9);
    	xml3.addAll(xml8);
    	xml3.addAll(xml7);
    	xml3.addAll(xml5);
    	xml3.addAll(xml32);
//    	xml3.addAll(xml39);
//    	return gird;
    	return xml3;
    }
    @SuppressWarnings({"rawtypes"})
    @Json
    @RequestMapping("/yilerllist")
    public Object yilerllist() {
    	Object gird = new Object();
    	String parameter = request.getParameter("where");
    	Map paras = null;
    	if (StrKit.isBlank(parameter)) {
    		return gird;
    	} else {
    		if (parameter.contains("%")) {
    			parameter = URLKit.decode(parameter, CharsetKit.UTF_8);
    		}
    		paras = JSON.parseObject(parameter, Map.class);
    	}
    	
    	List<Map> xml3 = commonService.getInfoList("player_gold_change_log.3_gold_change",paras);
    	List<Map> xml5 = commonService.getInfoList("player_gold_change_log.5_gold_change",paras);
    	List<Map> xml7 = commonService.getInfoList("player_gold_change_log.7_gold_change",paras);
    	List<Map> xml8 = commonService.getInfoList("player_gold_change_log.8_gold_change",paras);
    	List<Map> xml9 = commonService.getInfoList("player_gold_change_log.9_gold_change",paras);
    	List<Map> xml13 = commonService.getInfoList("player_gold_change_log.13_gold_change",paras);
    	List<Map> xml14 = commonService.getInfoList("player_gold_change_log.14_gold_change",paras);
    	List<Map> xml16 = commonService.getInfoList("player_gold_change_log.16_gold_change",paras);
    	List<Map> xml17 = commonService.getInfoList("player_gold_change_log.17_gold_change",paras);
    	List<Map> xml18 = commonService.getInfoList("player_gold_change_log.18_gold_change",paras);
    	List<Map> xml19 = commonService.getInfoList("player_gold_change_log.19_gold_change",paras);
    	List<Map> xml21 = commonService.getInfoList("player_gold_change_log.21_gold_change",paras);
    	List<Map> xml22 = commonService.getInfoList("player_gold_change_log.22_gold_change",paras);
    	List<Map> xml25 = commonService.getInfoList("player_gold_change_log.25_gold_change",paras);
    	List<Map> xml27 = commonService.getInfoList("player_gold_change_log.27_gold_change",paras);
    	List<Map> xml28 = commonService.getInfoList("player_gold_change_log.28_gold_change",paras);
    	List<Map> xml32 = commonService.getInfoList("player_gold_change_log.32_gold_change",paras);
    	List<Map> xml51 = commonService.getInfoList("player_gold_change_log.51_gold_change",paras);
    	List<Map> xml52 = commonService.getInfoList("player_gold_change_log.52_gold_change",paras);
    	xml3.addAll(xml28);
    	xml3.addAll(xml27);
    	xml3.addAll(xml25);
    	xml3.addAll(xml21);
    	xml3.addAll(xml22);
    	xml3.addAll(xml19);
    	xml3.addAll(xml18);
    	xml3.addAll(xml17);
    	xml3.addAll(xml16);
    	xml3.addAll(xml14);
    	xml3.addAll(xml13);
    	xml3.addAll(xml9);
    	xml3.addAll(xml8);
    	xml3.addAll(xml7);
    	xml3.addAll(xml5);
    	xml3.addAll(xml32);
    	xml3.addAll(xml51);
    	xml3.addAll(xml52);
    	return xml3;
    }
    
    
    @SuppressWarnings({"rawtypes"})
    @Json
    @RequestMapping("/jinlerllist")
    public Object jinlerllist() {
        Object gird = new Object();
        String parameter = request.getParameter("where");
        Map paras = null;
        if (StrKit.isBlank(parameter)) {
            return gird;
        } else {
            if (parameter.contains("%")) {
                parameter = URLKit.decode(parameter, CharsetKit.UTF_8);
            }
            paras = JSON.parseObject(parameter, Map.class);
        }
		
        //3,5,7,8,9,13,14,17,18,19,20,21,22,25,27,28
        List<Map> xml5 = commonService.getInfoList("player_gold_change_log.5_gold_change",paras);
        List<Map> xml7 = commonService.getInfoList("player_gold_change_log.7_gold_change",paras);
        List<Map> xml8 = commonService.getInfoList("player_gold_change_log.8_gold_change",paras);
        List<Map> xml9 = commonService.getInfoList("player_gold_change_log.9_gold_change",paras);
        List<Map> xml13 = commonService.getInfoList("player_gold_change_log.13_gold_change",paras);
        List<Map> xml14 = commonService.getInfoList("player_gold_change_log.14_gold_change",paras);
        List<Map> xml16 = commonService.getInfoList("player_gold_change_log.16_gold_change",paras);
        List<Map> xml17 = commonService.getInfoList("player_gold_change_log.17_gold_change",paras);
        List<Map> xml18 = commonService.getInfoList("player_gold_change_log.18_gold_change",paras);
        List<Map> xml19 = commonService.getInfoList("player_gold_change_log.19_gold_change",paras);
        /*List<Map> xml20 = commonService.getInfoList("player_gold_change_log.20_gold_change",paras);*/
        List<Map> xml21 = commonService.getInfoList("player_gold_change_log.21_gold_change",paras);
        List<Map> xml22 = commonService.getInfoList("player_gold_change_log.22_gold_change",paras);
        List<Map> xml25 = commonService.getInfoList("player_gold_change_log.25_gold_change",paras);
        List<Map> xml27 = commonService.getInfoList("player_gold_change_log.27_gold_change",paras);
        List<Map> xml28 = commonService.getInfoList("player_gold_change_log.28_gold_change",paras);
        xml22.addAll(xml28);
        xml22.addAll(xml27);
        xml22.addAll(xml25);
        xml22.addAll(xml21);
        /*xml22.addAll(xml20);*/
        xml22.addAll(xml19);
        xml22.addAll(xml18);
        xml22.addAll(xml17);
        xml22.addAll(xml16);
        xml22.addAll(xml14);
        xml22.addAll(xml13);
        xml22.addAll(xml9);
        xml22.addAll(xml8);
        xml22.addAll(xml7);
        xml22.addAll(xml5);
        return xml22;
    }
    @SuppressWarnings({"rawtypes"})
    @Json
    @RequestMapping("/yunlairllist")
    public Object yunlairllist() {
    	Object gird = new Object();
    	String parameter = request.getParameter("where");
    	Map paras = null;
    	if (StrKit.isBlank(parameter)) {
    		return gird;
    	} else {
    		if (parameter.contains("%")) {
    			parameter = URLKit.decode(parameter, CharsetKit.UTF_8);
    		}
    		paras = JSON.parseObject(parameter, Map.class);
    	}
    	
    	//3,5,7,8,9,13,14,17,18,19,20,21,22,25,27,28
    	List<Map> xml3 = commonService.getInfoList("player_gold_change_log.3_gold_change",paras);
    	List<Map> xml5 = commonService.getInfoList("player_gold_change_log.5_gold_change",paras);
    	List<Map> xml7 = commonService.getInfoList("player_gold_change_log.7_gold_change",paras);
    	List<Map> xml8 = commonService.getInfoList("player_gold_change_log.8_gold_change",paras);
    	List<Map> xml9 = commonService.getInfoList("player_gold_change_log.9_gold_change",paras);
    	List<Map> xml13 = commonService.getInfoList("player_gold_change_log.13_gold_change",paras);
    	List<Map> xml14 = commonService.getInfoList("player_gold_change_log.14_gold_change",paras);
    	List<Map> xml16 = commonService.getInfoList("player_gold_change_log.16_gold_change",paras);
    	List<Map> xml17 = commonService.getInfoList("player_gold_change_log.17_gold_change",paras);
    	List<Map> xml18 = commonService.getInfoList("player_gold_change_log.18_gold_change",paras);
    	List<Map> xml19 = commonService.getInfoList("player_gold_change_log.19_gold_change",paras);
    	//List<Map> xml20 = commonService.getInfoList("player_gold_change_log.20_gold_change",paras);
    	List<Map> xml21 = commonService.getInfoList("player_gold_change_log.21_gold_change",paras);
    	List<Map> xml22 = commonService.getInfoList("player_gold_change_log.22_gold_change",paras);
    	List<Map> xml25 = commonService.getInfoList("player_gold_change_log.25_gold_change",paras);
    	List<Map> xml27 = commonService.getInfoList("player_gold_change_log.27_gold_change",paras);
    	List<Map> xml28 = commonService.getInfoList("player_gold_change_log.28_gold_change",paras);
    	xml22.addAll(xml28);
    	xml22.addAll(xml27);
    	xml22.addAll(xml25);
    	xml22.addAll(xml21);
    	//xml22.addAll(xml20);
    	xml22.addAll(xml19);
    	xml22.addAll(xml18);
    	xml22.addAll(xml17);
    	xml22.addAll(xml16);
    	xml22.addAll(xml14);
    	xml22.addAll(xml13);
    	xml22.addAll(xml9);
    	xml22.addAll(xml8);
    	xml22.addAll(xml7);
    	xml22.addAll(xml5);
    	xml22.addAll(xml3);
    	return xml22;
    }
    @SuppressWarnings({"rawtypes"})
    @Json
    @RequestMapping("/legaorllist")
    public Object legaorllist() {
    	Object gird = new Object();
    	String parameter = request.getParameter("where");
    	Map paras = null;
    	if (StrKit.isBlank(parameter)) {
    		return gird;
    	} else {
    		if (parameter.contains("%")) {
    			parameter = URLKit.decode(parameter, CharsetKit.UTF_8);
    		}
    		paras = JSON.parseObject(parameter, Map.class);
    	}
    	
    	List<Map> xml1 = commonService.getInfoList("player_gold_change_log.1_gold_change",paras);
    	List<Map> xml3 = commonService.getInfoList("player_gold_change_log.3_gold_change",paras);
    	List<Map> xml4 = commonService.getInfoList("player_gold_change_log.4_gold_change",paras);
    	List<Map> xml5 = commonService.getInfoList("player_gold_change_log.5_gold_change",paras);
    	List<Map> xml7 = commonService.getInfoList("player_gold_change_log.7_gold_change",paras);
    	List<Map> xml11 = commonService.getInfoList("player_gold_change_log.11_gold_change",paras);
    	List<Map> xml15 = commonService.getInfoList("player_gold_change_log.15_gold_change",paras);
    	List<Map> xml16 = commonService.getInfoList("player_gold_change_log.16_gold_change",paras);
    	List<Map> xml19 = commonService.getInfoList("player_gold_change_log.19_gold_change",paras);
    	List<Map> xml22 = commonService.getInfoList("player_gold_change_log.22_gold_change",paras);
    	List<Map> xml28 = commonService.getInfoList("player_gold_change_log.28_gold_change",paras);
    	List<Map> xml29 = commonService.getInfoList("player_gold_change_log.29_gold_change",paras);
    	List<Map> xml32 = commonService.getInfoList("player_gold_change_log.32_gold_change",paras);
    	List<Map> xml33 = commonService.getInfoList("player_gold_change_log.33_gold_change",paras);
    	List<Map> xml34 = commonService.getInfoList("player_gold_change_log.34_gold_change",paras);
    	List<Map> xml35 = commonService.getInfoList("player_gold_change_log.35_gold_change",paras);
    	List<Map> xml38 = commonService.getInfoList("player_gold_change_log.38_gold_change",paras);
    	List<Map> xml41 = commonService.getInfoList("player_gold_change_log.41_gold_change",paras);
    	List<Map> xml44 = commonService.getInfoList("player_gold_change_log.44_gold_change",paras);
    	List<Map> xml45 = commonService.getInfoList("player_gold_change_log.45_gold_change",paras);
    	List<Map> xml47 = commonService.getInfoList("player_gold_change_log.47_gold_change",paras);
    	List<Map> xml49 = commonService.getInfoList("player_gold_change_log.49_gold_change",paras);
    	List<Map> xml51 = commonService.getInfoList("player_gold_change_log.51_gold_change",paras);
    	List<Map> xml53 = commonService.getInfoList("player_gold_change_log.53_gold_change",paras);
    	List<Map> xml58 = commonService.getInfoList("player_gold_change_log.58_gold_change",paras);
    	List<Map> xml59 = commonService.getInfoList("player_gold_change_log.59_gold_change",paras);
    	List<Map> xml61 = commonService.getInfoList("player_gold_change_log.61_gold_change",paras);
    	List<Map> xml63 = commonService.getInfoList("player_gold_change_log.63_gold_change",paras);
    	List<Map> xml64 = commonService.getInfoList("player_gold_change_log.64_gold_change",paras);
    	List<Map> xml65 = commonService.getInfoList("player_gold_change_log.65_gold_change",paras);
    	List<Map> xml66 = commonService.getInfoList("player_gold_change_log.66_gold_change",paras);
    	List<Map> xml67 = commonService.getInfoList("player_gold_change_log.67_gold_change",paras);
    	List<Map> xml70 = commonService.getInfoList("player_gold_change_log.70_gold_change",paras);
    	List<Map> xml71 = commonService.getInfoList("player_gold_change_log.71_gold_change",paras);
    	xml1.addAll(xml3); xml1.addAll(xml4); xml1.addAll(xml5); xml1.addAll(xml7);
    	xml1.addAll(xml11); xml1.addAll(xml15); xml1.addAll(xml16); xml1.addAll(xml19);
    	xml1.addAll(xml22); xml1.addAll(xml28); xml1.addAll(xml29); xml1.addAll(xml32);
    	xml1.addAll(xml33); xml1.addAll(xml34); xml1.addAll(xml35); xml1.addAll(xml38);
    	xml1.addAll(xml41); xml1.addAll(xml44); xml1.addAll(xml45); xml1.addAll(xml47);
    	xml1.addAll(xml49); xml1.addAll(xml51); xml1.addAll(xml53); xml1.addAll(xml58);
    	xml1.addAll(xml59); xml1.addAll(xml61); xml1.addAll(xml63); xml1.addAll(xml64);
    	xml1.addAll(xml65); xml1.addAll(xml66); xml1.addAll(xml67); xml1.addAll(xml70);
    	xml1.addAll(xml71);
    	return xml1;
    }

    @SuppressWarnings({"rawtypes"})
    @Json
    @RequestMapping("/yulerllist")
    public Object yulerllist() {
    	Object gird = new Object();
    	String parameter = request.getParameter("where");
    	Map paras = null;
    	if (StrKit.isBlank(parameter)) {
    		return gird;
    	} else {
    		if (parameter.contains("%")) {
    			parameter = URLKit.decode(parameter, CharsetKit.UTF_8);
    		}
    		paras = JSON.parseObject(parameter, Map.class);
    	}
    	List<Map> list = new ArrayList<>();
//    	String[] games = ConstConfig.SYS_GAMES.split(",");
		String gameIds = Db.queryStr("select param from blade_dict_data where code='128'", null);
		String[] games = gameIds.split(",");
    	for (String gameId : games) {
			paras.put("gameTable","[QPGameRecordDB].[dbo].AA_ZZ_Log_PropChange_"+gameId);
//			list.addAll(commonService.getInfoList("player_gold_change_log."+gameId+"_gold_change",paras));
			list.addAll(commonService.getInfoList("player_gold_change_log.all_gold_change",paras));
		}
    	/*
    	List<Map> xml5 = commonService.getInfoList("player_gold_change_log.5_gold_change",paras);
    	List<Map> xml7 = commonService.getInfoList("player_gold_change_log.7_gold_change",paras);
    	List<Map> xml8 = commonService.getInfoList("player_gold_change_log.8_gold_change",paras);
    	List<Map> xml9 = commonService.getInfoList("player_gold_change_log.9_gold_change",paras);
    	List<Map> xml13 = commonService.getInfoList("player_gold_change_log.13_gold_change",paras);
    	List<Map> xml14 = commonService.getInfoList("player_gold_change_log.14_gold_change",paras);
    	List<Map> xml16 = commonService.getInfoList("player_gold_change_log.16_gold_change",paras);
    	List<Map> xml17 = commonService.getInfoList("player_gold_change_log.17_gold_change",paras);
    	List<Map> xml18 = commonService.getInfoList("player_gold_change_log.18_gold_change",paras);
    	List<Map> xml19 = commonService.getInfoList("player_gold_change_log.19_gold_change",paras);
    	List<Map> xml21 = commonService.getInfoList("player_gold_change_log.21_gold_change",paras);
    	List<Map> xml22 = commonService.getInfoList("player_gold_change_log.22_gold_change",paras);
    	List<Map> xml25 = commonService.getInfoList("player_gold_change_log.25_gold_change",paras);
    	List<Map> xml27 = commonService.getInfoList("player_gold_change_log.27_gold_change",paras);
    	List<Map> xml28 = commonService.getInfoList("player_gold_change_log.28_gold_change",paras);
    	List<Map> xml38 = commonService.getInfoList("player_gold_change_log.38_gold_change",paras);
    	List<Map> xml39 = commonService.getInfoList("player_gold_change_log.39_gold_change",paras);
    	xml5.addAll(xml7);
    	xml5.addAll(xml8); 
    	xml5.addAll(xml9);
    	xml5.addAll(xml13);
    	xml5.addAll(xml14);
    	xml5.addAll(xml16);
    	xml5.addAll(xml17);
    	xml5.addAll(xml18);
    	xml5.addAll(xml19);
    	xml5.addAll(xml21);
    	xml5.addAll(xml22);
    	xml5.addAll(xml25);
    	xml5.addAll(xml27);
    	xml5.addAll(xml28);
    	xml5.addAll(xml38);
    	xml5.addAll(xml39);
    	*/
    	return list;
    }
    @SuppressWarnings({"rawtypes"})
    @Json
    @RequestMapping("/yundingrllist")
    public Object yundingrllist() {
    	Object gird = new Object();
    	String parameter = request.getParameter("where");
    	Map paras = null;
    	if (StrKit.isBlank(parameter)) {
    		return gird;
    	} else {
    		if (parameter.contains("%")) {
    			parameter = URLKit.decode(parameter, CharsetKit.UTF_8);
    		}
    		paras = JSON.parseObject(parameter, Map.class);
    	}
    	List<Map> list = new ArrayList<>();
    	String[] games = ConstConfig.SYS_GAMES.split(",");
    	for (String gameId : games) {
			list.addAll(commonService.getInfoList("player_gold_change_log."+gameId+"_gold_change",paras));
		}
    	return list;
    }
    @SuppressWarnings({"rawtypes"})
    @Json
    @RequestMapping("/qianlimarllist")
    public Object qianlimarllist() {
    	Object gird = new Object();
    	String parameter = request.getParameter("where");
    	Map paras = null;
    	if (StrKit.isBlank(parameter)) {
    		return gird;
    	} else {
    		if (parameter.contains("%")) {
    			parameter = URLKit.decode(parameter, CharsetKit.UTF_8);
    		}
    		paras = JSON.parseObject(parameter, Map.class);
    	}
    	List<Map> list = new ArrayList<>();
    	String[] games = ConstConfig.SYS_GAMES.split(",");
    	for (String gameId : games) {
    		list.addAll(commonService.getInfoList("player_gold_change_log."+gameId+"_gold_change",paras));
    	}
    	return list;
    }
}