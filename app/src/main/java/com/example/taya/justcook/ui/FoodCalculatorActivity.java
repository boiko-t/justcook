package com.example.taya.justcook.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.taya.justcook.domain.entity.Ingredient;
import com.example.taya.justcook.R;
import com.example.taya.justcook.domain.entity.dimension.Dimension;
import com.example.taya.justcook.domain.entity.dimension.Glass;
import com.example.taya.justcook.domain.entity.dimension.Gram;
import com.example.taya.justcook.domain.entity.dimension.Kilogram;
import com.example.taya.justcook.domain.entity.dimension.Litre;
import com.example.taya.justcook.domain.entity.dimension.Mililitre;
import com.example.taya.justcook.domain.entity.dimension.TableSpoon;
import com.example.taya.justcook.domain.entity.dimension.TeaSpoon;


public class FoodCalculatorActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private String dimensionNames[]= Dimension.getDimensionNames(false);
    private EditText inputValue;
    private TextView g, kg, ml, l, glass, tbS, teaS;
    private Dimension dimension;
    private Spinner products;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_converter);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Spinner spinner=(Spinner)findViewById(R.id.spinnerDimentionForChange);
        products=(Spinner)findViewById(R.id.spinnerProducts);
        inputValue=(EditText)findViewById(R.id.inputValue);
        g=(TextView)findViewById(R.id.textGram);
        kg=(TextView)findViewById(R.id.textKilogram);
        ml=(TextView)findViewById(R.id.textMillilitre);
        l=(TextView)findViewById(R.id.textLitre);
        glass=(TextView)findViewById(R.id.textGlass);
        tbS=(TextView)findViewById(R.id.textTableSpoon);
        teaS=(TextView)findViewById(R.id.textTeaSpoon);
        inputValue.addTextChangedListener(watcher);

        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, dimensionNames);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(categoryAdapter);
        spinner.setSelection(0);
        spinner.setOnItemSelectedListener(this);
        dimension=new Gram();
        String[] s =dimension.getProductsNames();
        ArrayAdapter<String> productAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, s);
        productAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        products.setAdapter(productAdapter);
        products.setSelection(0);
        products.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                watcher.afterTextChanged(inputValue.getText());
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    TextWatcher watcher=new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
           if(s.length()==0)
               return;
            double value = Double.parseDouble(s.toString());
            dimension.setValue(value);
            setCountedInformation();
        }
    };

    private void setCountedInformation(){
        String pattern = "####.##";
        g.setText(dimensionNames[0] + ": " +  Gram.convertToGram(dimension, pattern, products.getSelectedItemPosition()));
        kg.setText(dimensionNames[1] + ": " + Kilogram.convertToKilogram(dimension, pattern, products.getSelectedItemPosition()));
        ml.setText(dimensionNames[2] + ": " + Mililitre.convertToMililitre(dimension, pattern, products.getSelectedItemPosition()));
        l.setText(dimensionNames[3] + ": " + Litre.convertToLitre(dimension, pattern, products.getSelectedItemPosition()));
        glass.setText(dimensionNames[4] + ": " + Glass.convertToGlass(dimension, pattern, products.getSelectedItemPosition()));
        tbS.setText(dimensionNames[5] + ": " + TableSpoon.convertToTableSpoon(dimension, pattern, products.getSelectedItemPosition()));
        teaS.setText(dimensionNames[6] + ": " + TeaSpoon.convertToTeaSpoon(dimension, pattern, products.getSelectedItemPosition()));

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(position>=4)
            position++;
        dimension = Ingredient.setDimensionType(position);
        watcher.afterTextChanged(inputValue.getText());
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
