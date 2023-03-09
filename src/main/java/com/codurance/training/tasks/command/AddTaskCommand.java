package com.codurance.training.tasks.command;

import com.codurance.training.tasks.data.Task;
import com.codurance.training.tasks.data.Projects;
import com.codurance.training.tasks.output.Outputter;
import java.io.PrintWriter;
import java.util.List;

public class AddTaskCommand implements Command {

    @Override
    public boolean appliesTo(String commandLine) {
        return commandLine.startsWith("add task");
    }

    @Override
    public void execute(String commandLine, Projects projects) {
        addTask(Command.parse(commandLine), projects);
    }

    private void addTask(Command command, Projects projects) {
        List<Task> projectTasks = projects.getProject(command.projectName).getTasks();
        if (projectTasks == null) {

            Outputter out = Outputter.getInstance();
            out.printf("Could not find a project with the name \"%s\".", command.projectName);
            out.println();
            return;
        }
        projectTasks.add(new Task(projects.nextId(), command.taskDescription, false));
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
