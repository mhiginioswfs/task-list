package com.codurance.training.tasks.command;

import com.codurance.training.tasks.data.Tasks;

public interface Command {
    boolean appliesTo(String commandLine);

    void execute(String commandLine, Tasks tasks);
}
