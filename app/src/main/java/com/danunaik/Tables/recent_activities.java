package com.danunaik.Tables;

public class recent_activities {
    public recent_activities(String text) {
        this.text = text;
    }

    public recent_activities() {
    }

    public recent_activities(String text, String link) {
        this.text = text;
        this.link = link;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    String text;

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    String link;
}
