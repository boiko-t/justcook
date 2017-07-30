package com.example.taya.justcook.dimension;

import java.text.DecimalFormat;

public abstract class Dimension {

    protected double value;
    protected String name;
    protected static Product products[];

    public Dimension(){
        value=0;
        products=new Product[7];
        createProducts();
    }
    public Dimension(double value) {
        this.value = value;
        products=new Product[7];
        createProducts();
    }

    public double getValue() {
        return value;
    }

    public String getName(){
        return name;
    }

    public void setValue(double value) {
        this.value = value;
    }

    protected abstract double convertToBaseDimension(int index);

    protected static String convertValueToString(double v, String format){
        DecimalFormat formatter = new DecimalFormat(format);
        return formatter.format(v);
    }

    protected class Product{
        private double koefitient;
        private String productName;
        public Product(){
            koefitient=0;
            productName="";
        }
        public void setValues(String productName, double koefitient){
            this.productName=productName;
            this.koefitient = koefitient;
        }
        public String getProductName(){
            return productName;
        }
        public double getKoefitient(){
            return koefitient;
        }
    }

    private void createProducts(){
        for (int i=0; i<7; i++)
            products[i]=new Product();
        products[0].setValues("Вода", 1);
        products[1].setValues("Молоко", 1);
        products[2].setValues("Цукор", 1.1);
        products[3].setValues("Сіль", 1.4);
        products[4].setValues("Олія", 0.85);
        products[5].setValues("Борошно", 0.65);
        products[6].setValues("Рис", 0.85);
    }

    public String[] getProductsNames(){
        String arr[]=new String [7];
        for (int i=0; i<7; i++)
            arr[i]=products[i].getProductName();
        return arr;
    }
    public static String[] getDimensionNames(boolean getFullList){
        if(getFullList)
            return new String[]{"грам", "кілограм", "мілілітр", "літр", "штук", "стакан", "столова ложка", "чайна ложка"};
        return new String[]{"грам", "кілограм", "мілілітр", "літр","стакан", "столова ложка", "чайна ложка"};
    }
}
