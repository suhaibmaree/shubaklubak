package edu.mareeaaup.s.shubaklubak;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import edu.mareeaaup.s.shubaklubak.Model.Device;

public class HomeFragment extends Fragment {

    private static final String TAG = "MainActivity";
    private List<Device> mDevices = new ArrayList<>();
    public RecyclerViewAdapter mAdapter;
    public RecyclerView recyclerView;
    private ProgressBar progressBar;
    private DatabaseReference mDatabase;
    private int flag =0;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home,container,false);

        progressBar = view.findViewById(R.id.progress_bar);

        if (FirebaseAuth.getInstance().getUid() !=null) {
            mDatabase = FirebaseDatabase.getInstance().getReference().child("devices");
            mDatabase.child(FirebaseAuth.getInstance().getUid())
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            progressBar.setVisibility(View.VISIBLE);
                            Iterator<DataSnapshot> iterator = dataSnapshot.getChildren().iterator();
                            Log.d("getFireData", "before loop");

                            for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                mDevices.add(ds.getValue(Device.class));
                                Log.d("getFireData", "dev name" + ds.getValue(Device.class).getName());
                                Log.d("getFireData", "dev state" + ds.getValue(Device.class).getState());

                            }

                            mAdapter.notifyDataSetChanged();
                            Log.d("getFireData", "After loop");
                            Log.d("getFireData", "List size " + mDevices.size());
                            progressBar.setVisibility(View.INVISIBLE);
                        }


                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

        }//end if

        Log.d("getFireData","recyclerView");

        //build recycler view
        recyclerView = view.findViewById(R.id.recycler_view);
        mAdapter = new RecyclerViewAdapter(getContext(), mDevices);
        recyclerView.setAdapter(mAdapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        //on long item click listener for recycler view card
        mAdapter.setOnItemLongClickListener(new RecyclerViewAdapter.onItemLongClickListener() {
            @Override
            public void onItemLongClick(int position) {
                Toast.makeText(getContext(),"state "+mDevices.get(position).getState(),Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

}
