package edu.mareeaaup.s.shubaklubak.Fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import edu.mareeaaup.s.shubaklubak.Models.Device;
import edu.mareeaaup.s.shubaklubak.Models.ModesAdapter;
import edu.mareeaaup.s.shubaklubak.Models.Moode;
import edu.mareeaaup.s.shubaklubak.R;

public class ModesFragment extends Fragment {

    private DatabaseReference mDatabase;
    private FirebaseAuth mFirebaseAuth = FirebaseAuth.getInstance();
    private FloatingActionButton mFloatingActionButton;
    private RecyclerView recyclerView;
    private ModesAdapter mAdapter;
    private List<Device> mDevices = new ArrayList<>();
    private ArrayList<Moode> mModes = new ArrayList<>();
    private ProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_modes, container, false);
        progressBar = view.findViewById(R.id.progress_bar_modes);

        //-------------- Fitch data from firebase---------------
        if (FirebaseAuth.getInstance().getUid() != null) {
            mDatabase = FirebaseDatabase.getInstance().getReference().child("Users");

            //get devices array from firebase
            mDatabase.child(FirebaseAuth.getInstance().getUid()).child("devices")
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            Iterator<DataSnapshot> iterator = dataSnapshot.getChildren().iterator();
                            Log.d("getFireData", "before loop");

                            for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                mDevices.add(ds.getValue(Device.class));
                                Log.d("getFireData", "dev name " + ds.getValue(Device.class).getName());
                                Log.d("getFireData", "dev state " + ds.getValue(Device.class).getState());

                            }
                            Log.d("getFireData", "After loop");
                            Log.d("getFireData", "List size " + mDevices.size());
                        }


                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });


            //get modes array from Firebase
            mDatabase.child(FirebaseAuth.getInstance().getUid()).child("modes")
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            progressBar.setVisibility(View.VISIBLE);

                            for (DataSnapshot dsM : dataSnapshot.getChildren()) {
                                mModes.add(dsM.getValue(Moode.class));
                            }

                            //build recycler view
                            recyclerView = view.findViewById(R.id.recycler_view_modes);
                            mAdapter = new ModesAdapter(view.getContext(), mModes);
                            recyclerView.setAdapter(mAdapter);
                            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
                            recyclerView.setLayoutManager(layoutManager);
                            progressBar.setVisibility(View.INVISIBLE);
                            //end build recycler view

                        }//end onDataChange

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });//end listener



        }//-------------- end Fitch data from firebase---------------


        //Floating action button setup
        mFloatingActionButton = view.findViewById(R.id.moode_fab);
        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //create Dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                View mView = getLayoutInflater().inflate(R.layout.layout_dialog, null);
                final EditText mDeviceName = mView.findViewById(R.id.dialog_device_name);
                //set ok and cancel button
                builder.setView(mView)
                        .setTitle("Add Mode Name")
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {


                                String name = mDeviceName.getText().toString();
                                DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("Users");

                                Moode mode = new Moode();
                                mode.setName(name);
                                mode.setState(Boolean.FALSE);
                                mode.setKey(mDatabase.push().getKey());
                                mode.setDevices(mDevices);


                                mDatabase.child(mFirebaseAuth.getUid()).child("modes").child(mode.getKey()).setValue(mode)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful())
                                                    Toast.makeText(view.getContext(), "success", Toast.LENGTH_SHORT).show();
                                                else
                                                    Toast.makeText(view.getContext(), "Error", Toast.LENGTH_SHORT).show();
                                            }
                                        });


                                //refresh fragment
                                getFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                        new ModesFragment()).commit();

                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();

            }
        });//end floating button listener

        return view;
    }//end onCreateView

}