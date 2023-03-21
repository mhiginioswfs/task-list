package com.codurance.training.tasks.command;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.codurance.training.tasks.data.Projects;
import com.codurance.training.tasks.data.Task;
import com.codurance.training.tasks.exception.ExecutionException;
import java.util.List;
import org.junit.jupiter.api.Test;

class AddTaskCommandTest {

    @Test
    void add_task_should_fail_if_project_doesnt_exist() {
        AddTaskCommand task = new AddTaskCommand();
        Projects projects = new Projects();
        ExecutionException exception = assertThrows(ExecutionException.class,
                () -> task.execute("add task myProject task bla bla bla", projects));

        assertEquals("Could not find a project with the name \"myProject\".", exception.getMessage());

        assertNull(projects.getProject("myProject"));
    }

    @Test
    void add_task_should_add_task_to_project() {
        AddTaskCommand task = new AddTaskCommand();
        Projects projects = new Projects();
        projects.addProject("myProject");
        task.execute("add task myProject myTask bla bla bla", projects);

        List<Task> projectTasks = projects.getProject("myProject").getTasks();
        assertNotNull(projectTasks);
        assertEquals(1, projectTasks.size());
        assertEquals("myTask bla bla bla", projectTasks.get(0).getDescription());
        assertEquals("1", projectTasks.get(0).getId());
    }
}
