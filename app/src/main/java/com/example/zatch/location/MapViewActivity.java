package com.example.zatch.location;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.zatch.R;
import com.example.zatch.databinding.ActivityMapviewBinding;
import com.example.zatch.navigation.chat.KakaoApiService;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapReverseGeoCoder;
import net.daum.mf.map.api.MapView;

import java.util.List;
import java.util.StringTokenizer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MapViewActivity extends AppCompatActivity implements MapReverseGeoCoder.ReverseGeoCodingResultListener
        , MapView.MapViewEventListener, MapView.POIItemEventListener, MapView.CurrentLocationEventListener {

    AlertDialog.Builder builder;
    AlertDialog dialog;
    MapView mapView;
    MapPOIItem poiItem;
    MapReverseGeoCoder.ReverseGeoCodingResultListener reverseListener;
    MapPoint currentPoint;
    MapReverseGeoCoder wantReverse, myReverse;
    String[] wantAddressArray;
    private FusedLocationProviderClient fusedLocationClient;
    private CallMapViewEnum serviceType;

    private ActivityMapviewBinding binding;

    static final String BASE_URL = "https://dapi.kakao.com/";
    static final String API_KEY = "KakaoAK de8b17f677631d70f9545c4b19608dcf";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapviewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        serviceType = (CallMapViewEnum) getIntent().getSerializableExtra("serviceType");

        initByServiceType();

        //?????? ??????
        permissionCheck();

        reverseListener = this;

        binding.mapButton.setOnClickListener(v->{

            if(serviceType == CallMapViewEnum.MakeMeeting){
                searchBuildingByMapPoint();
            }else {
                //??? ?????? ??????
                //for ??????->?????? ??????, ?????? method
                try {
                    wantAddressArray = null;
                    wantReverse = new MapReverseGeoCoder("636aa7f1b6a52dd8c64ef4de78b5f849"
                            , poiItem.getMapPoint(), reverseListener, MapViewActivity.this);
                    wantReverse.startFindingAddress();
                } catch (NullPointerException e) {
                    System.out.println("error");
                }
            }
        });

        binding.mapBackButton.setOnClickListener(v-> finish());

    }

    public class ResultSearchBuilding {
        List<KakaoRoadAddressData> documents;
    }

    private void searchBuildingByMapPoint() {

        MapPoint.GeoCoordinate point = poiItem.getMapPoint().getMapPointGeoCoord();

        Retrofit.Builder builder = new Retrofit.Builder();
        builder.baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create());
        KakaoApiService api = builder.build().create(KakaoApiService.class);
        Call<ResultSearchBuilding> call = api.getBuildingName(API_KEY, String.valueOf(point.longitude),String.valueOf(point.latitude));

        call.enqueue(new Callback<ResultSearchBuilding>() {
            @Override
            public void onResponse(Call<ResultSearchBuilding> call, Response<ResultSearchBuilding> response) {
                try {
                    Log.e("result","result");
                    RoadAddress data = response.body().documents.get(0).getRoad_address();
                    String place;
                    //????????? ???????????? ??????, ????????? ????????? ?????? dialog ?????????
                    if(data.getBuilding_name().length() == 0) {
                        place = String.format("%s %s", data.getRoad_name(), data.getMain_building_no());
                        //sub ???????????? ?????? ??????, place ?????? '-'??? ?????? ?????????
                        if(data.getSub_building_no().length() != 0)
                            place += String.format("-%s",data.getSub_building_no());
                    }else   //????????? ????????? ??? ?????? ??????, ????????? ????????? dialog ?????????
                        place = data.getBuilding_name();

                    showTownSetDialog(place);
                }catch (NullPointerException e){
                    Log.e("null","null");
                }
            }

            @Override
            public void onFailure(Call<ResultSearchBuilding> call, Throwable t) {
                Log.w("????????????", "Raw: ${response.raw()}");
            }
        });
    }

    private void initByServiceType(){
        if(serviceType.equals(CallMapViewEnum.TownSetting)){

        }else{  //MakeMeeting type??? ???
            binding.mapButton.setText("?????? ??????");
        }
    }
    //kako map ?????? -> ?????? ?????? ?????????
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
                sentDialog("??? ????????? ???????????? ????????? ????????????.");
        }

    }

    void showTownSetDialog(String result) {
        final String forFinish = result;
        builder = new AlertDialog.Builder(MapViewActivity.this);
        View view = LayoutInflater.from(MapViewActivity.this).inflate(R.layout.dialog_town_check, null);
        TextView townText = view.findViewById(R.id.townCheckText);
        if(serviceType == CallMapViewEnum.TownSetting)
            townText.setText("?????? ????????? " + result + " ??????????");
        else {
            result = String.format("'%s'",result);
            if(result.length() > 10)    //?????????/????????? ????????? ?????? ??? ?????? ??? ??????, \n ?????? ?????? ?????? ???
                result = "\n" + result;
            townText.setText(String.format("?????? ????????? %s ??????????",result));
        }
        view.findViewById(R.id.mapResettingButton).setOnClickListener(v->{
            dialog.dismiss();
        });
        view.findViewById(R.id.mapSettingButton).setOnClickListener(v->{
            dialog.dismiss();
            if(serviceType == CallMapViewEnum.MakeMeeting){
                Intent intent = new Intent();
                intent.putExtra("placeResult", forFinish);
                setResult(RESULT_OK,intent);
                finish();
            }
        });
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
                case '???':
                case '???':
                case '???':
                    return String.format("'%s'???", address[i]);
                case '???':
                case '???':
                    return String.format("'%s'???", address[i]);
            }
        }
        return null;
    }

    @Override
    public void onReverseGeoCoderFailedToFindAddress(MapReverseGeoCoder mapReverseGeoCoder) {

    }

    void addMapView() {
        mapView = new MapView(this);
        //by tacking mode on, ??? ?????? ?????? ?????? ?????????
        mapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeading);

        binding.mapView.addView(mapView);

        mapView.setMapViewEventListener(this);
        mapView.setPOIItemEventListener(this);

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
                            //?????? ?????????, ?????? ?????? ??? ????????????
                            currentPoint =  MapPoint.mapPointWithGeoCoord(location.getLatitude(),location.getLongitude());
                        }
                    }
                });
    }

    void sentDialog(String message){
        builder = new AlertDialog.Builder(MapViewActivity.this);
        View view = LayoutInflater.from(getBaseContext()).inflate(R.layout.dialog_message,null);
        TextView messageView = view.findViewById(R.id.dialogInfoMessageText);
        messageView.setText(message);
        if(message.equals("??? ????????? ???????????? ????????? ????????????.")) {
            TextView button = view.findViewById(R.id.dialogOKButton);
            button.setText("?????????");
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
            //?????? ?????? ???????????? ??? ??????
            addMapView();
        } else if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {
            //?????? ?????? ????????? ????????? ??? ??????
           sentDialog("?????? ????????? ??????????????? ?????? ????????? ???????????????.");
        } else {
            //?????? ?????? ????????? ??????. ?????? callback ?????? ?????????
            requestPermissionLauncher.launch(
                    Manifest.permission.ACCESS_FINE_LOCATION);
        }
    }

    private final ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted)
                    addMapView();
                else
                    sentDialog("?????? ????????? ??????????????? ?????? ????????? ???????????????.");   //?????? ?????? ??????????????? ?????? -> sentDialog ?????????
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
        poiItem.setItemName("??? ??????");
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
