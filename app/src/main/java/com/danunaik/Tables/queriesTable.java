package com.danunaik.Tables;

public class queriesTable {
    String Title;
    String Description;

    public queriesTable(String title, String description) {
        Title = title;
        Description = description;
    }

    public queriesTable() {
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
}
