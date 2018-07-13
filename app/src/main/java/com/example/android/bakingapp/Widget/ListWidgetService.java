package com.example.android.bakingapp.Widget;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.android.bakingapp.R;

import java.util.ArrayList;

/**
 * Created by SG on 7/10/2018.
 */



public class ListWidgetService extends RemoteViewsService {




    public static final String Widget_Action = "WidgetAction";;
    public static final String Extra_Measure = "ExtraMeasure";
    public static final String Extra_Quantity = "ExtraQuantity";
    public static final String Extra_Ing = "ExtraIng";


     ArrayList<String> Ing = new ArrayList<>();
      ArrayList<String> Measure = new ArrayList<>();
      ArrayList<String> Quantity = new ArrayList<>();




    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new ListRemoteViewsFactory(this.getApplicationContext());
    }


    class ListRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

        Context mContext;


        public ListRemoteViewsFactory(Context mContext) {
            this.mContext = mContext;

        }


        @Override
        public void onCreate() {
            Log.v("OnCreate","OnCreate");
        }

        @Override
        public void onDataSetChanged() {


             Ing = IngredientWidget.Ing;
             Measure = IngredientWidget.Measure;
             Quantity = IngredientWidget.Quantity;


        }

        @Override
        public void onDestroy() {


            Ing=new ArrayList<>();
            Measure=new ArrayList<>();
            Quantity=new ArrayList<>();

        }

        @Override
        public int getCount() {

            return Ing.size();

        }

        @Override
        public RemoteViews getViewAt(int position) {

            Log.v("GetView","GetViews");
             String currentIngredient = Ing.get(position);
             String currentMeasure = Measure.get(position);
             String currentQuantity = Quantity.get(position);


            RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.ing_widget_items);

            views.setTextViewText(R.id.Ingredient_Name_Widget,currentIngredient);

            views.setTextViewText(R.id.Ingredient_Quantity_Widget,currentQuantity);

            views.setTextViewText(R.id.Ingredient_size_Widget,currentMeasure);


            Bundle extras = new Bundle();
extras.putStringArrayList(IngredientWidget.Extra_Ing,IngredientWidget.Ing);

            extras.putStringArrayList(IngredientWidget.Extra_Measure,IngredientWidget.Measure);

            extras.putStringArrayList(IngredientWidget.Extra_Quantity,IngredientWidget.Quantity);
            extras.putInt("rrr",3);
            Intent fillInIntent = new Intent();
            fillInIntent.putExtras(extras);
            views.setOnClickFillInIntent(R.id.WidgetRelativeLayout, fillInIntent);

            return views;


        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }
    }
}