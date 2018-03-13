package com.example.captain.compass.bean;

import com.example.captain.compass.constant.Constant;
import com.google.gson.annotations.Expose;

import java.io.Serializable;

/**
 * Created by captain on 2018/1/15.
 */

public class Form implements Serializable {
    //    private String senderName;
//    private String senderAddress;
    @Expose
    private String senderTel;

    @Expose
    private String receiverName;
    @Expose
    private String receiverAddress;
    @Expose
    private String receiverTel;

    @Expose
    private String formId;
    //物品名称
//    private String objectName;
    //重量(g)
//    private int weight;

    //订单状态
    @Expose
    private int state = Constant.FORM_STATE_DELIVERYING;

    //备注
    @Expose
    private String mark;

    //收件人地址经度
    @Expose
    private double receiverLatitude;

    //收件人地址纬度
    @Expose
    private double receiverLongitude;

    @Expose(serialize = false, deserialize = false)
    private boolean checked = false;

    public Form() {
    }

    public Form(String senderTel, String receiverName, String receiverAddress, String receiverTel,
                String formId, String mark, double receiverLatitude, double receiverLongitude) {
        this.senderTel = senderTel;
        this.receiverName = receiverName;
        this.receiverAddress = receiverAddress;
        this.receiverTel = receiverTel;
        this.formId = formId;
        this.mark = mark;
        this.receiverLatitude = receiverLatitude;
        this.receiverLongitude = receiverLongitude;
    }

    //    public String getSenderName() {
//        return senderName;
//    }
//
//    public void setSenderName(String senderName) {
//        this.senderName = senderName;
//    }
//
//    public String getSenderAddress() {
//        return senderAddress;
//    }
//
//    public void setSenderAddress(String senderAddress) {
//        this.senderAddress = senderAddress;
//    }

    public String getSenderTel() {
        return senderTel;
    }

    public void setSenderTel(String senderTel) {
        this.senderTel = senderTel;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getReceiverAddress() {
        return receiverAddress;
    }

    public void setReceiverAddress(String receiverAddress) {
        this.receiverAddress = receiverAddress;
    }

    public String getReceiverTel() {
        return receiverTel;
    }

    public void setReceiverTel(String receiverTel) {
        this.receiverTel = receiverTel;
    }

    public String getFormId() {
        return formId;
    }

    public void setFormId(String formId) {
        this.formId = formId;
    }

//    public String getObjectName() {
//        return objectName;
//    }
//
//    public void setObjectName(String objectName) {
//        this.objectName = objectName;
//    }
//
//    public int getWeight() {
//        return weight;
//    }
//
//    public void setWeight(int weight) {
//        this.weight = weight;
//    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public double getReceiverLatitude() {
        return receiverLatitude;
    }

    public void setReceiverLatitude(double receiverLatitude) {
        this.receiverLatitude = receiverLatitude;
    }

    public double getReceiverLongitude() {
        return receiverLongitude;
    }

    public void setReceiverLongitude(double receiverLongitude) {
        this.receiverLongitude = receiverLongitude;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
