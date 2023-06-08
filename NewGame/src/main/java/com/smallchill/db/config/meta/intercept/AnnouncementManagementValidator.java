package com.smallchill.db.config.meta.intercept;

import com.smallchill.core.aop.Invocation;
import com.smallchill.core.intercept.BladeValidator;
import com.smallchill.core.plugins.dao.Db;
import com.smallchill.core.toolbox.CMap;

public class AnnouncementManagementValidator extends BladeValidator {
    @Override
    protected void doValidate(Invocation inv) {
        validateRequired("announcement.title","公告侧边栏显示");
        validateRequired("announcement.clientType","请输入包来源");
        validateRequired("announcement.code","请输入包来源");
        validateRequired("announcement.content","请输入公告内容");
        validateExist("announcement.id","announcement.clientType","announcement.code");
    }

    protected void validateExist(String fieldId,String fieldCli,String fieldCode) {
        // 判断是否
        String id = request.getParameter(fieldId);
        String clientType = request.getParameter(fieldCli);
        validateRequired(fieldCode,"请填写编码");
        String code = request.getParameter(fieldCode);
        // 获取大渠道id
        if (id==null){
            // 添加
            String sql = "select * from announcement_management where code=#{code} and clientType=#{clientType}";
            boolean temp = Db.isExist(sql,
                    CMap.init().set("code", code).set("clientType", clientType));
            if (temp){
                addError("包"+clientType+"中已经存在该编码");
            }
        }else {
            // 修改
            String sql = "select * from announcement_management where code=#{code} and clientType=#{clientType} and id!=#{id}";
            boolean temp = Db.isExist(sql,
                    CMap.init().set("code", code).set("clientType", clientType).set("id",id));
            if (temp){
                addError("包"+clientType+"中已经存在该编码");
            }
        }

    }

}
