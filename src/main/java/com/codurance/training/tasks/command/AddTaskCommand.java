package com.codurance.training.tasks.command;

import com.codurance.training.tasks.data.Projects;
import com.codurance.training.tasks.data.Task;
import com.codurance.training.tasks.exception.ExecutionException;
import java.util.Collections;
import java.util.List;

public class AddTaskCommand implements Command {

    @Override
    public boolean appliesTo(String commandLine) {
        return commandLine.startsWith("add task");
    }

    @Override
    public List<String> execute(String commandLine, Projects projects) {
        addTask(Command.parse(commandLine), projects);
        return Collections.emptyList();
    }

    private void addTask(Command command, Projects projects) {
        List<Task> projectTasks = projects.getProject(command.projectName).getTasks();
        if (projectTasks == null) {
            throw new ExecutionException(
                    String.format("Could not find a project with the name \"%s\".", command.projectName));
        }
        projectTasks.add(new Task(projects.nextId(), command.taskDescription, false));
    }

    @Override
    public String getHelpMessage() {
        return "  add task <project name> <task description>";
    }

    private static class Command {

        private String projectName;
        private String taskDescription;

        public static Command parse(String command) {
            String[] commandSplit = command.split(" ", 4);
            Command result = new Command();
            result.projectName = commandSplit[2];
            result.taskDescription = commandSplit[3];
            return result;
        }
    }
}
