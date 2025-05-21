package com.example.taskmanager.utility.testdata;

import com.example.taskmanager.model.Task;

public class TaskFactory {

    public static Task createTaskObject(int id, String title, String description) {
        return new Task(id, title, description);
    }

    public static Task createTaskObject(String title, String description) {
        return new Task(0, title, description);
    }
}
