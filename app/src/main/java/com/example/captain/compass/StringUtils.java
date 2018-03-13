/**
 * 项目名称：密信项目
 * 文件名称：StringUtils.java
 * 版本：1.0.0
 * CopyRight:2014 联信摩贝软件（北京）有限公司
 */
package com.example.captain.compass;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串工具类
 *
 * @author deliang.xie
 * @data 2014年5月3日
 * @time 上午10:11:55
 */
@SuppressLint({"SimpleDateFormat", "DefaultLocale"})
public class StringUtils {
    private static final String TAG = StringUtils.class.getSimpleName();
    // 输入内容中的纯文本部分
    public static final String LINK_CONTENT = "text";
    // 输入内容中的网络链接部分
    public static final String LINK_URL = "url";
    // 链接的标题
    public static final String LINK_TITLE = "title";
    // 链接的描述
    public static final String HTML_DESCRIPTION = "description";
    // 链接中的图片URL
    public static final String HTML_IMAGE_URL = "image";

    //链接中包含视频
    public static final String HTML_ISVIDEO = "isvideo";

    //    private static String REGEXP_TEL="^((13[0-9])|(15[^4,\\D])|(18[0,2,3,5-9]))\\d{8}$"；
    private static String REGEXP_TEL = "^1(3[0-9]|4[57]|5[0-35-9]|8[0-9]|70)\\d{8}$";
    private final static Pattern patternTel = Pattern.compile(REGEXP_TEL);

    //    private static String REGEXP_ACCOUNT="^(?!\\d+$)(?![a-zA-Z]+$)(?![_]+$)\\w{6,16}$";
    private static String REGEXP_ACCOUNT = "[a-zA-Z0-9]{6,16}$";
    private final static Pattern patternAccount = Pattern.compile(REGEXP_ACCOUNT);

    //    private static String REGEXP_EMAIL = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
    private static String REGEXP_EMAIL = "^[\\w!#$%&’*+/=?`{|}~^-]+(?:\\.[\\w!#$%&’*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
    private final static Pattern patternEmail = Pattern.compile(REGEXP_EMAIL);

    //www可以识别，但是如果就是文字"www.df"也会当作网址
//    private static String REGEXP_URL ="(http|https|www|ftp){1}(://)?[A-Za-z0-9./_-]+((\\?)?[A-Za-z0-9./_\\-~!$():@+#%=&'*,;]+)+";
    //这个不会把"www.df"也会当作网址,但是识别的网址必须有协议名http/https/ftp，"www.baidu.com"都不会识别为网址
//    private static String REGEXP_URL = "(http|ftp|https):\\/\\/[\\w\\-_]+(\\.[\\w\\-_]+)+([\\w\\-\\.,@?^=%&amp;:/~\\+#]*[\\w\\-\\@?^=%&amp;/~\\+#])?";
    //新的正则表达,两种情况应该都可以搞定
    private static String REGEXP_URL = "(http://|ftp://|https://|www){1}[^\u4e00-\u9fa5\\s]*?\\.(com|net|cn|me|tw|fr|tv|cc)[^\u4e00-\u9fa5\\s]*";
    private final static Pattern patternUrl = Pattern.compile(REGEXP_URL);

    /**
     * 字符串转整数
     *
     * @param str
     * @param defValue
     * @return
     */
    public static int toInt(String str, int defValue) {
        try {
            return Integer.parseInt(str);
        } catch (Exception e) {
        }
        return defValue;
    }

    /**
     * 对象转整数
     *
     * @param obj
     * @return 转换异常返回 0
     */
    public static int toInt(Object obj) {
        if (obj == null)
            return 0;
        return toInt(obj.toString(), 0);
    }

