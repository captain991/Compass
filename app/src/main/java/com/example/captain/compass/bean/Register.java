package com.example.captain.compass.bean;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.example.captain.compass.BR;

/**
 * Created by captain on 2018/3/22 下午10:40}.
 */

public class Register extends BaseObservable {
    public String tel;
    public String pwd;
    public String captcha;

    public Register(String tel, String pwd, String captcha) {
        this.tel = tel;
        this.pwd = pwd;
        this.captcha = captcha;
    }

    @Bindable
    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
        notifyPropertyChanged(BR.tel);
    }

    @Bindable
    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
        notifyPropertyChanged(BR.pwd);
    }

    @Bindable
    public String getCaptcha() {
        return captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
        notifyPropertyChanged(BR.captcha);
    }
}
