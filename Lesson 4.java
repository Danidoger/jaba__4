package com.company;

import java.util.HashMap;
import java.util.ArrayList;
import java.io.IOException;
import java.io.*;
public class FileHandler{
  private static int counter = 0;
  private static String[] arr;
  private static ArrayList<geoPoint> gt = new ArrayList<>();
  private static int globalnum = 0;
  private static String globalcontur;
  public static String decodeDefiningMethod(String code){
    HashMap<String, String> def_dict = new HashMap<>();
    def_dict.put("692001000000", "Геодезический метод");
    def_dict.put("692002000000", "Фотограмметрический метод");
    def_dict.put("692003000000", "Картометрический метод");
    def_dict.put("692004000000", "Иное описание");
    def_dict.put("692005000000", "Метод спутниковых геодезических измерений");
    def_dict.put("692006000000", "Аналитический метод");
    String decode_def;
    if (def_dict.containsKey(code) == true){
      decode_def = def_dict.get(code);
    }
    else{
      decode_def = "Иной метод";
    }
    return decode_def;
  }
  public static String decodePinningMethod(String code){
    HashMap<String, String> pin_dict = new HashMap<>();
    pin_dict.put("626001000000", "Долговременный межевой знак");
    pin_dict.put("626002000000", "Временный межевой знак");
    pin_dict.put("626003000000", "Закрепление отсутствует");
    String decode_pin;
    if (pin_dict.containsKey(code) == true){
      decode_pin = pin_dict.get(code);
    }
    else{
      decode_pin = "-";
    }
    return decode_pin;
  }
  public static void ReadFile(String filename) throws IOException{
    Reader reader = new FileReader(filename);
    BufferedReader buffReader = new BufferedReader(reader);
    int i = 0;
    while (buffReader.ready()){
      String s_line = buffReader.readLine();
      arr = s_line.split(";");
      if (i > 1){
        String contur = arr[0];
        globalcontur = contur;
        int number = Integer.parseInt(arr[1]);
        globalnum = number;
        arr[2] = arr[2].replace(',', '.');
        arr[3] = arr[3].replace(',', '.');
        arr[5] = arr[5].replace(',', '.');
        double x = Double.parseDouble(arr[2]);
        double y = Double.parseDouble(arr[3]);
        String[] temp_def_code = arr[4].split(" ");
        String def_code = temp_def_code[1];
        double inaccuracy = Double.parseDouble(arr[5]);
        String[] temp_pin_code = arr[6].split(" ");
        String pin_code = temp_pin_code[1];
        String decoded_def = FileHandler.decodeDefiningMethod(def_code);
        String decoded_pin = FileHandler.decodePinningMethod(pin_code);
        System.out.println(s_line);
        System.out.println(decoded_def);
        System.out.println(decoded_pin);
        System.out.println("||||||||||");
        geoPoint gp = new geoPoint(x,y, inaccuracy, decoded_pin,decoded_def);
        gp.setNum(number);
        geoPoint.add(gp);
        counter++;
        
      }
      i++;
    }
    reader.close();
    buffReader.close();
  }
  public static void WriteFile(String filename, geoPoint gt) throws IOException{
    FileWriter writer = new FileWriter(filename, true);
    String contur = globalcontur;
    int number = gt.getNum();
    globalnum = number;
    double[] coords = {gt.getCoord("x"),gt.getCoord("y"),gt.getCoord("z")};
    String def_method = gt.get("opr_method");
    double inaccuracy = Double.parseDouble(gt.get("accuracy"));
    String pin_method = gt.get("zakrep_method");
    writer.write(globalcontur);
    writer.append(';');
    writer.write(String.valueOf(number));
    writer.append(';');
    writer.write(String.valueOf(coords[0]));
    writer.append(';');
    writer.write(String.valueOf(coords[1]));
    writer.append(';');
    writer.write(def_method);
    writer.append(';');
    writer.write(String.valueOf(inaccuracy));
    writer.append(';');
    writer.write(pin_method);
    writer.append('\n');
    writer.flush();
    
  }
  public static ArrayList getGPointArrayList(){
    return geoPoint;
  }
  public static int getGlobalnum(){
    return FileHandler.globalnum;
  }
  public static void TurnArrayListGeodeticPoints(int num, int factor){
    int index = -1;
    geoPoint main_point = null;
    for (int i = 0; i < geoPoint.size(); i++){
      geoPoint gPoint = ge0Point.get(i);
      int current_num = gPoint.getNum();
      if (current_num == num){
        index = i;
        main_point = gPoint;
        break;
      }
    }
    for (int i = 0; i < geoPoint.size(); i++){
      geoPoint current_point = geoPoint.get(i);
      double inaccuracy = current_point.getInaccuracy();
      int number = current_point.getNum();
      String def_method = current_point.get("opr_method");
      String pin_method = current_point.get("zakrep_method");
      geoPoint turned_point = current_point.relative_turn(main_point, factor);
      turned_point.setNum(number);
      turned_point.set_accuracy(inaccuracy);
      turned_point.set_opr_method(def_method);
      turned_point.set_zakrep_method(pin_method);
      geoPoint.set(i, turned_point);
    }

  }
  public static int getCounter(){
    return counter;
  }
  public static double getArrayListLength(){
    double length = 0;
    for (int i = 0; i < geoPoint.size()-1; i++){
      geoPoint gp1 = geoPoint.get(i);
      geoPoint gp2 = geoPoint.get(i+1);
      segment_on_surf seg = new segment_on_surf(gp1, gp2);
      double curr_length = seg.Length();
      length += curr_length;
    }
    return length;
  }
}