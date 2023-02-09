package com.codurance.training.tasks.command;

import com.codurance.training.tasks.data.Task;
import com.codurance.training.tasks.data.Tasks;
import java.io.PrintWriter;
import java.util.List;

public class LegacyCommand implements Command {

    private final PrintWriter out;

    public LegacyCommand(PrintWriter out) {
        this.out = out;
    }

    @Override
    public boolean appliesTo(String commandLine) {
        return true;
    }

    @Override
    public void execute(String commandLine, Tasks tasks) {
        String[] commandRest = commandLine.split(" ", 2);
        String command = commandRest[0];
        switch (command) {
            case "add" -> add(commandRest, tasks);
            case "check" -> check(commandRest[1], tasks);
            case "uncheck" -> uncheck(commandRest[1], tasks);
            case "help" -> help();
            default -> error(command);
        }
    }

    private void add(String[] commandLine, Tasks tasks) {
        String[] subcommandRest = commandLine[1].split(" ", 2);
        tasks.addProject(subcommandRest[1]);
    }

    private void check(String taskId, Tasks tasks) {
        tasks.setDone(taskId, true);
    }

    private void uncheck(String taskId, Tasks tasks) {
        tasks.setDone(taskId, false);
    }



    private void help() {
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
        out.printf("I don't know what the command \"%s\" is.", command);
        out.println();
    }
}
