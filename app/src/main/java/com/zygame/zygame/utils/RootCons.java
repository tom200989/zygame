package com.zygame.zygame.utils;

import java.util.ArrayList;
import java.util.List;

/*
 * Created by qianli.ma on 2019/8/15 0015.
 */
public class RootCons {

    public static String LOCALE_LANGUAGE_COUNTRY = "LOCALE_LANGUAGE_COUNTRY";// 当前需要显示的语言和国家, 如:es-MX

    public static class LANGUAGES {// 支持的语言
        public static final String ENGLISH = "en";
        public static final String CHINA = "zh";
    }

    public static List<String> LANGUAGE_COUNTRY_LIST = new ArrayList<>();// 语言集合

    static {
        LANGUAGE_COUNTRY_LIST.add(LANGUAGES.ENGLISH + "-US");
        LANGUAGE_COUNTRY_LIST.add(LANGUAGES.CHINA);
    }


}
