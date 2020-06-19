package com.example.yurynikolaev.myapplication.views.fragments;

        import android.os.Bundle;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;

        import com.example.yurynikolaev.myapplication.ListModel;
        import com.example.yurynikolaev.myapplication.R;
        import com.example.yurynikolaev.myapplication.views.adapters.RecViewAdapter;
        import com.google.firebase.database.DataSnapshot;
        import com.google.firebase.database.DatabaseError;
        import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.FirebaseDatabase;
        import com.google.firebase.database.ValueEventListener;

        import java.io.Serializable;
        import java.util.ArrayList;

        import androidx.annotation.NonNull;
        import androidx.annotation.Nullable;
        import androidx.fragment.app.Fragment;
        import androidx.recyclerview.widget.LinearLayoutManager;
        import androidx.recyclerview.widget.RecyclerView;

public class FragmentTwo extends Fragment {

    private RecyclerView recyclerView;
    public ArrayList<ListModel> list;
    private RecViewAdapter adapter;
    private FirebaseDatabase database;
    private DatabaseReference reference;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_two, container, false);

        database = FirebaseDatabase.getInstance();
        reference = database.getReference().child("items");

        recyclerView = rootView.findViewById(R.id.obj_list);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerView.setLayoutManager(llm);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list = new ArrayList<ListModel>();
                for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()) {
                    ListModel p = dataSnapshot1.getValue(ListModel.class);
                    list.add(p);
                }
                adapter = new RecViewAdapter(getActivity(), list);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return rootView;
    }
}
