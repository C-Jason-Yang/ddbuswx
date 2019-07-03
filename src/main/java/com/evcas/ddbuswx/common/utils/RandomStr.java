package com.evcas.ddbuswx.common.utils;

/**
 * Created by noxn on 2018/9/18.
 */
public class RandomStr {

    private String CHAR_UPPERCASE [] = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N",
            "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};

    private String CHAR_LOWERCASE [] = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n",
            "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};

    private static String NUM_CHAR_UPPERCASE [] = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "0", "A", "B", "C",
            "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N","O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};

    private static String NUM_CHAR_LOWERCASE [] = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "0", "a", "b", "c",
            "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n","o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};

    /**
     * 根据参数获取指定位数的字母（大写）数字混合随机字符串
     * @param randomStrDigits
     * @return
     */
    public static String getNumCharUppercaseRandomStr(Integer randomStrDigits) {
        if (randomStrDigits != null && randomStrDigits > 0) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < randomStrDigits; i++) {
                int charSort = (int)(Math.random()*36);
                sb.append(NUM_CHAR_UPPERCASE[charSort]);
            }
            return sb.toString();
        }
        return null;
    }

    /**
     * 根据参数获取指定位数的字母（小写）数字混合随机字符串
     * @param randomStrDigits
     * @return
     */
    public static String getNumCharLowercaseRandomStr(Integer randomStrDigits) {
        if (randomStrDigits != null && randomStrDigits > 0) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < randomStrDigits; i++) {
                int charSort = (int)(Math.random()*36);
                sb.append(NUM_CHAR_LOWERCASE[charSort]);
            }
            return sb.toString();
        }
        return null;
    }

    public static String getNumRandomStr(Integer randomStrDigits) {
        if (randomStrDigits != null && randomStrDigits > 0) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < randomStrDigits; i++) {
                int charSort = (int)(Math.random()*10);
                sb.append(charSort);
            }
            return sb.toString();
        }
        return null;
    }
}
