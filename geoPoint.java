package com.company;

import java.util.ArrayList;
import java.util.List;

public class geoPoint extends Point {
    private String zakrep_method, opr_method;
    private double accuracy;
    geoPoint(double x, double y, double accuracy,String zakrep_method, String opr_method){
        this.x = x;
        this.y = y;
        set_accuracy(accuracy);
        set_zakrep_method(zakrep_method);
        set_opr_method(opr_method);
    }
    public String get(String a){
        if (a.equals("zakrep_method")){
            return this.zakrep_method;
        } else if (a.equals("opr_method")){
            return  this.opr_method;
        } else if (a.equals("accuracy")){ return String.valueOf(this.accuracy);}
        return "";
    }
    public void turn_half_pi(int pi_num) {
        for(int i = 0;i<pi_num;i++){
            this.x = -1*this.y;
            this.y = this.x;
        }
    }
    public void set_accuracy(double accuracy) {
        if (accuracy>1){
            this.accuracy = 1;
        } else if (accuracy<0.01){
            this.accuracy = 0.01;
        } else {
            this.accuracy = accuracy;
        }
    }
    public void set_zakrep_method(String zak) {
        List<String> Zakreplen = new ArrayList<String>(3);
        Zakreplen.add("долговременный межевой знак");
        Zakreplen.add("временный межевой знак");
        Zakreplen.add("значение отсутствует");
        if (Zakreplen.contains(zak)){
            this.zakrep_method = zak;
        } else this.zakrep_method = Zakreplen.get(3);
    }
    public void set_opr_method(String zak) {
        List<String> Opred = new ArrayList<String>(6);
        Opred.add("Аналитический метод");
        Opred.add("Картометрический метод");
        Opred.add("Геодезический метод");
        Opred.add("Фотограмметрический метод");
        Opred.add("Метод спутниковых геодезических измерений");
        Opred.add("Иное описание");
        if (Opred.contains(zak)){
            this.opr_method = zak;
        } else this.opr_method = Opred.get(6);
    }
    public void shift(double deltax, double deltay){
        this.x = this.x + deltax;
        this.y = this.y + deltay;
    }
    public geoPoint new_shifted_Point(double deltax, double deltay){
        geoPoint a = new geoPoint(this.x,this.y,this.accuracy,this.zakrep_method,this.opr_method);
        a.shift(deltax, deltay);
        return a;
    }
    public void reflect_x(){
        this.x = -1*this.x;
    }
    public void reflect_y(){
        this.y = -1*this.y;
    }
}
