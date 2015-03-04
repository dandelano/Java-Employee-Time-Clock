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
import java.util.ArrayList;
import timeclock.objects.Employee;
import timeclock.utilities.DbUtils;
import timeclock.utilities.Debugger;

/**
 * Contains the database functions for employee information.
 * @author demont-imac
 */
public class DbEmployeeManager {
    
    /**
     * Default Constructor
     */
    public DbEmployeeManager()
    {
        
    }
    
    public Employee addEmployee(Employee emp)
    {
        // Get the database connection
        Connection conn = null;
        PreparedStatement add = null;
        ResultSet key = null;
        Employee resultEmp = null;
        try{
                // get connection and add employee              
               conn = DbConnection.establishConnection();
               add = conn.prepareStatement(DbStatements.ADD_EMPLOYEE,new String[] {"EMPLOYEE_ID"});
               
               // Set the prepared values
               add.setString(1,emp.getFirstName());
               add.setString(2,emp.getLastName());
               add.setInt(3,emp.getIsSalary_int());
               add.setInt(4,emp.getIsAdmin_int());
               add.setString(5,emp.getPassword());
               add.executeUpdate();
               // If return count is more than zero emmployee added
               //if(add.executeUpdate() > 0)
               //{
                   // Get the employee id, create new employee to return
                   key = add.getGeneratedKeys();
               if(key != null && key.next()){
                   resultEmp = new Employee(key.getInt(1),
                           emp.getFirstName(),emp.getLastName(),
                           emp.getIsSalary_int(),emp.getIsAdmin_int(),
                           emp.getPassword());
                   
                   Debugger.log("Added Employee: "+resultEmp.toString());                   
               }
            }
        catch (SQLException ex)
        {             
            Debugger.log("Unable to add Employee: " + ex);
        }finally
        {
            DbUtils.closeQuietly(key);
            DbUtils.closeQuietly(add);
            DbUtils.closeQuietly(conn);
            return resultEmp;
        }
    }
    
    public Employee editEmployee(Employee emp)
    {
        // Get the database connection
        Connection conn = null;
        PreparedStatement add = null;        
        Employee resultEmp = null;
        try{
                // get connection and add employee              
               conn = DbConnection.establishConnection();
               add = conn.prepareStatement(DbStatements.EDIT_EMPLOYEE);
               
               // Set the prepared values
               add.setString(1,emp.getFirstName());
               add.setString(2,emp.getLastName());
               add.setInt(3,emp.getIsSalary_int());
               add.setInt(4,emp.getIsAdmin_int());
               add.setString(5,emp.getPassword());
               add.setInt(6, emp.getEmployeeStatus_int());
               add.setInt(7, emp.getEmployeeID());
               
               // If return count is more than zero emmployee added
               if(add.executeUpdate() > 0)
               {                  
                   resultEmp = new Employee(emp.getEmployeeID(),
                           emp.getFirstName(),emp.getLastName(),
                           emp.getIsSalary_int(),emp.getIsAdmin_int(),
                           emp.getPassword(),emp.getEmployeeStatus_int());
                   
                   Debugger.log("Updated Employee: "+resultEmp.toString());                   
               }
            }
        catch (SQLException ex)
        {             
            Debugger.log("Unable to add Employee: " + ex);
        }finally
        {
            DbUtils.closeQuietly(add);
            DbUtils.closeQuietly(conn);
            return resultEmp;
        }
    }
     
    public Employee deleteEmployee(Employee emp)
    {
        // Get the database connection
        Connection conn = null;
        PreparedStatement deleteEmp = null;
        PreparedStatement deleteTimes = null;
        Employee resultEmp = null;
        try{
                // get connection and add employee              
               conn = DbConnection.establishConnection();
               // Delete timepunches by employee id
               deleteTimes = conn.prepareStatement(DbStatements.DELETE_TIME_PUNCHES);
               
               // Delete employee by employee id and first name
               deleteEmp = conn.prepareStatement(DbStatements.DELETE_EMPLOYEE);
               
               // Set the prepared values for timepunches table
               deleteTimes.setInt(1, emp.getEmployeeID());               
               // Set the prepared values for employee tables
               deleteEmp.setInt(1, emp.getEmployeeID());
               deleteEmp.setString(2, emp.getFirstName());
               
               // If return count is more than zero emmployee added
               if(deleteTimes.executeUpdate() > 0)
               {
                   if(deleteEmp.executeUpdate() > 0)
                    {                  
                        resultEmp = emp;
                        Debugger.log("Deleted Employee: "+resultEmp.toString());                   
                    }
               }
            }
        catch (SQLException ex)
        {             
            Debugger.log("Unable to delete Employee: " + ex);
        }finally
        {
            DbUtils.closeQuietly(deleteTimes);
            DbUtils.closeQuietly(deleteEmp);
            DbUtils.closeQuietly(conn);
            return resultEmp;
        }       
    }
    
