package com.example.yurynikolaev.myapplication.views.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yurynikolaev.myapplication.R;
import com.example.yurynikolaev.myapplication.views.fragments.HomeFragment;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

public class InfoWindowAdapter implements GoogleMap.InfoWindowAdapter {
    private Context context;

    private HomeFragment homeFragment;

    public InfoWindowAdapter(Context context) {
        this.context = context.getApplicationContext();
    }

    public View getInfoWindow(Marker marker) {
        return null;
    }

    public View getInfoContents(Marker marker) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v =  inflater.inflate(R.layout.custom_info_window, null);

        TextView title = v.findViewById(R.id.titleViewWindow);
        TextView description = v.findViewById(R.id.descViewWindow);
        ImageView image = v.findViewById(R.id.imgViewWindow);
        for (int i = 0; i < homeFragment.itemCount; i++) {
            title.setText(homeFragment.list.get(i).getName());
            description.setText(homeFragment.list.get(i).getDesc());
        }
        image.setImageResource(R.drawable.ic_launcher_background);
        return v;
    }
}
