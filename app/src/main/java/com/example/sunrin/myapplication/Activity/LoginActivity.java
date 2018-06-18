package com.example.sunrin.myapplication.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sunrin.myapplication.Data.UserModel;
import com.example.sunrin.myapplication.R;
import com.example.sunrin.myapplication.Server.RetrofitItner;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    Button btn_login;
    Button btn_register;
    EditText edit_school;
    EditText edit_identity;
    EditText edit_birth;
    EditText edit_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btn_login = (Button)findViewById(R.id.btn_login);
        btn_register = (Button)findViewById(R.id.btn_register);
        edit_school = (EditText) findViewById(R.id.edit_school);
        edit_identity = (EditText)findViewById(R.id.edit_identity);
        edit_birth = (EditText)findViewById(R.id.edit_birth);
        edit_password = (EditText)findViewById(R.id.edit_password);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String school = edit_school.getText().toString();
                String identity = edit_school.getText().toString();
                String birth = edit_school.getText().toString();
                String password = edit_school.getText().toString();

                if((birth.length() != 8) || password.length() < 8){
                    Toast.makeText(LoginActivity.this, "Try Again", Toast.LENGTH_LONG).show();
                }else{
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(RetrofitItner.BaseUrl)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();

                    RetrofitItner apiRequest = retrofit.create(RetrofitItner.class);

                    apiRequest.login(edit_school.getText().toString(), edit_identity.getText().toString(), edit_birth.getText().toString(), edit_password.getText().toString()).enqueue(new Callback<UserModel>() {
                        @Override
                        public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                            Intent photoListActivity = new Intent(LoginActivity.this, PhotoListActivity.class);
                            startActivity(photoListActivity);
                            finish();
                        }

                        @Override
                        public void onFailure(Call<UserModel> call, Throwable t) {
                            Toast.makeText(LoginActivity.this, "서버에러", Toast.LENGTH_SHORT).show();
                            Log.e("Error", t.getMessage());
                        }
                    });

                }
            }
        });

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(registerIntent);
                finish();
            }
        });
    }
}
