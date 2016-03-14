package com.example.job94_000.lists_v2;

import java.io.Serializable;

/**
 * TodoItem is a class that holds information for a single TodoItem, it can be instantiated with
 * a titlename and then sets and returns information
 */
public class TodoItem implements Serializable {
    /**
     * Create global variables
     */
    private String title;
    private Boolean completed = false;
    private Boolean inProgress = false;
    private String description;
    private int duration;

    /**
     * Instantiates an TodoItem using a String with a title name
     */
    public TodoItem(String name) {
        title = name;
    }

    /**
     * Set a title of the TodoItem
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Set completion of the TodoItem
     */
    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

    /**
     * Set in progress of the TodoItem
     */
    public void setInProgress(Boolean inProgress) {
        this.inProgress = inProgress;
    }

    /**
     * Set description of the TodoItem
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Set duration of the TodoItem
     */
    public void setDuration(int duration) {
        this.duration = duration;
    }

    /**
     * Get a title of the TodoItem
     */
    public String getTitle() {
        return title;
    }

    /**
     * Get completion of the TodoItem
     */
    public Boolean isCompleted() {
        return completed;
    }

    /**
     * Get progress of the TodoItem
     */
    public Boolean isInProgress() {
        return inProgress;
    }

    /**
     * Get description of the TodoItem
     */
    public String getDescription() {
        return description;
    }

    /**
     * Get duration of the TodoItem
     */
    public int getDuration() {
        return duration;
    }
}
