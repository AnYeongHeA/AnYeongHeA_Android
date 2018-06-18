package com.example.sunrin.myapplication.Server;

import com.example.sunrin.myapplication.Data.PhotoModel;
import com.example.sunrin.myapplication.Data.PhotobookModel;
import com.example.sunrin.myapplication.Data.SuccessModel;
import com.example.sunrin.myapplication.Data.UserModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;

public interface RetrofitItner {

    String BaseUrl = "http://soylatte.kr:5000";

    @FormUrlEncoded
    @POST("/auth/login")
    Call<UserModel> login(@Field("schoolName") String schoolName, @Field("schoolNumber") String schoolNumber, @Field("birthday") String birthday, @Field("password") String password);

    @FormUrlEncoded
    @POST("/auth/register")
    Call<SuccessModel> register(@Field("username") String username, @Field("schoolName") String schoolName, @Field("schoolNumber") String schoolNumber, @Field("birthday") String birthday, @Field("password") String password);

    //새 사진첩 생성
    //TODO /photobook/make 파일업로드 인터페이스 구현
    @Multipart
    @POST("/photobook/make")
    Call<SuccessModel> makePhotobook();

    //내가 작성할수 있는 사진첩 목록받아오기
    @FormUrlEncoded
    @POST("/photobook/list")
    Call<ArrayList<PhotobookModel>> getmyphotobook(String usertoken);

    //모든 사진첩 목록 받아오기
    @FormUrlEncoded
    @POST("/photobook/list/all")
    Call<ArrayList<PhotobookModel>> getAllPhotobook();

    //사진 상세정보 받기
    @FormUrlEncoded
    @POST("/photobook/photo/show")
    Call<PhotoModel> getPhotoData(@Field("url") String url);

    //TODO /photobook/add 파일업로드 인터페이스 구현
    @Multipart
    @POST("/photobook/photo/add")
    Call<SuccessModel> addPhoto();

    //특정 사진첩의 사진들 링크
    @FormUrlEncoded
    @POST("/photobook/photo/list")
    Call<ArrayList<String>> getMyPhotos(String booktoken);

    //모든 사진첩의 사진들 링크
    @POST("/photobook/photo/all")
    Call<ArrayList<String>> getAllPhotos();

    //검색한 사진첩 목록
    @FormUrlEncoded
    @POST("/photobook/search")
    Call<ArrayList<PhotobookModel>> getSearchPhotobook(String name);


}
