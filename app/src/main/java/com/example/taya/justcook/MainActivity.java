package com.example.taya.justcook;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Display;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.taya.justcook.fragments.ListFragment;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ListFragment listFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setImageResource(R.drawable.ic_add);

        fab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent in = new Intent(MainActivity.this, AddRecipe.class);
                startActivityForResult(in, 1);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ImageView images[] =
                {(ImageView)findViewById(R.id.imgDesserts),
                        (ImageView)findViewById(R.id.imgMainDish),
                        (ImageView)findViewById(R.id.imgSalad),
                        (ImageView)findViewById(R.id.imgSoup)
                };
        Display display = getWindowManager().getDefaultDisplay();
        int height = display.getHeight();
        for (ImageView v:images) {
            v.getLayoutParams().height = height / 3;
            v.setOnClickListener(pictureClick);
        }
        listFragment=new ListFragment();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode!=RESULT_OK)
            return;
        if(requestCode==1)
            Toast.makeText(MainActivity.this, "Рецепт успішно створено", Toast.LENGTH_SHORT).show();
        if(requestCode==0)
            listFragment.setList();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }else if(listFragment.isAdded()){
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.remove(listFragment);
            ft.commit();
        }else {
            super.onBackPressed();
        }
    }
    View.OnClickListener pictureClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int id = v.getId();
            switch (id){
                case R.id.imgDesserts:
                    listFragment.setCategory(Category.DESSERT.ordinal());
                    break;
                case R.id.imgMainDish:
                    listFragment.setCategory(Category.MAIN_DISH.ordinal());
                    break;
                case R.id.imgSoup:
                    listFragment.setCategory(Category.SOUP.ordinal());
                    break;
                case R.id.imgSalad:
                    listFragment.setCategory(Category.SALAD.ordinal());
                    break;
            }
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.container, listFragment, "List");
            ft.commit();
        }
    };

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_all_recipes) {
            listFragment.setCategory(Category.ALL.ordinal());
            if(listFragment.isAdded())
                listFragment.setList();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.container, listFragment, "List");
            ft.commit();
        } else if (id == R.id.nav_category) {
            if(listFragment.isAdded())
            {
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.remove(listFragment);
                ft.commit();
            }
        } else if (id == R.id.nav_favorite) {
            listFragment.setCategory(Category.FAVOURITE.ordinal());
            if(listFragment.isAdded())
                listFragment.setList();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.container, listFragment, "List");
            ft.commit();
        } else if (id == R.id.nav_calc) {
            Intent in = new Intent(MainActivity.this, FoodCalculatorActivity.class);
            startActivityForResult(in, 1);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
