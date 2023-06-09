package com.smallchill.game.player.util;

import com.smallchill.core.plugins.dao.Blade;
import org.beetl.sql.core.OnConnection;
import org.beetl.sql.core.SQLManager;

import java.lang.reflect.MalformedParameterizedTypeException;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class utils {

    /**发送邮件绑定手机
     * @User_Id INT,--用户ID
     * @szPhoneNum varchar(33), -- 手机号码
     * @szPassword varchar(33), -- 新密码
     * @szCode varchar(10) -- 邀请码
     *
     * @szEmail varchar(32),--邮件
     * @szRealName varchar(100)
     * @return
     */

    public static Integer SendBindPhoneEmail(Integer userId,String phone,String email){
        SQLManager dao = Blade.dao("gameuserdb");
        Integer re = dao.executeOnConnection(new OnConnection<Integer>() {
            @Override
            public Integer call(Connection connection) throws SQLException {
                CallableStatement statement = connection.prepareCall("{?=call [QPGameUserDB].[dbo].[AA_Pro_UserInfo_BindPhone](?,?,?,?,?,?)}");
                // 注册输出参数
                statement.registerOutParameter(1, Types.INTEGER);
                // 设置参数
                statement.setInt("User_Id", userId);
                statement.setString("szPhoneNum", phone);
                statement.setString("szPassword", "e10adc3949ba59abbe56e057f20f883e");
                statement.setString("szCode", "");
                statement.setString("szEmail", email);
                statement.setString("szRealName", "");
                statement.execute();
                return statement.getInt(1);
            }
        });
        return re;
    }

}
