package com.codurance.training.tasks.command;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.codurance.training.tasks.data.Projects;
import com.codurance.training.tasks.data.Task;
import com.codurance.training.tasks.exception.ExecutionException;
import org.junit.jupiter.api.Test;

class CheckCommandTest {

    @Test
    void check_task_should_fail_if_task_doesnt_exist() {
        CheckCommand task = new CheckCommand();
        Projects projects = new Projects();
        ExecutionException exception = assertThrows(ExecutionException.class,
                () -> task.execute("check task", projects));

        assertEquals("Could not find a task with an ID of task.", exception.getMessage());

        assertNull(projects.getProject("myProject"));
    }

    @Test
    void check_task_should_mark_task_as_done() {
        CheckCommand task = new CheckCommand();
        Projects projects = new Projects();
        projects.addProject("myProject");
        projects.getProject("myProject").getTasks().add(new Task("task", "description", false));

        task.execute("check task", projects);

        assertTrue(projects.getTaskById("task").isDone());
    }

    @Test
    void check_task_should_mark_task_as_undone() {
        CheckCommand task = new CheckCommand();
        Projects projects = new Projects();
        projects.addProject("myProject");
        projects.getProject("myProject").getTasks().add(new Task("task", "description", true));

        task.execute("uncheck task", projects);

        assertFalse(projects.getTaskById("task").isDone());
    }
}
