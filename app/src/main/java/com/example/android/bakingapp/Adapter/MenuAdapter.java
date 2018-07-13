package com.example.android.bakingapp.Adapter;

/**
 * Created by SG on 7/8/2018.
 */
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

//import com.bumptech.glide.Glide;

import com.bumptech.glide.Glide;
import com.example.android.bakingapp.Model.Recipe;
import com.example.android.bakingapp.R;
import com.example.android.bakingapp.RecipeDetail;

import java.util.Collections;
import java.util.List;


public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MyViewHolder> {


    private final LayoutInflater mInflator;
    private Context mContext;
    List<Recipe> mData = Collections.emptyList();


    public MenuAdapter(Context mContext, List<Recipe> mData) {
        this.mInflator =LayoutInflater.from(mContext);
        this.mContext = mContext;
        this.mData = mData;

    }




    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = mInflator.inflate(R.layout.menu_item,parent,false);
        MyViewHolder holder =new MyViewHolder(view);
        return holder;
    }



    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        final Recipe currentRecipe = mData.get(position);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, RecipeDetail.class);
                intent.putExtra("Recipe",currentRecipe);
                mContext.startActivity(intent);
            }
        });





holder.recipeName.setText(currentRecipe.getName());



Log.v("Videourl",currentRecipe.getSteps().get(currentRecipe.getSteps().size()-1).getVideoURL());

        Glide.with(mContext)
                .load(currentRecipe.getSteps().get(currentRecipe.getSteps().size()-1).getVideoURL())
                .thumbnail(Glide.with(mContext).load(currentRecipe.getSteps().get(currentRecipe.getSteps().size()-1).getVideoURL()))
                .into(holder.Recipe_Image);


    }



    @Override
    public int getItemCount() {


        Log.v("ItemCount",mData.size()+"");

        return mData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView recipeName;
ImageView Recipe_Image;

        public MyViewHolder(View itemView) {
            super(itemView);
            recipeName = (TextView)itemView.findViewById(R.id.Recipe_Name);
           Recipe_Image = (ImageView)itemView.findViewById(R.id.Recipe_Image);

        }




    }


}
