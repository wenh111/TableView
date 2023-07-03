package com.gonsin.tableviewlibrary;

/**
 * 日程表item对象
 */
public class TimeTableModel {
    private int id;
    private String meetingKey;
    private int week;
    private String name = "";

    private long startTime;
    private long endTime;

    public String getMeetingKey() {
        return meetingKey;
    }

    public void setMeetingKey(String meetingKey) {
        this.meetingKey = meetingKey;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }


    public int getId() {
        return id;
    }


    public int getWeek() {
        return week;
    }


    public String getName() {
        return name;
    }


    public void setId(int id) {
        this.id = id;
    }


    public void setWeek(int week) {
        this.week = week;
    }


    public void setName(String name) {
        this.name = name;
    }


    public TimeTableModel() {

    }

    public TimeTableModel(int week,
                          long startTime, long endTime, String name, String meetingKey) {
        super();

        this.meetingKey = meetingKey;
        this.week = week;
        this.startTime = startTime;
        this.endTime = endTime;
        this.name = name;
    }

}
