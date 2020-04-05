package com.example.yurynikolaev.myapplication.views.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yurynikolaev.myapplication.ListModel;
import com.example.yurynikolaev.myapplication.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecViewAdapter extends RecyclerView.Adapter<RecViewAdapter.ListViewHolder> {

    Context context;
    ArrayList<ListModel> items;

    public RecViewAdapter(Context c, ArrayList<ListModel> i) {
        context = c;
        items = i;
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ListViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.view_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final ListViewHolder holder, int position) {
        holder.textName.setText(items.get(position).getName());
        holder.textDesc.setText(items.get(position).getDesc());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ListViewHolder extends RecyclerView.ViewHolder {

        TextView textName, textDesc;
        ImageView objImage;

        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            textName = itemView.findViewById(R.id.nameView);
            textDesc = itemView.findViewById(R.id.descViewWindow);
            objImage = itemView.findViewById(R.id.imageView);
        }
    }
}
