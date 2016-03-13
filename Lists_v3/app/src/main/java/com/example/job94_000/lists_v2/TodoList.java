package com.example.job94_000.lists_v2;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by job94_000 on 13-3-2016.
 */
public class TodoList implements Serializable {

    private Boolean isCurrent = false;
    private String title;
    private ArrayList<TodoItem> todos = new ArrayList<>();

    public TodoList(String title) {
        this.title = title;
    }

    public void createTodoItem(String title) {
        TodoItem todoItem = new TodoItem(title);
        todos.add(todoItem);
    }

    public void removeTodoItem(String title) {
        for (TodoItem item : todos) {
            if (item.getTitle().equals(title)) {
                todos.remove(item);
            }
        }
    }

    public void setIsCurrent(Boolean isCurrent) {
        this.isCurrent = isCurrent;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Boolean getIsCurrent() {
        return isCurrent;
    }

    public String getTitle() {
        return title;
    }

    public ArrayList<TodoItem> getTodos() {
        return todos;
    }
}
