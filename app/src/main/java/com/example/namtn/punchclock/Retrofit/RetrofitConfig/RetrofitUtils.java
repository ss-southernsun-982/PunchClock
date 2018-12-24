package com.example.namtn.punchclock.Retrofit.RetrofitConfig;

public class RetrofitUtils {
    public static RetrofitAPIs apiUserLogin(){
        return RetrofitInstance.getInstance("oauth/").create(RetrofitAPIs.class);
    }

    public static RetrofitAPIs apiUserInfo(){
        return RetrofitInstance.getInstance("api/user/").create(RetrofitAPIs.class);
    }

    public static RetrofitAPIs apiFetchData(){
        return RetrofitInstance.getInstance("api/nks/").create(RetrofitAPIs.class);
    }
}
