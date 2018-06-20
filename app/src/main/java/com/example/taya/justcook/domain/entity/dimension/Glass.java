package com.example.taya.justcook.domain.entity.dimension;

/**
 * Created by tayab on 25.05.2016.
 */
public class Glass extends Dimension {
    public Glass() {
        name="стакан";
    }

    public Glass(double value) {
        super(value);
        name="стакан";


    }

    @Override
    protected double convertToBaseDimension(int index) {

        return value*250*products[index].getKoefitient();
    }
    public static double convertToGlass(Dimension d, int index){
        double v = d.convertToBaseDimension(index);
        return v/250/products[index].getKoefitient();
    }
    public static String convertToGlass(Dimension d, String pattern, int index){
        return convertValueToString(convertToGlass(d, index), pattern);
    }
}
