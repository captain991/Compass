package com.example.captain.compass.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.captain.compass.R;
import com.example.captain.compass.bean.Order;
import com.google.gson.Gson;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class QRCodeActivity extends AppCompatActivity {
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
                Order order = new Order("Tom", "Beijing", 2057,
                        "Jerry", "", 2000, "Cheese", 500);
                Gson gson = new Gson();
                String content = gson.toJson(order);
                Bitmap logo = BitmapFactory.decodeResource(getResources(), R.drawable.ic_galaxy);
                Bitmap bitmap = CodeUtils.createImage(content, 100, 100, logo);
                subscriber.onNext(bitmap);
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

                    }

                    @Override
                    public void onNext(Bitmap bitmap) {
                        if (bitmap != null)
                            ivQRCode.setImageBitmap(bitmap);
                    }
                });
    }

}
