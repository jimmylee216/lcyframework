/*
 * Copyright © 2015-2026 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.lcyframework.kernel.common.util;

import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <pre>
 * 名称: StringUtil
 * 描述: 字符串工具类
 * </pre>
 * @author Jimmy Li
 * @since 1.0.0
 */
public class StringUtil extends StringUtils {

    private static final String REGXPHTML = "<[^>]*>";

    /**
     * 第一个字符小写
     *
     * @param string str
     * @return string
     */
    public static String firstCharLower(final String string) {
        return firstChar(string).toString().toLowerCase() + string.substring(1, string.length());
    }

    /**
     * 第一个字符大写
     *
     * @param string str
     * @return string
     */
    public static String firstCharUpper(final String string) {
        return firstChar(string).toString().toUpperCase() + string.substring(1, string.length());
    }

    /**
     * 第一个字母大写转小写，或者第一个字母小写转大写
     *
     * @param string 输入字符串
     * @return 字符串
     */
    public static String firstCharUpperOrLower(final String string) {
        if (EmptyUtils.isEmpty(string)) {
            return null;
        }
        Character ch = firstChar(string);
        if (Character.isLowerCase(ch)) {
            return firstCharUpper(string);
        }
        if (Character.isUpperCase(ch)) {
            return firstCharLower(string);
        }
        return string;
    }

    /**
     * 获取第一个字符
     *
     * @param string 输入字符串
     * @return 输入字符串的第一个字符
     */
    public static Character firstChar(final String string) {
        if (EmptyUtils.isEmpty(string)) {
            return null;
        }
        return string.charAt(0);
    }

    /**
     * 替换
     *
     * @param string    字符串
     * @param oldString 被替换内容
     * @param newString 替换成
     * @return 字符串
     */
    public static String replaceHTML(final String string, final String oldString, final String newString) {
        if (EmptyUtils.isEmpty(string)) {
            return null;
        }
        return string.replace(oldString, newString);
    }

    /**
     * 剔除所有带html标签的字符串
     *
     * @param string 输入字符串
     * @return 剔除html后字符串
     */
    public static String replaceHTML(final String string) {
        if (EmptyUtils.isEmpty(string)) {
            return null;
        }
        Pattern pattern = Pattern.compile(REGXPHTML);
        Matcher matcher = pattern.matcher(string);
        StringBuffer sb = new StringBuffer();
        boolean result1 = matcher.find();
        while (result1) {
            matcher.appendReplacement(sb, "");
            result1 = matcher.find();
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    /**
     * replace oldChar -> ''
     *
     * @param string       被替换字符串
     * @param replaceChars 替换字符
     * @return 字符串
     */
    public static String replaceCharToNull(final String string, final String... replaceChars) {
        if (EmptyUtils.isEmpty(string)) {
            return null;
        }
        String result = null;
        for (String replaceChar : replaceChars) {
            result = string.replace(replaceChar, "");
        }
        return result;
    }

    /**
     * 字符串长度
     * @param string 输入字符串
     * @return 字符串长度
     */
    public static int getLength(final String string) {
        if (EmptyUtils.isEmpty(string)) {
            return 0;
        }
        return string.length();
    }

    /**
     * 判断字符串是否为空
     *
     * @param str 输入字符串
     * @return 空为true, 否者false
     */
    public static boolean isEmpty(final String str) {
        return str == null || str.length() == 0;
    }

    /**
     * 判断字符串是否不为空
     *
     * @param str 输入字符串
     * @return 空为false, 否者true
     */
    public static boolean isNotEmpty(final String str) {
        return !isEmpty(str);
    }

    /**
     * 将元数据前补零，补后的总长度为指定的长度，以字符串的形式返回
     * @param sourceData    元数据
     * @param formatLength  长度
     * @return 重组后的数据
     */
    public static String frontCompWithZore(final int sourceData, final int formatLength) {
        /*
         * 0 指前面补充零
         * formatLength 字符总长度为 formatLength
         * d 代表为正数。
         */
        String newString = String.format("%0" + formatLength + "d", sourceData);
        return newString;
    }
}
