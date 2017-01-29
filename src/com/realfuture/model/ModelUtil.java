package com.realfuture.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ModelUtil {
    public static <T> Collection<T> selectNotBlank(T... items) {
        List<T> result = new ArrayList<T>();
        for(T item : items) {
            if(item != null && !isBlank(item.toString())) {
                result.add(item);
            }
        }
        return result;
    }

    public static boolean isBlank(String str) {
        int strLen;
        if(str != null && (strLen = str.length()) != 0) {
            for(int i = 0; i < strLen; ++i) {
                if(!Character.isWhitespace(str.charAt(i))) {
                    return false;
                }
            }

            return true;
        } else {
            return true;
        }
    }

    public static String join(Iterable<?> items, String delimiter) {
        return join(items, delimiter, false);
    }

    public static String join(Iterable<?> items, String delimiter, boolean hash) {
        StringBuffer sb = new StringBuffer();
        if(items != null && items.iterator().hasNext()) {
            for (Object object : items) {
                if(sb.length() > 0) {
                    sb.append(delimiter);
                }
                sb.append(object == null ? "null" : (hash?object.hashCode()+"": object.toString()));
            }
        }
        return sb.toString();
    }

    public static String notNull(String prefix, String str) {
        if (str != null && str.length() > 0)
            return prefix + str;
        return "";
    }

    public static String notNull(String str) {
        if (str == null) return "";
        return str;
    }

    public static final boolean isEmpty(String string) {
        return (string == null) || (string.length() == 0);
    }

    public static String encodeNotNull(String value)
    {
        if(value == null) {
            return "";
        }
        return value;
    }

    public static final boolean isEmptyTrim(String string) {
        return (string == null) || (string.trim().length() == 0);
    }

    public static String encodeNotNull(String prefix, String value)
    {
        if(value == null) {
            return "";
        }
        return value.length() == 0 ? value : prefix + value;
    }

}
