package com.codurance.training.tasks.command;

import com.codurance.training.tasks.command.message.DeadLineMessage;
import com.codurance.training.tasks.data.Projects;
import com.codurance.training.tasks.data.Task;
import java.util.Collections;
import java.util.List;

public class DeadLineCommand implements Command {

    @Override
    public boolean appliesTo(String commandLine) {
        return commandLine.startsWith("deadline");
    }

    @Override
    public List<String> execute(String commandLine, Projects projects) {
        DeadLineMessage message = new DeadLineMessage().parse(commandLine);
        Task task = projects.getTaskById(message.getTaskId());
        task.setDeadline(message.getDeadline());
        return Collections.emptyList();
    }

    @Override
    public String getHelpMessage() {
        return "  deadline <task ID> <date>";
    }
}
