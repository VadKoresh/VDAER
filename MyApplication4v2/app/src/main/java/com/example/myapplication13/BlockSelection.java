package com.example.myapplication13;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication13.PVD.PvdActivity;
import com.example.myapplication13.PVD.PVD;

import java.util.HashMap;
import java.util.Set;

public class BlockSelection extends AppCompatActivity implements View.OnClickListener {

    HashMap<String, PVD> oborudovanie = new HashMap<>();

    ImageButton blok1;
    ImageButton blok2;
    ImageButton blok3;
    ImageButton blok4;
    Button beginButton;
    EditText tempSred;
    EditText tempPover;
    EditText numberZap;
    TextView blok1Text;
    TextView blok2Text;
    TextView blok3Text;
    TextView blok4Text;
    TextView info;
    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_block_selection);

        blok1Text = (TextView) findViewById(R.id.textView5);
        blok2Text = (TextView) findViewById(R.id.textView6);
        blok3Text = (TextView) findViewById(R.id.textView8);
        blok4Text = (TextView) findViewById(R.id.textView7);
        info = (TextView) findViewById(R.id.info);

        blok1 = (ImageButton) findViewById(R.id.blok1);
        blok2 = (ImageButton) findViewById(R.id.blok2);
        blok3 = (ImageButton) findViewById(R.id.blok3);
        blok4 = (ImageButton) findViewById(R.id.blok4);

        beginButton = (Button) findViewById(R.id.begin);

        tempSred = (EditText) findViewById(R.id.tempSred);
        tempPover = (EditText) findViewById(R.id.tempPoverxn);
        numberZap = (EditText) findViewById(R.id.numberZapisi);

        blok1.setOnClickListener(this);
        blok2.setOnClickListener(this);
        blok3.setOnClickListener(this);
        blok4.setOnClickListener(this);
        beginButton.setOnClickListener(this);

        beginButton.setEnabled(false);

        oborudovanie.put("1RD21W01", new PVD("1RD21W01"
                , "Подогреватель высокого давления ПВ 2500-97-18А " +
                "(ПВД-6А), черт. 08.8111.260СБ, зав. №48765, 1RD21W01. Спирали"
                , "3.05СК-ТО-1RD", "26.06.2000"));
        oborudovanie.put("1RD22W01", new PVD("1RD22W01"
                , "Подогреватель высокого давления ПВ 2500-97-18А " +
                "(ПВД-6А), черт. 08.8111.260СБ, зав. №48614, 1RD22W01. Спирали"
                , "3.06СК-ТО-1RD","26.06.2000"));
        oborudovanie.put("1RD11W01", new PVD("1RD11W01"
                , "Подогреватель высокого давления ПВ 2500-97-28А " +
                "(ПВД-7А), черт. 08.8111.264СБ, зав. №48615, 1RD11W01. Спирали"
                , "3.03СК-ТО-1RD","26.06.2000"));
        oborudovanie.put("1RD12W01", new PVD("1RD12W01"
                ,"Подогреватель высокого давления ПВ 2500-97-28А " +
                "(ПВД-7А), черт. 08.8111.264СБ, зав. №48766, 1RD12W01. Спирали"
                ,"3.04СК-ТО-1RD","26.06.2000"));

        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_item, getRTMs(oborudovanie));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setAdapter(adapter);
        // заголовок
        spinner.setPrompt("Оборудование");
        //обработчик нажатия
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // показываем позиция нажатого элемента
                Toast.makeText(getBaseContext(), "Position = " + position, Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
        spinner.setEnabled(false);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.blok1:
                info.setText("Далее:\nвыберите оборудование!");
                spinner.setEnabled(true);
                beginButton.setEnabled(true);
                break;
            case R.id.blok2:
                info.setText("Данный блок недоступен!");
                break;
            case R.id.blok3:
                info.setText("Данный блок недоступен!");
                break;
            case R.id.blok4:
                info.setText("Данный блок недоступен!");
                break;
            case R.id.begin:
                if (tempSred.getText().toString().length() == 0
                        || tempPover.getText().toString().length() == 0
                        || numberZap.getText().toString().length() == 0) {
                    info.setText("Введите все параметры!");
                } else {
                    PVD vubor = oborudovanie.get(spinner.getSelectedItem().toString());
                    String tPoverh = tempPover.getText().toString();
                    String tSred = tempSred.getText().toString();
                    String numberGurn = numberZap.getText().toString();

                    Intent intent = new Intent(BlockSelection.this, PvdActivity.class);
                    intent.putExtra(PVD.class.getSimpleName(), vubor);
                    intent.putExtra("tPoverh", tPoverh);
                    intent.putExtra("tSred", tSred);
                    intent.putExtra("numberGurn", numberGurn);
                    startActivity(intent);
                }
                break;
        }
    }

    public String[] getRTMs(HashMap<String, PVD> perecheny){
        String[] rtm  = new String[perecheny.size()];
        Set<String> mnogestvo = perecheny.keySet();
        int i = 0;
        for (String key :  mnogestvo){
            rtm[i] = key;
            i++;
        }
        return rtm;
    }
}