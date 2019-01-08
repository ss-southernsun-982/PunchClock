package com.example.namtn.punchclock.Retrofit.RetrofitResponse.AssignLeaveResult;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AssignLeaveResult {
    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("data")
    @Expose
    private AssignLeaveData data;
    @SerializedName("message")
    @Expose
    private String message;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public AssignLeaveData getData() {
        return data;
    }

    public void setData(AssignLeaveData data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
