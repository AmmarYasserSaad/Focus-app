package com.example.focusapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class habit_main extends Fragment {
    public interface OnBackPressedListener {
        void onBackPressed();
    }
    public void onBackPressed() {
        if (onBackPressedListener != null) {
            onBackPressedListener.onBackPressed();
            // Add code here to refresh the fragment
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.detach(this).attach(this).commit();
        }
    }
    private OnBackPressedListener onBackPressedListener;
    SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView recyclerView;
    HabitAdapter adapter;
    DAOHabit dao;
    boolean isLoading = false;
    String key = null;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_habit_main, container, false);
        FloatingActionButton fab = v.findViewById(R.id.fab_add);
        swipeRefreshLayout = v.findViewById(R.id.swipeToRefresh);
        recyclerView = v.findViewById(R.id.rv_habits);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(manager);
        adapter = new HabitAdapter(getActivity());
        recyclerView.setAdapter(adapter);
        dao = new DAOHabit();
        loadData();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                int totalItem = linearLayoutManager.getItemCount();
                int lastVisible = linearLayoutManager.findLastCompletelyVisibleItemPosition();
                if(totalItem < lastVisible+3){
                    if(!isLoading){
                        isLoading = true;
                        loadData();
                    }
                }
            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Create_Habit.class);
                startActivity(intent);
            }
        });
        return v;
    }

    private void loadData() {
        swipeRefreshLayout.setRefreshing(true);
        dao.get(key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Habit> habs = new ArrayList<>();
                for (DataSnapshot data : snapshot.getChildren()){
                    Habit hab = data.getValue(Habit.class);
                    hab.setKey(data.getKey());
                    habs.add(hab);
                    key = data.getKey();
                }
                adapter.setItems(habs);
                adapter.notifyDataSetChanged();
                isLoading = false;
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }
}