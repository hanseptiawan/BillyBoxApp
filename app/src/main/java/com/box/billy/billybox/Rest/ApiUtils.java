package com.box.billy.billybox.Rest;

public class ApiUtils {

    public static final String BASE_URL = "http://admin-web1.000webhostapp.com/";
//    public static final String BASE_URL = "https://webadmin1.000webhostapp.com/";


    public static ApiServices getApiServices(){
        return RetrofitClient.getClient(BASE_URL).create(ApiServices.class);
    }
}
