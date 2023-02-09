package com.codurance.training.tasks.command;

import com.codurance.training.tasks.data.Task;
import com.codurance.training.tasks.data.Tasks;
import java.time.LocalDate;

public class DeadLineCommand implements Command {

    @Override
    public boolean appliesTo(String commandLine) {
        return commandLine.startsWith("deadline");
    }

    @Override
    public void execute(String commandLine, Tasks tasks) {
        CommandData data = parse(commandLine);
        Task task = tasks.getTaskById(data.taskId);
        task.setDeadline(data.deadline);
    }

    private CommandData parse(String commandLine) {
        String[] split = commandLine.split(" ");
        return new CommandData(split[1], LocalDate.parse(split[2]));
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
