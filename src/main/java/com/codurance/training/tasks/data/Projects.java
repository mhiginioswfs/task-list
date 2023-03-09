package com.codurance.training.tasks.data;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Predicate;

public class Projects {

    private final Map<String, List<Task>> tasks;
    private final PrintWriter out;
    private long lastId = 0;

    public Projects(PrintWriter out) {
        this(out, new LinkedHashMap<>());
    }

    public Projects(PrintWriter out, Map<String, List<Task>> tasks) {
        this.tasks = tasks;
        this.out = out;
    }

    public Task getTaskById(String id) {
        Optional<Task> result = findTask(id);
        return result.orElseThrow(() -> new NoSuchElementException("Task " + id + " not found"));
    }

    public boolean existTask(String id) {
        return findTask(id).isPresent();
    }

    private Optional<Task> findTask(String id) {
        return tasks.values().stream().flatMap(List::stream).filter(task -> task.getId().equals(id)).findFirst();
    }

    public String nextId() {
        return String.valueOf(++lastId);
    }

    public void addProject(String projectName) {
        tasks.put(projectName, new ArrayList<>());
    }

    public Projects refineTasks(Predicate<Task> predicate) {
        Map<String, List<Task>> result = new LinkedHashMap<>();
        for (Map.Entry<String, List<Task>> entry : tasks.entrySet()) {
            List<Task> todayTasks = entry.getValue().stream().filter(predicate).toList();
            if (!todayTasks.isEmpty()) {
                result.put(entry.getKey(), todayTasks);
            }
        }
        return new Projects(out, result);
    }

    public List<Task> getProject(String project) {
        return tasks.get(project);
    }

    public void show() {
        for (Map.Entry<String, List<Task>> project : tasks.entrySet()) {
            out.println(project.getKey());
            for (Task task : project.getValue()) {
                out.printf("    [%c] %s: %s%n", (task.isDone() ? 'x' : ' '), task.getId(), task.getDescription());
            }
            out.println();
        }
    }

    public void setDone(String taskId, boolean done) {
        for (Map.Entry<String, List<Task>> project : tasks.entrySet()) {
            for (Task task : project.getValue()) {
                if (task.getId().equals(taskId)) {
                    task.setDone(done);
                    return;
                }
            }
        }
        out.printf("Could not find a task with an ID of %s.", taskId);
        out.println();
    }
}
