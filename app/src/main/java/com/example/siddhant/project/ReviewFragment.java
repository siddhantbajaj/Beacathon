package com.example.siddhant.project;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReviewFragment extends AppCompatActivity  {
    public ArrayList<reviews> Reviews;
    public TabLayout tabLayout;
    public ViewPager TabPager1;

    private ArrayAdapter<String> arrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_fragment);
        final ArrayList<String>item=new ArrayList<>();



        Reviews=new ArrayList<>();

        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, item);
        ListView listView = (ListView)findViewById(R.id.list_view);

        listView.setAdapter(arrayAdapter);
        final ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Fetching Data");
        progressDialog.show();
        progressDialog.setCancelable(false);
        final CNInterface service= CNclient.getService();
        String regionName= getIntent().getStringExtra("id");
        Call<ReviewResponse> call=service.getCity("application/json","ec0c14b3b6fcb260c79e323df7697b1d",regionName);
        call.enqueue(new Callback<ReviewResponse>() {
            @Override
            public void onResponse(Call<ReviewResponse> call, Response<ReviewResponse> response) {
                Log.i("test","test");
                if (response.isSuccessful())
                {
                    Reviews=response.body().getUser_reviews();
                    for (reviews r:Reviews){
                        item.add(r.getReview().getReview_text());
                    }
                    arrayAdapter.notifyDataSetChanged();
                    progressDialog.dismiss();


                }

            }

            @Override
            public void onFailure(Call<ReviewResponse> call, Throwable t) {
                Log.i("fail","fail");

            }
        });

    }


}