    /**
     * 判断给定字符串是否空白串。 空白串是指由空格、制表符、回车符、换行符组成的字符串 若输入字符串为null或空字符串，返回true
     *
     * @param input
     * @return boolean
     */
    public static boolean isEmpty(String input) {
        if (input == null || "".equals(input) || "null".equals(input)
                || "[null]".equals(input))
            return true;

        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (c != ' ' && c != '\t' && c != '\r' && c != '\n') {
                return false;
            }
        }
        return true;
    }

    /**
     * 方法名称：isNotEmpty 方法作用： 返回值类型：boolean
     *
     * @param input
     * @return
     */
    public static boolean isNotEmpty(String input) {
        return !isEmpty(input);
    }

    private final static ThreadLocal<SimpleDateFormat> dateFormater = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }
    };

    private final static ThreadLocal<SimpleDateFormat> dateFormater2 = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd");
        }
    };

    private final static ThreadLocal<SimpleDateFormat> dateFormater3 = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("MM-dd");
        }
    };

    private final static ThreadLocal<SimpleDateFormat> dateFormater4 = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm");
        }
    };

    private final static ThreadLocal<SimpleDateFormat> dateFormater5 = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yy-MM-dd");
        }
    };
    private final static ThreadLocal<SimpleDateFormat> dateFormater6 = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("MM-dd HH:mm:ss");
        }
    };
    private final static ThreadLocal<SimpleDateFormat> dateFormater7 = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("HH:mm");
        }
    };
    private final static ThreadLocal<SimpleDateFormat> dateFormater8 = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM");
        }
    };

    /**
     * 格式化MM-dd 月-日
     *
     * @param date
     * @return
     */
    public static String formatMonthDayDate(Date date) {
        try {
            return dateFormater3.get().format(date);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 格式化MM-dd 月-日(不带小时和秒)
     *
     * @param date
     * @return
     */
    public static String formatYearMonthDayDate(Date date) {
        try {
            return dateFormater2.get().format(date);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 格式化yy-MM 年-月(不带小时和秒)
     *
     * @param date
     * @return
     */
    public static String formatYearMonthDate(Date date) {
        try {
            return dateFormater8.get().format(date);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 将字符串转位日期类型
     *
     * @param sdate
     * @return
     */
    public static Date toDate(String sdate) {
        try {
            return dateFormater.get().parse(sdate);
        } catch (Exception e) {
            return null;
        }
    }

    public static Date toMinuteDate(String sdate) {
        try {
            return dateFormater4.get().parse(sdate);
        } catch (Exception e) {
            return null;
        }
    }

    public static Date toDayDate(String date) {
        try {
            return dateFormater2.get().parse(date);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 把String 转化成 年-月-日 时：分：秒
     *
     * @param date
     * @return
     */
    public static Date toData(String date) {
        try {
            return dateFormater.get().parse(date);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 获取当前日期
     *
     * @return
     */
    public static String formatDate(Date date) {
        try {
            return dateFormater.get().format(date);
        } catch (Exception e) {
            return null;
        }
    }

    public static String formatYearMonthDayHourMinute(Date date) {
        try {
            return dateFormater4.get().format(date);
        } catch (Exception e) {
            return null;
        }
    }

    public static String formatMonthDayHourMinute(Date date) {
        try {
            return dateFormater6.get().format(date);
        } catch (Exception e) {
            return null;
        }
    }

    public static String formatHourMinute(Date date) {
        try {
            return dateFormater7.get().format(date);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * @param day
     * @return
     */
    public static long formatYearMonthDayHourMinute(long day) {
        try {
            Date fromDate = new Date(day);
            String formatDate = formatYearMonthDayHourMinute(fromDate);
            Date resultFromDate = toMinuteDate(formatDate);
            day = resultFromDate.getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return day;
    }

    public static long formateYearMonthDay(String day) {
        long time = 0;
        try {
            Date date = toDayDate(day);
            time = date.getTime();
        } catch (Exception e) {
            LogUtil.e(TAG, e.toString());
        }
        return time;
    }




    /**
     * 判断两个字符串是否相等
     *
     * @param a
     * @param b
     * @return
     */
    public static boolean isEquals(String a, String b) {
        if (a != null) {
            return a.equals(b);
        } else if (b != null) {
            return b.equals(a);
        } else {
            return true;
        }
    }

    /**
     * 将阿拉伯数字1-7 转换为星期中的 周一到周日
     *
     * @param arabicNumber
     * @return
     */
    public static String arabicNumber2WeekDay(int arabicNumber) {// TODO

        String result = "";
        if (Calendar.MONDAY == arabicNumber) {
            result = "一";
        } else if (Calendar.TUESDAY == arabicNumber) {
            result = "二";
        } else if (Calendar.WEDNESDAY == arabicNumber) {
            result = "三";
        } else if (Calendar.THURSDAY == arabicNumber) {
            result = "四";
        } else if (Calendar.FRIDAY == arabicNumber) {
            result = "五";
        } else if (Calendar.SATURDAY == arabicNumber) {
            result = "六";
        } else if (Calendar.SUNDAY == arabicNumber) {
            result = "日";
        }
        return result;
    }

    /**
     * 将 4 这样的阿拉伯数字前面填上0
     *
     * @param number
     * @return
     */
    public static String formatMember(int number) {
        String result = "";
        if (number < 10 && number >= 0) {
            result = "0" + number;
        } else {
            result = number + "";
        }
        return result;
    }

    /**
     * 获取字符串长度
     *
     * @param c
     * @return
     */
    public static long calculateLength(CharSequence c) {
        double len = 0;
        for (int i = 0; i < c.length(); i++) {
            len++;
        }
        return Math.round(len);
    }

    /**
     * @param account
     * @return
     */
    public static boolean isAccountNo(String account) {
        if (isEmpty(account)) {
            return false;
        }
        return patternAccount.matcher(account).matches();
    }

    /**
     * 判断是不是一个合法的电子邮件地址
     *
     * @param email
     * @return
     */
    public static boolean isEmail(String email) {
        if (email == null || email.trim().length() == 0)
            return false;
        return patternEmail.matcher(email).matches();
    }

    /**
     * 手机号正则表达式
     *
     * @param tel
     * @return
     */
    public static boolean isTel(String tel) {
        if (tel == null || tel.trim().length() == 0) {
            return false;
        } else
//            return patternTel.matcher(tel).matches();
            return tel.length() == 11;
    }


    public static boolean isYeaterday(Date oldTime, Date newTime) {
        if (newTime == null) {
            newTime = new Date();
        }
        // 将下面的 理解成 yyyy-MM-dd 00：00：00 更好理解点
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            String todayStr = format.format(newTime);
            Date today = format.parse(todayStr);
            // 昨天 86400000=24*60*60*1000 一天
            if ((today.getTime() - oldTime.getTime()) > 0
                    && (today.getTime() - oldTime.getTime()) <= 86400000) {
                return true;
            } else if ((today.getTime() - oldTime.getTime()) <= 0) { // 至少是今天
                return false;
            } else { // 至少是前天
                return false;
            }
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }

    }

    /**
     * @param progress
     * @return
     */
    public static long getLiveTime(int progress) {

        long liveTime = 0;
        switch (progress) {
            case 0:
                break;
            case 1:
                liveTime = 5;
                break;
            case 2:
                liveTime = 10;
                break;
            case 3:
                liveTime = 20;
                break;
            case 4:
                liveTime = 30;
                break;
            case 5:
                liveTime = 60;
                break;
            case 6:
                liveTime = 60 * 2;
                break;
            case 7:
                liveTime = 60 * 5;
                break;
            case 8:
                liveTime = 60 * 10;
                break;
            case 9:
                liveTime = 60 * 30;
                break;
            case 10:
                liveTime = 60 * 60 * 1;
                break;
            case 11:
                liveTime = 60 * 60 * 2;
                break;
            case 12:
                liveTime = 60 * 60 * 3;
                break;
            case 13:
                liveTime = 60 * 60 * 4;
                break;
            case 14:
                liveTime = 60 * 60 * 5;
                break;
            case 15:
                liveTime = 60 * 60 * 6;
                break;
            case 16:
                liveTime = 60 * 60 * 24;
                break;
            case 17:
                liveTime = 60 * 60 * 24 * 2;
                break;
            case 18:
                liveTime = 60 * 60 * 24 * 3;
                break;
            case 19:
                liveTime = 60 * 60 * 24 * 4;
                break;
            case 20:
                liveTime = 60 * 60 * 24 * 5;
                break;
            case 21:
                liveTime = 60 * 60 * 24 * 6;
                break;
            case 22:
                liveTime = -1;
                break;

            default:
                break;
        }
        return liveTime;
    }



    /**
     * @param liveTime
     * @return
     */
    public static int getTimeIndex(long liveTime) {
        int index = 0;
        if (liveTime == 5) {// 秒
            index = 1;
        } else if (liveTime == 10) {
            index = 2;
        } else if (liveTime == 20) {
            index = 3;
        } else if (liveTime == 30) {
            index = 4;
        } else if (liveTime == 60) {
            index = 5;
        } else if (liveTime == 60 * 2) {
            index = 6;
        } else if (liveTime == 60 * 5) {
            index = 7;
        } else if (liveTime == 60 * 10) {
            index = 8;
        } else if (liveTime == 60 * 30) {
            index = 9;
        } else if (liveTime == 60 * 60 * 1) {// 1小时
            index = 10;
        } else if (liveTime == 60 * 60 * 2) {
            index = 11;
        } else if (liveTime == 60 * 60 * 3) {
            index = 12;
        } else if (liveTime == 60 * 60 * 4) {
            index = 13;
        } else if (liveTime == 60 * 60 * 5) {
            index = 14;
        } else if (liveTime == 60 * 60 * 6) {
            index = 15;
        } else if (liveTime == 60 * 60 * 24) {// 1天
            index = 16;
        } else if (liveTime == 60 * 60 * 24 * 2) {
            index = 17;
        } else if (liveTime == 60 * 60 * 24 * 3) {
            index = 18;
        } else if (liveTime == 60 * 60 * 24 * 4) {
            index = 19;
        } else if (liveTime == 60 * 60 * 24 * 5) {
            index = 20;
        } else if (liveTime == 60 * 60 * 24 * 6) {
            index = 21;
        } else if (liveTime == -1) {
            index = 22;
        }
        return index;
    }



    /**
     * 判断一个字符串是不是链接
     *
     * @param str
     * @return
     */
    public static boolean isLink(String str) {
        //11.16
        boolean isLink = false;
        Matcher m = patternUrl.matcher(str);
        if (m.find()) {
            isLink = true;
        }
        return isLink;
    }


    /*
 * 判断一个字符串是不是链接     进化版
 * isLink(String str)  判断网址的时候wwwsdfafa也会判断为网址
 * add v5.4 liwenquan 10.12
 */
    public static boolean isLinkLi(String str) {
        String regex = "^(http|https)?(:)?(//)?(?<=//|)((\\w)+\\.)+\\w+";
        boolean isLink = false;
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(str);
        if (m.find()) {
            isLink = true;
        }
        return isLink;
    }


    public static Map<String, String> getLinkUrl(String str) {
        Map<String, String> map = new HashMap<>();
        //11.16
        Matcher m = patternUrl.matcher(str);
        if (m.find()) {
            map.put(LINK_URL, m.group(0));
            map.put(LINK_CONTENT, str.replace(m.group(0), ""));
        } else if (m.matches()) {
            map.put(LINK_URL, str);
            map.put(LINK_CONTENT, str.replaceAll(REGEXP_URL, ""));
        }
        return map;
    }




    /**
     * v5.4 add
     * 获取构造图片访问路径
     *
     * @param urlPath 取得的图片路径
     * @param linkUrl 请求的路径
     */
    public static String getUrlStructure(String urlPath, String linkUrl) {
        String result = "";
        String patternStr1 = "^(http|https|ftp|)?(:)?(//)?((/w)*.)*/"; //匹配网址多级目录，匹配到最后一个“/”
        String patternStr2 = "^(http|https)?(:)?(//)?(?<=//|)((\\w)+\\.)+\\w+"; //匹配出网址中的域名(可以包含http前面的头,不包含后面“/”)
        String dnsName = "";    //取出图片路径的中的域名
        Pattern pattern = Pattern.compile(patternStr2);
        Matcher m = pattern.matcher(urlPath);
        if (m.find()) {
            dnsName = m.group() + "/";
        }
        if (!TextUtils.isEmpty(dnsName)) {
            if (dnsName.startsWith("http://")) {
                result = urlPath;
            } else {
                if (urlPath.startsWith("//")) {
                    result = "http:" + urlPath;
                } else if (urlPath.startsWith("/")) {
                    result = "http:/" + urlPath;
                } else {
                    result = "http://" + urlPath;
                }
            }
        } else {
            Pattern pattern1 = Pattern.compile(patternStr1);
            Matcher m1 = pattern1.matcher(linkUrl);    //匹配出请求路径的最后一个“/”之前的字符串
            String requestLink = "";
            if (m1.find()) {
                requestLink = m1.group();
            }

            if (!TextUtils.isEmpty(requestLink)) {
                if (!requestLink.startsWith("http://")) {    //判断连接的地址是否包含“http://”,不包含则加上
                    requestLink = "http://" + requestLink;
                }
                if (urlPath.startsWith("/")) {
                    Matcher m2 = pattern.matcher(linkUrl);    //匹配出请求路径的域名
                    if (m2.find()) {
                        dnsName = m2.group() + "/";
                        if (!dnsName.startsWith("http://")) {    //判断连接的地址是否包含“http://”,不包含则加上
                            dnsName = "http://" + dnsName;
                        }
                        result = dnsName + urlPath;
                    } else {
                        //只有请求的路径是错误的情况
                    }
                } else {
                    result = requestLink + urlPath;
                }
            }
        }
        return result;
    }

    /**
     * 匹配出字符串中包含的顶级域名
     *
     * @param dns
     * @return
     */
    public static String matcherTopLevelDomain(String dns) {
        String result = "";
        if (!TextUtils.isEmpty(dns)) {
            String patternStr = "^(http|https)?(:)?(//)?(?<=//|)((\\w)+\\.)+\\w+"; //匹配出网址中的域名(可以包含http前面的头,不包含后面“/”)
            Pattern pattern = Pattern.compile(patternStr);
            Matcher m = pattern.matcher(dns);
            if (m.find()) {
                result = m.group();
            }
        }
        return result;
    }





//v5.4

    /**
     * 把String 转化成 年-月-日
     *
     * @param date
     * @return
     */
    public static String formatYYMMDDDate(Date date) {
        try {
            return dateFormater2.get().format(date);
        } catch (Exception e) {
            return null;
        }
    }


    public static List<String> stringList(String str) {

        String d[] = str.split(",");
        List<String> list = new ArrayList<String>();

        for (int i = 0; i < d.length; i++) {
            list.add(d[i]);

        }
        return list;
    }
    /*
     * 获取到字节长度 add liwenquan 10.14
	 */

    public static int byteLength(String str) {
        int m = 0;
        char arr[] = str.toCharArray();
        for (int i = 0; i < arr.length; i++) {
            char c = arr[i];
            if ((c >= 0x0391 && c <= 0xFFE5))  //中文字符
            {
                m = m + 2;
            } else if ((c >= 0x0000 && c <= 0x00FF)) //英文字符
            {
                m = m + 1;
            }
        }
        Log.e("liwenquan", "max" + m);
        return m;
    }

    public static String getSubString(String str, int pstart, int pend) {
        String resu = "";
        int beg = 0;
        int end = 0;
        int count1 = 0;
        char[] temp = new char[str.length()];
        str.getChars(0, str.length(), temp, 0);
        boolean[] bol = new boolean[str.length()];
        for (int i = 0; i < temp.length; i++) {
            bol[i] = false;
            if ((int) temp[i] > 255) {//说明是中文
                count1++;
                bol[i] = true;
            }
        }
        if (pstart > str.length() + count1) {
            resu = null;
        }
        if (pstart > pend) {
            resu = null;
        }
        if (pstart < 1) {
            beg = 0;
        } else {
            beg = pstart - 1;
        }
        if (pend > str.length() + count1) {
            end = str.length() + count1;
        } else {
            end = pend;//在substring的末尾一样
        }
        //下面开始求应该返回的字符串
        if (resu != null) {
            if (beg == end) {
                int count = 0;
                if (beg == 0) {
                    if (bol[0] == true)
                        resu = null;
                    else
                        resu = new String(temp, 0, 1);
                } else {
                    int len = beg;//zheli
                    for (int y = 0; y < len; y++) {//表示他前面是否有中文,不管自己
                        if (bol[y] == true)
                            count++;
                        len--;//想明白为什么len--
                    }
                    //for循环运行完毕后，len的值就代表在正常字符串中，目标beg的上一字符的索引值
                    if (count == 0) {//说明前面没有中文
                        if ((int) temp[beg] > 255)//说明自己是中文
                            resu = null;//返回空
                        else
                            resu = new String(temp, beg, 1);
                    } else {//前面有中文，那么一个中文应与2个字符相对
                        if ((int) temp[len + 1] > 255)//说明自己是中文
                            resu = null;//返回空
                        else
                            resu = new String(temp, len + 1, 1);
                    }
                }
            } else {//下面是正常情况下的比较
                int temSt = beg;
                int temEd = end - 1;//这里减掉一
                for (int i = 0; i < temSt; i++) {
                    if (bol[i] == true)
                        temSt--;
                }//循环完毕后temSt表示前字符的正常索引
                for (int j = 0; j < temEd; j++) {
                    if (bol[j] == true)
                        temEd--;
                }//循环完毕后temEd-1表示最后字符的正常索引
                if (bol[temSt] == true)//说明是字符，说明索引本身是汉字的后半部分，那么应该是不能取的
                {
                    int cont = 0;
                    for (int i = 0; i <= temSt; i++) {
                        cont++;
                        if (bol[i] == true)
                            cont++;
                    }
                    if (pstart == cont)//是偶数不应包含,如果pstart<cont则要包含
                        temSt++;//从下一位开始
                }
                if (bol[temEd] == true) {//因为temEd表示substring 的最面参数，此处是一个汉字，下面要确定是否应该含这个汉字
                    int cont = 0;
                    for (int i = 0; i <= temEd; i++) {
                        cont++;
                        if (bol[i] == true)
                            cont++;
                    }
                    if (pend < cont)//是汉字的前半部分不应包含
                        temEd--;//所以只取到前一个
                }
                if (temSt == temEd) {
                    resu = new String(temp, temSt, 1);
                } else if (temSt > temEd) {
                    resu = null;
                } else {
                    resu = str.substring(temSt, temEd + 1);
                }
            }
        }
        return resu;//返回结果
    }
    public static String subStringDecimalPoint(String str) {
        String resu = new String();
        resu = str.substring(0, str.indexOf("."));
        return resu;
    }


    public static Long getUserId(String str) {
        Long UserId = null;
        if (str.length() > 0) {
            int i = str.lastIndexOf("@");
            str = str.substring(4, i);
            UserId= Long.valueOf(str);
        }

        return UserId;
    }
}