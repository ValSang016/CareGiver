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

public class kakaomap extends AppCompatActivity implements MapView.CurrentLocationEventListener, MapReverseGeoCoder.ReverseGeoCodingResultListener {

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
            mMapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeading);
            return;
        }

        ActivityCompat.requestPermissions(this, REQUIRED_PERMISSIONS, PERMISSIONS_REQUEST_CODE);
    }

    private void storeLocation() {
        if (isLocationUpdated) {
            LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
            if (locationManager != null && locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                final FirebaseUser user = mAuth.getCurrentUser();
                if (user != null) {
                    MapPoint currentLocation = mMapView.getMapCenterPoint();
                    double latitude = currentLocation.getMapPointGeoCoord().latitude;
                    double longitude = currentLocation.getMapPointGeoCoord().longitude;

                    Map<String, Object> locationData = new HashMap<>();
                    locationData.put("latitude", latitude);
                    locationData.put("longitude", longitude);

                    databaseReference.child("locations").child(user.getUid()).setValue(locationData)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(kakaomap.this, "위치 정보가 업로드되었습니다.", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(kakaomap.this, "위치 정보 업로드에 실패했습니다.", Toast.LENGTH_SHORT).show();
                                }
                            });
                } else {
                    Toast.makeText(kakaomap.this, "사용자 인증에 실패했습니다.", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(kakaomap.this, "GPS가 활성화되지 않았습니다.", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(kakaomap.this, "현재 위치를 업데이트 중입니다. 잠시 후에 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
        }
    }

    private void getSavedLocations() {
        final FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            DatabaseReference locationsRef = databaseReference.child("locations");
            locationsRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    // Iterate over each child node under "locations"
                    for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                        String userId = userSnapshot.getKey();
                        if (!userId.equals(user.getUid())) {
                            // Retrieve latitude and longitude values from the user's location data
                            Double latitude = userSnapshot.child("latitude").getValue(Double.class);
                            Double longitude = userSnapshot.child("longitude").getValue(Double.class);

                            if (latitude != null && longitude != null) {
                                // Create a marker for the user's location
                                MapPOIItem marker = new MapPOIItem();
                                marker.setItemName("User Location");
                                marker.setTag(0);
                                marker.setMapPoint(MapPoint.mapPointWithGeoCoord(latitude, longitude));
                                marker.setMarkerType(MapPOIItem.MarkerType.RedPin);

                                mMapView.addPOIItem(marker);
                                mMapView.selectPOIItem(marker, true);
                            }
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(kakaomap.this, "위치 정보를 가져오는 데 실패했습니다.", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(kakaomap.this, "사용자 인증에 실패했습니다.", Toast.LENGTH_SHORT).show();
        }
    }
}