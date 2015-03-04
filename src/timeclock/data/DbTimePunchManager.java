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
import java.sql.Timestamp;
import java.util.ArrayList;
import timeclock.objects.Employee;
import timeclock.objects.TimePunch;
import timeclock.utilities.DateUtils;
import timeclock.utilities.DbUtils;
import timeclock.utilities.Debugger;

/**
 * Contains the database functions for time punch information.
 * @author dannyjdelanojr
 */
public class DbTimePunchManager {
    
    /**
     * Default Constructor
     */
    public DbTimePunchManager()
    {
        
    }
    
    public boolean punchIn(TimePunch empTp){
        // Get the database connection
        Connection conn = null;
        PreparedStatement clockIn = null;
        PreparedStatement update = null;
        boolean result = false;
        try{
                // get connection and add employee              
               conn = DbConnection.establishConnection();
               clockIn = conn.prepareStatement(DbStatements.CLOCK_IN);
               update = conn.prepareStatement(DbStatements.UPDATE_STATUS);
               
               // Insert new record with empID and current time stamp
               clockIn.setInt(1,empTp.getEmployeeId());
               clockIn.setTimestamp(2, DateUtils.getNewTimestamp());
               
               // Set status to IN where empID = ID
               update.setInt(1, 1);
               update.setInt(2, empTp.getEmployeeId());
               if(clockIn.executeUpdate()>0)
                   if(update.executeUpdate()>0)
                       result = true;
               
            }
        catch (SQLException ex)
        {             
            Debugger.log("Unable to clock in Employee: "+ex);
        }finally
        {
            DbUtils.closeQuietly(clockIn);
            DbUtils.closeQuietly(update);
            DbUtils.closeQuietly(conn);
            return result;
        }
    }
    
    public boolean punchOut(TimePunch empTp)
    {
        // Get the database connection
        Connection conn = null;
        PreparedStatement clockOut = null;
        PreparedStatement update = null;
        boolean result = false;
        try{
                // get connection and add employee              
               conn = DbConnection.establishConnection();
               clockOut = conn.prepareStatement(DbStatements.CLOCK_OUT);
               update = conn.prepareStatement(DbStatements.UPDATE_STATUS);
               
               // Update record with empID and current time stamp               
               clockOut.setTimestamp(1, DateUtils.getNewTimestamp());
               clockOut.setInt(2,empTp.getEmployeeId());
               
               // Set status to OUT where empID = ID
               update.setInt(1, 0);
               update.setInt(2, empTp.getEmployeeId());
               if(clockOut.executeUpdate()>0)
                   if(update.executeUpdate()>0)
                       result = true;
            }
        catch (SQLException ex)
        {             
            Debugger.log("Unable to clock out Employee: "+ex);
        }finally
        {
            DbUtils.closeQuietly(clockOut);
            DbUtils.closeQuietly(update);
            DbUtils.closeQuietly(conn);
            return result;
        }
    }
    
    /**
     * Print all time punches to Debugger.log()
     */
    public void printTimePunches()
    {
        // Get the database connection
        Connection conn = null;
        PreparedStatement getTimes = null;
        ResultSet rs = null;
        try{
                // get connection and add employee              
               conn = DbConnection.establishConnection();
               getTimes = conn.prepareStatement(DbStatements.GET_ALL_TIME_PUNCHES);                             
               rs = getTimes.executeQuery();
               Debugger.log("printing time punches: ");
               while(rs.next())
               {
                   int id = rs.getInt(DbStatements.PUNCH_COLUMNS[0]);               
                   int empID = rs.getInt(DbStatements.PUNCH_COLUMNS[1]);
                   Timestamp in = rs.getTimestamp(DbStatements.PUNCH_COLUMNS[2]);
                   Timestamp out = rs.getTimestamp(DbStatements.PUNCH_COLUMNS[3]);                   
                   Debugger.log("Time clock: "+id +" employeeid: "+empID+" in: "+in+" out: "+out);
               }            
            }
        catch (SQLException ex)
        { 
            Debugger.log("Unable to List Employees: ");
        }finally
        {
            DbUtils.closeQuietly(rs);
            DbUtils.closeQuietly(getTimes);
            DbUtils.closeQuietly(conn);
        }
    }
    
    public ArrayList<TimePunch> getTimePunches(Employee emp,Timestamp start,Timestamp end)
    {
        ArrayList<TimePunch> punches = new ArrayList<>();
        // Get the database connection
        Connection conn = null;
        PreparedStatement getTimes = null;
        ResultSet rs = null;
        try{
                // get connection and add employee              
               conn = DbConnection.establishConnection();
               getTimes = conn.prepareStatement(DbStatements.GET_TIME_BY_ID);
               // Equals employee id
               getTimes.setInt(1, emp.getEmployeeID());
               // Punch in times between dates
               getTimes.setTimestamp(2, start);
               getTimes.setTimestamp(3, end);
               // Punch out times between dates
               getTimes.setTimestamp(4, start);
               getTimes.setTimestamp(5, end);
               rs = getTimes.executeQuery();               
               while(rs.next())
               {
                   int id = rs.getInt(DbStatements.PUNCH_COLUMNS[0]);               
                   int empID = rs.getInt(DbStatements.PUNCH_COLUMNS[1]);
                   Timestamp in = rs.getTimestamp(DbStatements.PUNCH_COLUMNS[2]);
                   Timestamp out = rs.getTimestamp(DbStatements.PUNCH_COLUMNS[3]);
                   // Add to array list
                   punches.add(new TimePunch(id,empID,in,out));                   
               }            
            }
        catch (SQLException ex)
        { 
            Debugger.log("Unable to List Employees: ");
        }finally
        {
            DbUtils.closeQuietly(rs);
            DbUtils.closeQuietly(getTimes);
            DbUtils.closeQuietly(conn);
            return punches;
        }        
    }
}
