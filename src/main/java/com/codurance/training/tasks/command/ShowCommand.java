package com.codurance.training.tasks.command;

import com.codurance.training.tasks.data.Project;
import com.codurance.training.tasks.data.Projects;
import com.codurance.training.tasks.data.ProjectsVisitor;
import com.codurance.training.tasks.data.Task;
import java.util.ArrayList;
import java.util.List;

public class ShowCommand implements Command {

    @Override
    public boolean appliesTo(String commandLine) {
        return commandLine.startsWith("show");
    }

    @Override
    public final List<String> execute(String commandLine, Projects projects) {
        ShowCommandProjectsVisitor visitor = new ShowCommandProjectsVisitor();
        filterTasks(projects).accept(visitor);
        return visitor.getResults();
    }

    @Override
    public String getHelpMessage() {
        return "  show";
    }

    protected Projects filterTasks(Projects projects) {
        return projects;
    }

    public static class ShowCommandProjectsVisitor implements ProjectsVisitor {
        private final List<String> results = new ArrayList<>();

        @Override
        public void visitProject(String key, Project project) {
            results.add(key);
        }

        @Override
        public void visitTask(Task task) {
            String format = String.format("    [%c] %s: %s", (task.isDone() ? 'x' : ' '), task.getId(),
                    task.getDescription());
            results.add(format);
        }

        @Override
        public void endVisitProject(Project project) {
            results.add("");
        }

        public List<String> getResults() {
            return results;
        }
    }
}
