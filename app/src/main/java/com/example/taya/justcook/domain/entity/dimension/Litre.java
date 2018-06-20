package com.example.taya.justcook.domain.entity.dimension;

/**
 * Created by tayab on 25.05.2016.
 */
public class Litre extends Dimension {
    public Litre(double value) {
        super(value);
        name="літр";
    }

    public Litre() {
        name="літр";
    }

    @Override
    protected double convertToBaseDimension(int index) {
        return value*1000*products[index].getKoefitient();
    }

    public static double convertToLitre(Dimension d, int index){
        return Mililitre.convertToMililitre(d, index)/1000;
    }
    public static String convertToLitre(Dimension d, String pattern, int index){
        return convertValueToString(convertToLitre(d, index), pattern);
    }
}
