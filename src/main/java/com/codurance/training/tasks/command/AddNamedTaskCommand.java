package com.codurance.training.tasks.command;

import com.codurance.training.tasks.command.message.AddNamedTaskMessage;
import com.codurance.training.tasks.data.Project;
import com.codurance.training.tasks.data.Projects;
import com.codurance.training.tasks.data.Task;
import com.codurance.training.tasks.exception.ExecutionException;
import java.util.Collections;
import java.util.List;

public class AddNamedTaskCommand implements Command {

    @Override
    public boolean appliesTo(String commandLine) {
        return commandLine.startsWith("add namedTask");
    }

    @Override
    public List<String> execute(String commandLine, Projects projects) {
        AddNamedTaskMessage message = new AddNamedTaskMessage().parse(commandLine);
        add(message, projects);
        return Collections.emptyList();
    }

    private void add(AddNamedTaskMessage message, Projects projects) {
        Project project = projects.getProject(message.getProjectName());
        if (project == null) {
            String error = String.format("Could not find a project with the name \"%s\".", message.getProjectName());
            throw new ExecutionException(error);
        }
        if (isInvalidId(message.getTaskId())) {
            throw new ExecutionException("Task name can't be a number or contain special characters.");
        }
        List<Task> projectTasks = project.getTasks();
        addTaskToProjectTasks(message, projects, projectTasks);
    }

    private static boolean isInvalidId(String taskId) {
        return isNumeric(taskId) || !isAlphanumeric(taskId);
    }

    private static boolean isAlphanumeric(String taskId) {
        return taskId.chars().filter(k -> !(Character.isAlphabetic(k) || Character.isDigit(k))).findFirst().isEmpty();
    }

    private static boolean isNumeric(String taskId) {
        return taskId.chars().filter(k -> !Character.isDigit(k)).findFirst().isEmpty();
    }

    private void addTaskToProjectTasks(AddNamedTaskMessage message, Projects projects, List<Task> projectTasks) {
        if (!projects.existTask(message.getTaskId())) {
            projectTasks.add(new Task(message.getTaskId(), message.getTaskDescription(), false));
        } else {
            throw new ExecutionException(String.format("Task \"%s\" already exists.", message.getTaskId()));
        }
    }

    @Override
    public String getHelpMessage() {
        return "  add namedTask <project name> <task id> <task description>";
    }
}
