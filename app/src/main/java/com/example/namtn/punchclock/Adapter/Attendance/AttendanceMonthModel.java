package com.example.namtn.punchclock.Adapter.Attendance;

public class AttendanceMonthModel {

    private int id;
    private String month;

    public AttendanceMonthModel(int id, String month) {
        this.id = id;
        this.month = month;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }
}
