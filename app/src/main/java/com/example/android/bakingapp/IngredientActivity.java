package com.example.android.bakingapp;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.example.android.bakingapp.Model.Ingredient;
import com.example.android.bakingapp.Model.Recipe;
import com.example.android.bakingapp.Model.Step;

import java.util.ArrayList;

public class IngredientActivity extends AppCompatActivity {

    private boolean videoFragment=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredient);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        Recipe recipe;
        Step step;
ArrayList<Ingredient> ingredient;


int type = getIntent().getIntExtra("rrr",0);


        Log.v("type activity",type+"");
if (type==1)

{
    recipe = (Recipe)getIntent().getSerializableExtra("Recipe");
    ingredient = recipe.getIngredients();
    IngredientFragment f = new IngredientFragment();
    FragmentManager fragmentManager = getSupportFragmentManager();

    fragmentManager.beginTransaction()
            .add(R.id.fragmentcontainer1, f)
            .commit();
}

if (type==2)
{
   if (savedInstanceState != null) {
       videoFragment = savedInstanceState.getBoolean("VideoRotation");
   }
Log.v("VideoFragment","ffff");
    if(!videoFragment)
    {
        Log.v("VideoFragment","iff");
        videoFragment = true;
    step = (Step)getIntent().getSerializableExtra("Step");
    StepssFragment f = new StepssFragment();
    FragmentManager fragmentManager = getSupportFragmentManager();

    fragmentManager.beginTransaction()
            .replace(R.id.fragmentcontainer1, f)
            .commit();
    }

}


        if (type==3)

        {

            IngredientFragment f = new IngredientFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
                f.setType(3);
            fragmentManager.beginTransaction()
                    .add(R.id.fragmentcontainer1, f)
                    .commit();

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        //Back Button to navigate back to the details screen
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }
        return true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
       outState.putBoolean("VideoRotation", videoFragment);
    }

}
