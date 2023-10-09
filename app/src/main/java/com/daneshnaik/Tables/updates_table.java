package com.daneshnaik.Tables;

public class updates_table {

    String Title;
    String Description;
    String updates_Email;

    public updates_table( String title, String description) {

        Title = title;
        Description = description;
    }

    public updates_table(String title, String description, String updates_Email) {
        Title = title;
        Description = description;
        this.updates_Email = updates_Email;
    }

    public updates_table() {
    }


    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }
    public String getUpdates_Email() {
        return updates_Email;
    }

    public void setUpdates_Email(String updates_Email) {
        this.updates_Email = updates_Email;
    }
}
