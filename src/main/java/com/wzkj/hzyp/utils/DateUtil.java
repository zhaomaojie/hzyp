package com.wzkj.hzyp.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @user zhaoMaoJie
 * @date {DATE}
 */
public class DateUtil {

    public static SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");


    public static SimpleDateFormat ymdFormat = new SimpleDateFormat("yyyy-MM-dd");

//    public static void main(String[] args){
////        System.out.println(getDateAfter(new Date(),21));
//        System.out.println(dateToString(new Date(),DateUtil.ymdFormat));
//    }

    public static String dateToString(Date date,SimpleDateFormat sim){
        String dateString = sim.format(date);
        return dateString;
    }

    /* *
     * 计算时间差值
     * @author zhaoMaoJie
     * @date 2019/7/31 0031
     */
    public int getDateBetween(Date startDate, Date endDate){
        long start = startDate.getTime();
        long end = startDate.getTime();
        int c = (int)((start - end) / 1000);
        return c;
    }

    /* *
     * 获取两个时间的差值 精确到秒
     * @author zhaoMaoJie
     * @date 2019/7/31 0031
     */
    public static int getTimeDifferent(Date startDate,Date endDate){
        String start = simpleFormat.format(startDate);
        String end = simpleFormat.format(endDate);
        long from = 0;
        int ss = 0;
        try {
            from = simpleFormat.parse(start).getTime();
            long to = simpleFormat.parse(end).getTime();
            ss = (int)(to - from)/1000;
            return ss;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return ss;
    }

    /* *
     * 获取两个时间的差值 精确到秒
     * 输入参数 两个simpleDateformat格式
     * @author zhaoMaoJie
     * @date 2019/7/31 0031
     */
    public static int getDateStringDifferent(String startDate,String endDate){
//        SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");//如2016-08-10 20:40
        int ss = 0;
        long from = 0;
        try {
            from = simpleFormat.parse(startDate).getTime();
            long to = simpleFormat.parse(endDate).getTime();
            ss = (int)(to - from)/1000;
            return ss;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return ss;
    }

    public static String dateToString(Date date){
        String dateString = simpleFormat.format(date);
        return dateString;
    }


    /**
     * 得到几天后的时间
     *
     * @param d
     * @param day
     * @return
     */
    public static Date getDateAfter(Date d, int day) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar now = Calendar.getInstance();
        now.setTime(d);
        now.set(Calendar.DATE, now.get(Calendar.DATE) + day);//+后 -前
        return now.getTime();
    }

}
