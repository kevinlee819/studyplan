package com.se1703.core.Utils;

import javax.xml.crypto.Data;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * @author leekejin
 * @date 2020/9/16 12:27
 **/
public class TimeUtils {

    /**
     * 返回分钟
     * @param startDate
     * @param endDate
     * @return
     */
    public static Long timeDiff(Date startDate, Date endDate){
        Calendar start = Calendar.getInstance();
        Calendar end = Calendar.getInstance();
        start.setTime(startDate);
        end.setTime(endDate);
        long between = end.getTimeInMillis() - start.getTimeInMillis();
        return between / (60 * 1000);
    }

    /**
     * 是否在同一天
     * @param cal1
     * @param cal2
     * @return
     */
    public static boolean isSameDay(Calendar cal1, Calendar cal2){
        if(cal1 != null && cal2 != null) {
            return cal1.get(0) == cal2.get(0) && cal1.get(1) == cal2.get(1) && cal1.get(6) == cal2.get(6);
        } else {
            throw new IllegalArgumentException("The date must not be null");
        }
    }

    public static boolean isSameDay(Date date1, Date date2) {
        if(date1 != null && date2 != null) {
            Calendar cal1 = Calendar.getInstance();
            cal1.setTime(date1);
            Calendar cal2 = Calendar.getInstance();
            cal2.setTime(date2);
            return isSameDay(cal1, cal2);
        } else {
            throw new IllegalArgumentException("The date must not be null");
        }
    }

    /**
     * 产生一个零点的时间
     * @param date
     * @return
     */
    public static Date genZeroClockDate(Date date) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);
        Date date2 = sdf.parse(date.toString());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date2);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    /**
     * 产生一个23:59:59的时间
     * @param date
     * @return
     */
    public static Date genFullClockDate(Date date) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);
        Date date2 = sdf.parse(date.toString());
        format.format(date2);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date2);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        return calendar.getTime();
    }


    public static boolean isBetween(Date startDate, Date endDate, Date centerDate){
        Calendar pre = Calendar.getInstance();
        Calendar next = Calendar.getInstance();
        Calendar center = Calendar.getInstance();
        pre.setTime(startDate);
        next.setTime(endDate);
        center.setTime(centerDate);
        return center.before(endDate) && center.after(startDate);
    }

}
