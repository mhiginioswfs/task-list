package com.codurance.training.tasks;

import com.codurance.training.tasks.command.AddNamedTaskCommand;
import com.codurance.training.tasks.command.AddTaskCommand;
import com.codurance.training.tasks.command.Command;
import com.codurance.training.tasks.command.DeadLineCommand;
import com.codurance.training.tasks.command.LegacyCommand;
import com.codurance.training.tasks.command.ShowCommand;
import com.codurance.training.tasks.command.TodayCommand;
import com.codurance.training.tasks.data.Projects;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.List;
import java.util.Optional;

public final class TaskList implements Runnable {
    private static final String QUIT = "quit";
    private final List<Command> commands;

    private final Projects projects;
    private final BufferedReader in;
    private final PrintWriter out;

    private Exception error;


    public TaskList(BufferedReader reader, PrintWriter writer) {
        this.in = reader;
        this.out = writer;
        commands = List.of(new DeadLineCommand(),
                new ShowCommand(),
                new TodayCommand(),
                new AddTaskCommand(out),
                new AddNamedTaskCommand(out),
                new LegacyCommand(out));
        projects = new Projects(out);
    }

    public void run() {
        try {
            while (true) {
                out.print("> ");
                out.flush();
                String command;
                try {
                    command = in.readLine();
                } catch (IOException e) {

                    throw new RuntimeException(e);
                }
                if (command.equals(QUIT)) {
                    break;
                }
                execute(command);
            }
        } catch (Exception ex) {
            error = ex;
        }
    }

    private void execute(String commandLine) {
        Command command = findCommand(commandLine);
        command.execute(commandLine, projects);
    }

    private Command findCommand(String commandLine) {
        Optional<Command> result = commands.stream().filter((Command command) -> command.appliesTo(commandLine)).findFirst();
        return result.orElseThrow();
    }

    public Projects getTasks() {
        return projects;
    }

    public Exception getError() {
        return error;
    }

    public static void main(String[] args) {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);
        new TaskList(in, out).run();
    }

}
