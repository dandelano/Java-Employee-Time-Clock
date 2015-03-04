/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package timeclock.objects;

import java.sql.Timestamp;
import timeclock.utilities.DateUtils;

/**
 * Object to contain all of the information of a clock in/clock out
 * record for an Employee.
 * @author dannyjdelanojr
 */
public class TimePunch {
    private int punchId;
    private int employeeId;
    private Timestamp punchIn;
    private Timestamp punchOut;
    
    
    public TimePunch()
    {
        this.punchId = 0;
        this.employeeId = 0;
        this.punchIn = null;
        this.punchOut = null;
    }
    
    public TimePunch(int empId)
    {
        this.punchId = 0;
        this.employeeId = empId;
        this.punchIn = DateUtils.getNewTimestamp();
        this.punchOut = null;
    }
    
    public TimePunch(int empId,Timestamp in)
    {
        this.punchId = 0;
        this.employeeId = empId;
        this.punchIn = in;
        this.punchOut = null;
    }
    
    public TimePunch(int empId,Timestamp in,Timestamp out)
    {
        this.punchId = 0;
        this.employeeId = empId;
        this.punchIn = in;
        this.punchOut = out;
    }
    
    public TimePunch(int pId,int empId,Timestamp in)
    {
        this.punchId = pId;
        this.employeeId = empId;
        this.punchIn = in;
        this.punchOut = null;
    }
    
    public TimePunch(int pId,int empId,Timestamp in,Timestamp out)
    {
        this.punchId = pId;
        this.employeeId = empId;
        this.punchIn = in;
        this.punchOut = out;
    }
    
    public int getPunchId()
    {
        return (this.punchId != 0)?this.punchId:0;
    }
    
    public void setPunchId(int id)
    {
        this.punchId = id;
    }
    
    public int getEmployeeId()
    {
        return (this.employeeId != 0)?this.employeeId:0;
    }
    
    public void setEmployeeId(int empId)
    {
        this.employeeId = empId;
    }
    
    public Timestamp getPunchInTimestamp()
    {
        return this.punchIn;
    }
    
    public void setPunchInTimestamp(Timestamp in)
    {
        this.punchIn = in;
    }
    
    public Timestamp getPunchOutTimestamp()
    {
        return this.punchOut;
    }
    
    public void setPunchOutTimestamp(Timestamp out)
    {
        this.punchOut = out;
    }
    
    @Override public String toString()
    {
        StringBuilder sb = new StringBuilder("Employee: ");
        if(getEmployeeId() != 0)
            sb.append(getEmployeeId());
        if(getPunchInTimestamp() != null)
        { 
            sb.append(" Punched in at: ");
            sb.append(DateUtils.getDateTimeFromTimestamp(getPunchInTimestamp()));
        }
        if(getPunchInTimestamp() != null)
        {
            sb.append(" Punched out at: ");
            sb.append(DateUtils.getDateTimeFromTimestamp(getPunchOutTimestamp()));
        }
        return sb.toString();
    }
}
