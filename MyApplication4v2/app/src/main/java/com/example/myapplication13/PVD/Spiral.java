package com.example.myapplication13.PVD;

import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.Scanner;

public class Spiral {

    private float[] floats;
    private String numberSpiral;
    private ArrayList<Tochki> allTochki = new ArrayList<Tochki>();
    private TextView myTest;
    private String minPrym = "3.0";
    private String minGib = "1.87";


    public Spiral(String numberSpiral, float[] floats, TextView myTest){
        this.numberSpiral = numberSpiral;
        this.floats = floats;
        this.myTest = myTest;
        myTest.setText("я в конструкторе");
        setTochkisZonuKonds();
    }

    private void setTochkisZonuKonds(){
        for (int i = 0; i < floats.length; i++) {
            allTochki.add(new Tochki(i+1, floats[i]));
        }
    }

    public String getNumberSpiral() {
        return numberSpiral;
    }

    public ArrayList<Tochki> getAllTochki() {
        return allTochki;
    }

    public String getMinPrym() {
        return minPrym;
    }

    public String getMinGib() {
        return minGib;
    }

    public float getMinPrimUch(){
        ArrayList<Tochki> minPrimUch = new ArrayList<>();
        for (Tochki tochki : allTochki){
            if (tochki.getNumberTochki() < 5){
                minPrimUch.add(tochki);
            }
        }
        float min = minPrimUch.stream()
                .min(Comparator.comparing(Tochki::getZnachenie))
                .get()
                .getZnachenie();
        return min;
    }

    public float getMinZonKond(){
        ArrayList<Tochki> minZonKond = new ArrayList<>();
        for (Tochki tochki : allTochki){
            if (tochki.getNumberTochki() > 4){
                minZonKond.add(tochki);
            }
        }

        float min = minZonKond.stream()
                .min(Comparator.comparing(Tochki::getZnachenie))
                .get()
                .getZnachenie();
        return min;
    }
}
