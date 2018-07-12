package com.box.billy.billybox.Rest;

import com.box.billy.billybox.Model.AuthSignIn;
import com.box.billy.billybox.Model.AuthSignUp;
import com.box.billy.billybox.Model.GetProductCatResponse;
import com.box.billy.billybox.Model.GetProductResponse;
import com.box.billy.billybox.Model.GetUserResponse2;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiServices {

    @POST("/api/auth/login/")
    @FormUrlEncoded
    Call<AuthSignIn> login(@Field("username") String username,
                            @Field("password") String password);

    @POST("/api/auth/signin")
    @FormUrlEncoded
    Call<AuthSignUp> signUp(@Field("firstName") String firstname,
                            @Field("lastName") String lastname,
                            @Field("username") String username,
                            @Field("password") String password,
                            @Field("alamat") String alamat,
                            @Field("noTelp") String notelp,
                            @Field("tglLahir") String tgllahir);

    @GET("/api/auth/user/username/{username}")
    Call<GetUserResponse2> getUser2(@Path("username") String username);

    @GET("/api/carton/kategori/")
    Call<GetProductCatResponse> getProductCatResponse(@Query("status") String status);

    @GET("/api/carton/cartons/cat/{categoryCartonId}")
    Call<GetProductResponse> getProductResponse(@Path("categoryCartonId") String catID,
                                                @Query("status") String status);

}
