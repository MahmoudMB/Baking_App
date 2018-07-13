package com.example.android.bakingapp;

import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import android.util.Log;

import com.example.android.bakingapp.Adapter.MenuAdapter;
import com.example.android.bakingapp.Model.Recipe;

public class MainActivity extends AppCompatActivity implements VolleyCallBack {


    private RecyclerView mRecyclerView;
    private MenuAdapter menuAdapter;
    ArrayList<Recipe> recipes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        mRecyclerView = (RecyclerView)findViewById(R.id.recylerView);
        mRecyclerView.setHasFixedSize(true);

        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 1));



        if (findViewById(R.id.RecyclerViewMainTab)!=null)
            mRecyclerView.setLayoutManager(new GridLayoutManager(this, 4));

        else  if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE )
        {
            mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        }



        Utils.getInstance(getApplicationContext()).GetRecipes(MainActivity.this);


    }


    @Override
    public void onSuccess(ArrayList<Recipe> recipes) {

        this.recipes = recipes;

        menuAdapter = new MenuAdapter(MainActivity.this, recipes);
        mRecyclerView.setAdapter(menuAdapter);
    }


}
