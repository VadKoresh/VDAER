package com.example.myapplication13.PVD;

import android.content.res.AssetManager;

import com.example.myapplication13.Intrrface.EKI;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Comparator;

public class OtchetPVD implements EKI {

    private File mFile;
    private PVD pvd;
    private AssetManager assetManager;
    private LocalDate localDate;
    private String tempPoverx;
    private String tempSred;
    private String numbGurn;


    public OtchetPVD(PVD pvd, AssetManager assetManager, File mFile, String tempPoverx
        , String tempSred, String numbGurn) {
        this.pvd = pvd;
        this.assetManager = assetManager;
        this.mFile = mFile;
        this.tempPoverx = tempPoverx;
        this.tempSred = tempSred;
        this.numbGurn = numbGurn;
        localDate = LocalDate.now();

    }

    @Override
    public void run() {
        try {
            FileOutputStream fos = new FileOutputStream(mFile);

            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));

            InputStream stream = assetManager.open("pvdDocx/begin1");
            BufferedReader begBR = new BufferedReader(new InputStreamReader(stream));
            String str = "";
            int stroka = 0;
            while ((str = begBR.readLine()) != null) {
                stroka++;
                if (stroka == 280) {
                    str = str.replaceFirst("Уха", pvd.getFullInfo());
                } else if (stroka == 359) {
                    str = str.replaceFirst("Уха", pvd.getShemaKontrol());
                } else if (stroka == 907) {
                    str = str.replaceFirst("Уха", tempPoverx.substring(25));
                } else if (stroka == 965) {
                    str = str.replaceFirst("Уха", tempSred.substring(19));
                }
                bw.write(str);
                bw.newLine();
            }
            begBR.close();
            stream.close();
//=======================================================================================================================
            stream = assetManager.open("pvdDocx/tableResultBegin1.5");
            BufferedReader poltoraBR = new BufferedReader(new InputStreamReader(stream));
            stroka = 0;
            while ((str = poltoraBR.readLine()) != null) {
                stroka++;
                if (stroka == 187) {
                    str = str.replaceFirst("Уха", numbGurn);
                }else if (stroka == 246){
                    String mounth = localDate.getDayOfMonth() < 10
                        ? String.valueOf(localDate.getMonthValue())
                        : "0" + localDate.getMonthValue();
                    str = str.replaceFirst("уха", localDate.getDayOfMonth()
                        + "." + mounth + "." + localDate.getYear());
                }
                bw.write(str);
                bw.newLine();
            }
            poltoraBR.close();
            stream.close();
//=======================================================================================================================
            for (Spiral spiral : pvd.getSpirals()) {
                stream = assetManager.open("pvdDocx/tableResultKond2");
                BufferedReader twoBR = new BufferedReader(new InputStreamReader(stream));
                stroka = 0;
                while ((str = twoBR.readLine()) != null) {
                    stroka++;
                    if (stroka == 51) {
                        str = str.replaceFirst("Уха", spiral.getNumberSpiral());
                    }else if (stroka == 206) {
                        str = str.replaceFirst("Уха", String.valueOf(spiral.getMinZonKond()));
                    }else if (stroka == 265) {
                        str = str.replaceFirst("Уха", getLymdavSkobax(spiral));
                    }else if (stroka == 295) {
                        str = str.replaceFirst("Уха", getOzenka(spiral));
                    }
                    bw.write(str);
                    bw.newLine();
                }
                twoBR.close();
                stream.close();
            }
//=======================================================================================================================
            for (Spiral spiral : pvd.getSpirals()) {
                stream = assetManager.open("pvdDocx/tableResultPrim3");
                BufferedReader threeBR = new BufferedReader(new InputStreamReader(stream));
                stroka = 0;
                while ((str = threeBR.readLine()) != null) {
                    stroka++;
                    if (stroka == 60) {
                        str = str.replaceFirst("Уха", spiral.getNumberSpiral());
                    }else if (stroka == 226) {
                        str = str.replaceFirst("Уха", String.valueOf(spiral.getMinPrimUch()));
                    }else if (stroka == 285) {
                        str = str.replaceFirst("Уха", getLymdavSkobax4PrymUch(spiral));
                    }else if (stroka == 315) {
                        str = str.replaceFirst("Уха", getOzenka4PrymUch(spiral));
                    }
                    bw.write(str);
                    bw.newLine();
                }
                threeBR.close();
                stream.close();
            }
//=======================================================================================================================
            stream = assetManager.open("pvdDocx/tableResultEnd3.5");
            BufferedReader threeSPolovinBR = new BufferedReader(new InputStreamReader(stream));
            while ((str = threeSPolovinBR.readLine()) != null) {
                bw.write(str);
                bw.newLine();
            }
            threeSPolovinBR.close();
            stream.close();
//=======================================================================================================================
            for (Spiral spiral : pvd.getSpirals()) {
                stream = assetManager.open("pvdDocx/tableKartPrim4");
                BufferedReader foBR = new BufferedReader(new InputStreamReader(stream));
                stroka = 0;
                while ((str = foBR.readLine()) != null) {
                    stroka++;
                    if (stroka == 152){
                        str = str.replaceFirst("Уха", spiral.getNumberSpiral());
                    }else if (stroka == 723){
                        str = str.replaceFirst("Уха",
                            String.valueOf(spiral.getAllTochki().get(0).getZnachenie()));
                    }else if (stroka == 752){
                        str = str.replaceFirst("Уха",
                            String.valueOf(spiral.getAllTochki().get(1).getZnachenie()));
                    }else if (stroka == 781){
                        str = str.replaceFirst("Уха",
                            String.valueOf(spiral.getAllTochki().get(2).getZnachenie()));
                    }else if (stroka == 810){
                        str = str.replaceFirst("Уха",
                            String.valueOf(spiral.getAllTochki().get(3).getZnachenie()));
                    }else if (stroka == 1790){
                        str = str.replaceFirst("Уха", getLymda01());
                    }else if (stroka == 1819){
                        str = str.replaceFirst("Уха", String.valueOf(spiral.getMinPrimUch()));
                    }else if (stroka == 1848){
                        str = str.replaceFirst("Уха", havEKI4PrimUch(spiral));
                    }else if (stroka == 1933){
                        str = str.replaceFirst("Уха", String.valueOf(getWsPrimUch(spiral)));
                    }else if (stroka == 2049){
                        str = str.replaceFirst("Уха"
                            , String.valueOf(getLymdavSkobax4PrymUch(spiral)));
                    }else if (stroka == 2077){
                        str = str.replaceFirst("Уха", getOzenka4PrymUch(spiral));
                    }

                    bw.write(str);
                    bw.newLine();
                }
                foBR.close();
                stream.close();
            }
//=======================================================================================================================
            for (Spiral spiral : pvd.getSpirals()) {
                stream = assetManager.open("pvdDocx/tableKartKond5");
                BufferedReader fivefoBR = new BufferedReader(new InputStreamReader(stream));
                stroka = 0;
                while ((str = fivefoBR.readLine()) != null) {
                    stroka++;//потом пригодится
                    if (stroka == 150){
                        str = str.replaceFirst("Уха", spiral.getNumberSpiral());
                    }else if (stroka == 660){
                        str = str.replaceFirst("Уха",
                            String.valueOf(spiral.getAllTochki().get(4).getZnachenie()));
                    }else if (stroka == 689){
                        str = str.replaceFirst("Уха",
                            String.valueOf(spiral.getAllTochki().get(5).getZnachenie()));
                    }else if (stroka == 718){
                        str = str.replaceFirst("Уха",
                            String.valueOf(spiral.getAllTochki().get(6).getZnachenie()));
                    }else if (stroka == 1698){
                        str = str.replaceFirst("Уха", getLymda01());
                    }else if (stroka == 1727){
                        str = str.replaceFirst("Уха", String.valueOf(spiral.getMinZonKond()));
                    }else if (stroka == 1755){
                        str = str.replaceFirst("Уха", havEKI(spiral));
                    }else if (stroka == 1841){
                        str = str.replaceFirst("Уха", String.valueOf(getWs(spiral)));
                    }else if (stroka == 1956){
                        str = str.replaceFirst("Уха"
                            , String.valueOf(getLymdavSkobax(spiral)));
                    }else if (stroka == 1984){
                        str = str.replaceFirst("Уха", getOzenka(spiral));
                    }
                    bw.write(str);
                    bw.newLine();
                }
                fivefoBR.close();
                stream.close();
            }
//=======================================================================================================================
            stroka = 0;
            stream = assetManager.open("pvdDocx/end");
            BufferedReader endBR = new BufferedReader(new InputStreamReader(stream));
            while ((str = endBR.readLine()) != null) {
                stroka++;
                String buff;
                if (stroka == 423 || stroka == 1937){
                    buff = String.valueOf(localDate.getYear()).charAt(2) +
                        String.valueOf(String.valueOf(localDate.getYear()).charAt(3));
                    str = str.replaceFirst("Уха", buff);
                } else if (stroka == 455 || stroka == 1983) {
                    String  mounth = localDate.getDayOfMonth() < 10
                        ? String.valueOf(localDate.getMonthValue())
                        : "0" + localDate.getMonthValue();
                    buff = localDate.getDayOfMonth() + "."
                        + mounth + "."
                        + localDate.getYear();
                    str = str.replaceFirst("Уха", buff);
                }
                bw.write(str);
                bw.newLine();
            }
            endBR.close();
            stream.close();

