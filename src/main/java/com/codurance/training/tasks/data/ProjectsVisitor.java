package com.codurance.training.tasks.data;

public interface ProjectsVisitor {

    void visitProject(String key, Project project);

    void visitTask(Task task);

    void endVisitProject(Project project);

}
