/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package timeclock.utilities;

/**
 * This provides a simple wrapper class
 * for debug print functions.
 * @author demont-imac
 */
public class Debugger {
    // TODO: LOG_FILE - Implement a log file instead of printing to the console.
    /**
     * This returns if the debugger is enabled value
     * @return set to true 
     */
    public static boolean isEnabled()
    {
            return true;
    }
        
    /**
     * This logs the debug info to the console for now.
     * @param o Object to be printed
     */
    public static void log(Object o)
    {
        if(Debugger.isEnabled())
        {
            System.out.println(o.toString());
        }
    }
}
