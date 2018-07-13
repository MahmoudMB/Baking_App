package com.example.android.bakingapp;

/**
 * Created by SG on 7/9/2018.
 */
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

/**
 * Created by SG on 7/9/2018.
 */
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.bakingapp.Adapter.IngredientAdapter;
import com.example.android.bakingapp.Model.Ingredient;
import com.example.android.bakingapp.Model.Recipe;
import com.example.android.bakingapp.Widget.IngredientService;


public class IngredientFragment extends Fragment {

    private RecyclerView mRecyclerViewIngredient;
    private IngredientAdapter IngAdapter;

    public static final String Extra_Measure = "ExtraMeasure";
    public static final String Extra_Quantity = "ExtraQuantity";
    public static final String Extra_Ing = "ExtraIng";


    private int type;
    private Recipe recipe;
    public IngredientFragment() {
    }




    public void setType(int t){type = t;}
    public void setRecipe(Recipe r){recipe = r;}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        ArrayList<String> Ing = new ArrayList<>();
        ArrayList<String> Measure = new ArrayList<>();
        ArrayList<String> Quantity = new ArrayList<>();

        ArrayList<Ingredient> Ings = new ArrayList<>();



        if (recipe==null)
            recipe = (Recipe)getActivity().getIntent().getSerializableExtra("Recipe");
        else
            type = getActivity().getIntent().getIntExtra("rrr",0);


        Log.v("typeeee",type+"");
        if (type==3)
        {


          Ing = getActivity().getIntent().getStringArrayListExtra(Extra_Ing);

            Measure = getActivity().getIntent().getStringArrayListExtra(Extra_Measure);

            Quantity = getActivity().getIntent().getStringArrayListExtra(Extra_Quantity);


            for(int j=0;j<Ing.size();j++) {

            Ingredient ii = new Ingredient(Integer.valueOf(Quantity.get(j)),Measure.get(j),Ing.get(j));
                Ings.add(ii);
            }


        }
else{



            Ings = recipe.getIngredients();
        }

        final  View rootView = inflater.inflate(R.layout.ing_list,container,false);


        RecyclerView mRecyclerViewSteps = (RecyclerView) rootView.findViewById(R.id.IngssRecycler);



        IngredientAdapter adapter = new IngredientAdapter(getContext(),Ings);



        mRecyclerViewSteps.setHasFixedSize(true);
        mRecyclerViewSteps.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerViewSteps.setAdapter(adapter);

if(type!=3)
        IngredientService.StartIngredientService(getContext(),recipe);
        return rootView;
    }


}
