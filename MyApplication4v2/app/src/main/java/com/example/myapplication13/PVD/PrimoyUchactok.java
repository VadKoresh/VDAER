package com.example.myapplication13.PVD;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.Scanner;

public class PrimoyUchactok{

    private String dateFormat = "dd.MM.yyyy";
    private double MIN_DOPYST_ZNACHENYA = 3;
    private int[] NUMBER_TOCHEK = {1, 2, 3, 4};
    private ArrayList<Tochki> tochkis = new ArrayList<Tochki>();
    private Date dateKontrol;


    public PrimoyUchactok(){
        dateKontrol = new Date();
    }

    public void setNumberTochek(){
        Scanner scanner = new Scanner(System.in);
        for (int i = 0; i < NUMBER_TOCHEK.length; i++) {
            System.out.println("Введите показания точки № " + NUMBER_TOCHEK[i]);
//            Tochki tochka = new Tochki(NUMBER_TOCHEK[i]);
//            tochka.setZnachenie(scanner.nextDouble());
//            tochkis.add(tochka);
        }
    }

    public Float getMinIzmerZnach(){
        return tochkis.stream().min(Comparator.comparing(Tochki::getZnachenie)).get().getZnachenie();
    }

    public int getNumberTochek(){
        return NUMBER_TOCHEK.length;
    }

    public Double getMinDopustZnach(){
        return MIN_DOPYST_ZNACHENYA;
    }

    public String getDateKontrol(){
        return new SimpleDateFormat(dateFormat).format(dateKontrol);
    }

    public String getOzenka(){
        if (getMinIzmerZnach() < MIN_DOPYST_ZNACHENYA){
            return "Неуд.";
        }
        return "Уд.";
    }

    public String getRezult(){
        return "\t| Минимально допустимое значение: " + getMinDopustZnach()
                + "\t| Минимальное измеренное значение: " + getMinIzmerZnach()
                + "\t| Оценка качества: " + getOzenka()
                + "\t| Дата контроля: " + getDateKontrol();
    }

    public String getAllZnacheniya(){
        String rezult = "";
        for (Tochki tochki : tochkis){
            rezult += tochki.toString() + "\t";
        }
        return rezult;
    }
}
