package com.example.sunrin.myapplication.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sunrin.myapplication.Data.PhotoModel;
import com.example.sunrin.myapplication.R;
import com.example.sunrin.myapplication.Server.RetrofitItner;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PhotoInfoActivity extends AppCompatActivity {

    TextView date, title, summary;
    ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_info);

        img = findViewById(R.id.img_image);
        summary = findViewById(R.id.txt_infoo);

        Intent intent = getIntent();
        String url = intent.getStringExtra("url");

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(RetrofitItner.BaseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitItner apiRequest = retrofit.create(RetrofitItner.class);

        apiRequest.getPhotoData(url).enqueue(new Callback<PhotoModel>() {
            @Override
            public void onResponse(Call<PhotoModel> call, Response<PhotoModel> response) {
                Picasso.with(PhotoInfoActivity.this).load(response.body().getPhoto()).into(img);
                summary.setText(response.body().getSummary());
            }

            @Override
            public void onFailure(Call<PhotoModel> call, Throwable t) {
                Toast.makeText(PhotoInfoActivity.this, "서버에러", Toast.LENGTH_SHORT).show();
                Log.e("Error", t.getMessage());
            }
        });


    }
}
