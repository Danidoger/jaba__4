package com.company;

public class segment_on_surf extends segment{
    geoPoint start,end;
    segment_on_surf(geoPoint a1, geoPoint a2){
        start = a1;
        end = a2;
    }
    public segment_on_surf relative_turn(geoPoint hh, int multiplier){
        geoPoint one = start.new_shifted_Point(-hh.getCoord("x"), -hh.getCoord("y"));
        geoPoint two = end.new_shifted_Point(-hh.getCoord("x"), -hh.getCoord("y"));
        one.turn_half_pi(multiplier);
        two.turn_half_pi(multiplier);
        geoPoint onen = one.new_shifted_Point(hh.getCoord("x"),hh.getCoord("y"));
        geoPoint twon = two.new_shifted_Point(hh.getCoord("x"),hh.getCoord("y"));
        return new segment_on_surf(onen, twon);
    }
}
