package com.codurance.training.tasks.data;

import com.codurance.training.tasks.exception.ExecutionException;
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

    public void setDone(String taskId, boolean done) {
        findTask(taskId)
                .orElseThrow(
                        () -> new ExecutionException(String.format("Could not find a task with an ID of %s.", taskId)))
                .setDone(done);
    }

    public void removeTask(String id) {
        projectMap.values().forEach(project -> project.remove(id));
    }

    public int projectCount() {
        return projectMap.size();
    }

    public void accept(ProjectsVisitor visitor) {
        for (Map.Entry<String, Project> entry : projectMap.entrySet()) {
            visitor.visitProject(entry.getKey(), entry.getValue());
            for (Task task : entry.getValue().getTasks()) {
                visitor.visitTask(task);
            }
            visitor.endVisitProject(entry.getValue());
        }
    }
}
