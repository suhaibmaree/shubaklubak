package edu.mareeaaup.s.shubaklubak.Fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import java.util.List;

import edu.mareeaaup.s.shubaklubak.Model.Device;
import edu.mareeaaup.s.shubaklubak.Model.ModesAdapter;
import edu.mareeaaup.s.shubaklubak.Model.Moode;
import edu.mareeaaup.s.shubaklubak.R;

public class ModesFragment extends Fragment {

    private DatabaseReference mDatabase;
    private FirebaseAuth mFirebaseAuth= FirebaseAuth.getInstance();
    private FloatingActionButton mFloatingActionButton;
    private RecyclerView recyclerView;
    private ModesAdapter mAdapter;
    private List<Moode> modeList;
    private ProgressBar progressBar;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_modes,container,false);
        progressBar = view.findViewById(R.id.progress_bar_modes);

//        if (FirebaseAuth.getInstance().getUid() !=null) {
//            mDatabase = FirebaseDatabase.getInstance().getReference().child("Users");
//            mDatabase.child(FirebaseAuth.getInstance().getUid()).child("modes")
//                    .addListenerForSingleValueEvent(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                            progressBar.setVisibility(View.VISIBLE);
//                            Iterator<DataSnapshot> iterator = dataSnapshot.getChildren().iterator();
//                            Log.d("getFireDatamode", "before loop");
//
//                            for (DataSnapshot ds : dataSnapshot.getChildren()) {
//                                modeList.add(ds.getValue(Moode.class));
//                                Log.d("getFireDatamode", "dev name" + ds.getValue(Device.class).getName());
//                                Log.d("getFireDatamode", "dev state" + ds.getValue(Device.class).getState());
//
//                            }
//
//                            mAdapter.notifyDataSetChanged();
//                            Log.d("getFireDatamode", "After loop");
//                            Log.d("getFireDatamode", "List size " + modeList.size());
//                            progressBar.setVisibility(View.INVISIBLE);
//                        }
//
//
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                        }
//                    });
//
//        }//end if

        mFloatingActionButton = view.findViewById(R.id.moode_fab);
        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //create Dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                View mView = getLayoutInflater().inflate(R.layout.layout_dialog,null);
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

                                //refresh fragment
                                getFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                        new ModesFragment()).commit();

                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();

            }
        });

        //build mode recycler view
        recyclerView = view.findViewById(R.id.recycler_view_modes);
        mAdapter = new ModesAdapter(getContext(), modeList);
        recyclerView.setAdapter(mAdapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        return view;
    }

}