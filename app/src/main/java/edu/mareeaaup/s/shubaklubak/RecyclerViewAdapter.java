package edu.mareeaaup.s.shubaklubak;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import edu.mareeaaup.s.shubaklubak.Model.Device;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private static final String TAG = "RecyclerViewAdapter";

    //for long click listener
    private onItemLongClickListener mListener;
    public interface onItemLongClickListener{
        void onItemLongClick(int position);
    }
    public void setOnItemLongClickListener(onItemLongClickListener listener){
        mListener = listener;
    }

    //for database
    private Context mContext;
    private DatabaseReference databaseReference;
    private List<Device> devices = new ArrayList<>();
    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("devices");
    private FirebaseAuth mFirebaseAuth = FirebaseAuth.getInstance();

    public RecyclerViewAdapter(Context context, List<Device> devices ){
        this.devices = devices;
        mContext = context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_deviseslist, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        Log.d(TAG, "onBindViewHolder: inside");
        viewHolder.deviceName.setText(devices.get(i).getName());
        viewHolder.status.setChecked(devices.get(i).getState());

        viewHolder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Clicked2: "+ devices.size());
                Toast.makeText(mContext, devices.get(i).getName(),Toast.LENGTH_SHORT).show();
            }
        });
        viewHolder.status.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                Device device = new Device();

                if(isChecked==Boolean.TRUE){
                    devices.get(i).setState(Boolean.TRUE);

                }else{
                    devices.get(i).setState(Boolean.FALSE);

                }

                // code to update the firebase
                device = devices.get(i);
                mDatabase.child(mFirebaseAuth.getUid()).child(devices.get(i).getKey()).setValue(device);

                Toast.makeText(mContext, devices.get(i).getName(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return (devices != null) ? devices.size():0;
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView deviceName;
        Switch status;
        LinearLayout parentLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            deviceName = itemView.findViewById(R.id.device_name);
            status =  itemView.findViewById(R.id.status);
            parentLayout = itemView.findViewById(R.id.parent_layout);

            //on click listener for card
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (mListener != null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            mListener.onItemLongClick(position);
                        }
                    }
                    return false;
                }
            });
        }
    }
}
