package com.example.taya.justcook.dal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.taya.justcook.domain.entity.Category;
import com.example.taya.justcook.domain.entity.Ingredient;
import com.example.taya.justcook.domain.entity.Recipe;

import java.util.ArrayList;

/**
 * Created by tayab on 06.06.2016.
 */
public class DBHelper extends SQLiteOpenHelper {
    public static DBHelper instance;

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "RecipesDataBase.db";
    public static final String TABLE_RECIPES = "Recipes";
    public static final String ID = "_id";
    public static final String RECIPE_NAME = "name";
    public static final String RECIPE_DESCRIPTION = "description";
    public static final String RECIPE_CATEGORY = "category";
    public static final String RECIPE_COOKING = "cooking";
    public static final String RECIPE_IMAGE_PATH = "img_path";
    public static final String RECIPE_LIKE = "like";
    public static final String TABLE_INGREDIENT = "Ingredients";
    public static final String INGREDIENT_NAME = "name";
    public static final String KEY_RECIPE_ID = "recipe_id";
    public static final String INGREDIENT_AMOUNT = "amount";
    public static final String INGREDIENT_DIMENSION = "dimension";

    public static DBHelper getInstance(Context context) {
        if (instance == null) {
            instance = new DBHelper(context);
        }

        return instance;
    }

    private DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String CREATE_TABLE_RECIPE = "CREATE TABLE " + TABLE_RECIPES +
                " ( " + ID + " INTEGER PRIMARY KEY ," +
                RECIPE_NAME + " TEXT," +
                RECIPE_DESCRIPTION + " TEXT," +
                RECIPE_CATEGORY + " INTEGER," +
                RECIPE_COOKING + " TEXT," +
                RECIPE_IMAGE_PATH + " TEXT," +
                RECIPE_LIKE + " INTEGER)";
        final String CREATE_TABLE_INGREDIENTS = "CREATE TABLE " + TABLE_INGREDIENT +
                " ( " + ID + " INTEGER PRIMARY KEY," +
                KEY_RECIPE_ID + " INTEGER, " +
                INGREDIENT_NAME + " TEXT, " +
                INGREDIENT_AMOUNT + " REAL, " +
                INGREDIENT_DIMENSION + " TEXT, " +
                "FOREIGN KEY(" + KEY_RECIPE_ID + ") REFERENCES " + TABLE_RECIPES + " (" + ID + "))";
        db.execSQL(CREATE_TABLE_RECIPE);
        db.execSQL(CREATE_TABLE_INGREDIENTS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECIPES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_INGREDIENT);
        this.onCreate(db);
    }

    public void addRecipe(Recipe recipe, SQLiteDatabase db) {
        ContentValues values = new ContentValues();
        values.put(RECIPE_NAME, recipe.getRecipeName());
        values.put(RECIPE_DESCRIPTION, recipe.getRecipeDescription());
        values.put(RECIPE_CATEGORY, recipe.getCategory());
        values.put(RECIPE_COOKING, recipe.getRecipeCooking());
        values.put(RECIPE_IMAGE_PATH, recipe.getImagePath());
        values.put(RECIPE_LIKE, 0);
        long newId = db.insert(TABLE_RECIPES, null, values);
        ArrayList<Ingredient> list = recipe.getIngredientList();
        for (Ingredient ingredient : list) {
            ContentValues content = new ContentValues();
            content.put(INGREDIENT_NAME, ingredient.getIngredientName());
            content.put(INGREDIENT_AMOUNT, ingredient.getDimension().getValue());
            content.put(INGREDIENT_DIMENSION, ingredient.getDimension().getName());
            content.put(KEY_RECIPE_ID, newId);
            db.insert(TABLE_INGREDIENT, null, content);
        }
    }

    public Cursor getDataByCategory(int category, SQLiteDatabase db) {
        if (category == Category.ALL.ordinal())
            return db.rawQuery("SELECT " + ID + ", " + RECIPE_NAME + " FROM " + TABLE_RECIPES, null);
        if (category == Category.FAVOURITE.ordinal())
            return db.rawQuery("SELECT " + ID + ", " + RECIPE_NAME + " FROM " +
                    TABLE_RECIPES + " WHERE " + RECIPE_LIKE + " = ?", new String[]{"1"});
        return db.rawQuery("SELECT " + ID + ", " + RECIPE_NAME + " FROM " +
                TABLE_RECIPES + " WHERE " + RECIPE_CATEGORY + " = ?", new String[]{"" + category});
    }

    public void deleteRecipe(long id, SQLiteDatabase db) {
        db.delete(TABLE_RECIPES, ID + " =? ", new String[]{String.valueOf(id)});
        db.delete(TABLE_INGREDIENT, KEY_RECIPE_ID + " =? ", new String[]{String.valueOf(id)});
    }

    public void toggleRecipeLike(boolean like, long id, SQLiteDatabase db) {
        ContentValues values = new ContentValues();
        int l = 0;
        if (like)
            l = 1;
        values.put(RECIPE_LIKE, l);
        db.update(TABLE_RECIPES, values, ID + " =? ", new String[]{"" + id});
    }

    public Recipe getRecipeById(long id, SQLiteDatabase db) {
        Cursor c = db.query(TABLE_RECIPES, new String[]{RECIPE_NAME, RECIPE_DESCRIPTION, RECIPE_COOKING, RECIPE_CATEGORY, RECIPE_IMAGE_PATH, RECIPE_LIKE},
                ID + "= ? ", new String[]{"" + id}, null, null, null);
        Cursor c1 = db.query(TABLE_INGREDIENT, new String[]{INGREDIENT_NAME, INGREDIENT_AMOUNT, INGREDIENT_DIMENSION},
                KEY_RECIPE_ID + "= ? ", new String[]{"" + id}, null, null, null);
        String[] stringsIngr = new String[c1.getCount()];
        c.moveToFirst();
        if (c1.moveToFirst()) {
            int i = 0;
            do {
                for (String cn : c1.getColumnNames()) {
                    if (stringsIngr[i] == null)
                        stringsIngr[i] = c1.getString(c1.getColumnIndex(cn)) + " ";
                    else stringsIngr[i] += c1.getString(c1.getColumnIndex(cn)) + " ";
                }
                i++;
            } while (c1.moveToNext());
        }
        boolean like = true;
        if (c.getString(c.getColumnIndex(RECIPE_LIKE)).equals("0"))
            like = false;
        return new Recipe(c.getString(c.getColumnIndex(RECIPE_NAME)), c.getString(c.getColumnIndex(RECIPE_DESCRIPTION)), c.getString(c.getColumnIndex(RECIPE_COOKING)),
                Category.values()[Integer.parseInt(c.getString(c.getColumnIndex(RECIPE_CATEGORY)))],
                stringsIngr, c.getString(c.getColumnIndex(RECIPE_IMAGE_PATH)), like);
    }
}
