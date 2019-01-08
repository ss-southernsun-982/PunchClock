package com.example.namtn.punchclock.CustomWidget.CustomCalendar;

public class CalendarState {

    int id;
    boolean selected;
    int date;
    int month;
    int year;

    public CalendarState(int id, boolean selected, int date, int month, int year) {
        this.id = id;
        this.selected = selected;
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

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
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
