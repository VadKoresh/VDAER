package com.example.myapplication13.Intrrface;

public interface EKI {

    void run(); //формирование отчета

    String getSном();     //номинальная толщина,мм

    String getLymda01();  // общее время эксплуатации,год

    String getSfakt2(Object object);   //минимальная толщина в текущий контроль, мм

    String havEKI(Object object);    //наличие ЭКИ

    void getSfakt1();   //минимальная толщина в предыдущий контроль

    void getLymda0();   //время эксплуатации между предыдущим контролями, год

    String getWs(Object object);       // скорость утонения, мм/год

    void getLymda();    //планируемое время до следующего контроля, год

    void getSmin();     //минимально допустимая толщина в соответствии НД, мм

    void getSvSkobax(); //расчетная минимально допустимая толщина с учетом времени до следующего контроля, мм

    String getLymdavSkobax(Object object); //год достижения минимально допустимой толщины

    String getOzenka(Object object);
}
