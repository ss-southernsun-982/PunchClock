package com.example.namtn.punchclock.ModelView;

public class NotificationModelVIew {
    private String title;
    private String sunTitle;

    public NotificationModelVIew(String title, String sunTitle) {
        this.title = title;
        this.sunTitle = sunTitle;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSunTitle() {
        return sunTitle;
    }

    public void setSunTitle(String sunTitle) {
        this.sunTitle = sunTitle;
    }
}
