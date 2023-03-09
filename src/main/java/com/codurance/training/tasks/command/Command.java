package com.codurance.training.tasks.command;

import com.codurance.training.tasks.data.Projects;

public interface Command {
    boolean appliesTo(String commandLine);

    void execute(String commandLine, Projects projects);
}
