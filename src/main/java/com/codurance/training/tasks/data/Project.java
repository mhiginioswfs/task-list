package com.codurance.training.tasks.data;

import java.util.ArrayList;
import java.util.List;

public class Project {

    private List<Task> tasks;

    public Project() {
        this(new ArrayList<>());
    }

    public Project(List<Task> tasks) {
        this.tasks = tasks;
    }

    public List<Task> getTasks() {
        return tasks;
    }
}
