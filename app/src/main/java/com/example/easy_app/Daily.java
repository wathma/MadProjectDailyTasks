package com.example.easy_app;

public class Daily {
    private int taskID;
    private String taskTitle, taskDescription;
    private long started, finished;

    public Daily(){

    }

    public Daily(int id, String title, String description, long started, long finished) {
        this.taskID = id;
        this.taskTitle = title;
        this.taskDescription = description;
        this.started = started;
        this.finished = finished;
    }

    public Daily(String title, String description, long started, long finished) {
        this.taskTitle = title;
        this.taskDescription = description;
        this.started = started;
        this.finished = finished;
    }

    public int getTaskID() {
        return taskID;
    }

    public void setTaskID(int taskID) {
        this.taskID = taskID;
    }

    public String getTaskTitle() {
        return taskTitle;
    }

    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public long getStarted() {
        return started;
    }

    public void setStarted(long started) {
        this.started = started;
    }

    public long getFinished() {
        return finished;
    }

    public void setFinished(long finished) {
        this.finished = finished;
    }
}
