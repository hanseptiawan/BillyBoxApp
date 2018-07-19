package com.box.billy.billybox.Rest;

import com.box.billy.billybox.Model.AddBuktiTF;
import com.box.billy.billybox.Model.AddCartItem;
import com.box.billy.billybox.Model.AuthSignIn;
import com.box.billy.billybox.Model.AuthSignUp;
import com.box.billy.billybox.Model.DeleteCartItemResponse;
import com.box.billy.billybox.Model.GetCartIDResponse;
import com.box.billy.billybox.Model.GetCartResponse;
import com.box.billy.billybox.Model.GetPesananDetailResponse;
import com.box.billy.billybox.Model.GetPesananResponse;
import com.box.billy.billybox.Model.GetProductCatResponse;
import com.box.billy.billybox.Model.GetProductResponse;
import com.box.billy.billybox.Model.GetUserResponse2;
import com.box.billy.billybox.Model.UpdateDataUser;

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

    @POST("/api/auth/user/id/{userId}")
    @FormUrlEncoded
    Call<UpdateDataUser> updateDataUser(
                                @Path("userId") String userId,
                                @Field("firstName") String firstname,
                                @Field("lastName") String lastname,
                                @Field("imgSrc") String imgSrc,
                                @Field("password") String password,
                                @Field("tglLahir") String tgllahir);

    @POST("/api/Cart/addItem")
    @FormUrlEncoded
    Call<AddCartItem> addItem(@Field("cartonId") String cartonId,
                             @Field("jumlah") String jumlah,
                             @Field("harga") String harga,
                             @Field("cartId") String cartId);

    @POST("/api/order/pembayaran")
    @FormUrlEncoded
    Call<AddBuktiTF> addBukti(@Field("paymentId") String paymentId,
                              @Field("noBank") String noBank,
                              @Field("nama") String nama,
                              @Field("nominal") String nominal,
                              @Field("imgData") String imgData);

    @GET("/api/auth/user/username/{username}")
    Call<GetUserResponse2> getUser2(@Path("username") String username);

    @GET("/api/carton/kategori/")
    Call<GetProductCatResponse> getProductCatResponse(@Query("status") String status);

    @GET("/api/carton/cartons/cat/{categoryCartonId}")
    Call<GetProductResponse> getProductResponse(@Path("categoryCartonId") String catID,
                                                @Query("status") String status);

    @GET("/api/order/orders/user/{userId}")
    Call<GetPesananResponse> getPesanan(@Path("userId") String userId);

    @GET("/api/order/detailOrder/order/{orderId}")
    Call<GetPesananDetailResponse> getPesananDetail(@Path("orderId") String userId);

    @GET("/api/Cart/createId/userid/{userid}")
    Call<GetCartIDResponse> getCartID(@Path("userid") String userid);

    @GET("/api/Cart/cart/id/{cartid}")
    Call<GetCartResponse> getCartList(@Path("cartid") String cartid);

    @GET("/api/Cart/deleteItem/id/{detailCartId}")
    Call<DeleteCartItemResponse> deleteItem(@Path("detailCartId") String detailCartId);


}
