package com.codurance.training.tasks.command;

import com.codurance.training.tasks.data.Task;
import com.codurance.training.tasks.data.Projects;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

public class DeadLineCommand implements Command {

    @Override
    public boolean appliesTo(String commandLine) {
        return commandLine.startsWith("deadline");
    }

    @Override
    public List<String> execute(String commandLine, Projects projects) {
        CommandData data = parse(commandLine);
        Task task = projects.getTaskById(data.taskId);
        task.setDeadline(data.deadline);
        return Collections.emptyList();
    }

    private CommandData parse(String commandLine) {
        String[] split = commandLine.split(" ");
        return new CommandData(split[1], LocalDate.parse(split[2]));
    }

    @Override
    public String getHelpMessage() {
        return "  deadline <task ID> <date>";
    }

    private static class CommandData {
        private final String taskId;
        private final LocalDate deadline;

        private CommandData(String taskId, LocalDate date) {
            this.taskId = taskId;
            this.deadline = date;
        }
    }
}
