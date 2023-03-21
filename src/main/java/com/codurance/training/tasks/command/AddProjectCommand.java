package com.codurance.training.tasks.command;

import com.codurance.training.tasks.command.message.AbstractMessage;
import com.codurance.training.tasks.command.message.AddProjectMessage;
import com.codurance.training.tasks.command.message.AddTaskMessage;
import com.codurance.training.tasks.data.Projects;
import com.codurance.training.tasks.data.Task;
import com.codurance.training.tasks.exception.ExecutionException;
import java.util.Collections;
import java.util.List;

public class AddProjectCommand implements Command {


    @Override
    public boolean appliesTo(String commandLine) {
        return commandLine.startsWith("add project");
    }

    @Override
    public String getHelpMessage() {
        return "  add project <project name>";
    }

    @Override
    public List<String> execute(String commandLine, Projects projects) {
        AddProjectMessage message = new AddProjectMessage().parse(commandLine);
        projects.addProject(message.getProjectName());
        return Collections.emptyList();
    }
}
