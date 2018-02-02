package com.example.captain.compass.activity;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

/**
 * Created by captain on 2018/1/27.
 */

public class BaseActivity extends AppCompatActivity {
    protected ProgressDialog progressdialog;
    public Toast toast;

    public void showLoading(String msg) {
        if (progressdialog != null && progressdialog.isShowing())
            return;
        progressdialog = new ProgressDialog(this);
        progressdialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressdialog.setMessage(msg);
        progressdialog.setCanceledOnTouchOutside(false);
        progressdialog.setIndeterminate(true);
        progressdialog.setCancelable(true);
        progressdialog.show();
    }

    public void hideLoading() {
        if (null != progressdialog && progressdialog.isShowing()) {
            progressdialog.dismiss();
        }

    }


    public void showToast(String msg) {
        if (toast == null)
            toast = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
        else
            toast.setText(msg);
        toast.show();
    }

}
