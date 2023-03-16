package com.codurance.training.tasks.command;

import com.codurance.training.tasks.data.Projects;
import com.codurance.training.tasks.exception.ExecutionException;
import java.util.Collections;
import java.util.List;

public class LegacyCommand implements Command {

    @Override
    public boolean appliesTo(String commandLine) {
        return true;
    }

    @Override
    public List<String> execute(String commandLine, Projects projects) {
        String[] commandRest = commandLine.split(" ", 2);
        String command = commandRest[0];
        switch (command) {
            case "add" -> add(commandRest, projects);
            case "check" -> check(commandRest[1], projects);
            case "uncheck" -> uncheck(commandRest[1], projects);
            default -> error(command);
        }
        return Collections.emptyList();
    }

    private void add(String[] commandLine, Projects projects) {
        String[] subcommandRest = commandLine[1].split(" ", 2);
        projects.addProject(subcommandRest[1]);
    }

    private void check(String taskId, Projects projects) {
        projects.setDone(taskId, true);
    }

    private void uncheck(String taskId, Projects projects) {
        projects.setDone(taskId, false);
    }

    @Override
    public String getHelpMessage() {
        return "  add project <project name>\r\n" +
                "  check <task ID>\r\n" +
                "  uncheck <task ID>";
    }

    private void error(String command) {
        throw new ExecutionException(String.format("I don't know what the command \"%s\" is.", command));
    }
}
