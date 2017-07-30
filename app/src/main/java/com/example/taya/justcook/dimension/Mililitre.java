package com.example.taya.justcook.dimension;

/**
 * Created by tayab on 25.05.2016.
 */
public class Mililitre extends Dimension {
    public Mililitre() {
        name="мілілітр";
    }

    public Mililitre(double value) {
        super(value);
        name="мілілітр";
    }

    @Override
    protected double convertToBaseDimension(int index) {
        return value*products[index].getKoefitient();
    }

    public static double convertToMililitre(Dimension d, int index){
        double v = d.convertToBaseDimension(index);
        return v/products[index].getKoefitient();
    }

    public static String convertToMililitre(Dimension d, String pattern, int index){
        return convertValueToString(convertToMililitre(d, index), pattern);
    }
}
