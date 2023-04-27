package com.smallchill.db.config.meta.intercept;

import com.smallchill.core.aop.Invocation;
import com.smallchill.core.intercept.BladeValidator;
import com.smallchill.core.plugins.dao.Db;
import com.smallchill.core.toolbox.CMap;

public class GameConfValidator extends BladeValidator {
    @Override
    protected void doValidate(Invocation inv) {
        validateRequired("gameconf.gameId","请输入游戏名字");
        validateRequired("gameconf.clientType","请选择游戏类型");
        validateRequired("gameconf.sort","请输入游戏排序");
        validateInteger("gameconf.sort",0,999,"请输入正确的数字格式");
        validateRequired("gameconf.state","请输入界面状态");
        validateRequired("gameconf.type","请选择游戏类型");
        validateRequired("gameconf.typeSort","请输入游戏类型中的排序");
        validateInteger("gameconf.typeSort",0,999,"请输入正确的数字格式");
        validateRequired("gameconf.isOpen","请设置开启状态");
        int clientType = Integer.parseInt(request.getParameter("gameconf.clientType"));
        int gameId = Integer.parseInt(request.getParameter("gameconf.gameId"));
        // 判断游戏是否已经存在包中
        String parameter = request.getParameter("gameconf.id");
        if (parameter==null){
            validateExist(clientType,gameId);
        }
        // 判断排序是否重复
        int type = Integer.parseInt(request.getParameter("gameconf.type"));
        int typeSort = Integer.parseInt(request.getParameter("gameconf.typeSort"));
        validateSortIsRepeat(clientType,type,typeSort);

    }

    private void validateExist(int clientType,Integer id) {
        Integer temp = Db.queryInt("select count(1) from game_conf where clientType=#{clientType} and gameId=#{gameId}",
                CMap.init().set("clientType", clientType).set("gameId",id));
        if (temp>0){
            addError("包"+clientType+"已经存在该游戏");
        }
    }

    /**
     *
     * @param Type 游戏类型
     * @param sort 游戏类型中的排序
     */
    private void validateSortIsRepeat(int clientType,int Type,int sort){
        // 判断类型中的排序是否重复
        Integer temp = Db.queryInt("select count(1) from game_conf where clientType=#{clientType} and type=#{type} and typeSort=#{typeSort}",
                CMap.init().set("type", Type).set("typeSort",sort).set("clientType",clientType));
        if (temp>0){
            addError("游戏类型中的排序重复");
        }
    }
}
