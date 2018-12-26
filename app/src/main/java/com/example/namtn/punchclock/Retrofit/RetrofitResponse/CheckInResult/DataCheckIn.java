package com.example.namtn.punchclock.Retrofit.RetrofitResponse.CheckInResult;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DataCheckIn {
    @SerializedName("date")
    @Expose
    private Date date;
    @SerializedName("emp_id")
    @Expose
    private String empId;
    @SerializedName("check_in")
    @Expose
    private CheckIn checkIn;
    @SerializedName("checkin_ip")
    @Expose
    private String checkinIp;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("id")
    @Expose
    private Integer id;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public CheckIn getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(CheckIn checkIn) {
        this.checkIn = checkIn;
    }

    public String getCheckinIp() {
        return checkinIp;
    }

    public void setCheckinIp(String checkinIp) {
        this.checkinIp = checkinIp;
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
