package com.example.android.bakingapp;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NavUtils;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

/**
 * Created by SG on 7/9/2018.
 */
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import android.content.Intent;

import com.example.android.bakingapp.Adapter.StepAdapter;
import com.example.android.bakingapp.Model.Recipe;
import com.example.android.bakingapp.Model.Step;

public class MasterListFragment extends Fragment {


    private RecyclerView mRecyclerViewSteps;
    private StepAdapter stepAdapter;
    ArrayList<Step> steps;

    public MasterListFragment(){}

    OnClickListener mCallback;
    public interface OnClickListener {
        void onClicked(Recipe r, int type);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);


        try {
            mCallback = (OnClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnClickListener");
        }
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final  View rootView = inflater.inflate(R.layout.master_list,container,false);

      Intent i = getActivity().getIntent();

         final Recipe recipe = (Recipe)i.getSerializableExtra("Recipe");

        RecyclerView mRecyclerViewSteps = (RecyclerView) rootView.findViewById(R.id.StepsRecycler);

        ArrayList<Step> steps = recipe.getSteps();
        StepAdapter adapter = new StepAdapter(getContext(),steps);

        mRecyclerViewSteps.setHasFixedSize(true);
        mRecyclerViewSteps.setLayoutManager(new LinearLayoutManager(getContext()));

        mRecyclerViewSteps.setAdapter(adapter);


        TextView RecipeIngredient = (TextView)rootView.findViewById(R.id.RecipeIngredient);
        TextView RecipeServings = (TextView)rootView.findViewById(R.id.RecipeServings);
        TextView  RecipeName = (TextView)rootView.findViewById(R.id. RecipeNameDetail);

        RecipeIngredient.setText("Ingredients");
        RecipeServings.setText("Servings : "+ recipe.getServings()+"");
RecipeName.setText(recipe.getName());


RecipeIngredient.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {


        mCallback.onClicked(recipe,1);

    }





});






        return rootView;


    }









}




