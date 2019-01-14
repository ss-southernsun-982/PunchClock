package com.example.namtn.punchclock.CustomWidget.CustomCalendar;

import java.io.Serializable;

public class CalendarSateLeaves implements Serializable {

    int id;
    boolean dateOfMonth;
    int date;
    int month;
    int year;

    public CalendarSateLeaves(int id, boolean dateOfMonth, int date, int month, int year) {
        this.id = id;
        this.dateOfMonth = dateOfMonth;
        this.date = date;
        this.month = month;
        this.year = year;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isDateOfMonth() {
        return dateOfMonth;
    }

    public void setDateOfMonth(boolean dateOfMonth) {
        this.dateOfMonth = dateOfMonth;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
