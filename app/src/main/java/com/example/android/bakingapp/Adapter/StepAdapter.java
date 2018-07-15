package com.example.android.bakingapp.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.android.bakingapp.Model.Step;
import com.example.android.bakingapp.R;

import java.util.Collections;
import java.util.List;

/**
 * Created by SG on 7/9/2018.
 */

public class StepAdapter extends RecyclerView.Adapter<StepAdapter.MyViewHolder> {


    private final LayoutInflater mInflator;
    private Context mContext;
    List<Step> mData = Collections.emptyList();

    OnStepClickListener mCallback;
    public interface OnStepClickListener {
        void onStepClicked(Step r,int type);
    }

    public StepAdapter(Context mContext,  List<Step> mData) {
        this.mInflator =LayoutInflater.from(mContext);
        this.mContext = mContext;
        this.mData = mData;
        mCallback = (OnStepClickListener) mContext;
    }




    @Override
    public StepAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = mInflator.inflate(R.layout.step_item,parent,false);
        StepAdapter.MyViewHolder holder =new StepAdapter.MyViewHolder(view);
        return holder;
    }



    @Override
    public void onBindViewHolder(StepAdapter.MyViewHolder holder, int position) {


        final Step currentStep = mData.get(position);



        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


               mCallback.onStepClicked(currentStep,2);


            }
        });





    holder.MasterItemsss.setText(currentStep.getShortDescription());

    }



    @Override
    public int getItemCount() {


        Log.v("ItemCount",mData.size()+"");

        return mData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView MasterItemsss;

        public MyViewHolder(View itemView) {
            super(itemView);
            MasterItemsss = (TextView)itemView.findViewById(R.id.MasterItemsss);

        }
    }





}
