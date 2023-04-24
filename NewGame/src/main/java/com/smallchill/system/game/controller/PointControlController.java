package com.smallchill.system.game.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.fastjson.JSON;
import com.smallchill.common.base.BaseController;
import com.smallchill.core.annotation.DoControllerLog;
import com.smallchill.core.annotation.Json;
import com.smallchill.core.constant.ConstShiro;
import com.smallchill.core.toolbox.ajax.AjaxResult;
import com.smallchill.core.toolbox.kit.CharsetKit;
import com.smallchill.core.toolbox.kit.StrKit;
import com.smallchill.core.toolbox.kit.URLKit;
import com.smallchill.core.toolbox.support.Convert;
import com.smallchill.game.service.CommonService;

@Controller
@RequestMapping("/point")
public class PointControlController extends BaseController implements ConstShiro{
	private static String BASE_PATH = "/system/gamesystem/pointcontrol/";
	private static String CODE = "point";
	
	@Autowired
	private CommonService commonService;
    @Autowired
    private HttpServletRequest request;
	
	@DoControllerLog(name="进入用户点控记录列表页面")
	@RequestMapping("")
	public String index(@RequestParam(name="id", required=false) Integer id,ModelMap mm) {
		mm.put("code", CODE);
		mm.put("id", id);
		return BASE_PATH + "point_control_log.html";
	}
	
	@Json
	@RequestMapping(KEY_LIST)
	public Object list() {
		Object gird = paginateBySelf("point_control_record.new_list");
		return gird;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Json
	@RequestMapping("/getWinAdnLose")
	public AjaxResult getWinAdnLose(ModelMap mm) {
		final String ids = getParameter("ids");
		Map map = new HashMap();
		map.put("ids", Convert.toIntArray(ids));
		Map winAdnLose = commonService.getInfoByOne("point_control_record.getWinAdnLose", map);
		return json(winAdnLose);
	}
	
    @SuppressWarnings({"unchecked", "rawtypes"})
    @Json
    @RequestMapping("/getAllWinAdnLose")
    public AjaxResult getAllWinAdnLose() {
        String parameter = request.getParameter("where");
        Map parseObject2 = null;
        if (StrKit.isBlank(parameter)) {
            return json(null);
        } else {
            if (parameter.contains("%")) {
                parameter = URLKit.decode(parameter, CharsetKit.UTF_8);
            }
            parseObject2 = JSON.parseObject(parameter, Map.class);
        }

        //赢
        Object win = commonService.getInfoByOne("point_control_record.win_point", parseObject2);
        //输
        Object lose = commonService.getInfoByOne("point_control_record.lose_point", parseObject2);
        Map m = new HashMap();
        m.put("win", win);//
        m.put("lose", lose);//
        return json(m);
    }
}
