package com.example.captain.compass.constant;

/**
 * Created by captain on 2018/1/25.
 */

public class Constant {

    //默认状态，还从未开始配送
    public static final int FORM_STATE_DEFAULT = 0;
    //派送中
    public static final int FORM_STATE_DELIVERYING = 1;
    //已签收
    public static final int FORM_STATE_RECEIVED = 2;
    //已拒收
    public static final int FORM_STATE_REJECTED = 3;
    //二次配送
    public static final int FORM_STATE_SECOND_DELIVERY = 4;
    //联系不到收件人
    public static final int FORM_STATE_RECEIVER_UNCONTACTABLE = 5;
    //包裹破损
    public static final int FORM_STATE_PACKAGE_BROKEN = 6;
    //包裹丢失
    public static final int FORM_STATE_PACKAGE_MISSED = 7;
}
