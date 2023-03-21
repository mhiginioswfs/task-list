package com.codurance.training.tasks.command;

import com.codurance.training.tasks.command.message.DeleteMessage;
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
        DeleteMessage message = new DeleteMessage().parse(commandLine);
        projects.removeTask(message.getTaskId());
        return Collections.emptyList();
    }

    @Override
    public String getHelpMessage() {
        return "  delete <task ID>";
    }
}
