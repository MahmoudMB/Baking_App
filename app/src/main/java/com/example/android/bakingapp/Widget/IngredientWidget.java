package com.example.android.bakingapp.Widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

import com.example.android.bakingapp.IngredientActivity;
import com.example.android.bakingapp.R;

import java.util.ArrayList;

/**
 * Implementation of App Widget functionality.
 */

public class IngredientWidget extends AppWidgetProvider {

    public static final String Widget_Action = "WidgetAction";;
    public static final String Extra_Measure = "ExtraMeasure";
    public static final String Extra_Quantity = "ExtraQuantity";
    public static final String Extra_Ing = "ExtraIng";


    static  ArrayList<String> Ing = new ArrayList<>();
    static  ArrayList<String> Measure = new ArrayList<>();
    static  ArrayList<String> Quantity = new ArrayList<>();


    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.ingredient_widget_list_view);


        Intent appIntent = new Intent(context, IngredientActivity.class);
        appIntent.addCategory(Intent.ACTION_MAIN);
        appIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        appIntent.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT|Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent appPendingIntent = PendingIntent.getActivity(context, 0, appIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setPendingIntentTemplate(R.id.ListViewIng, appPendingIntent);



//        RemoteViews views = getListRemoteView(context);
        Intent intent = new Intent(context, ListWidgetService.class);

        intent.putExtra(Extra_Ing, Ing);

        intent.putExtra(Extra_Measure, Measure);
        intent.putExtra(Extra_Quantity, Quantity);


        views.setRemoteAdapter(R.id.ListViewIng, intent);








        appWidgetManager.updateAppWidget(appWidgetId, views);
    }




    private static RemoteViews getListRemoteView(Context context)
    {   RemoteViews views =new RemoteViews(context.getPackageName(),R.layout.ingredient_widget_list_view);
        Intent intent = new Intent(context,ListWidgetService.class);
        views.setRemoteAdapter(R.id.ListViewIng ,intent);

    Intent appIntent = new Intent(context,IngredientActivity.class);
    PendingIntent appPendingIntent = PendingIntent.getActivity(context,0,appIntent,PendingIntent.FLAG_UPDATE_CURRENT);
    views.setPendingIntentTemplate(R.id.ListViewIng,appPendingIntent);

      return views;
    }


    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }


    @Override
    public void onReceive(Context context, Intent intent) {



              AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(context, IngredientWidget.class));
        final String action = intent.getAction();
        if (action.equals(Widget_Action)) {

             Ing = intent.getExtras().getStringArrayList(Extra_Ing);

             Measure = intent.getExtras().getStringArrayList(Extra_Measure);
             Quantity = intent.getExtras().getStringArrayList(Extra_Quantity);
            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.ListViewIng);
            //Now update all widgets

            onUpdate(context, appWidgetManager, appWidgetIds);
            super.onReceive(context, intent);

        }
    }
}

