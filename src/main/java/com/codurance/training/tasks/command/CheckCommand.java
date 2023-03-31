package com.codurance.training.tasks.command;

import com.codurance.training.tasks.command.message.CheckTaskMessage;
import com.codurance.training.tasks.data.Projects;
import java.util.Collections;
import java.util.List;

public class CheckCommand implements Command {

    @Override
    public boolean appliesTo(String commandLine) {
        return commandLine.startsWith("check") || commandLine.startsWith("uncheck");
    }

    @Override
    public List<String> execute(String commandLine, Projects projects) {
        CheckTaskMessage message = new CheckTaskMessage().parse(commandLine);
        projects.setDone(message.getTaskId(), message.isCheck());
        return Collections.emptyList();
    }

    @Override
    public String getHelpMessage() {
        return "  check <task ID>\r\n" +
                "  uncheck <task ID>";
    }
}
