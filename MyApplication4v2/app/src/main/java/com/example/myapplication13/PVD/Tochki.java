package com.example.myapplication13.PVD;


public class Tochki {
    private float znachenie;
    private int numberTochki;

    public Tochki(int numberTochki, float znachenie){
        this.numberTochki = numberTochki;
        this.znachenie = znachenie;
    }

    public float getZnachenie() {
        return znachenie;
    }

    public void setZnachenie(float znachenie) {
        this.znachenie = znachenie;
    }

    public int getNumberTochki() {
        return numberTochki;
    }

}
