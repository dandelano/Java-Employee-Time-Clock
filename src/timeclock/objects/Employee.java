/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package timeclock.objects;

/**
 * Object to contain all of the information of an Employee.
 *
 * @author dannyjdelanojr
 */
public class Employee implements Comparable<Employee> {

    public enum PUNCH_STATUS {

        IN, OUT, NOT_FOUND, ERROR
    }
    private int EmployeeID;
    private String EmployeeFirstName;
    private String EmployeeLastName;
    private int IsSalary;
    private int IsAdmin;
    private String Password;
    private PUNCH_STATUS Status;

    public Employee() {
        this.EmployeeID = 0;
        this.EmployeeFirstName = null;
        this.EmployeeLastName = null;
        this.IsSalary = 0;
        this.IsAdmin = 0;
        this.Password = "password";
        this.Status = PUNCH_STATUS.NOT_FOUND;
    }

    public Employee(String first, String last, int isSalary, int isAdmin, String pass) {
        this.EmployeeID = 0;
        this.EmployeeFirstName = first;
        this.EmployeeLastName = last;
        this.IsSalary = (isSalary > 0) ? 1 : 0;
        this.IsAdmin = (isAdmin > 0) ? 1 : 0;
        this.Password = pass;
        this.Status = PUNCH_STATUS.NOT_FOUND;
    }

    public Employee(int id, String first, String last, int isSalary, int isAdmin, String pass) {
        this.EmployeeID = id;
        this.EmployeeFirstName = first;
        this.EmployeeLastName = last;
        this.IsSalary = (isSalary > 0) ? 1 : 0;
        this.IsAdmin = (isAdmin > 0) ? 1 : 0;
        this.Password = pass;
        this.Status = PUNCH_STATUS.OUT;
    }

    public Employee(int id, String first, String last, int isSalary, int isAdmin, String pass, int status) {
        this.EmployeeID = id;
        this.EmployeeFirstName = first;
        this.EmployeeLastName = last;
        this.IsSalary = (isSalary > 0) ? 1 : 0;
        this.IsAdmin = (isAdmin > 0) ? 1 : 0;
        this.Password = pass;
        this.Status = PUNCH_STATUS.ERROR;
        this.setEmpStatus(status);
    }

    /**
     *
     * @return
     */
    public int getEmployeeID() {
        return (EmployeeID != 0) ? EmployeeID : 0;
    }

    /**
     *
     * @return
     */
    public String getFirstName() {
        return (EmployeeFirstName != null) ? EmployeeFirstName : "(Not Set)";
    }

    /**
     *
     * @return
     */
    public String getLastName() {
        return (EmployeeLastName != null) ? EmployeeLastName : "(Not Set)";
    }

    /**
     *
     * @return
     */
    public boolean getIsSalary_bool() {
        return IsSalary > 0;
    }

    /**
     *
     * @return
     */
    public int getIsSalary_int() {
        return IsSalary;
    }

    /**
     *
     * @return
     */
    public boolean getIsAdmin_bool() {
        return IsAdmin > 0;
    }

    /**
     *
     * @return
     */
    public int getIsAdmin_int() {
        return IsAdmin;
    }

    /**
     *
     * @return
     */
    public String getPassword() {
        return (Password != null) ? Password : "(Not Set)";
    }

    /**
     *
     * @param id
     */
    public void setEmployeeID(int id) {
        EmployeeID = id;
    }

    /**
     *
     * @param first
     */
    public void setFirstName(String first) {
        EmployeeFirstName = first;
    }

    /**
     *
     * @param last
     */
    public void setLasttName(String last) {
        EmployeeLastName = last;
    }

    /**
     *
     * @param isSalary
     */
    public void setIsSalary(boolean isSalary) {
        IsSalary = (isSalary) ? 1 : 0;
    }

    /**
     *
     * @param isSalary
     */
    public void setIsSalary(int isSalary) {
        IsSalary = (isSalary > 0) ? 1 : 0;
    }

    /**
     *
     * @param isAdmin
     */
    public void setIsAdmin(boolean isAdmin) {
        IsAdmin = (isAdmin) ? 1 : 0;
    }

    /**
     *
     * @param isAdmin
     */
    public void setIsAdmin(int isAdmin) {
        IsAdmin = (isAdmin > 0) ? 1 : 0;
    }

    /**
     *
     * @param pass
     */
    public void setPassword(String pass) {
        Password = pass;
    }

    /**
     *
     * @return
     */
    public boolean getIsClockedIn_bool() {
        return (Status == PUNCH_STATUS.IN);
    }

    /**
     *
     * @return
     */
    public int getEmployeeStatus_int() {
        int empStat = 3;
        switch (Status) {
            case OUT:
                empStat = 0;
                break;
            case IN:
                empStat = 1;
                break;
            case NOT_FOUND:
                empStat = 2;
                break;
            case ERROR:
                break;
        }
        return empStat;
    }

    /**
     *
     * @return
     */
    public PUNCH_STATUS getEmployeeStatus_ps() {
        return Status;
    }

    /**
     *
     * @param clockedIn
     */
    public void setClockedIn(boolean clockedIn) {
        Status = (clockedIn) ? PUNCH_STATUS.IN : PUNCH_STATUS.OUT;
    }

    /**
     *
     * @param empStat
     */
    private void setEmpStatus(int empStat) {
        switch (empStat) {
            case 0:
                Status = PUNCH_STATUS.OUT;
                break;
            case 1:
                Status = PUNCH_STATUS.IN;
                break;
            case 2:
                Status = PUNCH_STATUS.NOT_FOUND;
                break;
            case 3:
            default:
                Status = PUNCH_STATUS.ERROR;
                break;
        }
    }

    /**
     * Implements the toString method of the Employee object, prints employee id,
     * first name, and last name.
     * @return
     */
    @Override
    public String toString() {
        return getEmployeeID() + " " + getFirstName() + " " + getLastName();
    }

    /**
     * Implements compare for Collections.sort method, sorts by first name then
     * by last name.
     *
     * @param compareEmp the Employee being compared to this Employee
     */
    @Override
    public int compareTo(Employee compareEmp) {
        int result;
        String firstName1 = this.getFirstName().toUpperCase();
        String firstName2 = compareEmp.getFirstName().toUpperCase();
        result = firstName1.compareTo(firstName2);
        if (result == 0) {
            String lastName1 = this.getLastName().toUpperCase();
            String lastName2 = compareEmp.getLastName().toUpperCase();
            result = lastName1.compareTo(lastName2);
        }
        return result;
    }

}
