package com.example.yurynikolaev.myapplication.views.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.yurynikolaev.myapplication.ListModel;
import com.example.yurynikolaev.myapplication.PlaceholderFragment;
import com.example.yurynikolaev.myapplication.views.adapters.InfoWindowAdapter;
import com.example.yurynikolaev.myapplication.R;
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


import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;


public class HomeFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener, View.OnClickListener {

    private MapView mMapView;
    private GoogleMap mMap;
    private LocationManager locationManager;
    private LocationListener locationListener;
    private double lat = 62, lon = 129.8;
    private Marker marker;
    private FirebaseDatabase database;
    private DatabaseReference reference;
    private Button btnActivate;
    public MediaPlayer mediaPlayer;
    public boolean btnStartActivated = false;
    public ArrayList<ListModel> list;
    //private InfoWindowAdapter markerInfoWindowAdapter;
    //private double latitude;
    //private double longitude;

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
            getFragmentManager().beginTransaction().add(R.id.fragment_container, new PlaceholderFragment()).commit();
        }

        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        btnActivate = rootView.findViewById(R.id.btnStart);
        btnActivate.setOnClickListener(this);

        mMapView = rootView.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);

        mMapView.onResume();

        locationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                if (location != null) {
                    lat = location.getLatitude();
                    lon = location.getLongitude();
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
    public void onMapReady(final GoogleMap mMap) {

        mMap.setMyLocationEnabled(true);

        database = FirebaseDatabase.getInstance();
        reference = database.getReference().child("items");
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    list = new ArrayList<ListModel>();
                    ListModel p = ds.getValue(ListModel.class);
                    list.add(p);
                    String name = ds.getKey();
                    Double latitude = dataSnapshot.child(name).child("latitude").getValue(Double.class);
                    Double longitude = dataSnapshot.child(name).child("longitude").getValue(Double.class);
                    LatLng locat = new LatLng(latitude, longitude);
                    String str = p.getName();
                    //String str2 = p.getAudioDesc();
                    for (int i = 1; i <= 100 - p.getName().length(); i++) {
                        str += " ";
                    }
                    /*for (int i = 1; i <= 250 - p.getAudioDesc().length(); i++) {
                        str2 += " ";
                    }*/
                    mMap.addMarker(new MarkerOptions().position(locat).title(str + p.getDesc()).snippet(p.getImageUrl()));
                    //mMap.addMarker(new MarkerOptions().position(locat).title(str + p.getDesc()).snippet(str2 + p.getImageUrl()));
                    InfoWindowAdapter markerInfoWindowAdapter = new InfoWindowAdapter(getContext());
                    mMap.setInfoWindowAdapter(markerInfoWindowAdapter);
                    if (btnStartActivated == true) {
                    }
                }
                //markerInfoWindowAdapter = new InfoWindowAdapter(getActivity(), list);
                //markerInfoWindowAdapter = new InfoWindowAdapter(getContext().getApplicationContext(), list);
                //mMap.setInfoWindowAdapter(markerInfoWindowAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        };
        reference.addListenerForSingleValueEvent(eventListener);

        /*LatLng marker1 = new LatLng(62.022, 129.711);
        LatLng marker2 = new LatLng(62.017, 129.64);*/
        //MarkerOptions markerOptions = new MarkerOptions();
        if (mLastKnownLocation != null) {
            cameraPosition = new CameraPosition.Builder().target(new LatLng(mLastKnownLocation.getLatitude(), mLastKnownLocation.getLongitude())).zoom(16).build();
        } else {
            cameraPosition = new CameraPosition.Builder().target(new LatLng(lat, lon)).zoom(16).build();
        }
        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        mMap.clear();
        //marker = mMap.addMarker(markerOptions);
        //marker.showInfoWindow();
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
        //Toast.makeText(getActivity(), "Info window clicked", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        if (btnStartActivated == false) {
            btnActivate.setBackgroundResource(R.drawable.activated);
            btnStartActivated = true;
            Toast toast = Toast.makeText(getContext().getApplicationContext(), "Звук включен", Toast.LENGTH_SHORT);
            toast.show();
        } else {
            btnActivate.setBackgroundResource(R.drawable.deactivated);
            btnStartActivated = false;
            Toast toast = Toast.makeText(getContext().getApplicationContext(), "Звук выключен", Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}
