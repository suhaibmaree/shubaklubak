package edu.mareeaaup.s.shubaklubak;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private static final String TAG = "RecyclerViewAdapter";

    private ArrayList<String> mdevicesNames = new ArrayList<>();
    private ArrayList<Boolean> mdevicesStatus = new ArrayList<>();
    private Context mContext;
    public RecyclerViewAdapter(Context context, ArrayList<String> devicesNames, ArrayList<Boolean> devicesStatus){
        mdevicesNames = devicesNames;
        mdevicesStatus = devicesStatus;
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
        viewHolder.deviceName.setText(mdevicesNames.get(i));
        viewHolder.status.setChecked(mdevicesStatus.get(i));

        viewHolder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Clicked: "+ mdevicesNames.get(i));
                Toast.makeText(mContext, mdevicesNames.get(i),Toast.LENGTH_SHORT).show();
            }
        });
        viewHolder.status.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked==Boolean.TRUE){
                    mdevicesStatus.set(i,Boolean.TRUE);

                    //FirebaseDatabase database = FirebaseDatabase.getInstance();
                    //DatabaseReference myRef = database.getReference("message");

                    //myRef.setValue("Hello, World!");

                }else{
                    mdevicesStatus.set(i,Boolean.FALSE);
                }
                // code to update the firebase
                Toast.makeText(mContext, mdevicesStatus.get(i).toString(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mdevicesNames.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView deviceName;
        Switch status;
        RelativeLayout parentLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            deviceName = (TextView) itemView.findViewById(R.id.device_name);
            status = (Switch) itemView.findViewById(R.id.status);
            parentLayout = (RelativeLayout) itemView.findViewById(R.id.parent_layout);
        }
    }
}
