package com.example.focusapp;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class HabitVH extends RecyclerView.ViewHolder {

    public TextView tv_item_title,tv_item_description,tv_item_createdTimeStamp,txt_option;
    public CardView cardView;
    public HabitVH(@NonNull View itemView) {
        super(itemView);
        tv_item_title = itemView.findViewById(R.id.tv_item_title);
        tv_item_description = itemView.findViewById(R.id.tv_item_description);
        tv_item_createdTimeStamp = itemView.findViewById(R.id.tv_item_createdTimeStamp);
        cardView = itemView.findViewById(R.id.cv_cardView);
        txt_option = itemView.findViewById(R.id.txt_option);


    }
}
