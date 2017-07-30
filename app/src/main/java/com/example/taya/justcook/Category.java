package com.example.taya.justcook;

public enum Category {
    DESSERT, MAIN_DISH, SOUP, SALAD, ALL, FAVOURITE;
    public static String[] getCategoryNames(){
        return new String[] {"Десерти", "Головні страви", "Супи", "Салати"};
    }
}
