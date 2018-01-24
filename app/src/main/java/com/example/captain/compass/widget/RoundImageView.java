package com.example.captain.compass.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.example.captain.compass.R;


/**
 * Created by captain on 2017/5/20.
 */

public class RoundImageView extends AppCompatImageView {
    private Bitmap mBitmap;
    private Paint mPaint;
    private BitmapShader mBitmapShader;
//    private ShapeDrawable mShapeDrawable;

    public RoundImageView(Context context) {
        this(context, null);
    }

    public RoundImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();
        mPaint.setColor(Color.RED);
//
//        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.RoundImageView);
//        int bitmapRes = ta.getInteger(R.styleable.RoundImageView_source, 0);
//        mBitmap = BitmapFactory.decodeResource(context.getResources(), bitmapRes);
//        ta.recycle();
        mBitmap=((BitmapDrawable)getDrawable()).getBitmap();
        mBitmapShader = new BitmapShader(mBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
//        mShapeDrawable = new ShapeDrawable(new OvalShape());
//        mShapeDrawable.setBounds(0, 0, getWidth(), getHeight());
        mPaint.setShader(mBitmapShader);
    }


    @Override
    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
        Matrix matrix = new Matrix();
        float scaleX = (float) getWidth() / mBitmap.getWidth();
        float scaleY = (float) getHeight() / mBitmap.getHeight();
        float scale = (scaleX > scaleY) ? scaleX : scaleY;
        matrix.postScale(scale, scale);
        if (scaleX < scaleY)
            matrix.postTranslate((getWidth() - mBitmap.getWidth() * scale) / 2, 0);
        else
            matrix.postTranslate(0, (getHeight() - mBitmap.getHeight() * scale) / 2);
        Log.e("captain", "scaledX:" + mBitmap.getWidth() * scale + ",scaledY:" + scale * mBitmap.getHeight());
        mBitmapShader.setLocalMatrix(matrix);

        int layerId = canvas.saveLayer(0, 0, getWidth(), getHeight(), null, Canvas.ALL_SAVE_FLAG);
        //使用canvas画圆形
        float radius = getWidth() < getHeight() ? getWidth() / 2 : getHeight() / 2;
        canvas.drawCircle(getWidth() / 2, getHeight() / 2, radius, mPaint);

        //使用ShapeDrawable画
//        mShapeDrawable.setBounds(0, 0, getWidth(), getHeight());
//        mShapeDrawable.getPaint().setShader(mBitmapShader);
//        mShapeDrawable.draw(canvas);
//        canvas.restoreToCount(layerId);

    }
}
