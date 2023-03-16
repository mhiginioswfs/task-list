package com.codurance.training.tasks.command;

import com.codurance.training.tasks.data.Projects;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class HelpCommand implements Command {

    @Override
    public boolean appliesTo(String commandLine) {
        return commandLine.equals("help");
    }

    @Override
    public List<String> execute(String commandLine, Projects projects) {
        List<String> result = new ArrayList<>();
        result.add("Commands:");
        List<Command> commands = CommandRegistry.getInstance().getCommands();
        commands.stream().map(Command::getHelpMessage).filter(Objects::nonNull).forEach(result::add);
        return result;
    }

    @Override
    public String getHelpMessage() {
        return null;
    }
}
