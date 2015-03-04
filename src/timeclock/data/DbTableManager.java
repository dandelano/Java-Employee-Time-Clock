/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package timeclock.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import timeclock.utilities.DateUtils;
import timeclock.utilities.DbUtils;
import timeclock.utilities.Debugger;

/**
 * Contains the database functions for checking existence of required tables.
 * @author demont-imac
 */
public final class DbTableManager {
    
    /**
     * Checks that all of the necessary tables are in the database,
     * if not they get created.
     */
    public static boolean checkTables()
    {
        // Get the database connection
        Connection conn = DbConnection.establishConnection();
        if(conn == null)
            return false;
        
        // Loop through and check for each table
        for(int i = 0;i<DbStatements.TABLE_NAMES.length;++i)
        {
            if(!DbTableManager.isTableFound(conn,DbStatements.TABLE_NAMES[i]))
            {
                if(DbTableManager.createTables(conn,DbStatements.CREATE_TABLE_SQL[i]))
                {
                    Debugger.log(DbStatements.TABLE_NAMES[i] 
                            + " Table created " + DateUtils.getDate());
                }else
                {
                    Debugger.log(DbStatements.TABLE_NAMES[i] 
                            + " Table could not be created");
                }
            }
        }
        DbUtils.closeQuietly(conn);
        return true;
    }
    
    /**
     * Trys to query the table to see if it is in the database.
     * @param conn the database connection
     * @param tableName
     * @return boolean true if no exception is thrown.
     */
    private static boolean isTableFound(Connection conn,String tableName)
    {
        PreparedStatement statement = null;
        ResultSet results = null;
        int count = 0;
        try
        {
            statement = conn.prepareStatement("SELECT count(*) FROM APP."+tableName);            
            results = statement.executeQuery();
            while(results.next())
                count = results.getInt(1);
            
        }catch(SQLException ex)
        {
            //ex.printStackTrace();
            Debugger.log(tableName+": Table not found!");
            return false;
        }finally
        {
                 DbUtils.closeQuietly(results);
                 DbUtils.closeQuietly(statement);
        }
        return true;
    }
    
    /**
     * Executes the create table statement passed to it.
     * @param conn the database connection
     * @param createStatement
     * @return boolean true if no exception is thrown.
     */
    private static boolean createTables(Connection conn,String createStatement) 
    {
        boolean bCreatedTables = false;
        Statement statement = null;
        try {
            //System.out.println(createStatement);
            statement = conn.createStatement();
            statement.execute(createStatement);
            bCreatedTables = true;
        } catch (SQLException ex) {
            Debugger.log("Error creating table: "+ex.toString());
        }finally
        {
            DbUtils.closeQuietly(statement);
        }
        return bCreatedTables;
    }
}
