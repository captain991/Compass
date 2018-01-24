package com.example.captain.compass.bean;

/**
 * Created by captain on 2018/1/16.
 */

public class Plate<T> {
    private T t;

    public T get() {
        return t;
    }

    public void set(T t) {
        this.t = t;
    }
}
