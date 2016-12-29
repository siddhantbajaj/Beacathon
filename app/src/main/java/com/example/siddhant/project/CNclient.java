package com.example.siddhant.project;

import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by siddhant on 29/12/16.
 */

public class CNclient {
    public static CNInterface service;

    public static CNInterface getService() {
        if(service==null)
        {
            service= new Retrofit.Builder().baseUrl("https://developers.zomato.com").addConverterFactory(GsonConverterFactory.create(new GsonBuilder().serializeNulls().create())).build().create(CNInterface.class);
        }
        return service;
    }
}
