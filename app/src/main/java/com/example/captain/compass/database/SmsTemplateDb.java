package com.example.captain.compass.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.captain.compass.bean.SmsTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by captain on 2018/2/6.
 */

public class SmsTemplateDb {
    private final String TABLE_NAME = "sms_template";
    private DbHelper dbHelper;
    private SQLiteDatabase db;
    private static SmsTemplateDb instance = null;

    public static SmsTemplateDb getInstance() {
        if (instance == null) {
            synchronized (FormDb.class) {
                if (instance == null)
                    instance = new SmsTemplateDb();
            }
        }
        return instance;
    }

    private SmsTemplateDb() {
        dbHelper = DbHelper.getInstance();
        db = dbHelper.getWritableDatabase();
    }

    public SmsTemplate cursor2SmsTemplate(Cursor cursor) {
        SmsTemplate smsTemplate = new SmsTemplate();
        smsTemplate.setTemplateId(cursor.getInt(cursor.getColumnIndex("template_id")));
        smsTemplate.setTitle(cursor.getString(cursor.getColumnIndex("title")));
        smsTemplate.setContent(cursor.getString(cursor.getColumnIndex("content")));
        smsTemplate.setUpdateTime(cursor.getLong(cursor.getColumnIndex("update_time")));
        smsTemplate.setUserId(cursor.getInt(cursor.getColumnIndex("user_id")));
        return smsTemplate;
    }

    public List<SmsTemplate> querySmsTemplates() {
        List<SmsTemplate> smsTemplates = new ArrayList<>();
        Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            smsTemplates.add(cursor2SmsTemplate(cursor));
        }
        cursor.close();
        return smsTemplates;
    }

    public long insertSmsTemplate(SmsTemplate smsTemplate) {
        ContentValues contentValues = new ContentValues();
//        contentValues.put("user_id", smsTemplate.getUserId());
        contentValues.put("title", smsTemplate.getTitle());
        contentValues.put("content", smsTemplate.getContent());
        contentValues.put("update_time", smsTemplate.getUpdateTime());
        return db.insert(TABLE_NAME, null, contentValues);
    }

    public long updateSmsTemplate(SmsTemplate smsTemplate) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("title", smsTemplate.getTitle());
        contentValues.put("content", smsTemplate.getContent());
        contentValues.put("update_time", smsTemplate.getUpdateTime());
        return db.update(TABLE_NAME, contentValues, "template_id = ?", new String[]{smsTemplate.getTemplateId() + ""});
    }

    public int deleteSmsTemplate(SmsTemplate smsTemplate) {
        return db.delete(TABLE_NAME, "template_id = ?", new String[]{smsTemplate.getTemplateId() + ""});
    }
}