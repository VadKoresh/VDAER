package com.example.myapplication13.PVD;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.example.myapplication.R;
import com.example.myapplication13.PVD.Spiral;
import com.example.myapplication13.PVD.Tochki;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class PvdActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int PERMISSION_REQUEST_CODE = 1;

    private String FILENAME = "Заключение";
    private String filePath = "MyFileStorage";
    private File mFile;
    private String mData = "";

    PVD pvd;
    String numberGurnal;


    ArrayList<Spiral> spirals = new ArrayList<>();
    float[]floats = new float[7];
    String  numb;

    Button buttonOtchet;
    Button button1;

    TextView myTest;
    TextView lineGib;
    TextView primUch;
    TextView tempPov;
    TextView tempSr;
    TextView rtm;

    EditText editText;
    EditText one;
    EditText two;
    EditText thre;
    EditText fo;
    EditText five;
    EditText siks;
    EditText seven;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myTest = (TextView) findViewById(R.id.myText);
        lineGib = (TextView) findViewById(R.id.textView2);
        primUch = (TextView) findViewById(R.id.textView);
        tempPov = (TextView) findViewById(R.id.temppoverxnPVD);
        tempSr = (TextView) findViewById(R.id.tempSredPVD);
        rtm = (TextView) findViewById(R.id.rtmOborud);

        editText = (EditText) findViewById(R.id.vvodText);
        one= (EditText) findViewById(R.id.editTextTextPersonName3);
        two= (EditText) findViewById(R.id.editTextTextPersonName2);
        thre= (EditText) findViewById(R.id.editTextTextPersonName6);
        fo= (EditText) findViewById(R.id.editTextTextPersonName7);
        five= (EditText) findViewById(R.id.editTextTextPersonName4);
        siks= (EditText) findViewById(R.id.editTextTextPersonName5);
        seven= (EditText) findViewById(R.id.editTextTextPersonName);

        buttonOtchet = (Button) findViewById(R.id.button);
        button1 = (Button) findViewById(R.id.button3);

        buttonOtchet.setOnClickListener(this);
        button1.setOnClickListener(this);

        // проверям внешнюю память на доступность
        // и возможность записи
        if (!isAvailable() || isReadOnly()) {
            // если доступа нет, то делаем кнопки для сохранения
            // и считывания с внешней памяти неактивными
            button1.setEnabled(false);
            button1.setEnabled(false);
        } else {
            // если доступ есть, то создаем файл в ExternalStorage
            mFile = new File(getExternalFilesDir(filePath), FILENAME);
        }

        Bundle arguments = getIntent().getExtras();
        if(arguments!=null){
            pvd = (PVD) arguments.getSerializable(PVD.class.getSimpleName());
            numberGurnal = arguments.getString("numberGurn");
            rtm.setText("RTM: " + pvd.getRtm());
            tempPov.setText("Температура поверхности: "
                    + arguments.getString("tPoverh")
                    + "\u2103");
            tempSr.setText("Температура среды: "
                    + arguments.getString("tSred")
                    + "\u2103");
        }

    }

    // является ли внешнее хранилище только для чтения
    private static boolean isReadOnly() {
        String storageState = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED_READ_ONLY.equals(storageState);
    }

    // проверяем есть ли доступ к внешнему хранилищу
    private static boolean isAvailable() {
        String storageState = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(storageState);
    }

    // простой метод для создания всплывающих окон
    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {

        if (Build.VERSION.SDK_INT >= 23){
            if (checkAndRequestPermissions()){
                try {
                    switch (v.getId()) {
                        case R.id.button:
                            myTest.setText("Отчет сформулирован");
                            OtchetPVD otchetPVD = new OtchetPVD(pvd, getAssets(), mFile
                                , tempPov.getText().toString(), tempSr.getText().toString()
                                , numberGurnal);
                            otchetPVD.run();
                            break;
                        case R.id.button3:
                            floats[0] = Float.parseFloat(one.getText().toString());
                            floats[1] = Float.parseFloat(two.getText().toString());
                            floats[2] = Float.parseFloat(thre.getText().toString());
                            floats[3] = Float.parseFloat(fo.getText().toString());
                            floats[4] = Float.parseFloat(five.getText().toString());
                            floats[5] = Float.parseFloat(siks.getText().toString());
                            floats[6] = Float.parseFloat(seven.getText().toString());
                            numb = editText.getText().toString();
                            myTest.setText("Пытаюсь записать");//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                            pvd.getSpirals().add(new Spiral(numb, floats, myTest));
                            myTest.setText("Введите данные следующей спирали");
                            one.setText("");
                            two.setText("");
                            thre.setText("");
                            fo.setText("");
                            five.setText("");
                            siks.setText("");
                            seven.setText("");
                            editText.setText("");
                            break;
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }

    private  boolean checkAndRequestPermissions() {
        int writepermission = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        List<String> listPermissionsNeeded = new ArrayList<>();

        if (writepermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[0]), PERMISSION_REQUEST_CODE);
            return false;
        }
        return true;
    }

    public void run(){
        try {
            FileOutputStream fos = new FileOutputStream(mFile);

            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));

            InputStream stream = getAssets().open("pvdDocx/begin");
            BufferedReader begr = new BufferedReader(new InputStreamReader(stream));
            String str = "";
            while ((str = begr.readLine()) != null) {
                bw.write(str);
                bw.newLine();
            }
            begr.close();
            stream.close();
//=======================================================================================================================================
            for (Spiral spiral : spirals){
                stream = getAssets().open("pvdDocx/567r");

                BufferedReader oneFoR = new BufferedReader(new InputStreamReader(stream));
                int stroka = 0;
                while ((str = oneFoR.readLine()) != null) {
                    stroka++;
                    if (stroka == 47){
                        str = str.replaceFirst("Замена",spiral.getNumberSpiral());
                    } else if (stroka == 183){
                        str = str.replaceFirst("Замена",
                                Float.toString(spiral.getAllTochki()
                                        .stream()
                                        .min(Comparator.comparing(Tochki::getZnachenie))
                                        .get().getZnachenie()));
                    }
                else if (stroka == 218){
                        str = str.replaceFirst("Замена", spiral.getMinGib());
                    }
                    bw.write(str);
                    bw.newLine();
                }
                oneFoR.close();
                stream.close();
            }
//======================================================================================================================================
            InputStream promIS = getAssets().open("pvdDocx/prom");
            BufferedReader promBR = new BufferedReader(new InputStreamReader(promIS));
            String promSTR = "";
            while ((promSTR = promBR.readLine()) != null) {
                bw.write(promSTR);
                bw.newLine();
            }
            promBR.close();
            promIS.close();
//=======================================================================================================================================
            for (Spiral spiral : spirals){
                stream = getAssets().open("pvdDocx/1234r");
                BufferedReader oneFoR = new BufferedReader(new InputStreamReader(stream));
                int stroka = 0;
                while ((str = oneFoR.readLine()) != null) {
                    stroka++;
                    if (stroka == 47){
                        str = str.replaceFirst("Замена", spiral.getNumberSpiral());
                    } else if (stroka == 185){
                        str = str.replaceFirst("Замена",
                                Float.toString(spiral.getAllTochki()
                                        .stream()
                                        .min(Comparator.comparing(Tochki::getZnachenie))
                                        .get().getZnachenie()));
                    }
                    else if (stroka == 220){
                        str = str.replaceFirst("Замена", spiral.getMinPrym());
                    }
                    bw.write(str);
                    bw.newLine();
                }
                oneFoR.close();
                stream.close();
            }
//=======================================================================================================================================//======================================================================================================================================
            InputStream prom2IS = getAssets().open("pvdDocx/prom2");
            BufferedReader prom2BR = new BufferedReader(new InputStreamReader(prom2IS));
            String prom2STR = "";
            while ((prom2STR = prom2BR.readLine()) != null) {
                bw.write(prom2STR);
                bw.newLine();
            }
            prom2BR.close();
            prom2IS.close();
//=======================================================================================================================================
            for (Spiral spiral : spirals){
                stream = getAssets().open("pvdDocx/1234");
                BufferedReader oneFoR = new BufferedReader(new InputStreamReader(stream));
                int stroka = 0;
                while ((str = oneFoR.readLine()) != null) {
                    stroka++;
                    if (stroka == 156){
                        str = str.replaceFirst("Замена", spiral.getNumberSpiral());
                    } else if (stroka == 746){
                        for (Tochki tochki : spiral.getAllTochki()){
                            if (tochki.getNumberTochki() == 1) {
                                str = str.replaceFirst("Замена", Float.toString(tochki.getZnachenie()));
                                break;
                            }
                        }
                    }else if (stroka == 780){
                        for (Tochki tochki : spiral.getAllTochki()) {
                            if (tochki.getNumberTochki() == 2) {
                                str = str.replaceFirst("Замена", Float.toString(tochki.getZnachenie()));
                                break;
                            }
                        }
                    }else if (stroka == 812){
                        for (Tochki tochki : spiral.getAllTochki()) {
                            if (tochki.getNumberTochki() == 3) {
                                str = str.replaceFirst("Замена", Float.toString(tochki.getZnachenie()));
                                break;
                            }
                        }
                    }else if (stroka == 844){
                        for (Tochki tochki : spiral.getAllTochki()) {
                            if (tochki.getNumberTochki() == 4) {
                                str = str.replaceFirst("Замена", Float.toString(tochki.getZnachenie()));
                                break;
                            }
                        }
                    }
                    bw.write(str);
                    bw.newLine();
                }
                oneFoR.close();
                stream.close();
            }
//=======================================================================================================================================
            for (Spiral spiral : spirals){
                stream = getAssets().open("pvdDocx/5677");
                BufferedReader fiveSevenR = new BufferedReader(new InputStreamReader(stream));
                int stroka = 0;
                while ((str = fiveSevenR.readLine()) != null) {
                    stroka++;
                    if (stroka == 155){
                        str = str.replaceFirst("Замена", spiral.getNumberSpiral());
                    } else if (stroka == 688){
                        for (Tochki tochki : spiral.getAllTochki()){
                            if (tochki.getNumberTochki() == 5) {
                                str = str.replaceFirst("Замена", Float.toString(tochki.getZnachenie()));
                                break;
                            }
                        }
                    }else if (stroka == 720){
                        for (Tochki tochki : spiral.getAllTochki()) {
                            if (tochki.getNumberTochki() == 6) {
                                str = str.replaceFirst("Замена", Float.toString(tochki.getZnachenie()));
                                break;
                            }
                        }
                    }else if (stroka == 752) {
                        for (Tochki tochki : spiral.getAllTochki()) {
                            if (tochki.getNumberTochki() == 7) {
                                str = str.replaceFirst("Замена", Float.toString(tochki.getZnachenie()));
                                break;
                            }
                        }
                    }
                    bw.write(str);
                    bw.newLine();
                }
                fiveSevenR.close();
                stream.close();
            }
//=======================================================================================================================================
            stream = getAssets().open("pvdDocx/end");
            BufferedReader endr = new BufferedReader(new InputStreamReader(stream));
            while ((str = endr.readLine()) != null) {
                bw.write(str);
                bw.newLine();
            }
            endr.close();
            stream.close();


            bw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}