package com.example.taya.justcook.domain.entity.dimension;

/**
 * Created by tayab on 25.05.2016.
 */
public class TableSpoon extends Dimension{
    public TableSpoon(double value) {
        super(value);
        name="столова ложка";
    }

    public TableSpoon() {
        name="столова ложка";
    }

    @Override
    protected double convertToBaseDimension(int index) {
        return value*18*products[index].getKoefitient();
    }

    public static double convertToTableSpoon(Dimension d, int index){
        double v = d.convertToBaseDimension(index);
        return v/18/products[index].getKoefitient();
    }

    public static String convertToTableSpoon(Dimension d, String pattern, int index){
        return convertValueToString(convertToTableSpoon(d, index), pattern);
    }
}
