package com.codurance.training.tasks.command;

import com.codurance.training.tasks.data.Projects;
import java.util.Collections;
import java.util.List;

public class DeleteCommand implements Command {

    @Override
    public boolean appliesTo(String commandLine) {
        return commandLine.startsWith("delete");
    }

    @Override
    public final List<String> execute(String commandLine, Projects projects) {
        Command deleteCommand = Command.parse(commandLine);
        projects.removeTask(deleteCommand.id);
        return Collections.emptyList();
    }

    @Override
    public String getHelpMessage() {
        return "  delete <task ID>";
    }

    private static class Command {

        private String id;

        private Command(String id) {
            this.id = id;
        }

        public static Command parse(String command) {
            String[] commandSplit = command.split(" ", 2);
            return new Command(commandSplit[1]);
        }
    }
}