            bw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getSном() {
        return "4";
    }

    @Override
    public String getLymda01() {
        String[] years = pvd.getDateVvodVExp().split("\\.");
        Calendar calendarDo = Calendar.getInstance();
        Calendar calendar = Calendar.getInstance();
        calendarDo.set(Integer.parseInt(years[2]), Integer.parseInt(years[1]) - 1,Integer.parseInt(years[0]));
        long result = calendar.getTimeInMillis() - calendarDo.getTimeInMillis();
        int asf = (int) (result / 1000 / 60 / 60 / 24 /365.2422 * 100);
        float as = (float) asf / 100;
        return String.valueOf(as);
    }

    @Override
    public String getSfakt2(Object object) {
        return null;
    }

    @Override
    public String havEKI(Object object) { //для зоны конденс
        Spiral spiral = (Spiral) object;
        boolean logic = spiral.getMinZonKond() <= Float.parseFloat(getSном());
        if (logic){
            return "да";
        }
        return "нет";
    }

    public String havEKI4PrimUch(Object object) {
        Spiral spiral = (Spiral) object;
        boolean logic = spiral.getMinPrimUch() <= Float.parseFloat(getSном());
        if (logic){
            return "да";
        }
        return "нет";
    }

    @Override
    public void getSfakt1() {

    }

