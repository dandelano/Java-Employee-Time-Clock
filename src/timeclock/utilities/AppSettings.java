/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package timeclock.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Set;
// TODO: PROPERTIES -  Implement a way to save application settings,ie save locations, admin password
/**
 * Implements a way to save application settings,ie save locations, admin password.
 * @author demont-imac
 */
public final class AppSettings {
    private static final String CONFIG_FILE_NAME = "config.properties";
    private final Properties prop;
    
    public AppSettings() {
        prop = new Properties();
    }

    /**
     * Set a list of default key,value application properties.
     */
    private void setDefaultProps() {        
        try {
            //set the properties value
            prop.setProperty("app_name", "TimeClock");
            prop.setProperty("ver_major", "1");
            prop.setProperty("ver_minor", "0");
            prop.setProperty("ver_revision", "0");
            prop.setProperty("admin_pass", "FLH3gt7QHbW2xO9EFYh7XJcEzwgkouo2WThZbiKSIZCHNIgP4LSbWPjT0FB+ekZ6");
            prop.setProperty("dbdriver", "org.apache.derby.jdbc.EmbeddedDriver");
            prop.setProperty("dbproto", "jdbc:derby:");
            prop.setProperty("dbcreate", "create=true");
            prop.setProperty("dbshtdwn", "shutdown=true");
            prop.setProperty("dbname", "dbTimeClock");
            prop.setProperty("dbuser", "timeclock01");
            prop.setProperty("dbpassword", "kcolcemit");

            //save properties to project root folder
            prop.store(new FileOutputStream(AppSettings.CONFIG_FILE_NAME), "Default configurations");            
        } catch (IOException ex) {
            Debugger.log(ex);
        }
    }

    public void loadSettings() {        
        InputStream is = null;

        // Try and get properties file, if not found set defaults and try again
        try {
            File file = new File(AppSettings.CONFIG_FILE_NAME);
            is = new FileInputStream(file);
        } catch (Exception e) {
            is = null;
        }

        try {
            if (is == null) {
                setDefaultProps();
                loadSettings();
            } else {
                //load a properties file
                prop.load(is);              
            }
        } catch (IOException ex) {
            Debugger.log(ex);
        }
    }
    
    public String getAppTitle()
    {   
        return prop.getProperty("app_name")
                +" "+ prop.getProperty("ver_major")
                +"."+ prop.getProperty("ver_minor")
                +"."+ prop.getProperty("ver_revision");
    }
    
    public String getAdminPass()
    {
        return this.prop.getProperty("admin_pass");
    }
    
    public String getDBDriver()
    {
        return this.prop.getProperty("dbdriver");
    }
    
    public String getDBUrl()
    {
        return this.prop.getProperty("dbproto") 
                + this.prop.getProperty("dbname") 
                + ";"
                + this.prop.getProperty("dbcreate");
    }
    
    public String getDBShtDwnUrl()
    {
        return this.prop.getProperty("dbproto") 
                + ";"
                + this.prop.getProperty("dbshtdwn");
    }
    
    public String getDBUser()
    {
        return this.prop.getProperty("dbuser");
    }
    public String getDBPass()
    {
        return this.prop.getProperty("dbpassword");
    }
    
    /**
     * Get all of the keys in the config file
     * @return 
     */
    public Set<Object> getAllKeys(){
        Set<Object> keys = prop.keySet();
        return keys;
    }
    
    /**
     * Get specific setting keys value.
     * @param key
     * @return 
     */
    public String getSettingValue(String key){
        return this.prop.getProperty(key);
    }
    
    /**
     * Add a new setting to the config file.
     * @param key
     * @param value
     * @return 
     */
    public boolean addSetting(String key,String value)
    {
        boolean success = false;
        try {
            // check if key exists
            String test = prop.getProperty(key);
            if(test != null)
                return false;
            //set the properties value
            prop.setProperty(key, value);            
            //save properties to project root folder
            prop.store(new FileOutputStream(AppSettings.CONFIG_FILE_NAME), null);
            success = true;
        } catch (IOException ex) {
            Debugger.log(ex);
            return success;
        }
        return success;
    }
}
