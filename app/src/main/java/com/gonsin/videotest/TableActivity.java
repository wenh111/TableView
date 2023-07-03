package com.gonsin.videotest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.gonsin.tableviewlibrary.HorizontalSlidingListen;
import com.gonsin.tableviewlibrary.TimeTableModel;
import com.gonsin.tableviewlibrary.TimeTableView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class TableActivity extends AppCompatActivity {
    private TimeTableView timeTableView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table);
        timeTableView = findViewById(R.id.timeTableView);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) + 2);
        List<TimeTableModel> timeTableModels = new ArrayList<>();
        TimeTableModel timeTableModel = new TimeTableModel();
        timeTableModel.setStartTime(System.currentTimeMillis());
        timeTableModel.setEndTime(calendar.getTimeInMillis());
        timeTableModel.setWeek(1);
        timeTableModel.setName("测试会议一");
        timeTableModels.add(timeTableModel);
        TimeTableModel timeTableModel1 = new TimeTableModel();
        calendar.set(Calendar.HOUR_OF_DAY, 20);
        calendar.set(Calendar.MINUTE, 0);
        timeTableModel1.setStartTime(System.currentTimeMillis());
        timeTableModel1.setEndTime(calendar.getTimeInMillis());
        timeTableModel1.setWeek(2);
        timeTableModel1.setName("测试会议二");
        timeTableModels.add(timeTableModel1);
        timeTableView.setHorizontalSlidingListen(new HorizontalSlidingListen() {
            @Override
            public void onLeftSliding() {

            }

            @Override
            public void onRightSliding() {

            }
        });
        timeTableView.setTimeTableModels(timeTableModels);
    }
}