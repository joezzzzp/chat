package com.zzz.im.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author created by zzz at 2019/9/20 16:18
 **/

public class DateUtils {

    public static String formatDate(Date date, String format) {
        if (null == date) {
            return null;
        }
        if (null == format || "".equals(format)) {
            format = "yyyy-MM-dd HH:mm:ss";
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        return simpleDateFormat.format(date);
    }
}
