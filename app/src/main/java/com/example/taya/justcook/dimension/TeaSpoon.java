package com.example.taya.justcook.dimension;

/**
 * Created by tayab on 25.05.2016.
 */
public class TeaSpoon extends Dimension{
    public TeaSpoon(double value) {
        super(value);
        name="чайна ложка";
    }

    public TeaSpoon() {
        name="чайна ложка";
    }

    @Override
    protected double convertToBaseDimension(int index) {
        return value*5*products[index].getKoefitient();
    }

    public static double convertToTeaSpoon(Dimension d, int index){
        double v = d.convertToBaseDimension(index);
        return v/5/products[index].getKoefitient();
    }
    public static String convertToTeaSpoon(Dimension d, String pattern, int index){
        return convertValueToString(convertToTeaSpoon(d, index), pattern);
    }
}
