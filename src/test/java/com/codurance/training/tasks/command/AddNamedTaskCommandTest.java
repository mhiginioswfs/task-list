package com.codurance.training.tasks.command;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.codurance.training.tasks.command.AddNamedTaskCommand;
import com.codurance.training.tasks.data.Projects;
import com.codurance.training.tasks.data.Task;
import com.codurance.training.tasks.exception.ExecutionException;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class AddNamedTaskCommandTest {

    @Test
    void add_named_task_should_fail_if_project_doesnt_exist() {
        AddNamedTaskCommand task = new AddNamedTaskCommand();
        Projects projects = new Projects();
        ExecutionException exception = assertThrows(ExecutionException.class,
                () -> task.execute("add namedTask myProject task bla bla bla", projects));

        assertEquals("Could not find a project with the name \"myProject\".", exception.getMessage());

        assertNull(projects.getProject("myProject"));
    }

    @Test
    void add_named_task_should_add_task_to_project() {
        AddNamedTaskCommand task = new AddNamedTaskCommand();
        Projects projects = new Projects();
        projects.addProject("myProject");
        task.execute("add namedTask myProject myTask bla bla bla", projects);

        List<Task> projectTasks = projects.getProject("myProject").getTasks();
        assertNotNull(projectTasks);
        assertEquals(1, projectTasks.size());
        assertEquals("bla bla bla", projectTasks.get(0).getDescription());
        assertEquals("myTask", projectTasks.get(0).getId());
    }

    @Test
    void add_named_task_should_fail_if_task_already_exists() {
        AddNamedTaskCommand task = new AddNamedTaskCommand();
        Projects projects = new Projects();
        projects.addProject("myProject");
        task.execute("add namedTask myProject myTask bla bla bla", projects);

        ExecutionException exception = assertThrows(ExecutionException.class,
                () -> task.execute("add namedTask myProject myTask Add my task again", projects));

        List<Task> projectTasks = projects.getProject("myProject").getTasks();
        assertNotNull(projectTasks);
        assertEquals(1, projectTasks.size());
        assertEquals("bla bla bla", projectTasks.get(0).getDescription());
        assertEquals("myTask", projectTasks.get(0).getId());

        assertEquals("Task \"myTask\" already exists.", exception.getMessage());
    }

    @Test
    void add_named_task_should_fail_if_task_code_is_numeric() {
        AddNamedTaskCommand task = new AddNamedTaskCommand();
        Projects projects = new Projects();
        projects.addProject("myProject");
        ExecutionException exception = assertThrows(ExecutionException.class,
                () -> task.execute("add namedTask myProject 123 bla bla bla", projects));

        assertEquals(0, projects.getProject("myProject").getTasks().size());

        assertEquals("Task name can't be a number or contain special characters.", exception.getMessage());
    }

    @Test
    void add_named_task_should_fail_if_task_code_has_special_characters() {
        AddNamedTaskCommand task = new AddNamedTaskCommand();
        Projects projects = new Projects();
        projects.addProject("myProject");
        ExecutionException exception = assertThrows(ExecutionException.class,
                () -> task.execute("add namedTask myProject a.a bla bla bla", projects));

        assertEquals(0, projects.getProject("myProject").getTasks().size());
        assertEquals("Task name can't be a number or contain special characters.", exception.getMessage());
    }
}
