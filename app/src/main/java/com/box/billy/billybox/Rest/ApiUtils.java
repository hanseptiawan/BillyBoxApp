package com.box.billy.billybox.Rest;

public class ApiUtils {

//    public static final String BASE_URL = "http://admin-web1.000webhostapp.com/";
    public static final String BASE_URL = "http://10.0.2.2/karton/";


//    public static ApiServices getApiServices(){
//        return RetrofitClient.getClient(BASE_URL).create(ApiServices.class);
//    }

    public static ApiServicesLokal getApiServices(){
        return RetrofitClient.getClient(BASE_URL).create(ApiServicesLokal.class);
    }
}
