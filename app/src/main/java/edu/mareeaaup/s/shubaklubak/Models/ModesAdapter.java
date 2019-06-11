package edu.mareeaaup.s.shubaklubak.Models;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import edu.mareeaaup.s.shubaklubak.R;

public class ModesAdapter extends RecyclerView.Adapter<ModesAdapter.ViewHolder> {


    public ArrayList<Moode> modeList;
    public Context mContext;
    public int modecount;
    InsteadModeAdapter mAdapter;
    RecyclerView recyclerView;
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("Users");
    private FirebaseAuth mFirebaseAuth = FirebaseAuth.getInstance();

    public ModesAdapter(Context mContext, ArrayList<Moode> modeList) {
        this.modeList = modeList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.mode_card
                , viewGroup, false);
        ModesAdapter.ViewHolder holder = new ModesAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        modecount = 0;
        for (int j = 0; j < modeList.get(i).getDevices().size(); j++) {
            if (modeList.get(i).getDevices().get(j).getState() == Boolean.TRUE)
                modecount++;
        }
        viewHolder.modeName.setText(modeList.get(i).getName());
        viewHolder.status.setChecked(modeList.get(i).getState());
        viewHolder.modecounte.setText(modecount + " Active Devices");
        Log.d("GETF", "inside adapter " + modeList.size());
        viewHolder.status.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                Moode mode = new Moode();
                //update switch button
                if (isChecked == Boolean.TRUE) {
                    modeList.get(i).setState(Boolean.TRUE);
                    mode = modeList.get(i);
                    mDatabase.child(FirebaseAuth.getInstance().getUid()).child("modes")
                            .child(modeList.get(i).getKey()).setValue(mode);
                    //mode ON
                    for (int i = 0; i < mode.getDevices().size(); i++) {
                        mDatabase.child(FirebaseAuth.getInstance().getUid()).child("devices")
                                .child(mode.getDevices().get(i).getKey())
                                .setValue(mode.getDevices().get(i));
                    }//end for loop


                } else {
                    modeList.get(i).setState(Boolean.FALSE);
                    mode = modeList.get(i);
                    mDatabase.child(FirebaseAuth.getInstance().getUid()).child("modes")
                            .child(modeList.get(i).getKey()).setValue(mode);
                    //mode ON
                    for (int i = 0; i < mode.getDevices().size(); i++) {
                        String state;
                        if(mode.getDevices().get(i).getState() == Boolean.TRUE)
                            mode.getDevices().get(i).setState(Boolean.FALSE);


                        mDatabase.child(FirebaseAuth.getInstance().getUid()).child("devices")
                                .child(mode.getDevices().get(i).getKey())
                                .setValue(mode.getDevices().get(i));
                    }//end for loop

                }

                // code to update the firebase

            }
        });

        //build recycler view
        recyclerView = viewHolder.itemView.findViewById(R.id.insted_recyclerview_mode);
        mAdapter = new InsteadModeAdapter(viewHolder.itemView.getContext(), modeList.get(i).getDevices(), modeList.get(i));
        recyclerView.setAdapter(mAdapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(viewHolder.itemView.getContext());
        recyclerView.setLayoutManager(layoutManager);

        viewHolder.bind(modeList.get(i));

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean expanded = modeList.get(i).isExpanded();
                modeList.get(i).setExpanded(!expanded);
                notifyItemChanged(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return (modeList != null) ? modeList.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView modeName;
        TextView modecounte;
        Switch status;
        View subItem;
        ImageView cardImage;
        ImageView arrowUp;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            modeName = itemView.findViewById(R.id.card_name);
            status = itemView.findViewById(R.id.mode_switch);
            modecounte = itemView.findViewById(R.id.device_numbers);
            subItem = itemView.findViewById(R.id.sub_item);
            cardImage = itemView.findViewById(R.id.cover_image);
            arrowUp = itemView.findViewById(R.id.arrow_up);

        }

        private void bind(Moode moode) {
            boolean expanded = moode.isExpanded();
            subItem.setVisibility(expanded ? View.VISIBLE : View.GONE);
            modecounte.setVisibility(expanded ? View.GONE : View.VISIBLE);
            cardImage.setVisibility(expanded ? View.GONE : View.VISIBLE);
            arrowUp.setVisibility(expanded ? View.VISIBLE : View.GONE);

        }

    }
}
