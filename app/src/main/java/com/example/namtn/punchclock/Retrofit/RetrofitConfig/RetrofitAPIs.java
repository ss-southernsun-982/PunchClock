package com.example.namtn.punchclock.Retrofit.RetrofitConfig;

import com.example.namtn.punchclock.Retrofit.RetrofitResponse.LoginResult;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface RetrofitAPIs {
    @FormUrlEncoded
    @POST("token")
    Observable<LoginResult> uerLogin(
            @Field("username") String email,
            @Field("password") String password,
            @Field("grant_type") String grant_type,
            @Field("client_id") String client_id,
            @Field("client_secret") String client_secret
    );
}
