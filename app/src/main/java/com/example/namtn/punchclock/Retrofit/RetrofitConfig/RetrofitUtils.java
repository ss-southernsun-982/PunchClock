package com.example.namtn.punchclock.Retrofit.RetrofitConfig;

public class RetrofitUtils {
    public static RetrofitAPIs apiUserLogin(){
        return RetrofitUserInstance.getInstance("oauth/").create(RetrofitAPIs.class);
    }

    public static RetrofitAPIs apiUserInfo(String token){
        return RetrofitUserInfoInstance.getInstance("api/nks/", token).create(RetrofitAPIs.class);
    }

    public static RetrofitAPIs apiFetchData(){
        return RetrofitUserInstance.getInstance("api/nks/").create(RetrofitAPIs.class);
    }

    public static RetrofitAPIs apiAttendance(){
        return RetrofitAttendanceInstance.getInstance().create(RetrofitAPIs.class);
    }
}
