package com.gachon.caregiver;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class mapFragment extends Fragment {

    private GoogleMap googleMap;
    private LocationManager locationManager;
    private LocationListener locationListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Fragment에 연결된 XML 레이아웃 파일을 인플레이트합니다.
        View rootView = inflater.inflate(R.layout.fragment_map, container, false);

        // SupportMapFragment를 가져옵니다.
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map_fragment);
        mapFragment.getMapAsync(googleMap -> {
            this.googleMap = googleMap;

            // 위치 권한을 확인하고 요청합니다.
            if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                // 위치 권한이 허용된 경우
                enableMyLocation();
            } else {
                // 위치 권한을 요청하는 로직을 추가해야 합니다.
                requestLocationPermission();
            }
        });

        return rootView;
    }

    private void enableMyLocation() {
        // 사용자의 위치를 표시합니다.
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        googleMap.setMyLocationEnabled(true);

        // 위치 정보를 업데이트하는 리스너를 등록합니다.
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                // 사용자의 위치가 변경되었을 때 호출
                // 위치 변경에 따른 지도 업데이트 작업을 수행
                LatLng userLocation = new LatLng(location.getLatitude(), location.getLongitude());
                googleMap.moveCamera(CameraUpdateFactory.newLatLng(userLocation));

                // 상대방 위치를 표시
                LatLng friendLocation = new LatLng(location.getLatitude(), location.getLongitude()); // 사용자의 위치 좌표
                MarkerOptions friendMarkerOptions = new MarkerOptions()
                        .position(friendLocation)
                        .title("Friend's Location");
                googleMap.addMarker(friendMarkerOptions);

                // 반경 내의 특정 장소를 표시
                LatLng centerLocation = new LatLng(location.getLatitude(), location.getLongitude()); // 사용자의 위치 좌표를 원의 중심 위치 좌표로 설정
                float radius = 1000; // 반경(미터)
                CircleOptions circleOptions = new CircleOptions()
                        .center(centerLocation)
                        .radius(radius)
                        .strokeColor(Color.BLUE)
                        .fillColor(Color.argb(50, 0, 0, 255));
                googleMap.addCircle(circleOptions);
            }

            // onProviderEnabled(), onProviderDisabled() 등 다른 메서드도 구현
        };
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
    }

    private void requestLocationPermission() {
        // 위치 권한을 요청하는 로직을 추가
        // ActivityCompat.requestPermissions() 등을 사용하여 위치 권한을 요청합니다.
    }
}
