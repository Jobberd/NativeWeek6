package com.example.job94_000.lists_v2;

import java.io.Serializable;

/**
 * Created by job94_000 on 13-3-2016.
 */
public class TodoItem implements Serializable {
    private String title;
    private Boolean completed = false;
    private Boolean inProgress = false;
    private String description;
    private int duration;

    public TodoItem(String name) {
        title = name;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

    public void setInProgress(Boolean inProgress) {
        this.inProgress = inProgress;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getTitle() {
        return title;
    }

    public Boolean isCompleted() {
        return completed;
    }

    public Boolean isInProgress() {
        return inProgress;
    }

    public String getDescription() {
        return description;
    }

    public int getDuration() {
        return duration;
    }
}
