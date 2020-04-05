package com.example.yurynikolaev.myapplication.views.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.yurynikolaev.myapplication.ListModel;
import com.example.yurynikolaev.myapplication.views.adapters.InfoWindowAdapter;
import com.example.yurynikolaev.myapplication.R;
import com.example.yurynikolaev.myapplication.views.adapters.RecViewAdapter;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.io.Serializable;
import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;


public class HomeFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener {

    private MapView mMapView;
    private GoogleMap mMap;
    private LocationManager locationManager;
    private LocationListener locationListener;
    private double latitude = 0, longitude = 0, lat = 0, lon = 0;
    private Marker marker;
    private FirebaseDatabase database;
    private DatabaseReference reference;
    public ArrayList<ListModel> list;

    public int itemCount = 0;

    private CameraPosition mCameraPosition;
    CameraPosition cameraPosition;
    private Location mLastKnownLocation;

    private static final String KEY_CAMERA_POSITION = "camera_position";
    private static final String KEY_LOCATION = "location";

    @SuppressLint("MissingPermission")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            mLastKnownLocation = savedInstanceState.getParcelable(KEY_LOCATION);
            mCameraPosition = savedInstanceState.getParcelable(KEY_CAMERA_POSITION);
        }

        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        database = FirebaseDatabase.getInstance();
        reference = database.getReference().child("items");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list = new ArrayList<ListModel>();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    ListModel p = dataSnapshot1.getValue(ListModel.class);
                    list.add(p);
                    itemCount++;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        mMapView = rootView.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);

        mMapView.onResume();

        locationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                if (location != null) {
                    latitude = location.getLatitude();
                    longitude = location.getLongitude();
                }
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            @Override
            public void onProviderEnabled(String provider) {
            }

            @Override
            public void onProviderDisabled(String provider) {
            }
        };
        locationManager.requestLocationUpdates("gps", 0, 0, locationListener);
        locationManager.requestLocationUpdates("network", 0, 0, locationListener);

        mMapView.getMapAsync(this);

        return rootView;
    }

    @Override
    public void onMapReady(GoogleMap mMap) {
        mMap.setMyLocationEnabled(true);

        //LatLng marker1 = new LatLng(62.022, 129.711);
        LatLng marker2 = new LatLng(62.017, 129.64);
        for (int i = 0; i < itemCount; i++) {
            mMap.addMarker(new MarkerOptions().position(new LatLng(list.get(i).getLatitude(), list.get(i).getLongitude())).title(list.get(i).getName()).snippet(list.get(i).getDesc()));
        }


        if (mLastKnownLocation != null) {
            cameraPosition = new CameraPosition.Builder().target(new LatLng(mLastKnownLocation.getLatitude(), mLastKnownLocation.getLongitude())).zoom(12).build();
        } else {
            cameraPosition = new CameraPosition.Builder().target(new LatLng(latitude, longitude)).zoom(12).build();
        }

        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        InfoWindowAdapter markerInfoWindowAdapter = new InfoWindowAdapter(getContext().getApplicationContext());
        mMap.setInfoWindowAdapter(markerInfoWindowAdapter);

        mMap.clear();
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(marker2);
        mMap.animateCamera(CameraUpdateFactory.newLatLng(marker2));
        marker = mMap.addMarker(markerOptions);
        // marker.showInfoWindow();

        mMap.setOnInfoWindowClickListener(this);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        if (mMap != null) {
            outState.putParcelable(KEY_CAMERA_POSITION, mMap.getCameraPosition());
            outState.putParcelable(KEY_LOCATION, mLastKnownLocation);
            super.onSaveInstanceState(outState);
        }
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        Toast.makeText(getActivity(), "Info window clicked", Toast.LENGTH_SHORT).show();
    }
}
