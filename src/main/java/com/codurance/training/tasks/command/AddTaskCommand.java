package com.codurance.training.tasks.command;

import com.codurance.training.tasks.command.message.AddTaskMessage;
import com.codurance.training.tasks.data.Project;
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
        addTask(new AddTaskMessage().parse(commandLine), projects);
        return Collections.emptyList();
    }

    private void addTask(AddTaskMessage message, Projects projects) {
        Project project = projects.getProject(message.getProjectName());
        if (project == null) {
            throw new ExecutionException(
                    String.format("Could not find a project with the name \"%s\".", message.getProjectName()));
        }
        List<Task> projectTasks = project.getTasks();
        projectTasks.add(new Task(projects.nextId(), message.getTaskDescription(), false));
    }

    @Override
    public String getHelpMessage() {
        return "  add task <project name> <task description>";
    }
}
