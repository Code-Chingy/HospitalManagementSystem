package com.techupstudio.Base.Utils.DatabaseManager;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SQLDatabase {

    private Connection CONNECTION;
    private Logger LOG;

    public SQLDatabase(String db_path){

        CONNECTION = null;
        LOG = Logger.getLogger("SQLDatabase: ");

        try {
            String connectURI = "jdbc:sqlite:"+db_path;
            CONNECTION = DriverManager.getConnection(connectURI);
            LOG.log(Level.INFO, "HospitalDatabase connection successful");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    public Connection getConnection() {
        return CONNECTION;
    }

    public boolean hasConnection() {
        return CONNECTION != null;
    }

    public void close() {
        try {
            CONNECTION.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean isClosed() {
        try {
            return CONNECTION.isClosed();
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
