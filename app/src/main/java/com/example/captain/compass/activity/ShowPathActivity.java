package com.example.captain.compass.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
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
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.navi.AMapNavi;
import com.amap.api.navi.AMapNaviListener;
import com.amap.api.navi.AMapNaviView;
import com.amap.api.navi.model.AMapLaneInfo;
import com.amap.api.navi.model.AMapNaviCameraInfo;
import com.amap.api.navi.model.AMapNaviCross;
import com.amap.api.navi.model.AMapNaviInfo;
import com.amap.api.navi.model.AMapNaviLocation;
import com.amap.api.navi.model.AMapNaviPath;
import com.amap.api.navi.model.AMapNaviTrafficFacilityInfo;
import com.amap.api.navi.model.AMapServiceAreaInfo;
import com.amap.api.navi.model.AimLessModeCongestionInfo;
import com.amap.api.navi.model.AimLessModeStat;
import com.amap.api.navi.model.NaviInfo;
import com.amap.api.navi.model.NaviLatLng;
import com.amap.api.navi.view.RouteOverLay;
import com.autonavi.tbt.TrafficFacilityInfo;
import com.example.captain.compass.R;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ShowPathActivity extends BaseActivity implements AMapNaviListener, AMapLocationListener {

    private static final String INTENT_KEY_START_POINTS = "INTENT_KEY_START_POINTS";
    private static final String INTENT_KEY_END_POINTS = "INTENT_KEY_END_POINTS";
    private static final String INTENT_KEY_WAY_POINTS = "INTENT_KEY_WAY_POINTS";

    @BindView(R.id.map_view)
    MapView mapView;

    //起点
    private ArrayList<NaviLatLng> startPoints = new ArrayList<>();
    //终点
    private ArrayList<NaviLatLng> endPoints = new ArrayList<>();
    //途经点
    private ArrayList<NaviLatLng> wayPoints = new ArrayList<>();
    private AMapNavi aNavi;
    private AMap aMap;

    //定位
    private AMapLocationClient mLocationClient;
    private AMapLocationClientOption mLocationClientOption;

    //初次定位需要把界面移动到当前地理位置后再计算路径
    private boolean isFirstLocate = true;


    public static void launchActivity(Context context, ArrayList<NaviLatLng> startPoints,
                                      ArrayList<NaviLatLng> wayPoints, ArrayList<NaviLatLng> endPoints) {
        Intent intent = new Intent(context, ShowPathActivity.class);
        intent.putExtra(INTENT_KEY_START_POINTS, startPoints);
        intent.putExtra(INTENT_KEY_WAY_POINTS, wayPoints);
        intent.putExtra(INTENT_KEY_END_POINTS, endPoints);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_path);
        ButterKnife.bind(this);
        mapView.onCreate(savedInstanceState);
        initData();
        initBlueDotLocation();
    }

    public void initData() {
        startPoints = getIntent().getParcelableArrayListExtra(INTENT_KEY_START_POINTS);
        wayPoints = getIntent().getParcelableArrayListExtra(INTENT_KEY_WAY_POINTS);
        endPoints = getIntent().getParcelableArrayListExtra(INTENT_KEY_END_POINTS);

        aMap = mapView.getMap();

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

        mLocationClient = new AMapLocationClient(this);
        mLocationClient.setLocationListener(this);
        mLocationClientOption = new AMapLocationClientOption();
        mLocationClientOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        mLocationClientOption.setOnceLocationLatest(true);
        mLocationClient.setLocationOption(mLocationClientOption);
        mLocationClient.startLocation();

        aNavi = AMapNavi.getInstance(this);
        aNavi.addAMapNaviListener(this);
    }

    @OnClick({R.id.btn_my_location, R.id.btn_navigate})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_my_location:
                mLocationClient.startLocation();
                break;
            case R.id.btn_navigate:
                NavigateActivity.launchActivity(this, startPoints, wayPoints, endPoints);
                break;
            default:
                break;
        }
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

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {


        //缩放等级从3到19，数字越大越详细
        CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(new CameraPosition(
                new LatLng(aMapLocation.getLatitude(), aMapLocation.getLongitude()), 15, 0, 0));
        aMap.animateCamera(cameraUpdate, 1000, new AMap.CancelableCallback() {
            @Override
            public void onFinish() {
                if (isFirstLocate) {
                    isFirstLocate = false;
                    int strategy = aNavi.strategyConvert(true, true, true, false, false);
                    aNavi.calculateDriveRoute(startPoints, endPoints, wayPoints, strategy);
                }
            }

            @Override
            public void onCancel() {
                if (isFirstLocate) {
                    isFirstLocate = false;
                    int strategy = aNavi.strategyConvert(true, true, true, false, false);
                    aNavi.calculateDriveRoute(startPoints, endPoints, wayPoints, strategy);
                }
            }
        });
    }

    @Override
    public void onCalculateRouteSuccess(int[] ints) {
        //清空上次计算的路径列表。
//        routeOverlays.clear();
        HashMap<Integer, AMapNaviPath> paths = aNavi.getNaviPaths();
        for (int i = 0; i < ints.length; i++) {
            AMapNaviPath path = paths.get(ints[i]);
            if (path != null) {
                drawRoutes(ints[i], path);
            }
        }
    }

    private void drawRoutes(int routeId, AMapNaviPath path) {
//        calculateSuccess = true;
        aMap.moveCamera(CameraUpdateFactory.changeTilt(0));
        RouteOverLay routeOverLay = new RouteOverLay(aMap, path, this);
        routeOverLay.setTrafficLine(false);
        routeOverLay.addToMap();
//        routeOverlays.put(routeId, routeOverLay);
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

    @Override
    public void onInitNaviFailure() {

    }

    @Override
    public void onInitNaviSuccess() {

    }

    @Override
    public void onStartNavi(int i) {

    }

    @Override
    public void onTrafficStatusUpdate() {

    }

    @Override
    public void onLocationChange(AMapNaviLocation aMapNaviLocation) {

    }

    @Override
    public void onGetNavigationText(int i, String s) {

    }

    @Override
    public void onGetNavigationText(String s) {

    }

    @Override
    public void onEndEmulatorNavi() {

    }

    @Override
    public void onArriveDestination() {

    }

    @Override
    public void onCalculateRouteFailure(int i) {

    }

    @Override
    public void onReCalculateRouteForYaw() {

    }

    @Override
    public void onReCalculateRouteForTrafficJam() {

    }

    @Override
    public void onArrivedWayPoint(int i) {

    }

    @Override
    public void onGpsOpenStatus(boolean b) {

    }

    @Override
    public void onNaviInfoUpdate(NaviInfo naviInfo) {

    }

    @Override
    public void onNaviInfoUpdated(AMapNaviInfo aMapNaviInfo) {

    }

    @Override
    public void updateCameraInfo(AMapNaviCameraInfo[] aMapNaviCameraInfos) {

    }

    @Override
    public void onServiceAreaUpdate(AMapServiceAreaInfo[] aMapServiceAreaInfos) {

    }

    @Override
    public void showCross(AMapNaviCross aMapNaviCross) {

    }

    @Override
    public void hideCross() {

    }

    @Override
    public void showLaneInfo(AMapLaneInfo[] aMapLaneInfos, byte[] bytes, byte[] bytes1) {

    }

    @Override
    public void hideLaneInfo() {

    }

    @Override
    public void notifyParallelRoad(int i) {

    }

    @Override
    public void OnUpdateTrafficFacility(AMapNaviTrafficFacilityInfo aMapNaviTrafficFacilityInfo) {

    }

    @Override
    public void OnUpdateTrafficFacility(AMapNaviTrafficFacilityInfo[] aMapNaviTrafficFacilityInfos) {

    }

    @Override
    public void OnUpdateTrafficFacility(TrafficFacilityInfo trafficFacilityInfo) {

    }

    @Override
    public void updateAimlessModeStatistics(AimLessModeStat aimLessModeStat) {

    }

    @Override
    public void updateAimlessModeCongestionInfo(AimLessModeCongestionInfo aimLessModeCongestionInfo) {

    }

    @Override
    public void onPlayRing(int i) {

    }
}
