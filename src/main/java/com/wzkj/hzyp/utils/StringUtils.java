package com.wzkj.hzyp.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * @user zhaoMaoJie
 * @date {DATE}
 */
public class StringUtils {

    public static final String EMPTY = "";

    /* *
     * 判断字符串是否为空 如果为空则返回true
     * @author zhaoMaoJie
     * @date 2019/7/26 0026
     */
    public static boolean isEmpty(String str) {
        if (str != null && !str.equals("")) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 生成随机数
     *
     * @param num 位数
     * @return
     */
    public static String createRandomNum(int num) {
        String randomNumStr = "";
        for (int i = 0; i < num; i++) {
            int randomNum = (int) (Math.random() * 10);
            randomNumStr += randomNum;
        }
        return randomNumStr;
    }

    /**
     * 生成随机数
     *
     * @return
     */
    public static String createRandomCode() {
        String randomNumStr = "";
        for (int i = 0; i < 6; i++) {
            int randomNum = (int) (Math.random() * 10);
            randomNumStr += randomNum;
        }
        return randomNumStr;
    }

    private static final String FILTER_REGEX = "\\s*|\t|\r|\n";
    private static final Random RANDOM = new Random();

    public StringUtils() {
    }

    public static String removeCrlf(String str) {
        return str == null ? null : join((Object[]) tokenizeToStringArray(str, "\t\n\r\f"), " ");
    }

    public static String toInSql(List<String> list) {
        StringBuffer sql = new StringBuffer();
        sql.append(" ( ");

        for (int a = 0; a < list.size(); ++a) {
            sql.append("'" + (String) list.get(a) + "'");
            if (a < list.size() - 1) {
                sql.append(",");
            }
        }

        sql.append(" ) ");
        return sql.toString();
    }

    public static String removePrefix(String str, String prefix) {
        if (str == null) {
            return null;
        } else {
            return str.startsWith(prefix) ? str.substring(prefix.length()) : str;
        }
    }

    public static boolean isBlank(String str) {
        return str == null || str.trim().length() == 0;
    }

    public static String nullToStr(String str) {
        return isBlank(str) ? "" : str;
    }

    public static boolean isNotBlank(String str) {
        return !isBlank(str);
    }

    public static String getExtension(String str) {
        if (str == null) {
            return null;
        } else {
            int i = str.lastIndexOf(46);
            return i >= 0 ? str.substring(i + 1) : null;
        }
    }

    public static boolean contains(String str, String... keywords) {
        if (str == null) {
            return false;
        } else if (keywords == null) {
            throw new IllegalArgumentException("'keywords' must be not null");
        } else {
            String[] var2 = keywords;
            int var3 = keywords.length;

            for (int var4 = 0; var4 < var3; ++var4) {
                String keyword = var2[var4];
                if (str.contains(keyword.toLowerCase())) {
                    return true;
                }
            }

            return false;
        }
    }

    public static String defaultString(Object value) {
        return value == null ? "" : value.toString();
    }

    public static String defaultIfEmpty(Object value, String defaultValue) {
        return value != null && !"".equals(value) ? value.toString() : defaultValue;
    }

    public static String removeSeperatorFirstLetterUpperCase(String str, String seperator) {
        String[] strs = str.toLowerCase().split(seperator);
        String result = "";
        String preStr = "";

        for (int i = 0; i < strs.length; ++i) {
            if (preStr.length() == 1) {
                result = result + strs[i];
            } else {
                result = result + capitalize(strs[i]);
            }

            preStr = strs[i];
        }

        return result;
    }

    public static String replace(String inString, String oldPattern, String newPattern) {
        if (inString == null) {
            return null;
        } else if (oldPattern != null && newPattern != null) {
            StringBuffer sbuf = new StringBuffer();
            int pos = 0;
            int index = inString.indexOf(oldPattern);

            for (int patLen = oldPattern.length(); index >= 0; index = inString.indexOf(oldPattern, pos)) {
                sbuf.append(inString.substring(pos, index));
                sbuf.append(newPattern);
                pos = index + patLen;
            }

            sbuf.append(inString.substring(pos));
            return sbuf.toString();
        } else {
            return inString;
        }
    }

    public static String capitalize(String str) {
        return changeFirstCharacterCase(str, true);
    }

    public static String uncapitalize(String str) {
        return changeFirstCharacterCase(str, false);
    }

    private static String changeFirstCharacterCase(String str, boolean capitalize) {
        if (str != null && str.length() != 0) {
            StringBuffer buf = new StringBuffer(str.length());
            if (capitalize) {
                buf.append(Character.toUpperCase(str.charAt(0)));
            } else {
                buf.append(Character.toLowerCase(str.charAt(0)));
            }

            buf.append(str.substring(1));
            return buf.toString();
        } else {
            return str;
        }
    }


    public static String toUnderscoreName(String name) {
        return addSeperatorFirstLetterUpperCase(name, "_");
    }

    public static String addSeperatorFirstLetterUpperCase(String name, String seperator) {
        if (name == null) {
            return null;
        } else {
            String filteredName = name;
            if (name.equals(name.toUpperCase())) {
                filteredName = name.toUpperCase();
            }

            StringBuffer result = new StringBuffer();
            if (filteredName != null && filteredName.length() > 0) {
                result.append(filteredName.substring(0, 1).toLowerCase());

                for (int i = 1; i < filteredName.length(); ++i) {
                    String preChart = filteredName.substring(i - 1, i);
                    String c = filteredName.substring(i, i + 1);
                    if (c.equals(seperator)) {
                        result.append(seperator);
                    } else if (preChart.equals(seperator)) {
                        result.append(c.toLowerCase());
                    } else if (c.matches("\\d")) {
                        result.append(c);
                    } else if (c.equals(c.toUpperCase())) {
                        result.append(seperator);
                        result.append(c.toLowerCase());
                    } else {
                        result.append(c);
                    }
                }
            }

            return result.toString();
        }
    }

    public static String removeEndWiths(String inputString, String... endWiths) {
        String[] var2 = endWiths;
        int var3 = endWiths.length;

        for (int var4 = 0; var4 < var3; ++var4) {
            String endWith = var2[var4];
            if (inputString.endsWith(endWith)) {
                return inputString.substring(0, inputString.length() - endWith.length());
            }
        }

        return inputString;
    }

    public static boolean substringMatch(CharSequence str, int index, CharSequence substring) {
        for (int j = 0; j < substring.length(); ++j) {
            int i = index + j;
            if (i >= str.length() || str.charAt(i) != substring.charAt(j)) {
                return false;
            }
        }

        return true;
    }

    public static String[] tokenizeToStringArray(String str, String seperators) {
        if (str == null) {
            return new String[0];
        } else {
            StringTokenizer tokenlizer = new StringTokenizer(str, seperators);
            ArrayList result = new ArrayList();

            while (tokenlizer.hasMoreElements()) {
                Object s = tokenlizer.nextElement();
                result.add(s);
            }

            return (String[]) ((String[]) result.toArray(new String[result.size()]));
        }
    }

    public static String join(List list, String seperator) {
        return join(list.toArray(new Object[0]), seperator);
    }

    public static String join(Object[] array, String seperator) {
        if (array == null) {
            return null;
        } else {
            StringBuffer result = new StringBuffer();

            for (int i = 0; i < array.length; ++i) {
                result.append(array[i]);
                if (i != array.length - 1) {
                    result.append(seperator);
                }
            }

            return result.toString();
        }
    }

    public static int containsCount(String str, String sub) {
        if (str != null && sub != null && str.length() != 0 && sub.length() != 0) {
            int count = 0;

            int idx;
            for (int pos = 0; (idx = str.indexOf(sub, pos)) != -1; pos = idx + sub.length()) {
                ++count;
            }

            return count;
        } else {
            return 0;
        }
    }

    public static String splitString(String str, String split, int length) {
        int len = str.length();
        StringBuilder temp = new StringBuilder();

        for (int i = 0; i < len; ++i) {
            if (i % length == 0 && i > 0) {
                temp.append(split);
            }

            temp.append(str.charAt(i));
        }

        String[] attrs = temp.toString().split(split);
        StringBuilder finalMachineCode = new StringBuilder();
        String[] var7 = attrs;
        int var8 = attrs.length;

        for (int var9 = 0; var9 < var8; ++var9) {
            String attr = var7[var9];
            if (attr.length() == length) {
                finalMachineCode.append(attr).append(split);
            }
        }

        String result = finalMachineCode.toString().substring(0, finalMachineCode.toString().length() - 1);
        return result;
    }

    public static String stringFilter(String str) throws PatternSyntaxException {
        String regEx = "[`~!@#$%^&*()+=|{}':;',//[//]<>/?~！@#￥%……&*（）——|{}【】‘；：”“’。，、？]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.replaceAll("").trim();
    }

    public static String tranStr(String str) {
        String dest = "";
        if (str != null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }

        return dest;
    }
}
