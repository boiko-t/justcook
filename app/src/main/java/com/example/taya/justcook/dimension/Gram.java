package com.example.taya.justcook.dimension;

public class Gram extends Dimension{

    public Gram(){
        super();
        name="грам";
    }
    public Gram(double value) {
        super(value);
        name="грам";
    }

    @Override
    public double convertToBaseDimension(int index) {
        return value;
    }

    public static double convertToGram(Dimension d, int index){
        double v = d.convertToBaseDimension(index);
        return v;
    }
    public static String convertToGram(Dimension d, String pattern, int index){
        return convertValueToString(d.convertToBaseDimension(index), pattern);
    }
}
