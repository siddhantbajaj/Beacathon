package com.example.siddhant.project;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

/**
 * Created by siddhant on 29/12/16.
 */

public interface CNInterface {
    @GET("/api/v2.1/reviews")
    Call<ReviewResponse> getCity(@Header("Accept") String header1, @Header("user-key") String header2, @Query("res_id") String id);
    @GET("/api/v2.1/restaurant")
    Call<RestrauntResponse> getinfo(@Header("Accept") String header1, @Header("user-key") String header2, @Query("res_id") String id);
}
