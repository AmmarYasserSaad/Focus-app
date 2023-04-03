package com.example.focusapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class Create_Habit extends AppCompatActivity implements habit_main.OnBackPressedListener {
    private habit_main Habit;
    @Override
    public void onBackPressed() {
        if (Habit != null) {
            Habit.onBackPressed();
        } else {
            super.onBackPressed();
        }
    }

    private String title = "";
    private String description = "";
    private String timestamp = "";

    private int day = 0;
    private int month = 0;
    private int year = 0;
    private int hour = 0;
    private int minute = 0;

    private String cleanDate = "";
    private String cleanTime = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_habit);
        Button btn_confirm = findViewById(R.id.btn_confirm);
        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DAOHabit dao = new DAOHabit();
                EditText et_habitTitle = findViewById(R.id.et_habitTitle);
                title = et_habitTitle.getText().toString();
                EditText et_habitDescription = findViewById(R.id.et_habitDescription);
                description = et_habitDescription.getText().toString();
                timestamp = cleanDate + " " + cleanTime;
                if (!(title.isEmpty() || description.isEmpty() || timestamp.isEmpty())) {
                    Habit hab = new Habit(0, title, description, timestamp);
                    dao.add(hab);
                    Toast.makeText(getApplicationContext(), "Habit created successfully", Toast.LENGTH_LONG).show();
                    onBackPressed();
                } else {
                    Toast.makeText(getApplicationContext(), "please fill all field", Toast.LENGTH_LONG).show();
                }
            }
        });

        Button btn_pickDate = findViewById(R.id.btn_pickDate);
        btn_pickDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDateCalendar();
                new DatePickerDialog(Create_Habit.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int yearX, int monthX, int dayX) {
                        cleanDate = new Calculation().cleanDate(dayX, monthX, yearX);
                        TextView tv_dateSelected = findViewById(R.id.tv_dateSelected);
                        tv_dateSelected.setText("Date: " + cleanDate);
                    }
                }, year, month, day).show();
            }

        });
        Button btn_pickTime = findViewById(R.id.btn_pickTime);
        btn_pickTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getTimeCalendar();
                new TimePickerDialog(Create_Habit.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                        cleanTime = new Calculation().cleanTime(hourOfDay, minute);
                        TextView tv_timeSelected = findViewById(R.id.tv_timeSelected);
                        tv_timeSelected.setText("Time: " + cleanTime);
                    }
                }, hour, minute, true).show();
            }
        });
    }

    private void getTimeCalendar() {
        Calendar cal = Calendar.getInstance();
        hour = cal.get(Calendar.HOUR_OF_DAY);
        minute = cal.get(Calendar.MINUTE);
    }

    private void getDateCalendar() {
        Calendar cal = Calendar.getInstance();
        day = cal.get(Calendar.DAY_OF_MONTH);
        month = cal.get(Calendar.MONTH);
        year = cal.get(Calendar.YEAR);
    }
}