    public Employee getEmployee(int empID)
    {
        // Get the database connection
        Connection conn = null;
        PreparedStatement getEmp = null;
        ResultSet rs = null;
        Employee resultEmp = null;
        
        try{       
               conn = DbConnection.establishConnection();
               getEmp = conn.prepareStatement(DbStatements.GET_EMPLOYEE_BY_ID);
               getEmp.setInt(1, empID);
               rs = getEmp.executeQuery();
               
               while(rs.next())
               {
                   int id = rs.getInt(DbStatements.EMP_COLUMNS[0]);
                   String first = rs.getString(DbStatements.EMP_COLUMNS[1]);
                   String last = rs.getString(DbStatements.EMP_COLUMNS[2]);
                   int salary = rs.getInt(DbStatements.EMP_COLUMNS[3]);
                   int admin = rs.getInt(DbStatements.EMP_COLUMNS[4]);
                   String pass = rs.getString(DbStatements.EMP_COLUMNS[5]);
                   int status = rs.getInt(DbStatements.EMP_COLUMNS[6]);
                   resultEmp = new Employee(id,first,last,salary,admin,pass,status);
               }
        }
        catch (SQLException ex)
        { 
            Debugger.log("Unable to add Employee: ");
        }finally
        {
            DbUtils.closeQuietly(rs);
            DbUtils.closeQuietly(getEmp);
            DbUtils.closeQuietly(conn);
            return resultEmp;
        }
    }
    
    public ArrayList<Employee> getEmployeeList()
    {
        ArrayList<Employee> empList = new ArrayList<>();
        // Get the database connection
        Connection conn = null;
        PreparedStatement getEmps = null;
        ResultSet rs = null;
        try{
                // get connection and add employee              
               conn = DbConnection.establishConnection();
               getEmps = conn.prepareStatement(DbStatements.GET_ALL_EMPLOYEES);                             
               rs = getEmps.executeQuery();
               
               while(rs.next())
               {
                   int id = rs.getInt(DbStatements.EMP_COLUMNS[0]);
                   String first = rs.getString(DbStatements.EMP_COLUMNS[1]);
                   String last = rs.getString(DbStatements.EMP_COLUMNS[2]);
                   int salary = rs.getInt(DbStatements.EMP_COLUMNS[3]);
                   int admin = rs.getInt(DbStatements.EMP_COLUMNS[4]);
                   String pass = rs.getString(DbStatements.EMP_COLUMNS[5]);
                   int status = rs.getInt(DbStatements.EMP_COLUMNS[6]);
                   // Add the employee to the list of employees
                   empList.add(new Employee(id,first,last,salary,admin,pass,status));                   
               }            
            }
        catch (SQLException ex)
        { 
            Debugger.log("Unable to List Employees: ");
        }finally
        {
            DbUtils.closeQuietly(rs);
            DbUtils.closeQuietly(getEmps);
            DbUtils.closeQuietly(conn);
        }
        return empList;
    }
    
    public ArrayList<Employee> getClockedInEmployees()
    {
        ArrayList<Employee> empList = new ArrayList<>();
        // Get the database connection
        Connection conn = null;
        PreparedStatement getEmps = null;
        ResultSet rs = null;
        try{
                // get connection and add employee              
               conn = DbConnection.establishConnection();
               getEmps = conn.prepareStatement(DbStatements.GET_ALL_CLOCKED_IN);                             
               rs = getEmps.executeQuery();
               
               while(rs.next())
               {
                   int id = rs.getInt(DbStatements.EMP_COLUMNS[0]);
                   String first = rs.getString(DbStatements.EMP_COLUMNS[1]);
                   String last = rs.getString(DbStatements.EMP_COLUMNS[2]);
                   int salary = rs.getInt(DbStatements.EMP_COLUMNS[3]);
                   int admin = rs.getInt(DbStatements.EMP_COLUMNS[4]);
                   String pass = rs.getString(DbStatements.EMP_COLUMNS[5]);
                   int status = rs.getInt(DbStatements.EMP_COLUMNS[6]);
                   // Add the employee to the list of employees
                   empList.add(new Employee(id,first,last,salary,admin,pass,status));                   
               }            
            }
        catch (SQLException ex)
        { 
            Debugger.log("Unable to List Employees: ");
        }finally
        {
            DbUtils.closeQuietly(rs);
            DbUtils.closeQuietly(getEmps);
            DbUtils.closeQuietly(conn);
        }
        return empList;
    }
    
    /**
     * Used for debugging purposes
     */
    public void PrintEmployees()
    {
        // Get the database connection
        Connection conn = null;
        PreparedStatement getEmps = null;
        ResultSet rs = null;
        try{
                // get connection and add employee              
               conn = DbConnection.establishConnection();
               getEmps = conn.prepareStatement(DbStatements.GET_ALL_EMPLOYEES);                             
               rs = getEmps.executeQuery();
               Debugger.log("printing employees: ");
               while(rs.next())
               {
                   int id = rs.getInt(DbStatements.EMP_COLUMNS[0]);
                   String first = rs.getString(DbStatements.EMP_COLUMNS[1]);
                   String last = rs.getString(DbStatements.EMP_COLUMNS[2]);
                   int salary = rs.getInt(DbStatements.EMP_COLUMNS[3]);
                   int admin = rs.getInt(DbStatements.EMP_COLUMNS[4]);
                   String pass = rs.getString(DbStatements.EMP_COLUMNS[5]);
                   int status = rs.getInt(DbStatements.EMP_COLUMNS[6]);
                   Debugger.log("Employee: "+id
                           +" "+first+" "+last+" "+salary+" "+admin+" "+pass+" "+status);
               }            
            }
        catch (SQLException ex)
        { 
            Debugger.log("Unable to List Employees: ");
        }finally
        {
            DbUtils.closeQuietly(rs);
            DbUtils.closeQuietly(getEmps);
            DbUtils.closeQuietly(conn);
        }
    }
     
}
