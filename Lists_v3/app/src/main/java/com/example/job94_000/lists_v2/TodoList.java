package com.example.job94_000.lists_v2;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * TodoList holds a number of TodoItems and has some information regarding the list of
 * items. It can also return the list.
 */
public class TodoList implements Serializable {
    /**
     * Create global variables
     */
    private Boolean isCurrent = false;
    private String title;
    private ArrayList<TodoItem> todos = new ArrayList<>();

    /**
     * Instantiates an TodoList using a String with a title name
     */
    public TodoList(String title) {
        this.title = title;
    }

    /**
     * Create a new TodoItem in this list with a given title
     */
    public void createTodoItem(String title) {
        TodoItem todoItem = new TodoItem(title);
        todos.add(todoItem);
    }

    /**
     * Create a TodoItem in this list with a certain title
     */
    public void removeTodoItem(String title) {
        for (TodoItem item : todos) {
            if (item.getTitle().equals(title)) {
                todos.remove(item);
            }
        }
    }

    /**
     * Set this list as current list for the program
     */
    public void setIsCurrent(Boolean isCurrent) {
        this.isCurrent = isCurrent;
    }

    /**
     * Set the title of this list to be a given String
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Returns if this list is the current list
     */
    public Boolean getIsCurrent() {
        return isCurrent;
    }

    /**
     * Returns the title of the list
     */
    public String getTitle() {
        return title;
    }

    /**
     * Returns the list of TodoItems
     */
    public ArrayList<TodoItem> getTodos() {
        return todos;
    }
}
