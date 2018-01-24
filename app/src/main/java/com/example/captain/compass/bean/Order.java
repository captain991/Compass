package com.example.captain.compass.bean;

import java.util.UUID;

/**
 * Created by captain on 2018/1/15.
 */

public class Order {
    private String senderName;
    private String senderAddress;
    private int senderTel;

    private String receiverName;
    private String receiverAddress;
    private int receiverTel;

    private String orderId;
    //物品名称
    private String objectName;
    //重量(g)
    private int weight;

    public Order(String senderName, String senderAddress, int senderTel, String receiverName,
                 String receiverAddress, int receiverTel, String objectName, int weight) {
        this.senderName = senderName;
        this.senderAddress = senderAddress;
        this.senderTel = senderTel;
        this.receiverName = receiverName;
        this.receiverAddress = receiverAddress;
        this.receiverTel = receiverTel;
        this.orderId = UUID.randomUUID().toString();
        this.objectName = objectName;
        this.weight = weight;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getSenderAddress() {
        return senderAddress;
    }

    public void setSenderAddress(String senderAddress) {
        this.senderAddress = senderAddress;
    }

    public int getSenderTel() {
        return senderTel;
    }

    public void setSenderTel(int senderTel) {
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

    public int getReceiverTel() {
        return receiverTel;
    }

    public void setReceiverTel(int receiverTel) {
        this.receiverTel = receiverTel;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getObjectName() {
        return objectName;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
}
