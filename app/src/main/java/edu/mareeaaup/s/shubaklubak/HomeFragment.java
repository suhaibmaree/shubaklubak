package edu.mareeaaup.s.shubaklubak;

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

import java.util.ArrayList;
import java.util.zip.Inflater;

public class HomeFragment extends Fragment {

    private static final String TAG = "MainActivity";
    public ArrayList<String> mdevicesNames = new ArrayList<>();
    public ArrayList<Boolean> mdevicesStatus = new ArrayList<>();
    public RecyclerViewAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home,container,false);

        initiatItems();
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        adapter = new RecyclerViewAdapter(getContext(), mdevicesNames, mdevicesStatus);
        recyclerView.setAdapter(adapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        return view;
    }


    public void initiatItems(){
        Log.d(TAG,"Initiating Items");
        mdevicesNames.add("Fan1");
        mdevicesNames.add("Fan2");
        mdevicesNames.add("Fan3");
        mdevicesNames.add("Fan4");
        mdevicesNames.add("Fan5");
        mdevicesNames.add("Fan6");
        mdevicesNames.add("Fan7");
        mdevicesNames.add("Fan8");
        mdevicesNames.add("Fan9");
        mdevicesNames.add("Fan10");
        mdevicesNames.add("Fan11");
        mdevicesNames.add("Fan12");
        mdevicesStatus.add(Boolean.TRUE);
        mdevicesStatus.add(Boolean.FALSE);
        mdevicesStatus.add(Boolean.TRUE);
        mdevicesStatus.add(Boolean.FALSE);
        mdevicesStatus.add(Boolean.TRUE);
        mdevicesStatus.add(Boolean.FALSE);
        mdevicesStatus.add(Boolean.TRUE);
        mdevicesStatus.add(Boolean.FALSE);
        mdevicesStatus.add(Boolean.TRUE);
        mdevicesStatus.add(Boolean.FALSE);
        mdevicesStatus.add(Boolean.TRUE);
        mdevicesStatus.add(Boolean.FALSE);
    }


}
