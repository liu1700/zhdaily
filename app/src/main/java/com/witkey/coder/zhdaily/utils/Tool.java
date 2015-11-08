package com.witkey.coder.zhdaily.utils;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * 工具类
 *
 */
public class Tool {
    private static final SimpleDateFormat BASIC_FORMAT = new SimpleDateFormat("yyyyMMdd", Locale.CHINA);
    private static final SimpleDateFormat STROY_FORMAT = new SimpleDateFormat("MM月dd日 E", Locale.CHINA);

    // 获取明日的日期，用于进行数据流请求
    public static String getTomorrowDate() {
        Calendar now = Calendar.getInstance(Locale.CHINA);
        now.add(Calendar.DAY_OF_MONTH, 1);

        return BASIC_FORMAT.format(now.getTime());
    }

    // 格式化显示在首页Story流中的时间格式
    public static String toFormatDate(String date) {
        Calendar now = Calendar.getInstance(Locale.CHINA);
        if (BASIC_FORMAT.format(now.getTime()).equals(date)) return "今日要闻";
        try {
            Date d = BASIC_FORMAT.parse(date);
            return STROY_FORMAT.format(d);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}
