package com.example.taya.justcook.domain.entity;

import java.util.ArrayList;

public class Recipe {
    private String recipeName, recipeDescription, recipeCooking, imagePath;
    private Category category;
    private ArrayList <Ingredient> ingredientList;
    private String[] ingredientText;
    private boolean like;

    public boolean isLike() {
        return like;
    }

    public void setLike(boolean like) {
        this.like = like;
    }

    public String[] getIngredientText() {
        return ingredientText;
    }

    public void setIngredientText(String[] ingredientText) {
        this.ingredientText = ingredientText;
    }

    public ArrayList<Ingredient> getIngredientList() {
        return ingredientList;
    }

    public Recipe(){

    }
    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getImagePath() {
        return imagePath;
    }

    public int getCategory() {
        return category.ordinal();
    }

    public String getRecipeCooking() {
        return recipeCooking;
    }

    public String getRecipeDescription() {
        return recipeDescription;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public Recipe(String recipeName, String recipeDescription, String recipeCooking, ArrayList<Ingredient> ingredientList, Category category) {
        this.recipeName = recipeName;
        this.recipeDescription = recipeDescription;
        this.recipeCooking = recipeCooking;
        this.ingredientList = ingredientList;
        this.category=category;
    }
    public Recipe(String recipeName, String recipeDescription, String recipeCooking, Category category, String[] ingredientText, String imagePath, boolean like) {
        this.recipeName = recipeName;
        this.recipeDescription = recipeDescription;
        this.recipeCooking = recipeCooking;
        this.ingredientText = ingredientText;
        this.category=category;
        this.imagePath=imagePath;
        this.like=like;
    }
}
