package com.example.captain.compass.event;

import com.example.captain.compass.bean.Form;

/**
 * Created by captain on 2018/1/30.
 */

public class AddFormEvent {
    private Form form;

    public AddFormEvent(Form form) {
        this.form = form;
    }

    public Form getForm() {
        return form;
    }

    public void setForm(Form form) {
        this.form = form;
    }
}
