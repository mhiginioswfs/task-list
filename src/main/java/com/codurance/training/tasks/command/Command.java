package com.codurance.training.tasks.command;

import com.codurance.training.tasks.data.Projects;
import java.util.List;

public interface Command {

    boolean appliesTo(String commandLine);

    String getHelpMessage();

    List<String> execute(String commandLine, Projects projects);
}
