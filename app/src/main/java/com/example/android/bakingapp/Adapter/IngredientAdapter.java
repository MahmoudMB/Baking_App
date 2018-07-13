package com.example.android.bakingapp.Adapter;

/**
 * Created by SG on 7/8/2018.
 */
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.bakingapp.Model.Ingredient;
import com.example.android.bakingapp.R;

import java.util.Collections;
import java.util.List;


public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.MyViewHolder> {


    private final LayoutInflater mInflator;
    private Context mContext;
    List<Ingredient> mData = Collections.emptyList();


    public IngredientAdapter(Context mContext, List<Ingredient> mData) {
        this.mInflator =LayoutInflater.from(mContext);
        this.mContext = mContext;
        this.mData = mData;

    }




    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = mInflator.inflate(R.layout.ing_item,parent,false);
        MyViewHolder holder =new MyViewHolder(view);
        return holder;
    }



    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        final Ingredient currentIngredient = mData.get(position);

        holder.Ingredient_Name.setText(currentIngredient.getIngredient());
        holder.Ingredient_Quantity.setText(currentIngredient.getQuantity()+"");
        holder.Ingredient_size.setText(currentIngredient.getMeasure());
    }



    @Override
    public int getItemCount() {


        Log.v("ItemCount",mData.size()+"");

        return mData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView Ingredient_Name;
        TextView Ingredient_Quantity;

        TextView Ingredient_size;


        public MyViewHolder(View itemView) {
            super(itemView);
            Ingredient_Name = (TextView)itemView.findViewById(R.id.Ingredient_Name);
            Ingredient_Quantity = (TextView)itemView.findViewById(R.id.Ingredient_Quantity);
            Ingredient_size = (TextView)itemView.findViewById(R.id.Ingredient_size);
        }




    }


}
