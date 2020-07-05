package com.techupstudio.school_management_system.base.sqlite_database;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SQLDatabase {

    private Connection connection;
    private Logger logger;

    public SQLDatabase(String db_path){

        connection = null;
        logger = Logger.getLogger("SQLDatabase: ");

        try {
            //Class.forName("org.sqlite.JDBC");
            String connectURI = "jdbc:sqlite:"+db_path;
            connection = DriverManager.getConnection(connectURI);
            logger.log(Level.INFO, "HospitalDatabase connection successful");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    public Connection getConnection() {
        return connection;
    }

    public boolean hasConnection() {
        return connection != null;
    }

    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean isClosed() {
        try {
            return connection.isClosed();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public Statement getStatement(){
        if (hasConnection()){
            try {
                return getConnection().createStatement();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public SQLCommandBuilder execSQL() {
        return new SQLCommandBuilder(getConnection());
    }

//    public void createSQLProcedure(Args args, String sql){}


}
