/**
 * 项目名称：密信项目
 * 文件名称：LogUtil.java
 * 版本：1.0.0
 * CopyRight:2014 联信摩贝软件（北京）有限公司
 */
package com.example.captain.compass.util;

import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Date;

/**
 * 记录用户习惯日志文件
 *
 * @author deliang.xie
 * @data 2014年5月2日
 * @time 下午11:56:29
 */
public class LogUtil {
    private static char MYLOG_TYPE = 'v';// 输入日志类型，w代表只输出告警信息等，v代表输出所有信息
    public static String LOG_PATH = Environment.getExternalStoragePublicDirectory("Compass").getAbsolutePath();


    public static void w(String tag, Object msg) { // 警告信息
        writeLog(tag, msg.toString(), 'w', "");
    }

    public static void e(String tag, Object msg) { // 错误信息
        writeLog(tag, msg.toString(), 'e', "");
    }

    public static void d(String tag, Object msg) {// 调试信息
        writeLog(tag, msg.toString(), 'd', "");
    }

    public static void i(String tag, Object msg) {//
        writeLog(tag, msg.toString(), 'i', "");
    }

    public static void v(String tag, Object msg) {
        writeLog(tag, msg.toString(), 'v', "");
    }

    public static void w(String tag, String text) {
        writeLog(tag, text, 'w', "");
    }

    public static void e(String tag, String text) {
        writeLog(tag, text, 'e', "");
    }

    public static void d(String tag, String text) {
        writeLog(tag, text, 'd', "");
    }

    public static void i(String tag, String text) {
        writeLog(tag, text, 'i', "");
    }

    public static void v(String tag, String text) {
        writeLog(tag, text, 'v', "");
    }

    public static void w(String tag, Object msg, String fileName) { // 警告信息
        writeLog(tag, msg.toString(), 'w', fileName);
    }

    public static void e(String tag, Object msg, String fileName) { // 错误信息
        writeLog(tag, msg.toString(), 'e', fileName);
    }

    public static void d(String tag, Object msg, String fileName) {// 调试信息
        writeLog(tag, msg.toString(), 'd', fileName);
    }

    public static void i(String tag, Object msg, String fileName) {//
        writeLog(tag, msg.toString(), 'i', fileName);
    }

    public static void v(String tag, Object msg, String fileName) {
        writeLog(tag, msg.toString(), 'v', fileName);
    }

    public static void w(String tag, String text, String fileName) {
        writeLog(tag, text, 'w', fileName);
    }

    public static void e(String tag, String text, String fileName) {
        writeLog(tag, text, 'e', fileName);
    }

    public static void d(String tag, String text, String fileName) {
        writeLog(tag, text, 'd', fileName);
    }

    public static void i(String tag, String text, String fileName) {
        writeLog(tag, text, 'i', fileName);
    }

    public static void v(String tag, String text, String fileName) {
        writeLog(tag, text, 'v', fileName);
    }

    /**
     * 根据tag, msg和等级，输出日志
     *
     * @param tag
     * @param msg
     * @param level
     * @return void
     * @since v 1.0
     */

    private static void writeLog(String tag, String msg, char level,
                                 String fileName) {

        if ('e' == level && ('e' == MYLOG_TYPE || 'v' == MYLOG_TYPE)) { // 输出错误信息
            Log.e(tag, msg);
        } else if ('w' == level && ('w' == MYLOG_TYPE || 'v' == MYLOG_TYPE)) {
            Log.w(tag, msg);
        } else if ('d' == level && ('d' == MYLOG_TYPE || 'v' == MYLOG_TYPE)) {
            Log.d(tag, msg);
        } else if ('i' == level && ('d' == MYLOG_TYPE || 'v' == MYLOG_TYPE)) {
            Log.i(tag, msg);
        } else {
            Log.v(tag, msg);
        }
            writeLogToFile( msg, fileName);
    }

    /**
     * 打开日志文件并写入日志
     *
     * @return
     **/
    public static void writeLogToFile(String text, String fileName) {// 新建或打开日志文件

        String errorLog = "log";
        if (!TextUtils.isEmpty(fileName)) {
            errorLog = fileName;
        }
        String logFilePath = "";
        FileWriter fw = null;
        PrintWriter pw = null;
        try {
            // 判断是否挂载了SD卡
            String storageState = Environment.getExternalStorageState();
            if (storageState.equals(Environment.MEDIA_MOUNTED)) {
                File file = new File(LOG_PATH + "/");
                if (!file.exists()) {
                    file.mkdirs();
                }
                logFilePath = LOG_PATH + "/" + errorLog + ".txt";
            }
            // 没有挂载SD卡，无法写文件
            if (logFilePath.isEmpty()) {
                return;
            }
            File logFile = new File(logFilePath);
            if (!logFile.exists()) {
                logFile.createNewFile();
            }
            fw = new FileWriter(logFile, true);
            pw = new PrintWriter(fw);
            pw.println("--" + StringUtils.formatMonthDayHourMinute(new Date()) + "(" +
                    System.currentTimeMillis() + ")--" + text + "--\r\n");
            pw.close();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeSFile(String content, String fileName, boolean append) {
        writeSFile(content.getBytes(), fileName, append);
    }

    public static void writeSFile(byte[] content, String fileName, boolean append) {
        String strLogFile = Environment.getExternalStorageDirectory() + "/" + fileName;

        OutputStream outputStream = null;
        try {
            File file = new File(strLogFile);
            file.createNewFile();
            outputStream = new FileOutputStream(file, append);
            outputStream.write(content);
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
