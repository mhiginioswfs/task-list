package com.codurance.training.tasks.command;

import com.codurance.training.tasks.data.Projects;
import com.codurance.training.tasks.output.Outputter;
import java.util.List;
import java.util.Objects;

public class HelpCommand implements Command {

    @Override
    public boolean appliesTo(String commandLine) {
        return commandLine.equals("help");
    }

    @Override
    public void execute(String commandLine, Projects projects) {
        Outputter out = Outputter.getInstance();
        out.println("Commands:");
        List<Command> commands = CommandRegistry.getInstance().getCommands();
        commands.stream().map(Command::getHelpMessage).filter(Objects::nonNull).forEach(out::println);
    }

    @Override
    public String getHelpMessage() {
        return null;
    }
}
