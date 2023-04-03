package com.example.focusapp;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.example.focusapp.Adapter.ToDoAdapter;
import com.example.focusapp.Model.ToDoModel;
import com.example.focusapp.Utils.DatabaseHandler;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;


public class todo_main<taskList> extends Fragment implements DialogCloseListener{

    RecyclerView taskRe;
    ToDoAdapter tasksAdapter;
    List<ToDoModel> taskList;
    FloatingActionButton fab;
    DatabaseHandler db;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_todo_main, container, false);
        Objects.requireNonNull(((AppCompatActivity)getActivity()).getSupportActionBar()).hide();

        taskList = new ArrayList<>();

        db = new DatabaseHandler(getContext());
        db.openDatabase();

        taskRe = view.findViewById(R.id.TaskRecycler);
        taskRe.setLayoutManager(new LinearLayoutManager(getContext()));
        tasksAdapter = new ToDoAdapter(db, getActivity());
        taskRe.setAdapter(tasksAdapter);

        fab = view.findViewById(R.id.fab);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new RecyclerItemTouchHelper(tasksAdapter));
        itemTouchHelper.attachToRecyclerView(taskRe);

        taskList = db.getAllTasks();
        Collections.reverse(taskList);
        tasksAdapter.setTasks(taskList);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddNewTask.newInstance().show(getParentFragmentManager(), AddNewTask.TAG);
            }
        });
        return view;

    }

    @Override
    public void handleDialogClose(DialogInterface dialog) {
        taskList = db.getAllTasks();
        Collections.reverse(taskList);
        tasksAdapter.setTasks(taskList);
        tasksAdapter.notifyDataSetChanged();
    }
}