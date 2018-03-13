package com.example.captain.compass.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.amap.api.maps.AMap;
import com.amap.api.navi.AMapNavi;
import com.amap.api.navi.AMapNaviListener;
import com.amap.api.navi.AMapNaviView;
import com.amap.api.navi.AMapNaviViewListener;
import com.amap.api.navi.AMapNaviViewOptions;
import com.amap.api.navi.enums.NaviType;
import com.amap.api.navi.model.AMapLaneInfo;
import com.amap.api.navi.model.AMapNaviCameraInfo;
import com.amap.api.navi.model.AMapNaviCross;
import com.amap.api.navi.model.AMapNaviInfo;
import com.amap.api.navi.model.AMapNaviLocation;
import com.amap.api.navi.model.AMapNaviTrafficFacilityInfo;
import com.amap.api.navi.model.AMapServiceAreaInfo;
import com.amap.api.navi.model.AimLessModeCongestionInfo;
import com.amap.api.navi.model.AimLessModeStat;
import com.amap.api.navi.model.NaviInfo;
import com.amap.api.navi.model.NaviLatLng;
import com.autonavi.tbt.TrafficFacilityInfo;
import com.example.captain.compass.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NavigateActivity extends BaseActivity implements AMapNaviViewListener, AMapNaviListener {
    private static final String INTENT_KEY_START_POINTS = "INTENT_KEY_START_POINTS";
    private static final String INTENT_KEY_END_POINTS = "INTENT_KEY_END_POINTS";
    private static final String INTENT_KEY_WAY_POINTS = "INTENT_KEY_WAY_POINTS";

    //起点
    private ArrayList<NaviLatLng> startPoints = new ArrayList<>();
    //终点
    private ArrayList<NaviLatLng> endPoints = new ArrayList<>();
    //途经点
    private ArrayList<NaviLatLng> wayPoints = new ArrayList<>();

    @BindView(R.id.map_navi_view)
    AMapNaviView mapNaviView;

    private AMap map;
    private AMapNavi mapNavi;

    public static void launchActivity(Context context, ArrayList<NaviLatLng> startPoints,
                                      ArrayList<NaviLatLng> wayPoints, ArrayList<NaviLatLng> endPoints) {
        Intent intent = new Intent(context, NavigateActivity.class);
        intent.putExtra(INTENT_KEY_START_POINTS, startPoints);
        intent.putExtra(INTENT_KEY_WAY_POINTS, wayPoints);
        intent.putExtra(INTENT_KEY_END_POINTS, endPoints);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigate);
        ButterKnife.bind(this);
        mapNaviView.setAMapNaviViewListener(this);
        mapNaviView.onCreate(savedInstanceState);
        initData();
    }

    public void initData() {
        startPoints = getIntent().getParcelableArrayListExtra(INTENT_KEY_START_POINTS);
        wayPoints = getIntent().getParcelableArrayListExtra(INTENT_KEY_WAY_POINTS);
        endPoints = getIntent().getParcelableArrayListExtra(INTENT_KEY_END_POINTS);

        map = mapNaviView.getMap();
        mapNavi = AMapNavi.getInstance(this);
        AMapNaviViewOptions options = new AMapNaviViewOptions();
        options.setTilt(0);
        mapNaviView.setViewOptions(options);
    }

    @Override
    public void onInitNaviFailure() {

    }

    @Override
    public void onInitNaviSuccess() {
        int strategy = 0;
        try {
            //再次强调，最后一个参数为true时代表多路径，否则代表单路径
            strategy = mapNavi.strategyConvert(true, false, false, false, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        mapNavi.calculateDriveRoute(startPoints, endPoints, wayPoints, strategy);
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
    public void onCalculateRouteSuccess(int[] ints) {
        mapNavi.startNavi(NaviType.GPS);
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

    @Override
    public void onNaviSetting() {

    }

    @Override
    public void onNaviCancel() {

    }

    @Override
    public boolean onNaviBackClick() {
        return false;
    }

    @Override
    public void onNaviMapMode(int i) {

    }

    @Override
    public void onNaviTurnClick() {

    }

    @Override
    public void onNextRoadClick() {

    }

    @Override
    public void onScanViewButtonClick() {

    }

    @Override
    public void onLockMap(boolean b) {

    }

    @Override
    public void onNaviViewLoaded() {

    }

    @Override
    protected void onPause() {
        super.onPause();
        mapNaviView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapNaviView.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapNaviView.onDestroy();
    }
}
