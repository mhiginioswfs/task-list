package com.codurance.training.tasks.command;

import com.codurance.training.tasks.data.Projects;
import com.codurance.training.tasks.output.Outputter;
import java.io.PrintWriter;

public class LegacyCommand implements Command {

    @Override
    public boolean appliesTo(String commandLine) {
        return true;
    }

    @Override
    public void execute(String commandLine, Projects projects) {
        String[] commandRest = commandLine.split(" ", 2);
        String command = commandRest[0];
        switch (command) {
            case "add" -> add(commandRest, projects);
            case "check" -> check(commandRest[1], projects);
            case "uncheck" -> uncheck(commandRest[1], projects);
            case "help" -> help();
            default -> error(command);
        }
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



    private void help() {
        Outputter out = Outputter.getInstance();
        // FIXME task implementations could return their own help info.
        out.println("Commands:");
        out.println("  show");
        out.println("  add project <project name>");
        out.println("  add task <project name> <task description>");
        out.println("  add namedTask <project name> <task id> <task description>");
        out.println("  check <task ID>");
        out.println("  uncheck <task ID>");
        out.println("  deadline <task ID> <date>");
        out.println();
    }

    private void error(String command) {
        Outputter out = Outputter.getInstance();
        out.printf("I don't know what the command \"%s\" is.", command);
        out.println();
    }
}
