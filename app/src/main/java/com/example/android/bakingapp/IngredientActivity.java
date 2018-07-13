package com.example.android.bakingapp;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.android.bakingapp.Model.Ingredient;
import com.example.android.bakingapp.Model.Recipe;
import com.example.android.bakingapp.Model.Step;

import java.util.ArrayList;

public class IngredientActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredient);

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
    step = (Step)getIntent().getSerializableExtra("Step");
    stepsFragment f = new stepsFragment();
    FragmentManager fragmentManager = getSupportFragmentManager();

    fragmentManager.beginTransaction()
            .add(R.id.fragmentcontainer1, f)
            .commit();

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
}
