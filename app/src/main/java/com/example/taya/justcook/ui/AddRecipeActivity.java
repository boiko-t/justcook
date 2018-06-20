package com.example.taya.justcook.ui;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.taya.justcook.domain.entity.Category;
import com.example.taya.justcook.dal.DBHelper;
import com.example.taya.justcook.domain.entity.Ingredient;
import com.example.taya.justcook.R;
import com.example.taya.justcook.domain.entity.Recipe;
import com.example.taya.justcook.domain.entity.dimension.Dimension;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class AddRecipeActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageButton buttonIngredient, buttonPhoto;
    private LinearLayout placeForIngredients;
    private Spinner categorySpinner;
    private EditText recipeName, recipeDescription, recipeCooking;
    private int cookingCounter=1;
    private final int maxIngredientsCount=15;
    private String categories[]= Category.getCategoryNames(), imagePath;
    private String dimensionNames[]= Dimension.getDimensionNames(true);
    private Recipe recipe;
    private static final int GALLERY_REQUEST=0;
    private TextWatcher watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            int i = s.length();
            if(i==0) {
                cookingCounter=1;
                if(before==1)
                {
                    recipeCooking.append("1. ");
                    cookingCounter++;
                }
                return;
            }
            if(s.charAt(i-1)=='\n' && count==0) {
                cookingCounter--;
                return;
            }
            if(s.charAt(i-1)=='\n' && count!=0){
                recipeCooking.append(cookingCounter+". ");
                cookingCounter++;
            }
        }

        @Override
        public void afterTextChanged(Editable s) {
            int i = s.length();
            if(i==0)
                return;
            if(s.charAt(i-1)=='\n')
                s.delete(i-1, i);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        buttonIngredient = (ImageButton)findViewById(R.id.buttonAddIngredient);
        buttonPhoto=(ImageButton)findViewById(R.id.buttonAddPhoto);
        buttonPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentGallery=new Intent(Intent.ACTION_PICK);
                intentGallery.setType("image/*");
                startActivityForResult(intentGallery, GALLERY_REQUEST);
            }
        });
        buttonIngredient.setOnClickListener(this);
        placeForIngredients = (LinearLayout)findViewById(R.id.placeForIngredients);
        recipeName = (EditText)findViewById(R.id.recipe_name);
        recipeDescription = (EditText)findViewById(R.id.recipe_description);
        recipeCooking = (EditText)findViewById(R.id.edTextCookingDescription);
        recipeCooking.addTextChangedListener(watcher);
        recipeCooking.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus)
                {
                    if(recipeCooking.getText().toString().equals("")) {
                        recipeCooking.setText("1. ");
                        cookingCounter=2;
                    }
                }else{
                    if(recipeCooking.getText().toString().length()<4)
                        recipeCooking.setText("");
                }

            }
        });
        LinearLayout firstIngredient = (LinearLayout)findViewById(R.id.ingredient);
        setDimensionSpinner((Spinner)firstIngredient.getChildAt(2));
        firstIngredient.getChildAt(3).setOnClickListener(this);
        categorySpinner = (Spinner)findViewById(R.id.spinnerCategory);
        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categories);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(categoryAdapter);
        categorySpinner.setSelection(0);
        recipe=new Recipe();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap galleryPic = null;

        Uri selectedImage = data.getData();
        try {
            galleryPic = android.provider.MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImage);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        imagePath = selectedImage.getPath();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_recipe, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        Intent intent = new Intent();
        if(id == R.id.action_confirms) {
            recipe = saveDataToObject();
            recipe.setImagePath(imagePath);
            DBHelper dbHelper = new DBHelper(this);
            SQLiteDatabase database = dbHelper.getWritableDatabase();
            dbHelper.addRecipe(recipe, database);
            setResult(RESULT_OK, intent);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setDimensionSpinner(Spinner v)
    {
        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, dimensionNames);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        v.setAdapter(categoryAdapter);
        v.setSelection(0);
    }
    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id==R.id.buttonAddIngredient)
        {
            if(placeForIngredients.getChildCount()==maxIngredientsCount)
            {
                Toast.makeText(AddRecipeActivity.this, R.string.toManyIngredients, Toast.LENGTH_LONG).show();
                return;
            }
            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            LinearLayout newLay = (LinearLayout) inflater.inflate(R.layout.list_item_add_rec, null);
            setDimensionSpinner((Spinner) newLay.getChildAt(2));
            newLay.getChildAt(3).setOnClickListener(this);
            placeForIngredients.addView(newLay);
        }else{
            if(placeForIngredients.getChildCount()==1)
                return;
            View layout = (View) v.getParent();
            placeForIngredients.removeView(layout);
        }
    }

    private Recipe saveDataToObject(){
        ArrayList<Ingredient> ingredientList = new ArrayList<>();
        LinearLayout l;
        for (int i=0; i<placeForIngredients.getChildCount(); i++) {
            Ingredient ing = new Ingredient();
            l=(LinearLayout)placeForIngredients.getChildAt(i);
            if(!ing.loadIngredientFromLayout(l))
                continue;
            ingredientList.add(ing);
        }
        String name, description, cooking;
        name=recipeName.getText().toString();
        description=recipeDescription.getText().toString();
        cooking=recipeCooking.getText().toString();
        Category c = Category.values()[categorySpinner.getSelectedItemPosition()];
        return new Recipe(name, description, cooking, ingredientList, c);
    }
}
