package com.example.android.bakingapp;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.android.bakingapp.Model.Ingredient;
import com.example.android.bakingapp.Model.Recipe;
import com.example.android.bakingapp.Model.Step;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by SG on 7/8/2018.
 */

public class Utils {


    private Context context;
    private  static Utils instance;

    private Utils(Context context)
    {

        this.context = context;
    }



    public static synchronized Utils getInstance(Context context)
    {
        if(instance == null)
        {
            instance = new Utils(context);
        }

        return instance;
    }


    public  void GetRecipes(final VolleyCallBack callback)
    {

        final ArrayList<Recipe> recipes = new ArrayList<Recipe>();
        String Url = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response)
            {

                try {
                    JSONArray initial = new JSONArray(response);


                    for(int i=0;i<initial.length();i++)
                    {
                        JSONObject Recipe = initial.getJSONObject(i);
                        int id = Recipe.getInt("id");
                        Log.v("Extract Features",id+"");
                        String name = Recipe.getString("name");
                        JSONArray ingredientsArray = Recipe.getJSONArray("ingredients");
                        ArrayList<Ingredient> ingredients = new ArrayList<>();
                        for(int j=0;j<ingredientsArray.length();j++)
                        {
                            JSONObject IngredientObject = ingredientsArray.getJSONObject(j);
                            int quantity = IngredientObject.getInt("quantity");
                            String measure = IngredientObject.getString("measure");
                            String ingredient = IngredientObject.getString("ingredient");

                            Ingredient ingredient1 = new Ingredient(quantity,measure,ingredient);
                            ingredients.add(ingredient1);
                        }

                        JSONArray stepsArray = Recipe.getJSONArray("steps");



                        ArrayList<Step> steps = new ArrayList<>();
                        for(int j=0;j<stepsArray.length();j++)
                        {

                            JSONObject stepsObject = stepsArray.getJSONObject(j);
                            int id1 = stepsObject.getInt("id");
                            String shortDescription = stepsObject.getString("shortDescription");
                            String description = stepsObject.getString("description");
                            String videoURL = stepsObject.getString("videoURL");
                            String thumbnailURL = stepsObject.getString("thumbnailURL");



                            Step step1 = new Step(id1,shortDescription,description,videoURL,thumbnailURL);
                            steps.add(step1);


                        }

                        int servings = Recipe.getInt("servings");
                        String image = Recipe.getString("image");
                        Recipe r = new Recipe(id,name,ingredients,steps,servings,image);
                        recipes.add(r);
                    }
                }
                catch (JSONException ex)
                {
                    Log.v("Extract Features","",ex);
                }


                callback.onSuccess(recipes);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.v("Extratures",volleyError.getMessage());
            }
        });

        volley.getInstance(context).addToRequestQueue(stringRequest);

    }

}
