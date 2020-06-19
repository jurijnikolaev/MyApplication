package com.example.yurynikolaev.myapplication;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.MenuItem;

import com.example.yurynikolaev.myapplication.views.adapters.RecViewAdapter;
import com.example.yurynikolaev.myapplication.views.adapters.SoundAdapter;
import com.example.yurynikolaev.myapplication.views.fragments.FragmentTwo;
import com.example.yurynikolaev.myapplication.views.fragments.HomeFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "MainActivity";
    LocationManager locationManager;
    LocationListener locationListener;
    private double latitude = 0, longitude = 0;
    public ArrayList<ListModel> list;
    SoundAdapter soundAdapter;

    MediaPlayer mediaPlayer;

    HomeFragment homeFragment = new HomeFragment();
    FragmentTwo fragmentTwo = new FragmentTwo();

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Log.i(TAG, "onNavigationItemSelected: ");
        return true;
    }

    static class Item implements Serializable {
        public String name, desc;                       // название, описание объекта
        public double latitude, longitude;              // координаты

        public Item() {
        }

        Item(String name, String desc, double latitude, double longitude) {
            this.name = name;
            this.desc = desc;
            this.latitude = latitude;
            this.longitude = longitude;
        }
    }

    private FirebaseDatabase database;
    private DatabaseReference dbRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.INTERNET}
                        , 10);
            }
            return;
        }
        setContentView(R.layout.activity_main);
        database = FirebaseDatabase.getInstance();
        dbRef = database.getReference("items");
        //Query query = dbRef.orderByKey();

        soundAdapter = new SoundAdapter();

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
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
                Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(i);
            }
        };
        locationManager.requestLocationUpdates("gps", 0, 0, locationListener);
        locationManager.requestLocationUpdates("network", 0, 0, locationListener);

        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list = new ArrayList<ListModel>();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    ListModel p = ds.getValue(ListModel.class);
                    list.add(p);
                    String name = ds.getKey();
                    Double lat = dataSnapshot.child(name).child("latitude").getValue(Double.class);
                    Double lon = dataSnapshot.child(name).child("longitude").getValue(Double.class);
                    if (((latitude - lat) < 0.02) & ((longitude - lon) < 0.02)) {
                        String url = dataSnapshot.child(name).child("audioDesc").getValue(String.class);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        BottomNavigationView bnView = findViewById(R.id.navigation);
        bnView.setSelectedItemId(R.id.navigation_home);
        bnView.setOnNavigationItemSelectedListener(this);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
        bnView.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem menuItem) {
                // do nothing
            }
        });
        bnView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, homeFragment).addToBackStack(null).commit();
                        return true;
                    case R.id.navigation_dashboard:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragmentTwo).addToBackStack(null).commit();
                        return true;
                }
                return false;
            }
        });
    }

    /*private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
    }*/

    /*private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;
                    switch (item.getItemId()) {
                        case R.id.navigation_home:
                            selectedFragment = new HomeFragment();
                            break;
                        case R.id.navigation_dashboard:
                            selectedFragment = new FragmentTwo();
                            break;
                        case R.id.navigation_notifications:
                            selectedFragment = new FragmentThree();
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
                    return true;
                }
            };

    private BottomNavigationView.OnNavigationItemReselectedListener navL = new BottomNavigationView.OnNavigationItemReselectedListener() {
                @Override
                public void onNavigationItemReselected(@NonNull MenuItem item) {
                    // do nothing here
                }
        };*/
}
