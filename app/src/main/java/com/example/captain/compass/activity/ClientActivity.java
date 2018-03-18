package com.example.captain.compass.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.captain.compass.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ClientActivity extends BaseActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    public static void launchActivity(Context context) {
        context.startActivity(new Intent(context, ClientActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);
        ButterKnife.bind(this);
        initToolbar();
        setDisplayHomeAsUpEnabled();
    }

    public void initToolbar() {
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
