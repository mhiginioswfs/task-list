package com.codurance.training.tasks.command;

import com.codurance.training.tasks.data.Task;
import com.codurance.training.tasks.data.Tasks;
import java.io.PrintWriter;
import java.util.List;

public class AddTaskCommand implements Command {

    private final PrintWriter out;

    public AddTaskCommand(PrintWriter out) {
        this.out = out;
    }

    @Override
    public boolean appliesTo(String commandLine) {
        return commandLine.startsWith("add task");
    }

    @Override
    public void execute(String commandLine, Tasks tasks) {
        addTask(Command.parse(commandLine), tasks);
    }

    private void addTask(Command command, Tasks tasks) {
        List<Task> projectTasks = tasks.getProject(command.projectName);
        if (projectTasks == null) {
            out.printf("Could not find a project with the name \"%s\".", command.projectName);
            out.println();
            return;
        }
        projectTasks.add(new Task(tasks.nextId(), command.taskDescription, false));
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
