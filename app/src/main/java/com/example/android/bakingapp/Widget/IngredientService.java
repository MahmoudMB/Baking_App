package com.example.android.bakingapp.Widget;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.android.bakingapp.Model.Ingredient;
import com.example.android.bakingapp.Model.Recipe;
import com.example.android.bakingapp.R;

import java.util.ArrayList;

/**
 * Created by SG on 7/11/2018.
 */

public class IngredientService extends IntentService {

    public static final String Extra_Ing = "ExtraIng";
    public static final String Extra_Measure = "ExtraMeasure";
    public static final String Extra_Quantity = "ExtraQuantity";
    public static final String Widget_Action = "WidgetAction";
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @paramname Used to name the worker thread, important only for debugging.
     */
    public IngredientService() {
        super("IngredientService");
    }







    public static void StartIngredientService(Context context,Recipe recipe) {

        Intent intent = new Intent(context, IngredientService.class);
        intent.setAction(Widget_Action);


        ArrayList<Ingredient> ii = recipe.getIngredients();





        ArrayList<String> Ing = new ArrayList<String>();
        ArrayList<String> Measure = new ArrayList<String>();
        ArrayList<String> Quantity = new ArrayList<String>();



        for(int i=0;i<ii.size();i++)
        {
            Ing.add(ii.get(i).getIngredient());
            Measure.add(ii.get(i).getMeasure());
            Quantity.add(ii.get(i).getQuantity()+"");
        }

        intent.putExtra(Extra_Ing, Ing);
        intent.putExtra(Extra_Measure, Measure);
        intent.putExtra(Extra_Quantity, Quantity);
        context.startService(intent);

    }



    @Override
    protected void onHandleIntent(@Nullable Intent intent) {


        if (intent != null) {
           ArrayList<String> Ing = intent.getExtras().getStringArrayList(Extra_Ing);
            ArrayList<String> Measure = intent.getExtras().getStringArrayList(Extra_Measure);
            ArrayList<String> Quantity = intent.getExtras().getStringArrayList(Extra_Quantity);
            handleActionUpdateIngredient(Ing,Measure,Quantity);

        }


    }




    private void handleActionUpdateIngredient(ArrayList<String>  Ing,ArrayList<String> Measure,ArrayList<String> Quantity) {
        Intent intent = new Intent(Widget_Action);
        intent.setAction(Widget_Action);
        intent.putExtra(Extra_Ing, Ing);
        intent.putExtra(Extra_Measure, Measure);
        intent.putExtra(Extra_Quantity, Quantity);


        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, IngredientWidget.class));
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.ListViewIng);
        //Now update all widgets
        IngredientWidget.UpdateWidgets(this, appWidgetManager,appWidgetIds);
        sendBroadcast(intent);

    }





}
