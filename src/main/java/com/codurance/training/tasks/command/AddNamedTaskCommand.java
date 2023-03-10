package com.codurance.training.tasks.command;

import com.codurance.training.tasks.data.Task;
import com.codurance.training.tasks.data.Tasks;
import java.io.PrintWriter;
import java.util.List;

public class AddNamedTaskCommand implements Command {

    private final PrintWriter out;

    public AddNamedTaskCommand(PrintWriter out) {
        this.out = out;
    }

    @Override
    public boolean appliesTo(String commandLine) {
        return commandLine.startsWith("add namedTask");
    }

    @Override
    public void execute(String commandLine, Tasks tasks) {
        Command command = Command.parse(commandLine);
        add(command, tasks);
    }

    private void add(Command command, Tasks tasks) {
        List<Task> projectTasks = tasks.getProject(command.projectName);
        if (projectTasks == null) {
            out.printf("Could not find a project with the name \"%s\".", command.projectName);
            out.println();
            return;
        }
        if (isInvalidId(command.taskId)) {
            out.printf("Task name can't be a number or contain special characters.");
            out.println();
            return;
        }
        addTaskToProjectTasks(command, tasks, projectTasks);

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

    private void addTaskToProjectTasks(Command command, Tasks tasks, List<Task> projectTasks) {
        if (!tasks.existTask(command.taskId)) {
            projectTasks.add(new Task(command.taskId, command.taskDescription, false));
        } else {
            out.printf("Task \"%s\" already exists.", command.taskId);
            out.println();
        }
    }


    private static class Command {

        private String projectName;
        private String taskId;
        private String taskDescription;

        public static Command parse(String command) {
            String[] commandSplit = command.split(" ", 5);
            Command result = new Command();
            result.projectName = commandSplit[2];
            result.taskId = commandSplit[3];
            result.taskDescription = commandSplit[4];
            return result;
        }
    }
}
