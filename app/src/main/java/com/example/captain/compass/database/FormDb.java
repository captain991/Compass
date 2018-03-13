package com.example.captain.compass.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.captain.compass.bean.Form;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by captain on 2018/1/27.
 */

public class FormDb {
    private final String TABLE_NAME = "form";
    private DbHelper dbHelper;
    private SQLiteDatabase db;
    private static FormDb instance = null;

    public static FormDb getInstance() {
        if (instance == null) {
            synchronized (FormDb.class) {
                if (instance == null)
                    instance = new FormDb();
            }
        }
        return instance;
    }

    private FormDb() {
        dbHelper = DbHelper.getInstance();
        db = dbHelper.getWritableDatabase();
    }

    public Form cursor2Form(Cursor cursor) {
        Form form = new Form();
        form.setSenderTel(cursor.getString(cursor.getColumnIndex("sender_tel")));
        form.setReceiverName(cursor.getString(cursor.getColumnIndex("receiver_name")));
        form.setReceiverAddress(cursor.getString(cursor.getColumnIndex("receiver_address")));
        form.setReceiverTel(cursor.getString(cursor.getColumnIndex("receiver_tel")));
        form.setFormId(cursor.getString(cursor.getColumnIndex("form_id")));
        form.setState(cursor.getInt(cursor.getColumnIndex("state")));
        form.setMark(cursor.getString(cursor.getColumnIndex("mark")));
        form.setReceiverLatitude(cursor.getDouble(cursor.getColumnIndex("receiver_latitude")));
        form.setReceiverLongitude(cursor.getDouble(cursor.getColumnIndex("receiver_longitude")));
        return form;
    }

    public long insertForm(Form form) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("form_id", form.getFormId());
        contentValues.put("sender_tel", form.getSenderTel());
        contentValues.put("receiver_name", form.getReceiverName());
        contentValues.put("receiver_address", form.getReceiverAddress());
        contentValues.put("receiver_tel", form.getReceiverTel());
        contentValues.put("mark", form.getMark());
        contentValues.put("receiver_latitude", form.getReceiverLatitude());
        contentValues.put("receiver_longitude", form.getReceiverLongitude());
        contentValues.put("state", form.getState());
        return db.insert(TABLE_NAME, null, contentValues);
    }

    public Form queryForm(String formId) {
        Form form = null;
        Cursor cursor = db.query(TABLE_NAME, null, "form_id = ?", new String[]{formId},
                null, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst())
                form = cursor2Form(cursor);
            cursor.close();
        }
        return form;
    }

    public List<Form> queryForms(double latitude, double longitude) {
        return queryForms(latitude, longitude, -1);
    }

    public List<Form> queryForms(double latitude, double longitude, int state) {
        List<Form> forms = new ArrayList<>();
        Form form = null;
        String selection = state > 0 ? "receiver_latitude = ? AND receiver_longitude = ? AND state = ?" :
                "receiver_latitude = ? AND receiver_longitude = ? ";
        String[] selectionArgs = state > 0 ? new String[]{latitude + "", longitude + "", state + ""} :
                new String[]{latitude + "", longitude + ""};
        Cursor cursor = db.query(TABLE_NAME, null, selection, selectionArgs,
                null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                form = cursor2Form(cursor);
                forms.add(form);
            }
            cursor.close();
        }
        return forms;
    }

    public List<Form> queryForms() {
        List<Form> forms = new ArrayList<>();
        Form form = null;
        Cursor cursor = db.query(TABLE_NAME, null, null, null,
                null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                form = cursor2Form(cursor);
                forms.add(form);
            }
            cursor.close();
        }
        return forms;
    }

    public List<Form> queryForms(int state) {
        List<Form> forms = new ArrayList<>();
        Form form = null;
        Cursor cursor = db.query(TABLE_NAME, null, "state = ?", new String[]{state + ""},
                null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                form = cursor2Form(cursor);
                forms.add(form);
            }
            cursor.close();
        }
        return forms;
    }

    public int getFormCount(int state) {
        String sql = "SELECT COUNT(*) FROM " + TABLE_NAME + " WHERE state = ? ";
        Cursor cursor = db.rawQuery(sql, new String[]{state + ""});
        int count = 0;
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                count = cursor.getInt(0);
            }
            cursor.close();
        }
        return count;
    }

    public int updateFormState(String formId, int state) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("state", state);
        return db.update(TABLE_NAME, contentValues, "form_id = ?", new String[]{formId + ""});
    }

    public int deleteForms(List<Form> forms) {
        String whereArgs = "";
        for (Form form : forms) {
            whereArgs += form.getFormId() + ",";
        }
        whereArgs = whereArgs.substring(0, whereArgs.lastIndexOf(","));

//        return db.delete(TABLE_NAME, "form_id IN (?)", new String[]{whereArgs});
        String sql = "DELETE FROM " + TABLE_NAME + " WHERE form_id IN (" + whereArgs + ")";
        db.execSQL(sql);
        return 1;
    }

}
