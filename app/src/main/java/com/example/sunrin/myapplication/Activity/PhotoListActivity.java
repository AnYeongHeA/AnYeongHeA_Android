package com.example.sunrin.myapplication.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import com.example.sunrin.myapplication.Adapter.PhotoListAdapter;
import com.example.sunrin.myapplication.R;
import com.example.sunrin.myapplication.Server.RetrofitItner;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PhotoListActivity extends AppCompatActivity {

    GridView gridView;
    ArrayList<String> urls = new ArrayList<>();
    PhotoListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_list);
        gridView = findViewById(R.id.grid_view_photo_list);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(RetrofitItner.BaseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitItner apiRequest = retrofit.create(RetrofitItner.class);

        apiRequest.getAllPhotos().enqueue(new Callback<ArrayList<String>>() {
            @Override
            public void onResponse(Call<ArrayList<String>> call, final Response<ArrayList<String>> response) {
                Log.e("count", String.valueOf(response.body().size()));

                urls = response.body();

                //TODO URL to Bitmap 전환이 제대로 안됨 (돌려도 Null로 들어감)
                Log.e("DATATA", urls.toString());

                adapter = new PhotoListAdapter(urls);

                gridView.setAdapter(adapter);

                gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent(PhotoListActivity.this, PhotoInfoActivity.class);
                        intent.putExtra("url", urls.get(position));
                        startActivity(intent);
                    }
                });
            }

            @Override
            public void onFailure(Call<ArrayList<String>> call, Throwable t) {
                Log.e("Error", t.getMessage());
            }
        });

    }

}
