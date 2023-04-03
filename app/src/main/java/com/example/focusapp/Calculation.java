package com.example.focusapp;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class Calculation {
    private static String timeStampToString(long timeStamp){
        Timestamp stamp = new Timestamp(timeStamp);
        SimpleDateFormat sdf= new SimpleDateFormat("dd/MM/yyyy HH:mm");
        String date= sdf.format((new Date(stamp.getTime())));
        return date.toString();

    }
    String cleanDate(int _day,int _month,int _year){
        String day = Integer.toString(_day);
        String month = Integer.toString(_month);
        if (_day<10){
            day="0"+_day;
        }

        if (_month<9){
            month="0"+(_month+1);
        }
        return day+"/"+month+"/"+_year;

    }
    String cleanTime(int _hour,int _minute){
        String hour = Integer.toString(_hour);
        String minute = Integer.toString(_minute);
        if (_hour<10){
            hour="0"+_hour;
        }

        if (_minute<10){
            minute="0"+(_minute);
        }
        return hour+":"+minute;
    }
}
