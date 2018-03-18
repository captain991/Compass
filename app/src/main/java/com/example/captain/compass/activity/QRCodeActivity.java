package com.example.captain.compass.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.example.captain.compass.util.LogTag;
import com.example.captain.compass.R;
import com.example.captain.compass.bean.Form;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class QRCodeActivity extends BaseActivity {
    @BindView(R.id.ivQRCode)
    ImageView ivQRCode;

    public static void launchActivity(Context context) {
        context.startActivity(new Intent(context, QRCodeActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode);
        ButterKnife.bind(this);
        generateQRCode();
    }

    public void generateQRCode() {
        Observable.create(new Observable.OnSubscribe<Bitmap>() {
            @Override
            public void call(Subscriber<? super Bitmap> subscriber) {
                List<Form> forms = new ArrayList<>();

                forms.add(new Form("13000000051", "荣佳美",
                        "武汉电脑大世界", "13900000051",
                        "02710051", "", 30.524585, 114.362348));

                forms.add(new Form("13000000050", "成元冬",
                        "广八路社区南门", "13900000050",
                        "02720050", "", 30.528692, 114.364537));

                forms.add(new Form("13000000015", "始李姝",
                        "银海雅苑西门", "13900000015",
                        "02730015", "", 30.52724, 114.364217));
                GsonBuilder gb = new GsonBuilder().excludeFieldsWithoutExposeAnnotation();
                Gson gson = gb.create();
                for (Form form : forms) {
                    String content = gson.toJson(form);
                    Log.d(LogTag.CAPTAIN, "gson:" + content);
                    Bitmap logo = BitmapFactory.decodeResource(getResources(), R.drawable.ic_deer);
                    Bitmap bitmap = CodeUtils.createImage(content, 400, 400, null);
                    File folder = Environment.getExternalStoragePublicDirectory("QRCode");
                    if (!folder.exists()) {
                        folder.mkdir();
                    }
                    File file = new File(folder.getAbsolutePath() + "/" + form.getReceiverName() + ".jpg");
                    if (!file.exists()) {
                        try {
                            FileOutputStream fos = new FileOutputStream(file);
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                            subscriber.onError(e);
                        }
                    }
                }
                subscriber.onNext(null);
                subscriber.onCompleted();
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Bitmap>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(LogTag.CAPTAIN, "generateQRCode error:" + e.toString());
                    }

                    @Override
                    public void onNext(Bitmap bitmap) {
                        if (bitmap != null)
                            ivQRCode.setImageBitmap(bitmap);
                    }
                });
    }

}
