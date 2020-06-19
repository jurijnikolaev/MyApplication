package com.example.yurynikolaev.myapplication.views.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
/*import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;*/
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yurynikolaev.myapplication.ListModel;
import com.example.yurynikolaev.myapplication.R;
import com.example.yurynikolaev.myapplication.views.fragments.FragmentTwo;
import com.example.yurynikolaev.myapplication.views.fragments.HomeFragment;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.squareup.picasso.Picasso;

/*import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;*/

//import static android.content.Context.LOCATION_SERVICE;

public class InfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

    Context context;
    /*HomeFragment homeFragment;
    LocationManager locationManager;
    LocationListener locationListener;
    private double latitude = 0;
    private double longitude = 0;*/

    public InfoWindowAdapter(Context c) {
        this.context = c.getApplicationContext();
    }

    public View getInfoWindow(Marker marker) {
        return null;
    }

    @SuppressLint("MissingPermission")
    public View getInfoContents(final Marker marker) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.custom_info_window, null);
        /*String audioUrl = "";
        audioUrl = marker.getSnippet().substring(0, 250);
        audioUrl.replaceAll(" ", "");
        locationManager = (LocationManager) context.getApplicationContext().getSystemService(LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                if (location != null) {
                    latitude = location.getLatitude();
                    longitude = location.getLongitude();
                }
                LatLng latLng = marker.getPosition();
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
        MediaPlayer mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
            mediaPlayer.setDataSource(audioUrl);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (homeFragment.btnStartActivated == true) {
            mediaPlayer.start();
        }*/
        TextView title = v.findViewById(R.id.titleViewWindow);
        TextView description = v.findViewById(R.id.descViewWindow);
        ImageView image = v.findViewById(R.id.imgViewWindow);
        title.setText(marker.getTitle().substring(0, 100));
        description.setText(marker.getTitle().substring(100));
        Picasso.get().load(marker.getSnippet()).into(image);
        //Picasso.get().load(marker.getSnippet().substring(250)).into(image);
        return v;
    }
}
