package com.example.focusapp;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.HashMap;

public class DAOHabit {
    private DatabaseReference databaseReference;
    public DAOHabit(){
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        databaseReference= db.getReference(Habit.class.getSimpleName());

    }
    public Task<Void> add(Habit hab){
        return databaseReference.push().setValue(hab);

    }
    public Task<Void> update(String key, HashMap<String,Object> hashMap){
        return databaseReference.child(key).updateChildren(hashMap);
    }

    public Task<Void> remove(String key){
        return databaseReference.child(key).removeValue();
    }

    public Query get(String key){
        if (key==null){
            return databaseReference.orderByKey().limitToFirst(8);
        }
        return databaseReference.orderByKey().startAfter(key).limitToFirst(8);
    }
}
