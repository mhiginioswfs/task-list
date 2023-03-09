package com.codurance.training.tasks.data;

import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Predicate;

public class Projects {

    private final Map<String, Project> projectMap;
    private final PrintWriter out;
    private long lastId = 0;

    public Projects(PrintWriter out) {
        this(out, new LinkedHashMap<>());
    }

    public Projects(PrintWriter out, Map<String, Project> projectMap) {
        this.projectMap = projectMap;
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
        return projectMap.values()
                .stream()
                .flatMap(k -> k.getTasks().stream())
                .filter(task -> task.getId().equals(id))
                .findFirst();
    }

    public String nextId() {
        return String.valueOf(++lastId);
    }

    public void addProject(String projectName) {
        projectMap.put(projectName, new Project());
    }

    public Projects refineTasks(Predicate<Task> predicate) {
        Map<String, Project> result = new LinkedHashMap<>();
        for (Map.Entry<String, Project> entry : projectMap.entrySet()) {
            List<Task> todayTasks = entry.getValue().getTasks().stream().filter(predicate).toList();
            if (!todayTasks.isEmpty()) {
                result.put(entry.getKey(), new Project(todayTasks));
            }
        }
        return new Projects(out, result);
    }

    public Project getProject(String project) {
        return projectMap.get(project);
    }

    public void show() {
        for (Map.Entry<String, Project> project : projectMap.entrySet()) {
            out.println(project.getKey());
            for (Task task : project.getValue().getTasks()) {
                out.printf("    [%c] %s: %s%n", (task.isDone() ? 'x' : ' '), task.getId(), task.getDescription());
            }
            out.println();
        }
    }

    public void setDone(String taskId, boolean done) {
        for (Map.Entry<String, Project> project : projectMap.entrySet()) {
            for (Task task : project.getValue().getTasks()) {
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
