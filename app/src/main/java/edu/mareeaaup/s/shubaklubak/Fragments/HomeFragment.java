package edu.mareeaaup.s.shubaklubak.Fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import edu.mareeaaup.s.shubaklubak.Models.Device;
import edu.mareeaaup.s.shubaklubak.R;
import edu.mareeaaup.s.shubaklubak.Models.RecyclerViewAdapter;

public class HomeFragment extends Fragment {

    private static final String TAG = "MainActivity";
    private List<Device> mDevices;
    public RecyclerViewAdapter mAdapter;
    public RecyclerView recyclerView;
    private ProgressBar progressBar;
    private DatabaseReference mDatabase;
    private FirebaseAuth mFirebaseAuth = FirebaseAuth.getInstance();
    private int flag = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_home, container, false);

        progressBar = view.findViewById(R.id.progress_bar);

        if (FirebaseAuth.getInstance().getUid() != null) {
            mDatabase = FirebaseDatabase.getInstance().getReference().child("Users");
            mDatabase.child(FirebaseAuth.getInstance().getUid()).child("devices")
                    .addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            mDevices = new ArrayList<>();
                            progressBar.setVisibility(View.VISIBLE);
                            Iterator<DataSnapshot> iterator = dataSnapshot.getChildren().iterator();
                            Log.d("getFireData", "before loop");

                            for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                mDevices.add(ds.getValue(Device.class));
                                Log.d("getFireData", "dev name " + ds.getValue(Device.class).getName());
                                Log.d("getFireData", "dev state " + ds.getValue(Device.class).getState());

                            }
                            mAdapter.setDevices(mDevices);
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


        Log.d("getFireData", "recyclerView");

        //build recycler view
        recyclerView = view.findViewById(R.id.recycler_view);
        mAdapter = new RecyclerViewAdapter(getContext(), mDevices);
        recyclerView.setAdapter(mAdapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        //long item click listener for recycler view card
        mAdapter.setOnItemLongClickListener(new RecyclerViewAdapter.onItemLongClickListener() {
            @Override
            public void onItemLongClick(final int position) {

                //create Dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                View mView = getLayoutInflater().inflate(R.layout.layout_dialog, null);
                final EditText mDeviceName = mView.findViewById(R.id.dialog_device_name);
                //set ok and cancel button
                builder.setView(mView)
                        .setTitle("Edit Device Name")
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String deviceName = mDeviceName.getText().toString();
                                mDevices.get(position).setName(deviceName);
                                Device device = mDevices.get(position);
                                mDatabase.child(mFirebaseAuth.getUid()).child("devices").child(mDevices.get(position).getKey()).setValue(device);

                                //refresh fragment
                                getFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                        new HomeFragment()).commit();

                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();


            }
        });


        //for value changed listener


        return view;
    }//end onCreate
}
