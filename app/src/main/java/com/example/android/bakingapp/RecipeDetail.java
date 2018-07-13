package com.example.android.bakingapp;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.android.bakingapp.Adapter.StepAdapter;
import com.example.android.bakingapp.Model.Recipe;
import com.example.android.bakingapp.Model.Step;

public class RecipeDetail extends AppCompatActivity implements MasterListFragment.OnClickListener,StepAdapter.OnStepClickListener {
public static int type;
public static Recipe recipe;


    boolean mTwoPane;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);


        if(findViewById(R.id.linear_layout)!=null)
        {

            mTwoPane = true;



        }
else {
            mTwoPane = false;
        }


        Recipe recipe = (Recipe)getIntent().getSerializableExtra("Recipe");



        MasterListFragment f = new MasterListFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();


        fragmentManager.beginTransaction()
                .add(R.id.fragmentcontainer, f)
                .commit();



    }

    @Override
    public void onClicked(Recipe r, int type) {

this.type = type;
this.recipe = r;

        if(mTwoPane)
        {

if(type==1)
{

    IngredientFragment f = new IngredientFragment();
    FragmentManager fragmentManager = getSupportFragmentManager();

    f.setRecipe(r);
    f.setType(type);

    fragmentManager.beginTransaction()
            .replace(R.id.fragmentcontainer1, f)
            .commit();

}

        }




        else
            {

                Intent intent = new Intent(getApplicationContext(), IngredientActivity.class);
                intent.putExtra("Recipe",recipe);
                intent.putExtra("rrr",1);
                startActivity(intent);

        }




    }

    @Override
    public void onStepClicked(Step r, int type) {
        if(mTwoPane)
        {

            if (type==2)
            {

                stepsFragment f = new stepsFragment();
                FragmentManager fragmentManager = getSupportFragmentManager();

                f.setStep(r);
                f.setType(type);

                fragmentManager.beginTransaction()
                        .replace(R.id.fragmentcontainer1, f)
                        .commit();

            }

        }


        else
        {

            Intent intent = new Intent(getApplicationContext(), IngredientActivity.class);
            intent.putExtra("Step",r);
            intent.putExtra("rrr",2);
            startActivity(intent);

        }






    }
}
