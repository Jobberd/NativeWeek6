package com.example.job94_000.lists_v2;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.view.Menu;
import android.view.SubMenu;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.HeaderViewListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;

/**
 * The MainActivity runs a program that can be used to maintain todolists.
 * It is possible to have multiple lists at the same time and you can switch between them using
 * a navigationdrawer.
 */
public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    /**
     * Create global variables
     */
    TodoManager todoManager;
    ArrayList<TodoList> todoLists;
    TodoList currentTodoList;

    Hashtable<String, ArrayList<String>> allLists = new Hashtable<>();
    Enumeration listNames;
    ArrayList<String> currentList = new ArrayList<>();
    String currentListName;
    ListView indexListView;
    EditText inputText;
    TextView titleText;
    Button addButton;
    ArrayAdapter<String> myIndexAdapter;
    NavigationView navigationView;
    Menu navigationMenu;
    SubMenu navSubMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        todoManager = TodoManager.getInstance(getApplicationContext());
        todoLists = todoManager.readTodos();
        for (TodoList list : todoLists) {
            if (list.getIsCurrent()) {
                currentTodoList = list;
            }
        }

        // Make the drawer toggle by using the actionbar button
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        // Set title name
        titleText = (TextView) findViewById(R.id.titleText);
        titleText.setLongClickable(true);
        titleText.setText(currentTodoList.getTitle());

        // Finding the ListView and adding an ArrayAdapter for the items in the list
        indexListView = (ListView) findViewById(R.id.indexList);
        indexListView.setLongClickable(true);
        setListViewAdapter();
//        checkForCheckedItems();

        // Find the EditText and Button to add items to the list with the String put in inputText
        inputText = (EditText) findViewById(R.id.inputText);
        addButton = (Button) findViewById(R.id.addButton);
        setAddButtonOnClick();

        // Create the navigationview and add the data to the menu
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationMenu = navigationView.getMenu();
        navSubMenu = navigationMenu.addSubMenu("Your To-Do Lists:");
        addNavigationItems();
    }

    /**
     * onSaveInstanceState() writes a file with the items of the list stored, so it can be reaccessed later.
     */
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // write to a file
        todoManager.writeTodos(todoLists);
    }

    /**
     * Make the drawer menu open on using the button in the actionbar
     */
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    /**
     * Sets the listviewadapter to the current list
     */
    private void setListViewAdapter() {
        currentList = new ArrayList<>();
        for (TodoItem item : currentTodoList.getTodos()) {
            currentList.add(item.getTitle());
        }
        myIndexAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_checked, currentList);
        indexListView.setAdapter(myIndexAdapter);
        setListItemLongClick();
//        setListItemClick();
        setTitleLongClick();
    }


//    private void checkForCheckedItems() {
//        for (int i = 0; i < myIndexAdapter.getCount(); i++) {
//            String listItem = myIndexAdapter.getItem(i);
//            for (TodoItem item : currentTodoList.getTodos()) {
//                if (listItem.equals(item.getTitle()) && item.isCompleted()) {
//                    indexListView.setItemChecked(i, true);
//                }
//            }
//        }
//    }

    /**
     * On longclicking the title TextView, the user can remove the current list. It the searches for
     * the right menu item, removes it and removes the list from the hashtable. It creates an
     * empty list when the last list is removed to prevent crashing
     */
    private void setTitleLongClick() {
        titleText.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                todoLists.remove(currentTodoList);
                for (int i = 0; i < navSubMenu.size(); i++) {
                    String title = (String) navSubMenu.getItem(i).getTitle();
                    if (currentTodoList.getTitle().equals(title)) {
                        navSubMenu.removeItem(i);
                    }
                }
                if (!todoLists.isEmpty()) {
                    currentTodoList = todoLists.get(0);
                } else {
                    currentTodoList = new TodoList("Empty list");
                    todoLists.add(currentTodoList);
                }
                currentTodoList.setIsCurrent(true);
                titleText.setText(currentTodoList.getTitle());
                setListViewAdapter();
                addNavigationItems();
                return false;
            }
        });
    }

    /**
     * Make the user be able to remove items from a list by long clicking them
     */
    private void setListItemLongClick() {
        indexListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                String itemTitle = currentList.get(position);
                currentTodoList.removeTodoItem(itemTitle);
                currentList.remove(position);
                myIndexAdapter.notifyDataSetChanged();
                return true;
            }
        });
    }

//    /*
//     *
//     */
//    private void setListItemClick() {
//        indexListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                String itemTitle = currentList.get(position);
//                CheckedTextView textView = (CheckedTextView) view;
//                Boolean isCompleted = textView.isChecked();
//                textView.setChecked(!isCompleted);
//                for (TodoItem item : currentTodoList.getTodos()) {
//                    if (itemTitle.equals(item.getTitle())) {
//                        item.setCompleted(!isCompleted);
//                    }
//                }
//            }
//        });
//    }

    /**
     * Set the add button to add the text from the inputtext to the current todolist
     */
    private void setAddButtonOnClick() {
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String itemTitle = inputText.getText().toString();
                currentTodoList.createTodoItem(itemTitle);
                currentList.add(itemTitle);
                inputText.setText("");
                myIndexAdapter.notifyDataSetChanged();
            }
        });
    }

    /**
     * Add the lists from the data to the navigation menu
     */
    private void addNavigationItems() {
        navSubMenu.clear();
        for (TodoList list : todoLists) {
            String listTitle = list.getTitle();
            navSubMenu.add(listTitle);
        }
        navigationView.setNavigationItemSelectedListener(this);
        editMenu();
        myIndexAdapter.notifyDataSetChanged();
    }

    /**
     * Set the onclick listeners for the menu items
     */
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        String listTitle = (String) item.getTitle();

        if (listTitle.equals("Add new list")) {
            promptUser();
        } else {
            currentTodoList.setIsCurrent(false);
            for (TodoList list : todoLists) {
                if (listTitle.equals(list.getTitle())) {
                    currentTodoList = list;
                    currentTodoList.setIsCurrent(true);
                }
            }
            titleText.setText(listTitle);
            setListViewAdapter();
//            checkForCheckedItems();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * Ask for user input when they try to add a new list by using an AlertDialog
     */
    private void promptUser() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Name your list:");

        // Set up the input
        final EditText input = new EditText(this);

        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        // Set up the positivebutton, creating a new menu item and refreshing all adapters when selected
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                currentTodoList.setIsCurrent(false);
                currentTodoList = new TodoList(input.getText().toString());
                currentTodoList.setIsCurrent(true);
                todoLists.add(currentTodoList);
                titleText.setText(currentTodoList.getTitle());
                setListViewAdapter();
                navSubMenu.add(currentTodoList.getTitle());
                editMenu();
            }
        });

        // Set up the negativebutton
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }

    /**
     * Stolen from the internet to update my navigationView during runtime
     */
    private void editMenu() {
        for (int i = 0, count = navigationView.getChildCount(); i < count; i++) {
            final View child = navigationView.getChildAt(i);
            if (child != null && child instanceof ListView) {
                final ListView menuView = (ListView) child;
                final HeaderViewListAdapter adapter = (HeaderViewListAdapter) menuView.getAdapter();
                final BaseAdapter wrapped = (BaseAdapter) adapter.getWrappedAdapter();
                wrapped.notifyDataSetChanged();
            }
        }
    }
}
