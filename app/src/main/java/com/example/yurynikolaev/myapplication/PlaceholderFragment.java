package com.example.yurynikolaev.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.location.Geofence;

public class PlaceholderFragment extends Fragment {

    public PlaceholderFragment() {
    }

    private EditText mLatitudeView;
    private EditText mLongitudeView;
    private EditText mRadiusView;

    private int mId = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        mLatitudeView = (EditText) rootView.findViewById(R.id.edit_latitude);
        mLongitudeView = (EditText) rootView.findViewById(R.id.edit_longitude);
        mRadiusView = (EditText) rootView.findViewById(R.id.edit_radius);

        if (BuildConfig.DEBUG) {
            mLatitudeView.setText("48.01478856");
            mLongitudeView.setText("37.80279636");
            mRadiusView.setText("150");
        }

        Button enterButton = (Button) rootView.findViewById(R.id.set_geofence_enter);
        Button exitButton = (Button) rootView.findViewById(R.id.set_geofence_exit);

        enterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendGeo(Geofence.GEOFENCE_TRANSITION_ENTER);
            }
        });
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendGeo(Geofence.GEOFENCE_TRANSITION_EXIT);
            }
        });

        return rootView;
    }


    private void sendGeo(int transitionType) {FragmentActivity activity = getActivity();

        double latitude = Double.parseDouble(mLatitudeView.getText().toString());
        double longitude = Double.parseDouble(mLongitudeView.getText().toString());
        float radius = Float.parseFloat(mRadiusView.getText().toString());


        MyGeofence myGeofence = new MyGeofence(mId, latitude, longitude, radius, transitionType);

        Intent geofencingService = new Intent(activity, GeofencingService.class);
        geofencingService.setAction(String.valueOf(Math.random()));
        geofencingService.putExtra(GeofencingService.EXTRA_ACTION, GeofencingService.Action.ADD);
        geofencingService.putExtra(GeofencingService.EXTRA_GEOFENCE, myGeofence);

        activity.startService(geofencingService);

        mId++;
    }
}