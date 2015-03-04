/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package timeclock.utilities;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Provides date manipulation methods.
 * @author dannyjdelanojr
 */
public class DateUtils {
    private static final String TIME_FORMAT = "hh:mm:ss aa";
    private static final String DATE_FORMAT = "yyyy/MM/dd";
    private static final String FILE_DATE_FORMAT = "MM_dd_yy_HHmmss";
    private static final String DATE_TIME_FORMAT = "MM/dd/yyyy HH:mm:ss";
    
    /**
     * Returns the date and time MM/dd/yyyy HH:mm:ss.
     * @return              The date and time in a String format
     */
    public static String getDateTime()
    {           
        DateFormat dateFormat = new SimpleDateFormat(DateUtils.DATE_TIME_FORMAT);
        Date date = new Date();
        return dateFormat.format(date);
    }
    
    /**
     * Returns the date yyyy/MM/dd.
     * @return The date in a String format
     */
    public static String getDate()
    {
        DateFormat dateFormat = new SimpleDateFormat(DateUtils.DATE_FORMAT);
        Date date = new Date();
        return dateFormat.format(date);
    }
    
    /**
     * Returns the date MM-dd-yyyy.
     * @return The date in a String format
     */
    public static String getFileDate()
    {
        DateFormat dateFormat = new SimpleDateFormat(DateUtils.FILE_DATE_FORMAT);
        Date date = new Date();
        return dateFormat.format(date);
    }
    
    /**
     * Returns the date in the given format.
     * @return The date in a String format
     */
    public static String getDate(String format)
    {
        DateFormat dateFormat = new SimpleDateFormat(format);
        Date date = new Date();
        return dateFormat.format(date);
    }
    
    /**
     * Returns the time hh:mm:ss aa.
     * @return The time in a String format
     */
    public static String getTime()
    {
        DateFormat dateFormat = new SimpleDateFormat(DateUtils.TIME_FORMAT);
        Date date = new Date();
        return dateFormat.format(date);
    }
    
    /**
     * Gets a new Timestamp object from the current time
     * @return Timestamp object
     */
    public static Timestamp getNewTimestamp()
    {
        return new Timestamp(new Date().getTime());
    }
    
    /**
     * Gets a new Timestamp object from the Date given, sets
     * time to 00:00:00.0
     * @param date The Date object
     * @return Timestamp object
     */
    public static Timestamp getStartTimestampFromDate(Date date)
    {
        Calendar cal = Calendar.getInstance(); // locale-specific
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        long time = cal.getTimeInMillis();
        return new Timestamp(time);
    }
    
    /**
     * Gets a new Timestamp object from the Date given, sets
     * time to 23:59:59.0
     * @param date The Date object
     * @return Timestamp object
     */
    public static Timestamp getEndTimestampFromDate(Date date)
    {
        Calendar cal = Calendar.getInstance(); // locale-specific
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 0);
        long time = cal.getTimeInMillis();
        return new Timestamp(time);
    }
    
    /**
     * Get the Date object for a Timestamp
     * @param ts the time stamp to get date from
     * @return Date object
     */
    public static Date getDateObjFromTimestamp(Timestamp ts)
    {
        return new Date(ts.getTime());
    }
    
    /**
     * Get the string of the date/time for the passed Timestamp
     * in the format MM/dd/yyyy HH:mm:ss
     * @param ts the time stamp to get date string
     * @return String of date/time
     */
    public static String getDateFromTimestamp(Timestamp ts)
    {
        DateFormat dateFormat = new SimpleDateFormat(DateUtils.DATE_FORMAT);
        Date date = new Date(ts.getTime());
        return dateFormat.format(date);
    }
    
    /**
     * Get the string of the date/time for the passed Timestamp
     * in the format MM/dd/yyyy HH:mm:ss
     * @param ts the time stamp to get date string
     * @return String of date/time
     */
    public static String getDateTimeFromTimestamp(Timestamp ts)
    {
        DateFormat dateFormat = new SimpleDateFormat(DateUtils.DATE_TIME_FORMAT);
        Date date = new Date(ts.getTime());
        return dateFormat.format(date);
    }
    
    public static String getTimestampDiff_Str(Timestamp ts1,Timestamp ts2)
    {
        long diff = ts2.getTime() - ts1.getTime();
        //long diffSec = diff / 1000 % 60;
        long diffMin = diff / (60 * 1000) % 60;
        long diffHour = diff / (60 * 60 * 1000);
        return diffHour + " hrs "+ diffMin + " mins";
    }
    
    public static String getTimeStrFromLong(long diff)
    {
        long diffMin = diff / (60 * 1000) % 60;
        long diffHour = diff / (60 * 60 * 1000);
        return diffHour + " hrs "+ diffMin + " mins";
    }
}
