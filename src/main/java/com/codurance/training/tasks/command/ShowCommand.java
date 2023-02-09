package com.codurance.training.tasks.command;

import com.codurance.training.tasks.data.Task;
import com.codurance.training.tasks.TaskList;
import com.codurance.training.tasks.data.Tasks;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

public class ShowCommand implements Command {

    @Override
    public boolean appliesTo(String commandLine) {
        return commandLine.startsWith("show");
    }

    @Override
    public final void execute(String commandLine, Tasks tasks) {
        filterTasks(tasks).show();
    }

    protected Tasks filterTasks(Tasks tasks) {
        return tasks;
    }

}
