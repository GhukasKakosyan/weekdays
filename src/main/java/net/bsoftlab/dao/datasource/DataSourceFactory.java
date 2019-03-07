package net.bsoftlab.dao.datasource;

import com.mysql.cj.jdbc.MysqlDataSource;

import javax.sql.DataSource;

public class DataSourceFactory {
    private static DataSource dataSource = null;
    public static DataSource getDataSource () {
        if (dataSource == null) {
            String urlDatabaseMysql = "jdbc:mysql://localhost:3306/daysholder";
            String userNameDatabaseMysql = "root";
            String passwordDatabaseMysql = "gktj19760207";
            MysqlDataSource mysqlDataSource = new MysqlDataSource();
            mysqlDataSource.setURL(urlDatabaseMysql);
            mysqlDataSource.setUser(userNameDatabaseMysql);
            mysqlDataSource.setPassword(passwordDatabaseMysql);
            dataSource = mysqlDataSource;
        }
        return dataSource;
    }
}
