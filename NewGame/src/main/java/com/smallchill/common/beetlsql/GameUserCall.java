package com.smallchill.common.beetlsql;


import com.smallchill.core.annotation.DbName;
import com.smallchill.core.plugins.dao.Db;
import org.apache.poi.ss.formula.functions.T;
import org.beetl.sql.core.OnConnection;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * @Description TODO
 * @classNameGameUser
 * @Date 2023/2/24 15:32@UserID INT,
 *        @Gold INT,
 *    @GameCoin INT,
 *    @Type INT--0:充值1：提现
 * @Version 1.0
 **/
@DbName(name = "gameroomitemdb")
public class GameUserCall extends OnConnection {
    @Override
    public Boolean call(Connection connection) throws SQLException {
        CallableStatement callableStatement = connection.prepareCall("{call RechargeRecord(?,?,?,?)}");
        /**
         * @UserID INT,31232293
         * @Gold INT,
         * @GameCoin INT,
         * @Type INT--0:充值1：提现31232293
         */
        callableStatement.setInt(1,31232293);
        callableStatement.setInt(2,50000);
        callableStatement.setLong(3,100);
        callableStatement.setInt(4,0);
        boolean execute = callableStatement.execute();
        return execute;
    }
}
