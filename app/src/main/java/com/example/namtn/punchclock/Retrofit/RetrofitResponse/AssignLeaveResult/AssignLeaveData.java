package com.example.namtn.punchclock.Retrofit.RetrofitResponse.AssignLeaveResult;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AssignLeaveData {
    @SerializedName("emp_id")
    @Expose
    private String empId;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("reason")
    @Expose
    private String reason;
    @SerializedName("halfday")
    @Expose
    private String halfday;
    @SerializedName("type_id")
    @Expose
    private String typeId;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("id")
    @Expose
    private Integer id;

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getHalfday() {
        return halfday;
    }

    public void setHalfday(String halfday) {
        this.halfday = halfday;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
