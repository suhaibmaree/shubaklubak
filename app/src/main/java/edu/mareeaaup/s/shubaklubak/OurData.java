package edu.mareeaaup.s.shubaklubak;

import java.util.ArrayList;
import java.util.List;

public class OurData {

    public static List<String> mdevicesNames = new ArrayList<>();
    public static List<Boolean> mdevicesStatus = new ArrayList<>();


    public static List<String> getMdevicesNames() {
        return mdevicesNames;
    }

    public static List<Boolean> getMdevicesStatus() {
        return mdevicesStatus;
    }

    public static void setMdevices() {
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
