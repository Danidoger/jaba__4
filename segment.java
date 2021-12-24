package com.company;
public class segment {
    Point start,end;
    segment(Point a1, Point a2) {
        start = a1;
        end = a2;
    }
    segment(){}
    public double Length() {
        return Math.pow(Math.pow(end.getCoord("x")-start.getCoord("x"),2)+Math.pow(end.getCoord("y")-start.getCoord("y"),2)+Math.pow(end.getCoord("z")-start.getCoord("z"),2), 0.5);
    }
    public boolean isPointIn(Point a){
        if ((a.getCoord("x")-start.getCoord("x"))/(end.getCoord("x")-start.getCoord("x"))==(a.getCoord("y")-start.getCoord("y"))/(end.getCoord("y")-start.getCoord("y")) &&(
                (a.getCoord("y")-start.getCoord("y"))/(end.getCoord("y")-start.getCoord("y"))==(a.getCoord("z")-start.getCoord("z"))/(end.getCoord("z")-start.getCoord("z")) || (start.getCoord("z")==0 && end.getCoord("z")==0 && a.getCoord("z")==0)))
            return true;
        return false;
    }
    public boolean isCrossin(segment AB) {
        if (AB.start.getCoord("x")/ start.getCoord("x") == AB.start.getCoord("y")/start.getCoord("y")  && AB.start.getCoord("y")/start.getCoord("y") == AB.start.getCoord("z")/start.getCoord("z")) return false;
        return true;
    }
}