    @Override
    public void getLymda0() {

    }

    @Override
    public String getWs(Object object) { //зона конденс
        Spiral spiral = (Spiral) object;
        float prom = (float) ((1.25 * Float.parseFloat(getSном())
                        - 1.05 * spiral.getMinZonKond()) / Float.parseFloat(getLymda01()));
        int promInt = (int) (prom * 100);
        float result = (float) promInt / 100;
        return String.valueOf(result);
    }

    public String getWsPrimUch(Object object) {
        Spiral spiral = (Spiral) object;
        float prom = (float) ((1.25 * Float.parseFloat(getSном())
                - 1.05 * spiral.getMinPrimUch()) / Float.parseFloat(getLymda01()));
        int promInt = (int) (prom * 100);
        float result = (float) promInt / 100;
        return String.valueOf(result);
    }

    @Override
    public void getLymda() {

    }

    @Override
    public void getSmin() {

    }

    @Override
    public void getSvSkobax() {

    }

    @Override
    public String getLymdavSkobax(Object object) { //зона конденсация
        Spiral spiral = (Spiral) object;
        if (havEKI(spiral).equalsIgnoreCase("да")){
            float result = spiral.getMinZonKond() / Float.parseFloat(getWs(spiral));
            if (result > 30){
                return "более 30 лет";
            }else {
                return String.valueOf(result);
            }
        }
        return "нет ЭКИ";
    }

    public String getLymdavSkobax4PrymUch(Object object) {
        Spiral spiral = (Spiral) object;
        if (havEKI4PrimUch(spiral).equalsIgnoreCase("да")){
            float prom = (spiral.getMinPrimUch() - Float.parseFloat(spiral.getMinPrym())) / Float.parseFloat(getWsPrimUch(spiral));
            LocalDate localDate = LocalDate.now();
            int result = (int) (prom + localDate.getYear());
            if (result - localDate.getYear() > 30){
                return "более 30 лет";
            }else {
                return String.valueOf(result);
            }
        }
        return "нет ЭКИ";
    }

    @Override
    public String getOzenka(Object object) { //зона конденсац
        Spiral spiral = (Spiral) object;
        if (Float.parseFloat(spiral.getMinGib()) > spiral.getMinZonKond()){
            return "неуд.";
        }
        return "уд.";
    }

    public String getOzenka4PrymUch(Object object) {
        Spiral spiral = (Spiral) object;
        if (Float.parseFloat(spiral.getMinPrym()) > spiral.getMinPrimUch()){
            return "неуд.";
        }
        return "уд.";
    }
}
