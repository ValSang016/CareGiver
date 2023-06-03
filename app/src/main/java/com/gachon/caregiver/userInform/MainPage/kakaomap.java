package com.gachon.caregiver.userInform.MainPage;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.gachon.caregiver.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.ValueEventListener;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapReverseGeoCoder;
import net.daum.mf.map.api.MapView;

import java.util.HashMap;
import java.util.Map;

public class kakaomap extends AppCompatActivity implements MapView.CurrentLocationEventListener, MapReverseGeoCoder.ReverseGeoCodingResultListener, MapView.POIItemEventListener {

    private MapView mMapView;
    private String[] REQUIRED_PERMISSIONS = {Manifest.permission.ACCESS_FINE_LOCATION};

    private static final int GPS_ENABLE_REQUEST_CODE = 2001;
    private static final int PERMISSIONS_REQUEST_CODE = 100;

    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;

    private boolean isLocationUpdated = false;

    private boolean checkLocationServicesStatus() {
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        return locationManager != null &&
                locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) &&
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map);

        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        mMapView = findViewById(R.id.map_View);
        mMapView.setCurrentLocationEventListener(this);
        mMapView.setPOIItemEventListener(this);

        if (!checkLocationServicesStatus()) {
            showDialogForLocationServiceSetting();
        } else {
            checkRunTimePermission();
        }

        Button saveLocationButton = findViewById(R.id.save_location_button);
        saveLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                storeLocation();
            }
        });

        getSavedLocations();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOff);
        mMapView.setShowCurrentLocationMarker(false);
    }

    @Override
    public void onCurrentLocationUpdate(MapView mapView, MapPoint currentLocation, float accuracyInMeters) {
        isLocationUpdated = true;
    }

    @Override
    public void onCurrentLocationDeviceHeadingUpdate(MapView mapView, float v) {
        // Empty implementation
    }

    @Override
    public void onCurrentLocationUpdateFailed(MapView mapView) {
        // Empty implementation
    }

    @Override
    public void onCurrentLocationUpdateCancelled(MapView mapView) {
        // Empty implementation
    }

    @Override
    public void onReverseGeoCoderFoundAddress(MapReverseGeoCoder mapReverseGeoCoder, String s) {
        // Empty implementation
    }

    @Override
    public void onReverseGeoCoderFailedToFindAddress(MapReverseGeoCoder mapReverseGeoCoder) {
        // Empty implementation
    }

    private void showDialogForLocationServiceSetting() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("위치 서비스 비활성화");
        builder.setMessage("앱을 사용하기 위해서는 위치 서비스가 필요합니다.\n위치 설정을 수정하시겠습니까?");
        builder.setCancelable(true);
        builder.setPositiveButton("설정", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivityForResult(intent, GPS_ENABLE_REQUEST_CODE);
            }
        });
        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                finish();
            }
        });
        builder.create().show();
    }

    private void checkRunTimePermission() {
        int hasFineLocationPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);

        if (hasFineLocationPermission == PackageManager.PERMISSION_GRANTED) {
            mMapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOnWithHeading);
            mMapView.setShowCurrentLocationMarker(true);
        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, REQUIRED_PERMISSIONS[0])) {
                Toast.makeText(this, "위치 접근 권한이 필요합니다.", Toast.LENGTH_SHORT).show();
                ActivityCompat.requestPermissions(this, REQUIRED_PERMISSIONS, PERMISSIONS_REQUEST_CODE);
            } else {
                ActivityCompat.requestPermissions(this, REQUIRED_PERMISSIONS, PERMISSIONS_REQUEST_CODE);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSIONS_REQUEST_CODE && grantResults.length == REQUIRED_PERMISSIONS.length) {
            boolean isAllGranted = true;

            for (int result : grantResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    isAllGranted = false;
                    break;
                }
            }

            if (isAllGranted) {
                mMapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOnWithHeading);
                mMapView.setShowCurrentLocationMarker(true);
            } else {
                Toast.makeText(this, "위치 접근 권한을 허용해야 합니다.", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    private void storeLocation() {
        if (!isLocationUpdated) {
            Toast.makeText(this, "현재 위치를 가져올 수 없습니다.", Toast.LENGTH_SHORT).show();
            return;
        }

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null) {
            Toast.makeText(this, "사용자 인증에 실패했습니다.", Toast.LENGTH_SHORT).show();
            return;
        }

        String userId = currentUser.getUid();
        String userName = currentUser.getDisplayName();
        MapPoint currentLocation = mMapView.getMapCenterPoint();

        MapPOIItem userMarker = new MapPOIItem();
        userMarker.setItemName(userName);
        userMarker.setTag(0);
        userMarker.setMapPoint(currentLocation);
        userMarker.setMarkerType(MapPOIItem.MarkerType.CustomImage);
        userMarker.setCustomImageResourceId(R.drawable.ic_marker);
        userMarker.setCustomImageAutoscale(false);
        userMarker.setCustomImageAnchor(0.5f, 1.0f);
        userMarker.setUserObject(userId);

        mMapView.addPOIItem(userMarker);
        mMapView.selectPOIItem(userMarker, true);
    }

    private void getSavedLocations() {
        DatabaseReference locationsRef = databaseReference.child("locations");
        locationsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mMapView.removeAllPOIItems();

                for (DataSnapshot locationSnapshot : snapshot.getChildren()) {
                    String userId = locationSnapshot.getKey();
                    double latitude = locationSnapshot.child("latitude").getValue(Double.class);
                    double longitude = locationSnapshot.child("longitude").getValue(Double.class);

                    MapPoint location = MapPoint.mapPointWithGeoCoord(latitude, longitude);

                    MapPOIItem marker = new MapPOIItem();
                    marker.setItemName(userId);
                    marker.setTag(1);
                    marker.setMapPoint(location);
                    marker.setMarkerType(MapPOIItem.MarkerType.RedPin);
                    marker.setSelectedMarkerType(MapPOIItem.MarkerType.BluePin);

                    mMapView.addPOIItem(marker);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Empty implementation
            }
        });
    }

    @Override
    public void onPOIItemSelected(MapView mapView, MapPOIItem mapPOIItem) {
        // Empty implementation
    }

    @Override
    public void onCalloutBalloonOfPOIItemTouched(MapView mapView, MapPOIItem mapPOIItem) {
        String userId = (String) mapPOIItem.getUserObject();

        // Pass the UID to the mapcomp_info activity
        Intent intent = new Intent(kakaomap.this, mapcomp_info.class);
        intent.putExtra("id", userId);
        startActivity(intent);
    }


    @Override
    public void onCalloutBalloonOfPOIItemTouched(MapView mapView, MapPOIItem mapPOIItem, MapPOIItem.CalloutBalloonButtonType calloutBalloonButtonType) {
        // Empty implementation
    }

    @Override
    public void onDraggablePOIItemMoved(MapView mapView, MapPOIItem mapPOIItem, MapPoint mapPoint) {
        // Empty implementation
    }
}
