package com.example.captain.compass.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.amap.api.maps.TextureMapView;
import com.example.captain.compass.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NavigationActivity extends AppCompatActivity {

    @BindView(R.id.texture_map_view)
    TextureMapView textureMapView;

    public static void launchActivity(Context context) {
        context.startActivity(new Intent(context, NavigationActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        ButterKnife.bind(this);
        textureMapView.onCreate(savedInstanceState);
    }
}
