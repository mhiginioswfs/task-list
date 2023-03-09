package com.codurance.training.tasks.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class Project {

    private final List<Task> tasks;

    public Project() {
        this(new ArrayList<>());
    }

    public Project(List<Task> tasks) {
        this.tasks = tasks;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public List<Task> refine(Predicate<Task> predicate) {
        return tasks.stream().filter(predicate).toList();
    }

    public Optional<Task> findTask(String id) {
        return tasks.stream().filter(task -> task.getId().equals(id)).findFirst();
    }

    public void remove(String id) {
        tasks.removeIf(task -> id.equals(task.getId()));
    }
}
