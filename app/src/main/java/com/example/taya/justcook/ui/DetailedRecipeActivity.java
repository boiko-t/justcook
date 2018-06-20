package com.example.taya.justcook.ui;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.taya.justcook.dal.DBHelper;
import com.example.taya.justcook.R;
import com.example.taya.justcook.domain.entity.Recipe;

import java.io.FileNotFoundException;
import java.io.IOException;

public class DetailedRecipeActivity extends AppCompatActivity {

    Recipe recipe;
    DBHelper dbHelper;
    long id;
    ImageView imageView;
    TextView txtDescription, txtIngredients, txtCooking;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_recipe);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        Intent intent=getIntent();
        id=intent.getLongExtra("recordId", id);
        dbHelper=new DBHelper(this);
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        recipe = dbHelper.getRecipeById(id, database);
        database.close();
        getSupportActionBar().setTitle(recipe.getRecipeName());
        imageView=(ImageView)findViewById(R.id.image);
        txtDescription=(TextView)findViewById(R.id.txtDescription);
        txtIngredients=(TextView)findViewById(R.id.txtIngredients);
        txtCooking=(TextView)findViewById(R.id.txtCooking);
        txtDescription.setText("\n"+recipe.getRecipeDescription()+"\n"+"Інгредієнти:");
        for(String s:recipe.getIngredientText())
            txtIngredients.append(s+"\n");
        txtCooking.append(recipe.getRecipeCooking());
        setImage();
    }

    public void setImage(){
        Bitmap galleryPic;
        String s ="content://media"+recipe.getImagePath();
        Uri selectedImage = Uri.parse(s);
        try {
            galleryPic = android.provider.MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImage);
            imageView.setImageBitmap(galleryPic);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onBackPressed() {
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detailed_recipe, menu);
        if(recipe.isLike())
            menu.getItem(1).setIcon(R.drawable.ic_dislike);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int idItem = item.getItemId();
        if(idItem == R.id.action_delete_recipe) {
            SQLiteDatabase database = dbHelper.getWritableDatabase();
            dbHelper.deleteRecipe(id, database);
            database.close();
            Toast.makeText(DetailedRecipeActivity.this, "Рецепт успішно видалено", Toast.LENGTH_SHORT).show();
            setResult(RESULT_OK);
            finish();
        }
        if(idItem==R.id.action_favourite){
            recipe.setLike(!recipe.isLike());
            SQLiteDatabase database = dbHelper.getWritableDatabase();
            dbHelper.changeRecipeLike(recipe.isLike(), id, database);
            database.close();
            if(!recipe.isLike()) {
                item.setIcon(R.drawable.ic_like);
                Toast.makeText(DetailedRecipeActivity.this, "Рецепт видалено з улюблених", Toast.LENGTH_SHORT).show();
            }
            else {
                item.setIcon(R.drawable.ic_dislike);
                Toast.makeText(DetailedRecipeActivity.this, "Рецепт додано до улюблених", Toast.LENGTH_SHORT).show();
            }
        }
        return super.onOptionsItemSelected(item);
    }

}
