package com.example.captain.compass.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.navi.model.NaviLatLng;
import com.example.captain.compass.LogTag;
import com.example.captain.compass.R;
import com.example.captain.compass.bean.Form;
import com.example.captain.compass.database.FormDb;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MapActivity extends BaseActivity implements AMapLocationListener,
        AMap.OnMarkerClickListener {

    @BindView(R.id.map_view)
    MapView mapView;

    private AMap aMap;

    //定位
    private AMapLocationClient mLocationClient;
    private AMapLocationClientOption mLocationClientOption;

    //起点
    private ArrayList<NaviLatLng> startPoints = new ArrayList<>();
    //终点
    private ArrayList<NaviLatLng> endPoints = new ArrayList<>();
    //途经点
    private ArrayList<NaviLatLng> wayPoints = new ArrayList<>();

    private Map<String, Integer> formsCount = new HashMap<>();

    private boolean isFirstLocate = true;

    //西二旗
    NaviLatLng latLngXiErQi = new NaviLatLng(40.04674, 116.30769);
    //领袖新硅谷D区
    NaviLatLng latLngLingXiu = new NaviLatLng(40.0541123, 116.30983114);
    //回龙观新村中区
    NaviLatLng latLngHuiLongGuan = new NaviLatLng(40.06252112, 116.30934834);
    //融泽嘉园
    NaviLatLng latLngRongZe = new NaviLatLng(40.06860538, 116.30557179);

    public static void launchActivity(Context context) {
        context.startActivity(new Intent(context, MapActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        ButterKnife.bind(this);
        mapView.onCreate(savedInstanceState);
        initView();
        initData();
        initMarker();
        initBlueDotLocation();
    }

    public void initView() {
//        if (Build.VERSION.SDK_INT >= 19) {
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        }
    }

    public void initData() {
        aMap = mapView.getMap();
        aMap.setOnMarkerClickListener(this);

        //设置默认定位按钮是否显示，非必需设置。
        UiSettings uiSettings = aMap.getUiSettings();
        //指南针
        uiSettings.setCompassEnabled(true);
        //缩放按钮
        uiSettings.setZoomControlsEnabled(false);
        //缩放比例尺
        uiSettings.setScaleControlsEnabled(true);
        // 禁止通过手势倾斜地图
        uiSettings.setTiltGesturesEnabled(false);

//        startPoints.add(latLngXiErQi);
//        endPoints.add(latLngRongZe);

        mLocationClient = new AMapLocationClient(this);
        mLocationClient.setLocationListener(this);
        mLocationClientOption = new AMapLocationClientOption();
        mLocationClientOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        mLocationClientOption.setOnceLocationLatest(true);
        mLocationClient.setLocationOption(mLocationClientOption);
        mLocationClient.startLocation();

    }

    public void initMarker() {
        MarkerOptions options1 = new MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap(
                BitmapFactory.decodeResource(getResources(), R.drawable.ic_marker_package_1)));
        MarkerOptions options2 = new MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap(
                BitmapFactory.decodeResource(getResources(), R.drawable.ic_marker_package_2)));
        MarkerOptions options3 = new MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap(
                BitmapFactory.decodeResource(getResources(), R.drawable.ic_marker_package_3)));
        MarkerOptions options4 = new MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap(
                BitmapFactory.decodeResource(getResources(), R.drawable.ic_marker_package_4)));
        showLoading("数据加载中");
        Observable.create(new Observable.OnSubscribe<List<Form>>() {
            @Override
            public void call(Subscriber<? super List<Form>> subscriber) {
                subscriber.onNext(FormDb.getInstance().queryForms());
                subscriber.onCompleted();
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Form>>() {
                    @Override
                    public void onCompleted() {
                        hideLoading();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(LogTag.DB, e.toString());
                    }

                    @Override
                    public void onNext(List<Form> forms) {
                        formsCount.clear();
                        for (Form f : forms) {
                            String key = f.getReceiverLatitude() + "_" + f.getReceiverLongitude();
                            if (formsCount.containsKey(key))
                                formsCount.put(key, formsCount.get(key) + 1);
                            else
                                formsCount.put(key, 1);
                        }

                        for (String key : formsCount.keySet()) {
                            String[] positions = key.split("_");
                            int count = formsCount.get(key);
                            MarkerOptions options;
                            if (count < 10) {
                                options = options1;
                            } else if (count < 20) {
                                options = options2;
                            } else if (count < 30) {
                                options = options3;
                            } else
                                options = options4;
                            Marker marker = aMap.addMarker(options);
                            marker.setPosition(new LatLng(Double.valueOf(positions[0]), Double.valueOf(positions[1])));
                            wayPoints.add(new NaviLatLng(Double.valueOf(positions[0]), Double.valueOf(positions[1])));
                        }
                        endPoints.add(wayPoints.get(wayPoints.size() - 1));
                        wayPoints.remove(wayPoints.size() - 1);
                        hideLoading();
                    }
                });


//        for (int i = 0; i < 20; i++) {
//            Marker marker1 = aMap.addMarker(markerOptions);
//            double lat1 = new Random().nextInt(1000) / 1000000d + 40.0541123d;
//            double lng1 = new Random().nextInt(1000) / 1000000d + 116.30983114d;
//            marker1.setPosition(new LatLng(lat1, lng1));
//
//            Marker marker2 = aMap.addMarker(markerOptions);
//            double lat2 = new Random().nextInt(1000) / 1000000d + 40.06252112d;
//            double lng2 = new Random().nextInt(1000) / 1000000d + 116.30934834d;
//            marker2.setPosition(new LatLng(lat2, lng2));
//
//            Marker marker3 = aMap.addMarker(markerOptions);
//            double lat3 = new Random().nextInt(1000) / 1000000d + 40.06860538d;
//            double lng3 = new Random().nextInt(1000) / 1000000d + 116.30557179d;
//            marker3.setPosition(new LatLng(lat3, lng3));
//        }
    }

    public void initBlueDotLocation() {
        //初始化定位蓝点样式类
        MyLocationStyle myLocationStyle = new MyLocationStyle();

        //连续定位、蓝点不会移动到地图中心点，并且蓝点会跟随设备移动。

        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_FOLLOW_NO_CENTER)
                .strokeWidth(0)
                .radiusFillColor(getResources().getColor(R.color.transparent))
                .myLocationIcon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.ic_my_location)))
//                .radiusFillColor(getResources().getColor(R.color.blue))
                .interval(2000);

        //设置定位蓝点的Style
        aMap.setMyLocationStyle(myLocationStyle);

        // 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。
        aMap.setMyLocationEnabled(true);
    }


    @OnClick({R.id.btn_show_path, R.id.btn_my_location, R.id.btn_navigate})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_show_path:
                ShowPathActivity.launchActivity(this, startPoints, wayPoints, endPoints);
                break;
            case R.id.btn_navigate:
                break;
            case R.id.btn_my_location:
                mLocationClient.startLocation();
                break;
            default:
                break;
        }
    }


    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        //缩放等级从3到19，数字越大越详细
        CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(new CameraPosition(
                new LatLng(aMapLocation.getLatitude(), aMapLocation.getLongitude()), 15, 0, 0));
        aMap.animateCamera(cameraUpdate, 1000, null);
        if (isFirstLocate) {
            startPoints.add(new NaviLatLng(aMapLocation.getLatitude(), aMapLocation.getLongitude()));
            isFirstLocate = false;
        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        FormListActivity.launchActivity(this, marker.getPosition().latitude, marker.getPosition().longitude);
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }
}
