package com.codurance.training.tasks;

import com.codurance.training.tasks.command.Command;
import com.codurance.training.tasks.command.CommandRegistry;
import com.codurance.training.tasks.data.Projects;
import com.codurance.training.tasks.exception.ExecutionException;
import com.codurance.training.tasks.output.Output;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Optional;

public final class TaskList implements Runnable {

    private static final String QUIT = "quit";


    private final Projects projects;
    private final BufferedReader in;

    private Exception error;


    public TaskList(BufferedReader reader) {
        this.in = reader;
        projects = new Projects();
    }

    public void run() {
        try {
            Output out = Output.getInstance();
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
        Output out = Output.getInstance();
        try {
            List<String> execute = command.execute(commandLine, projects);
            execute.forEach(out::println);
        } catch (ExecutionException e) {
            out.println(e.getMessage());
        }
    }

    private Command findCommand(String commandLine) {
        Optional<Command> result = CommandRegistry.getInstance()
                .getCommands()
                .stream()
                .filter((Command command) -> command.appliesTo(commandLine))
                .findFirst();
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
        new TaskList(in).run();
    }

}
