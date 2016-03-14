package com.example.job94_000.lists_v2;

import android.app.Activity;
import android.content.Context;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * TodoManager is a Singleton class that manages information about TodoLists by writing and
 * reading from files.
 */
public class TodoManager {
    /**
     * Create global variables
     */
    private static Context appContext;
    private static TodoManager instance = null;
    private TodoManager() {
        // Exists only to defeat instantiation.
    }

    /**
     * Get an instance of TodoManager if there has not yet been one created, the context of the
     * using App is being send with for file writing and reading purposes
     */
    public static TodoManager getInstance(Context context) {
        if (instance == null) {
            instance = new TodoManager();
            appContext = context;
        }
        return instance;
    }

    /**
     * Try to write a given list of TodoLists to AllLists.dat
     */
    public void writeTodos(ArrayList<TodoList> newLists) {
        try {
            FileOutputStream fileOutputStream = appContext.openFileOutput("AllLists.dat", appContext.MODE_PRIVATE);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeInt(newLists.size());
            for (TodoList list : newLists) {
                objectOutputStream.writeObject(list);
            }
            objectOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Try to read TodoLists from Alllists.dat, if not possible, create an example list
     */
    public ArrayList<TodoList> readTodos() {
        ArrayList<TodoList> todoLists = new ArrayList<>();
        try {
            FileInputStream fileInputStream = appContext.openFileInput("AllLists.dat");
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            int count = objectInputStream.readInt();
            for (int c=0; c < count; c++) {
                todoLists.add((TodoList) objectInputStream.readObject());
            }
            objectInputStream.close();

        } catch (Exception e) {
            TodoList newList = new TodoList("ExampleList");
            newList.createTodoItem("This is an example:"); newList.createTodoItem("Get groceries");
            newList.createTodoItem("Call my grandmother"); newList.createTodoItem("Finish homework");
            newList.setIsCurrent(true);
            todoLists.add(newList);
        }
        return todoLists;
    }
}
