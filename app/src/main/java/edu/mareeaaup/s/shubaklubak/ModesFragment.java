package edu.mareeaaup.s.shubaklubak;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import edu.mareeaaup.s.shubaklubak.Model.Device;

public class ModesFragment extends Fragment {

    private EditText name,state;
    private DatabaseReference mDatabase;
    private FirebaseAuth mFirebaseAuth;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         View view = inflater.inflate(R.layout.fragment_modes,container,false);

         name = view.findViewById(R.id.dev_name);
         state = view.findViewById(R.id.dev_state);
         mDatabase = FirebaseDatabase.getInstance().getReference().child("devices");
         mDatabase.keepSynced(true);
         mFirebaseAuth = FirebaseAuth.getInstance();



        return view;
    }

    public void add(View v) {
        String deviceName = name.getText().toString();
        boolean deviceState;
        if(state.getText().toString().equals("1"))
            deviceState = Boolean.TRUE;
        else
            deviceState = Boolean.FALSE;

        Device device = new Device();
        device.setName(deviceName);
        device.setState(deviceState);
        mDatabase.child(mFirebaseAuth.getUid()).child("testnane").push().setValue(device)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())
                            Toast.makeText(getContext(), "success", Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(getContext(),"Error", Toast.LENGTH_SHORT).show();
                    }
                });


    }

}
