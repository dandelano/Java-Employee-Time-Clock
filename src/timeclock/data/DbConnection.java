/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package timeclock.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import timeclock.utilities.AppSettings;
import timeclock.utilities.DateUtils;
import timeclock.utilities.DbUtils;
import timeclock.utilities.Debugger;

/**
 * Provides database connection and shutdown method for JavaDB.
 * @author dannyjdelanojr
 */
public final class DbConnection {

    private static String driver;    
    private static String db_url;
    private static String db_shtdwn_url;    
    private static String db_user;
    private static String db_pass;
    private static Connection conn;
    
    public static void configureDBSettings()
    {
        AppSettings appSet = new AppSettings();
        appSet.loadSettings();
        DbConnection.driver = appSet.getDBDriver();        
        DbConnection.db_url = appSet.getDBUrl();
        DbConnection.db_user = appSet.getDBUser();
        DbConnection.db_pass = appSet.getDBPass();
        DbConnection.db_shtdwn_url = appSet.getDBShtDwnUrl();
    }
    
    /**
     * Creates the connection to the database for the class.
     *
     * @return Connection or null if not created
     */
    public static Connection establishConnection() {        

        try {
            if (DbUtils.loadDriver(DbConnection.driver)) {
                DbConnection.conn = DriverManager.getConnection(DbConnection.db_url, DbConnection.db_user, DbConnection.db_pass);
            } else {
                Debugger.log("Could not load JavaDB Driver: " + DateUtils.getDateTime());
            }
        } catch (SQLException e) {
            Debugger.log(e.getMessage());
            return null;
        }
        return DbConnection.conn;
    }
    
    /**
     * Disconnects the Derby database and shuts down the service.
     *
     * @return true if shutdown occurred, false if not.
     */
    public static boolean shutdownDerby() {
        try {
            if (DbConnection.conn != null) {
                DbUtils.closeQuietly(DbConnection.conn);
            }

            DriverManager.getConnection(DbConnection.db_shtdwn_url);
        } catch (SQLException e) {
            Debugger.log(e.getMessage());
            return true;
        }
        return false;
    }
}
