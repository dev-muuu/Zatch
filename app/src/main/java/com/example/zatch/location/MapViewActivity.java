package com.example.zatch.location;

import android.Manifest;
import android.app.AlertDialog;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.zatch.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapReverseGeoCoder;
import net.daum.mf.map.api.MapView;

import java.util.StringTokenizer;

public class MapViewActivity extends AppCompatActivity implements MapReverseGeoCoder.ReverseGeoCodingResultListener
        , MapView.MapViewEventListener, MapView.POIItemEventListener, MapView.CurrentLocationEventListener {

    AlertDialog.Builder builder;
    AlertDialog dialog;
    MapView mapView;
    ViewGroup mapViewContainer;
    View view;
    MapPOIItem poiItem;
    MapReverseGeoCoder.ReverseGeoCodingResultListener reverseListener;
    MapPoint currentPoint;
    MapReverseGeoCoder wantReverse, myReverse;
    String[] wantAddressArray;
    private FusedLocationProviderClient fusedLocationClient;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapview);

        getSupportActionBar().hide();

        //권한 체크
        permissionCheck();

        reverseListener = this;
        mapView.setMapViewEventListener(this);
        mapView.setPOIItemEventListener(this);

        findViewById(R.id.registerTownButton).setOnClickListener(onClickListener);
        findViewById(R.id.mapBackButton).setOnClickListener(onClickListener);

    }

    //kako map 좌표 -> 주소 변환 메서드
    @Override
    public void onReverseGeoCoderFoundAddress(MapReverseGeoCoder mapReverseGeoCoder, String s) {
        String[] address = new String[10];
        StringTokenizer tokenizer = new StringTokenizer(s, " ");
        for (int i = 0; tokenizer.hasMoreTokens(); i++)
            address[i] = tokenizer.nextToken();

        if (mapReverseGeoCoder.equals(wantReverse)) {
            wantAddressArray = address;
            myReverse = new MapReverseGeoCoder("636aa7f1b6a52dd8c64ef4de78b5f849"
                    , currentPoint, reverseListener, MapViewActivity.this);
            myReverse.startFindingAddress();
            return;
        } else if (mapReverseGeoCoder.equals(myReverse)) {
            String town = findTownName(wantAddressArray);
            if (findTownName(address).equals(town))
                showTownSetDialog(town);
            else
                sentDialog("현 위치와 입력하신 동네가 다릅니다.");
        }

    }

    void showTownSetDialog(String town) {
        builder = new AlertDialog.Builder(MapViewActivity.this);
        view = LayoutInflater.from(MapViewActivity.this).inflate(R.layout.dialog_town_check, null);
        TextView townText = view.findViewById(R.id.townCheckText);
        townText.setText("우리 동네가 " + town + " 맞나요?");
        view.findViewById(R.id.townReSettingButton).setOnClickListener(onClickListener);
        view.findViewById(R.id.townSettingButton).setOnClickListener(onClickListener);
        builder.setView(view);
        dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

    String findTownName(String[] address) {
        for (int i = 2; i < address.length; i++) {
            Character character;
            try {
                character = address[i].charAt(address[i].length() - 1);
            } catch (NullPointerException e) {
                return null;
            }

            switch (character) {
                case '동':
                case '면':
                case '읍':
                    return String.format("'%s'이", address[i]);
                case '리':
                case '가':
                    return String.format("'%s'가", address[i]);
            }
        }
        return null;
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.registerTownButton:
                    //내 동네 설정
                    //for 좌표->주소 변환, 호출 method
                    try {
                        wantAddressArray = null;
                        wantReverse = new MapReverseGeoCoder("636aa7f1b6a52dd8c64ef4de78b5f849"
                                , poiItem.getMapPoint(), reverseListener, MapViewActivity.this);
                        wantReverse.startFindingAddress();
                    } catch (NullPointerException e) {
                        System.out.println("error");
                    }
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

    void addMapView() {
        mapView = new MapView(this);
        mapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeading);
        mapViewContainer = (ViewGroup) findViewById(R.id.mapView);
        mapViewContainer.addView(mapView);

        //for current map point
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            //지도 추가시, 현재 위치 값 대입하기
                            currentPoint =  MapPoint.mapPointWithGeoCoord(location.getLatitude(),location.getLongitude());
                        }
                    }
                });
    }

    void sentDialog(String message){
        builder = new AlertDialog.Builder(MapViewActivity.this);
        view = LayoutInflater.from(getBaseContext()).inflate(R.layout.dialog_location_message,null);
        TextView messageView = view.findViewById(R.id.locationMessageText);
        messageView.setText(message);
        if(message.equals("현 위치와 입력하신 동네가 다릅니다.")) {
            TextView button = view.findViewById(R.id.dialogOKButton);
            button.setText("재설정");
        }
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
           sentDialog("위치 권한을 허용하셔야 동네 인증이 가능합니다.");
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
                    sentDialog("위치 권한을 허용하셔야 동네 인증이 가능합니다.");   //새로 권한 요청했는데 거부 -> sentDialog 실행됨
            });

    //map view event listener
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
        if(poiItem != null)
            mapView.removePOIItem(poiItem);

        poiItem = new MapPOIItem();
        poiItem.setMarkerType(MapPOIItem.MarkerType.RedPin);
        poiItem.setItemName("내 동네");
        poiItem.setMapPoint(mapPoint);
        poiItem.setShowAnimationType(MapPOIItem.ShowAnimationType.SpringFromGround);
        mapView.addPOIItem(poiItem);
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
    }

    @Override
    public void onMapViewMoveFinished(MapView mapView, MapPoint mapPoint) {

    }

    //poi event listener
    @Override
    public void onPOIItemSelected(MapView mapView, MapPOIItem mapPOIItem) {
        mapView.removePOIItem(mapPOIItem);
        poiItem = null;
    }

    @Override
    public void onCalloutBalloonOfPOIItemTouched(MapView mapView, MapPOIItem mapPOIItem) {

    }

    @Override
    public void onCalloutBalloonOfPOIItemTouched(MapView mapView, MapPOIItem mapPOIItem, MapPOIItem.CalloutBalloonButtonType calloutBalloonButtonType) {

    }

    @Override
    public void onDraggablePOIItemMoved(MapView mapView, MapPOIItem mapPOIItem, MapPoint mapPoint) {

    }

    //current location listener
    @Override
    public void onCurrentLocationUpdate(MapView mapView, MapPoint mapPoint, float v) {
        currentPoint = mapPoint;
    }

    @Override
    public void onCurrentLocationDeviceHeadingUpdate(MapView mapView, float v) {

    }

    @Override
    public void onCurrentLocationUpdateFailed(MapView mapView) {

    }

    @Override
    public void onCurrentLocationUpdateCancelled(MapView mapView) {

    }
}
