package com.example.namtn.punchclock.Retrofit.RetrofitConfig;

import com.example.namtn.punchclock.Retrofit.RetrofitResponse.AssignLeaveResult.AssignLeaveResult;
import com.example.namtn.punchclock.Retrofit.RetrofitResponse.AttendanceResult.AttendanceResult;
import com.example.namtn.punchclock.Retrofit.RetrofitResponse.CheckInResult.CheckInResult;
import com.example.namtn.punchclock.Retrofit.RetrofitResponse.CheckOutResult.CheckOutResult;
import com.example.namtn.punchclock.Retrofit.RetrofitResponse.LeavesResult.LeavesResult;
import com.example.namtn.punchclock.Retrofit.RetrofitResponse.UserResult.LoginResult;
import com.example.namtn.punchclock.Retrofit.RetrofitResponse.UserResult.UserInfo.UserInfoResult;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface RetrofitAPIs {
    @FormUrlEncoded
    @POST("token")
    Observable<LoginResult> userLogin(
            @Field("username") String email,
            @Field("password") String password,
            @Field("grant_type") String grant_type,
            @Field("client_id") String client_id,
            @Field("client_secret") String client_secret
    );

    @FormUrlEncoded
    @POST("user")
    Observable<UserInfoResult> userInfo(
            @Field("email") String email
    );

    @FormUrlEncoded
    @POST("attendances")
    Observable<AttendanceResult> getAttendance(
            @Field("emp_id") String emp_id
    );

    @FormUrlEncoded
    @POST("attendances/checkIn")
    Observable<CheckInResult> checkIn(
            @Field("emp_id") String emp_id,
            @Field("checkin_ip") String checkin_ip
    );

    @FormUrlEncoded
    @POST("attendances/checkOut")
    Observable<CheckOutResult> checkOut(
            @Field("date") String date,
            @Field("emp_id") String emp_id,
            @Field("checkin_ip") String checkin_ip
    );

    @FormUrlEncoded
    @POST("leaves")
    Observable<LeavesResult> leaves(
            @Field("emp_id") String emp_id
    );

    @FormUrlEncoded
    @POST("leaves/add")
    Observable<AssignLeaveResult> leavesAdd(
            @Field("emp_id") String emp_id,
            @Field("date") String date,
            @Field("reason") String reason,
            @Field("halfday") String halfDay,
            @Field("type_id") String typeId
    );
}
