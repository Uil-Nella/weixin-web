package org.cs.demo.manager.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import me.inshion.inspress.ModelParser;
                                                                                     
                                                                                     
public class TUserDAOJDBC {
                                                                                     
    public List<BUserModel> testSelect() {
        String host = BAEHelper.getBaeEnv("BAE_ENV_ADDR_SQL_IP");
        String port = BAEHelper.getBaeEnv("BAE_ENV_ADDR_SQL_PORT");
        String username = BAEHelper.getBaeEnv("BAE_ENV_AK");
        String password = BAEHelper.getBaeEnv("BAE_ENV_SK");
                                                                                     
        String driverName = "com.mysql.jdbc.Driver";
        String dbUrl = "jdbc:mysql://";
        String serverName = host + ":" + port + "/";
                                                                                     
        // 从平台查询应用要使用的数据库名
        String databaseName = "ixhxRCxdxwLQLZEsZGyV";
        String connName = dbUrl + serverName + databaseName;
        String sql = "select * from user";
                                                                                     
        Connection connection = null;
        Statement stmt = null;
        ResultSet rs = null;
        List<BUserModel> ret = new ArrayList<BUserModel>();
        try {
            Class.forName(driverName);
            connection = DriverManager.getConnection(connName, username, password);
            stmt = connection.createStatement();
            rs = stmt.executeQuery(sql);
                                                                                     
            while (rs.next()) {
                BUserModel m = modelParser.parse(rs);
                ret.add(m);
                                                                                     
            }
        } catch (ClassNotFoundException ex) {} catch (SQLException e) {
                                                                                     
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                                                                                     
            }
        }
        return ret;
    }
                                                                                     
    ModelParser<BUserModel> modelParser = new ModelParser<BUserModel>() {
                                                                                     
        @Override
        public BUserModel parse(ResultSet rs) throws SQLException {
            return parseToModel(rs);
        }
    };
                                                                                     
    protected BUserModel parseToModel(ResultSet rs) throws SQLException {
        BUserModel model = new BUserModel();
        model.setId(rs.getInt("id"));
        model.setUsername(rs.getString("username"));
        model.setPassword(rs.getString("password"));
        return model;
    }
                                                                                     
}
