package com.example.zatch.location;

import android.Manifest;
import android.app.AlertDialog;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.zatch.R;

import net.daum.mf.map.api.MapCircle;
import net.daum.mf.map.api.MapCurrentLocationMarker;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapReverseGeoCoder;
import net.daum.mf.map.api.MapView;

import java.util.StringTokenizer;

public class MapViewActivity extends AppCompatActivity implements MapReverseGeoCoder.ReverseGeoCodingResultListener, MapView.MapViewEventListener {

    AlertDialog.Builder builder;
    AlertDialog dialog;
    MapView mapView;
    ViewGroup mapViewContainer;
    MapCircle mapCircle;
    View view;
    MapReverseGeoCoder.ReverseGeoCodingResultListener reverseListener;
    MapView.MapViewEventListener eventListener;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapview);

        getSupportActionBar().hide();

        //권한 체크
        permissionCheck();

        reverseListener = this;
        eventListener = this;
        mapView.setMapViewEventListener(eventListener);

        findViewById(R.id.registerTownButton).setOnClickListener(onClickListener);
        findViewById(R.id.mapBackButton).setOnClickListener(onClickListener);

        mapCircle = new MapCircle(mapView.getMapCenterPoint(),6100000,
                Color.TRANSPARENT, android.graphics.Color.argb((float) 0.5, 255, 189, 107));
        mapView.addCircle(mapCircle);

    }

    //kako map 좌표 -> 주소 변환 메서드
    @Override
    public void onReverseGeoCoderFoundAddress(MapReverseGeoCoder mapReverseGeoCoder, String s) {
        System.out.println(s);
        String[] address = new String[10];
        StringTokenizer tokenizer = new StringTokenizer(s," ");
        for(int i = 0; tokenizer.hasMoreTokens();i++ )
            address[i] = tokenizer.nextToken();

        String town = findTownName(address);

        builder = new AlertDialog.Builder(MapViewActivity.this);
        view = LayoutInflater.from(MapViewActivity.this).inflate(R.layout.dialog_town_check,null);
        TextView townText = view.findViewById(R.id.townCheckText);
        townText.setText("우리 동네가 ‘"+town+"’이 맞나요?");
        view.findViewById(R.id.townReSettingButton).setOnClickListener(onClickListener);
        view.findViewById(R.id.townSettingButton).setOnClickListener(onClickListener);
        builder.setView(view);
        dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

    }

    String findTownName(String[] address){
        for (int i = 2; i < address.length ; i++){
            Character character;
            try {
                character = address[i].charAt(address[i].length() - 1);
            }catch (NullPointerException e){
                return null;
            }
            if(character.equals('동') || character.equals('면') || character.equals('읍'))
                return address[i];
        }
        return null;
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.registerTownButton:
                    //내 동네 설정
                    //for 좌표->주소 변환, 호출 method
                    MapReverseGeoCoder addressReverse = new MapReverseGeoCoder("636aa7f1b6a52dd8c64ef4de78b5f849"
                            ,mapView.getMapCenterPoint(),reverseListener,MapViewActivity.this);
                    addressReverse.startFindingAddress();
                    break;
                case R.id.mapBackButton:
                    finish();
                    break;
                case R.id.townReSettingButton:
                    dialog.dismiss();
                    break;
                case R.id.townSettingButton:
                    dialog.dismiss();
                    break;
            }
        }
    };

    @Override
    public void onReverseGeoCoderFailedToFindAddress(MapReverseGeoCoder mapReverseGeoCoder) {

    }

    void addMapView(){
        mapView = new MapView(this);
        mapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeading);
        mapViewContainer = (ViewGroup) findViewById(R.id.mapView);
        mapViewContainer.addView(mapView);
    }

    void sentDialog(){
        builder = new AlertDialog.Builder(MapViewActivity.this);
        view = LayoutInflater.from(getBaseContext()).inflate(R.layout.dialog_location_message,null);
        TextView message = view.findViewById(R.id.locationMessageText);
        message.setText("위치 권한을 허용하셔야 동네 인증이 가능합니다.");
        view.findViewById(R.id.dialogOKButton).setOnClickListener(v -> {
            dialog.dismiss();
            finish();
        });
        builder.setView(view);
        dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

    public void permissionCheck(){

        if (ContextCompat.checkSelfPermission( getApplicationContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            //이미 권한 부여됐을 때 사용
            addMapView();
        } else if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {
            //이미 권한 거부된 상태일 때 사용
           sentDialog();
        } else {
            //새로 권한 요청시 실행. 이후 callback 결과 실행됨
            requestPermissionLauncher.launch(
                    Manifest.permission.ACCESS_FINE_LOCATION);
        }
    }

    private final ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted)
                    addMapView();
                else
                    sentDialog();   //새로 권한 요청했는데 거부 -> sentDialog 실행됨
            });

    @Override
    public void onMapViewInitialized(MapView mapView) {

    }

    @Override
    public void onMapViewCenterPointMoved(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewZoomLevelChanged(MapView mapView, int i) {

    }

    @Override
    public void onMapViewSingleTapped(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewDoubleTapped(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewLongPressed(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewDragStarted(MapView mapView, MapPoint mapPoint) {
        mapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOff);
    }

    @Override
    public void onMapViewDragEnded(MapView mapView, MapPoint mapPoint) {
        Log.e("end","end");
        System.out.println(mapPoint);

    }

    @Override
    public void onMapViewMoveFinished(MapView mapView, MapPoint mapPoint) {

    }
}
