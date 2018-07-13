package com.example.android.bakingapp;

import android.content.Context;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
/*** Created by SG on 7/8/2018.
1 */

public class volley {


    private  static volley instance;
    private RequestQueue requestQueue;
    private Context context;


    private volley(Context context)
    {

        this.context = context;
        requestQueue = Volley.newRequestQueue(context);
    }

    public static synchronized volley getInstance(Context context)
    {
        if(instance == null)
        {
            instance = new volley(context);
        }

        return instance;
    }


    public<T> void addToRequestQueue(Request<T> request)
    {

        requestQueue.add(request);
    }


}
