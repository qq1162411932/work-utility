package core.utils;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

/**
 * @Author: 栗真的很棒
 * @Date: 2022/9/15 17:36
 */

public class LocalDateTimeUtil {

    /**
     * 判断localDateTime是否是本周
     * @param localDateTime
     * @return
     */
    public static Boolean isThisWeek(LocalDateTime localDateTime) {
        Calendar calendar = Calendar.getInstance();
        int currentWeek = calendar.get(Calendar.WEEK_OF_YEAR);
        calendar.setTime(new Date(toAllMilliSeconds(localDateTime)));
        int paramWeek = calendar.get(Calendar.WEEK_OF_YEAR);
        if (currentWeek == paramWeek) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    public static Boolean isThisMonth(LocalDateTime localDateTime){
        return isThisTime(localDateTime, "yyyy-MM");
    }

    public static Boolean isThisYear(LocalDateTime localDateTime){
        return isThisTime(localDateTime, "yyyy-MM-dd");
    }

    public static Boolean isThisTime(LocalDateTime localDateTime, String pattern){
        String currentTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern(pattern));
        String paramTime = localDateTime.format(DateTimeFormatter.ofPattern(pattern));
        if(currentTime.equals(paramTime)){
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    /**
     *  LocalDateTime转为秒
     * @return
     */
    public static Long toAllSeconds(LocalDateTime localDateTime){
        return  localDateTime.toInstant(ZoneOffset.of("+8")).toEpochMilli();
    }

    /**
     *  LocalDateTime转为毫秒
     * @return
     */
    public static Long toAllMilliSeconds(LocalDateTime localDateTime){
        return localDateTime.toInstant(ZoneOffset.of("+8")).toEpochMilli();
    }

    /**
     * String转化为LocalDateTime
     */
    public static LocalDateTime toLocalDateTime(String time){
        return LocalDateTime.parse(time, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    /**
     * LocalDateTime转换为String
     */
    public static String toString(LocalDateTime localDateTime){
        return  localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }


    /**
     * 将java.util.Date 转换为java8 的java.time.LocalDateTime,默认时区为东8区
     */
    public static LocalDateTime dateConvertToLocalDateTime(Date date) {
        return date.toInstant().atOffset(ZoneOffset.of("+8")).toLocalDateTime();
    }


    /**
     * 将java8 的 java.time.LocalDateTime 转换为 java.util.Date，默认时区为东8区
     * @param localDateTime
     * @return
     */
    public static Date localDateTimeConvertToDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.toInstant(ZoneOffset.of("+8")));
    }
}
