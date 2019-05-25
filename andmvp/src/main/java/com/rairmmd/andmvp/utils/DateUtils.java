package com.rairmmd.andmvp.utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * @author Rair
 * @date 2017/8/2
 */

public class DateUtils {

    private static Calendar calendar(Date date) {
        Calendar calendar = Calendar.getInstance(Locale.CHINA);
        calendar.setTime(date);
        return calendar;
    }

    /**
     * 当月最大天数
     *
     * @param date Date
     * @return int
     */
    public static int maxDaysOfMonth(Date date) {
        return calendar(date).getActualMaximum(Calendar.DATE);
    }

    /**
     * 当月第一天在月份表中的索引
     *
     * @param date Date
     * @return int
     */
    public static int firstDayOfMonthIndex(Date date) {
        Calendar calendar = calendar(date);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return calendar.get(Calendar.DAY_OF_WEEK) - 1;
    }

    /**
     * 给定日期是否是今天所在的月份
     *
     * @param date Date
     * @return 今天是当月几号
     */
    public static int isTodayOfMonth(Date date) {
        Calendar current = calendar(new Date());
        Calendar calendar = calendar(date);
        if (!equals(current, calendar, Calendar.YEAR)) {
            return -1;
        }
        if (!equals(current, calendar, Calendar.MONTH)) {
            return -1;
        }
        return current.get(Calendar.DAY_OF_MONTH) - 1;
    }

    /**
     * 比较是否相同
     *
     * @param calendarA calendarA
     * @param calendarB calendarB
     * @param field     field
     * @return boolean
     */
    public static boolean equals(Calendar calendarA, Calendar calendarB, int field) {
        boolean same;
        try {
            same = calendarA.get(field) == calendarB.get(field);
        } catch (Exception e) {
            same = false;
        }
        return same;
    }

    /**
     * 区间内有多少个月
     *
     * @param sDate sDate
     * @param eDate eDate
     * @return int
     */
    public static int months(Date sDate, Date eDate) {
        Calendar before = calendar(min(sDate, eDate));
        Calendar after = calendar(max(sDate, eDate));
        int diffYear = after.get(Calendar.YEAR) - before.get(Calendar.YEAR);
        int diffMonth = after.get(Calendar.MONTH) - before.get(Calendar.MONTH);
        return diffYear * 12 + diffMonth;
    }

    public static Date max(Date sDate, Date eDate) {
        return sDate.getTime() > eDate.getTime() ? sDate : eDate;
    }

    public static Date min(Date sDate, Date eDate) {
        return sDate.getTime() > eDate.getTime() ? eDate : sDate;
    }

    /**
     * 获取区间内各月的Date
     *
     * @param sDate sDate
     * @param eDate eDate
     * @return List<Date>
     */
    public static List<Date> fillMonths(Date sDate, Date eDate) {
        List<Date> dates = new ArrayList<>();
        if (null == sDate || null == eDate) {
            dates.add(new Date());
        } else {
            int months = months(sDate, eDate);
            Calendar calendar = calendar(min(sDate, eDate));
            for (int i = 0; i < months; i++) {
                dates.add(calendar.getTime());
                int month = calendar.get(Calendar.MONTH);
                month += 1;
                calendar.set(Calendar.MONTH, month);
            }
        }
        return dates;
    }

    /**
     * 根据月份及日期索引计算出指定日期
     *
     * @param month month
     * @param index index
     * @return Date
     */
    public static Date specialDayInMonth(Date month, int index) {
        Calendar calendar = calendar(month);
        calendar.set(Calendar.DAY_OF_MONTH, index + 1);
        return calendar.getTime();
    }

    /**
     * 获取某月最后一天日期
     *
     * @param date 月份
     * @return date月最后一天日期
     */
    public static Date getLastDayFromMonth(Date date) {
        Calendar calendar = calendar(date);
        calendar.set(Calendar.DAY_OF_MONTH, maxDaysOfMonth(date));
        return calendar.getTime();
    }

    /**
     * 获取指定Date一年前的某月第一天日期
     *
     * @param date 制定日期
     * @return 指定Date一年前的某月第一天日期
     */
    public static Date getDayYearAgo(Date date) {
        Calendar calendar = calendar(date);
        calendar.add(Calendar.MONTH, -11);
        calendar.set(Calendar.DAY_OF_MONTH, 0);
        return calendar.getTime();
    }

    public static Date getMonthYear(Date date) {
        Calendar calendar = calendar(date);
        calendar.add(Calendar.MONTH, 0);
        calendar.set(Calendar.DAY_OF_MONTH, 0);
        return calendar.getTime();
    }

    /**
     * 获取当天0点时间戳
     *
     * @return timestamp
     */
    public static long getTodayStartTimeStamp() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTimeInMillis();
    }

    public static long getTodayStartTimeStamp(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTimeInMillis();
    }

    /**
     * 获取当天23点59分时间戳
     *
     * @return timestamp
     */
    public static long getTodayEndTimeStamp() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        return calendar.getTimeInMillis();
    }

    public static String format2YMDHM(long time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd  HH:mm", Locale.CHINA);
        return format.format(time);
    }
}