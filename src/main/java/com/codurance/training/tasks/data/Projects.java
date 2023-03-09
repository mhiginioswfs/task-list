package com.codurance.training.tasks.data;

import com.codurance.training.tasks.output.Outputter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Predicate;

public class Projects {

    private final Map<String, Project> projectMap;
    private long lastId = 0;

    public Projects() {
        this(new LinkedHashMap<>());
    }

    public Projects(Map<String, Project> projectMap) {
        this.projectMap = projectMap;
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
                .map(project -> project.findTask(id))
                .filter(Optional::isPresent)
                .map(Optional::get)
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
            Project project = entry.getValue();
            List<Task> todayTasks = project.refine(predicate);
            if (!todayTasks.isEmpty()) {
                result.put(entry.getKey(), new Project(todayTasks));
            }
        }
        return new Projects(result);
    }

    public Project getProject(String project) {
        return projectMap.get(project);
    }

    public void show() {
        Outputter out = Outputter.getInstance();
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
        Outputter out = Outputter.getInstance();
        out.printf("Could not find a task with an ID of %s.", taskId);
        out.println();
    }

    public void removeTask(String id) {
        projectMap.values().forEach(project -> project.remove(id));
    }

    public int projectCount() {
        return projectMap.size();
    }
}
