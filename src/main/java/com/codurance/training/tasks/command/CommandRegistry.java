package com.codurance.training.tasks.command;

import java.util.List;

public class CommandRegistry {

    private static final CommandRegistry INSTANCE = new CommandRegistry();
    private final List<Command> commands;

    public CommandRegistry() {
        commands = List.of(new DeadLineCommand(),
                new ShowCommand(),
                new TodayCommand(),
                new AddTaskCommand(),
                new AddProjectCommand(),
                new AddNamedTaskCommand(),
                new DeleteCommand(),
                new HelpCommand(),
                new CheckCommand());
    }

    public List<Command> getCommands() {
        return commands;
    }

    public static CommandRegistry getInstance() {
        return INSTANCE;
    }
}
