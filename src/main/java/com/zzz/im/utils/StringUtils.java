package com.zzz.im.utils;

import java.util.regex.Pattern;

/**
 * @author created by zzz at 2019/9/20 17:37
 **/

public class StringUtils {

    private static final Pattern EMPTY_TEXT_REGEX = Pattern.compile("\\s+");

    public static boolean isEmpty(String src) {
        return null == src || "".equals(src);
    }

    public static boolean isNotEmpty(String src) {
        return !isEmpty(src);
    }

    public static boolean isEmptyText(String src) {
        return EMPTY_TEXT_REGEX.matcher(src).matches();
    }
}
