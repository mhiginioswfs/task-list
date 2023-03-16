package com.codurance.training.tasks.command;

import com.codurance.training.tasks.data.Projects;

public class ShowCommand implements Command {

    @Override
    public boolean appliesTo(String commandLine) {
        return commandLine.startsWith("show");
    }

    @Override
    public final void execute(String commandLine, Projects projects) {
        filterTasks(projects).show();
    }

    @Override
    public String getHelpMessage() {
        return "  show";
    }

    protected Projects filterTasks(Projects projects) {
        return projects;
    }

}
