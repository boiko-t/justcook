package com.example.taya.justcook.domain.adapter.image;

import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class PicassoRecipeImageAdapter implements RecipeImageAdapter {

    @Override
    public void setImageSourceIntoView(ImageView imageView, String source) {
        Picasso.get().load(source).into(imageView);
    }
}
