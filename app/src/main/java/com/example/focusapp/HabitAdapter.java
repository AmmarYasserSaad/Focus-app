package com.example.focusapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.Serializable;
import java.util.ArrayList;

public class HabitAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    ArrayList<Habit> list = new ArrayList<>();
    public HabitAdapter(Context ctx){
        this.context = ctx;
    }
    public void setItems(ArrayList<Habit> hab){
        list.addAll(hab);
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_habit_item,parent,false);
        return new HabitVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        HabitVH vh = (HabitVH) holder;
        Habit hab = list.get(position);
        vh.tv_item_title.setText(hab.getHabit_title());
        vh.tv_item_description.setText(hab.getHabit_description());
        vh.tv_item_createdTimeStamp.setText((hab.getStart_time()));
        vh.txt_option.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(context,vh.txt_option);
                popupMenu.inflate(R.menu.option_menu);
                popupMenu.setOnMenuItemClickListener(menuItem -> {
                    switch (menuItem.getItemId()){
                        case R.id.menu_remove:
                            DAOHabit dao = new DAOHabit();
                            dao.remove(hab.getKey()).addOnSuccessListener(suc->
                            {
                                Toast.makeText(context,"Record is removed", Toast.LENGTH_LONG).show();
                                notifyItemRemoved(position);

                            }).addOnFailureListener(er ->{
                                Toast.makeText(context,""+er.getMessage(),Toast.LENGTH_LONG).show();
                            });
                            break;
                    }
                    return false;
                });
                popupMenu.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
