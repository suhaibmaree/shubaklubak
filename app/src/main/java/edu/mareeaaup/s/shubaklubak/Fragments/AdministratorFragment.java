package edu.mareeaaup.s.shubaklubak.Fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

import edu.mareeaaup.s.shubaklubak.Activitys.MainActivity;
import edu.mareeaaup.s.shubaklubak.Models.Device;
import edu.mareeaaup.s.shubaklubak.R;

public class AdministratorFragment extends Fragment {

    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("Users");
    private FirebaseAuth mFirebaseAuth = FirebaseAuth.getInstance();
    private Button mAddButton;
    private List<Device> mDevices;
    private DatabaseReference Database;
    private TextView activeDevices;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_administrator, container, false);

        //------------ fetching devices------------
        if (FirebaseAuth.getInstance().getUid() != null) {
            Database = FirebaseDatabase.getInstance().getReference().child("Users");
            mDatabase.child(FirebaseAuth.getInstance().getUid()).child("devices")
                    .addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            mDevices = new ArrayList<>();
                            Iterator<DataSnapshot> iterator = dataSnapshot.getChildren().iterator();
                            Log.d("getFireData", "before loop");

                            for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                mDevices.add(ds.getValue(Device.class));
                            }

                            activeDevices = view.findViewById(R.id.active_devices_text);
                            activeDevices.setText("You Have "+mDevices.size()+" Active Devices ");
                        }


                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

        }//------------------ End if -------------------

        mAddButton = view.findViewById(R.id.add_button);
        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //create Dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                View mView = getLayoutInflater().inflate(R.layout.layout_dialog, null);
                final EditText mDeviceName = mView.findViewById(R.id.dialog_device_name);
                final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("Users");
                //set ok and cancel button
                builder.setView(mView)
                        .setTitle("Apply for new device")
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                boolean deviceState = Boolean.FALSE;
                                final Device device = new Device();
                                device.setName(mDeviceName.getText().toString());
                                device.setState(deviceState);
                                device.setKey(mDatabase.push().getKey());
                                mDatabase.child(mFirebaseAuth.getUid()).child("devices").child(device.getKey()).setValue(device)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    mDatabase.child(mFirebaseAuth.getUid()).child("requests").child(device.getKey()).setValue(device);
                                                    Toast.makeText(getContext(), "success", Toast.LENGTH_SHORT).show();
                                                }
                                                else
                                                    Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();

            }//end onClick
        });


        return view;
    }//end onCreateView

}//end fragment class