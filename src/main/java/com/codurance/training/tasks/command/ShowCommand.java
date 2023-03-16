package com.codurance.training.tasks.command;

import com.codurance.training.tasks.data.Projects;
import java.util.List;

public class ShowCommand implements Command {

    @Override
    public boolean appliesTo(String commandLine) {
        return commandLine.startsWith("show");
    }

    @Override
    public final List<String> execute(String commandLine, Projects projects) {
        return filterTasks(projects).show();
    }

    @Override
    public String getHelpMessage() {
        return "  show";
    }

    protected Projects filterTasks(Projects projects) {
        return projects;
    }

}
