/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package timeclock.data;

import java.sql.PreparedStatement;
import java.util.HashMap;

/**
 * Wrapper class that contains all SQL statements being used in this application.
 * @author demont-imac
 */
public class DbStatements {
    private static HashMap<String,PreparedStatement> statements;
    
    /* Table names */
    public static final String[] TABLE_NAMES = {"EMPLOYEES","TIME_PUNCH"};
    /* Tables */
    public static final String CREATE_EMPLOYEE = "CREATE table APP.EMPLOYEES ("
            + "EMPLOYEE_ID INT NOT NULL "
            + "PRIMARY KEY GENERATED ALWAYS AS IDENTITY "
            + "(START WITH 1001, INCREMENT BY 1), "
            + "FIRST_NAME VARCHAR(30), "
            + "LAST_NAME VARCHAR(30), "
            + "IS_SALARY SMALLINT DEFAULT 0, "
            + "IS_ADMIN SMALLINT DEFAULT 0, "
            + "PASSWORD VARCHAR(64) NOT NULL,"
            + "PUNCH_STAT SMALLINT DEFAULT 0) ";
    
    /* OLD CODE - table not needed because we can tell status from null punch_out_time
    public static final String CREATE_EMP_STATUS = "CREATE table APP.EMP_STATUS ("
            + "EMP_STAT_ID INT NOT NULL "
            + "PRIMARY KEY GENERATED ALWAYS AS IDENTITY "
            + "(START WITH 1, INCREMENT BY 1), "
            + "EMPLOYEE_ID INT NOT NULL, "
            + "CLOCKED_IN SMALLINT)";
    
    public static final String[] CREATE_TABLE_SQL = {
        DbStatements.CREATE_EMPLOYEE,
        DbStatements.CREATE_EMP_STATUS,
        DbStatements.CREATE_TIME_PUNCH};*/
    
    public static final String CREATE_TIME_PUNCH = "CREATE table APP.TIME_PUNCH ("
            + "PUNCH_ID INT NOT NULL "
            + "PRIMARY KEY GENERATED ALWAYS AS IDENTITY "
            + "(START WITH 1, INCREMENT BY 1), "
            + "EMPLOYEE_ID INT NOT NULL, "
            + "PUNCH_IN_TIME TIMESTAMP NOT NULL, "
            + "PUNCH_OUT_TIME TIMESTAMP)";
    
    public static final String[] CREATE_TABLE_SQL = {
        DbStatements.CREATE_EMPLOYEE,
        DbStatements.CREATE_TIME_PUNCH};
    
    public static final String[] EMP_COLUMNS = {"EMPLOYEE_ID","FIRST_NAME",
        "LAST_NAME","IS_SALARY","IS_ADMIN","PASSWORD","PUNCH_STAT"};
    
    public static final String ADD_EMPLOYEE = "INSERT INTO APP.EMPLOYEES "
            + "(FIRST_NAME, LAST_NAME,IS_SALARY,IS_ADMIN,PASSWORD) "
            + "VALUES(?,?,?,?,?)";
    
    public static final String EDIT_EMPLOYEE = "UPDATE APP.EMPLOYEES "
            + "SET FIRST_NAME = ?, LAST_NAME = ?, "
            + "IS_SALARY = ?,IS_ADMIN = ?, "
            + "PASSWORD = ?, PUNCH_STAT = ? "
            + "WHERE EMPLOYEE_ID = ?";
    
    public static final String DELETE_EMPLOYEE = "DELETE FROM APP.EMPLOYEES "
            + "WHERE EMPLOYEE_ID = ? AND FIRST_NAME = ?";
    
    public static final String UPDATE_STATUS = "UPDATE APP.EMPLOYEES "
            + "SET PUNCH_STAT = ? WHERE EMPLOYEE_ID = ?";
    
    public static final String GET_EMPLOYEE_BY_ID = "SELECT EMPLOYEE_ID, FIRST_NAME, "
            + "LAST_NAME, IS_SALARY, IS_ADMIN, PASSWORD "
            + "FROM APP.EMPLOYEES WHERE EMPLOYEE_ID=?";
    
    public static final String GET_ALL_EMPLOYEES = "SELECT * FROM APP.EMPLOYEES";
    
    public static final String GET_ALL_CLOCKED_IN = "SELECT * FROM APP.EMPLOYEES "
            + "WHERE PUNCH_STAT = 1";
    
    /* Time punch table */
    public static final String[] PUNCH_COLUMNS = {"PUNCH_ID","EMPLOYEE_ID",
        "PUNCH_IN_TIME","PUNCH_OUT_TIME"};
    
    public static final String CLOCK_IN = "INSERT INTO APP.TIME_PUNCH "
            + "(EMPLOYEE_ID,PUNCH_IN_TIME) VALUES(?,?)";
    
    public static final String DELETE_TIME_PUNCHES = "DELETE FROM APP.TIME_PUNCH "
            + "WHERE EMPLOYEE_ID = ?";
    
    public static final String GET_CLOCKED_IN = "SELECT * FROM APP.TIME_PUNCH "
            + "WHERE EMPLOYEE_ID = ? "
            + "AND PUNCH_OUT_TIME = null";
            
    public static final String CLOCK_OUT = "UPDATE APP.TIME_PUNCH "
            + "SET PUNCH_OUT_TIME = ? "
            + "WHERE EMPLOYEE_ID = ? "
            + "AND PUNCH_OUT_TIME IS NULL";
    
    public static final String GET_TIME_BY_ID = "SELECT * FROM APP.TIME_PUNCH "
            + "WHERE EMPLOYEE_ID = ? "
            + "AND PUNCH_IN_TIME BETWEEN ? AND ? "
            + "AND PUNCH_OUT_TIME BETWEEN ? AND ?";
    
    public static final String GET_ALL_TIME_PUNCHES = "SELECT * FROM APP.TIME_PUNCH";
    
}
