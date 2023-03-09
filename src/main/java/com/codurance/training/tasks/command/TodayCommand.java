package com.codurance.training.tasks.command;

import com.codurance.training.tasks.data.Task;
import com.codurance.training.tasks.data.Projects;

public class TodayCommand extends ShowCommand {

    @Override
    public boolean appliesTo(String commandLine) {
        return commandLine.startsWith("today");
    }

    @Override
    protected Projects filterTasks(Projects projects) {
        return projects.refineTasks(Task::endsNow);
    }
}
