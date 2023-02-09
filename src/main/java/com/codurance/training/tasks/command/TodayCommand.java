package com.codurance.training.tasks.command;

import com.codurance.training.tasks.data.Task;
import com.codurance.training.tasks.TaskList;
import com.codurance.training.tasks.data.Tasks;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class TodayCommand extends ShowCommand {


    public TodayCommand(PrintWriter out) {
        super(out);
    }

    @Override
    public boolean appliesTo(String commandLine) {
        return commandLine.startsWith("today");
    }

    @Override
    protected Tasks filterTasks(Tasks tasks) {
        return tasks.refineTasks(Task::endsNow);
    }

    private boolean isEquals(LocalDate now, Task task) {
        return task.getDeadline() != null && task.getDeadline().equals(now);
    }
}
