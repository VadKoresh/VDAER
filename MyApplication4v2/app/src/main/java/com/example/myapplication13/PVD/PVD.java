package com.example.myapplication13.PVD;

import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Scanner;

public class PVD implements Serializable {

    private String rtm;
    private String fullInfo;
    private String shemaKontrol;
    private int spiralCount;
    private ArrayList<Spiral> spirals;
    private String dateVvodVExp;

    public PVD(String rtm, String fullInfo, String shemaKontrol, String dateVvodVExp) {
        this.rtm = rtm;
        this.fullInfo = fullInfo;
        this.shemaKontrol = shemaKontrol;
        spirals = new ArrayList<>();
        this.dateVvodVExp = dateVvodVExp;
    }

    public String getRtm() {
        return rtm;
    }

    public int getSpiralCount() {
        return spiralCount;
    }

    public ArrayList<Spiral> getSpirals() {
        return spirals;
    }

    public String getFullInfo() {
        return fullInfo;
    }

    public String getShemaKontrol() {
        return shemaKontrol;
    }

    public String getDateVvodVExp() {
        return dateVvodVExp;
    }
}
