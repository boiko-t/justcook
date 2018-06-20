package com.example.taya.justcook.domain.entity;

import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.example.taya.justcook.domain.entity.dimension.Dimension;
import com.example.taya.justcook.domain.entity.dimension.Glass;
import com.example.taya.justcook.domain.entity.dimension.Gram;
import com.example.taya.justcook.domain.entity.dimension.Kilogram;
import com.example.taya.justcook.domain.entity.dimension.Litre;
import com.example.taya.justcook.domain.entity.dimension.Mililitre;
import com.example.taya.justcook.domain.entity.dimension.Piece;
import com.example.taya.justcook.domain.entity.dimension.TableSpoon;
import com.example.taya.justcook.domain.entity.dimension.TeaSpoon;

public class Ingredient {

    private Dimension dimension;
    private String ingredientName;

    public Ingredient() {
    }

    public Ingredient(String ingredientName, double amount, Dimension dimension) {
        this.dimension = dimension;
        dimension.setValue(amount);
        this.ingredientName = ingredientName;
    }

    public Ingredient(String ingredientName, Dimension dimension) {
        this.dimension = dimension;
        this.ingredientName = ingredientName;
    }

    public Dimension getDimension() {
        return dimension;
    }

    public void setDimension(Dimension dimension) {
        this.dimension = dimension;
    }

    public String getIngredientName() {
        return ingredientName;
    }


    public void setIngredientName(String ingredientName) {
        this.ingredientName = ingredientName;
    }

    public static Dimension setDimensionType(int p) {
        Dimension d = new Gram();
        switch (p) {
            case 0:
                d = new Gram();
                break;
            case 1:
                d = new Kilogram();
                break;
            case 2:
                d = new Mililitre();
                break;
            case 3:
                d = new Litre();
                break;
            case 4:
                d = new Piece();
                break;
            case 5:
                d = new Glass();
                break;
            case 6:
                d = new TableSpoon();
                break;
            case 7:
                d = new TeaSpoon();
                break;
        }
        return d;
    }

    public boolean loadIngredientFromLayout(LinearLayout l) {
        String sValue = ((EditText) l.getChildAt(1)).getText().toString();
        String sName = ((EditText) l.getChildAt(0)).getText().toString();
        if (sValue.equals("") || sName.equals(""))
            return false;
        ingredientName = sName;
        int position = ((Spinner) l.getChildAt(2)).getSelectedItemPosition();
        dimension = setDimensionType(position);
        dimension.setValue(Double.parseDouble(sValue));
        return true;
    }
}
