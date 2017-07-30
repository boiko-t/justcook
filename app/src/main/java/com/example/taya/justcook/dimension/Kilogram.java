package com.example.taya.justcook.dimension;

public class Kilogram extends Dimension {

    public Kilogram(){
        super();
        name="кілограм";
    }

    public Kilogram(double value){
        super(value);
        name="кілограм";
    }

    @Override
    protected double convertToBaseDimension(int index) {
        return  value*1000;
    }

    public static double convertToKilogram(Dimension d, int index){
        double v = d.convertToBaseDimension(1);
        return v/1000;
    }

    public static String convertToKilogram(Dimension d, String pattern, int index){
        return convertValueToString(convertToKilogram(d, index), pattern);
    }
}
