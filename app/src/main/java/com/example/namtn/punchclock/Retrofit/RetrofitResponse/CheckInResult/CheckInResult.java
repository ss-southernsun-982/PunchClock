package com.example.namtn.punchclock.Retrofit.RetrofitResponse.CheckInResult;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CheckInResult {
    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("data")
    @Expose
    private DataCheckIn data;
    @SerializedName("message")
    @Expose
    private String message;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public DataCheckIn getData() {
        return data;
    }

    public void setData(DataCheckIn data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
