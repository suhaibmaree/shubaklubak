package edu.mareeaaup.s.shubaklubak.Model;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import edu.mareeaaup.s.shubaklubak.R;

public class ModesAdapter extends RecyclerView.Adapter<ModesAdapter.ViewHolder>{


    public List<Moode> modeList;
    public Context mContext;
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child("modes");
    private FirebaseAuth mFirebaseAuth = FirebaseAuth.getInstance();

    public ModesAdapter(Context mContext, List<Moode> modeList) {
        this.modeList = modeList;
        this.mContext = mContext;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_deviseslist
                , viewGroup, false);
        ModesAdapter.ViewHolder holder = new ModesAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {

        viewHolder.modeName.setText(modeList.get(i).getName());
        viewHolder.status.setChecked(modeList.get(i).getState());

        viewHolder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, modeList.get(i).getName(),Toast.LENGTH_SHORT).show();
            }
        });
        viewHolder.status.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                //update switch button
                if(isChecked==Boolean.TRUE){
                    modeList.get(i).setState(Boolean.TRUE);
                }else{
                    modeList.get(i).setState(Boolean.FALSE);
                }

                // code to update the firebase
                Moode mode = new Moode();
                mode = modeList.get(i);
                mDatabase.child(modeList.get(i).getKey()).setValue(mode);
            }
        });
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView modeName;
        Switch status;
        LinearLayout parentLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            modeName = itemView.findViewById(R.id.device_name);
            status =  itemView.findViewById(R.id.status);
            parentLayout = itemView.findViewById(R.id.parent_layout);


        }
    }
}
