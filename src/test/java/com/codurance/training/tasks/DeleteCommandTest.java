package com.codurance.training.tasks;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.codurance.training.tasks.command.DeleteCommand;
import com.codurance.training.tasks.data.Projects;
import com.codurance.training.tasks.data.Task;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class DeleteCommandTest {

    @Test
    void delete_command_should_not_fail_where_there_are_no_projects() {
        DeleteCommand task = new DeleteCommand();
        Projects projects = new Projects();
        task.execute("delete 1", projects);

        assertEquals(0, projects.projectCount());
    }

    @Test
    void delete_command_should_not_fail_where_task_not_exist() {
        DeleteCommand task = new DeleteCommand();
        Projects projects = new Projects();
        projects.addProject("test");
        projects.getProject("test").getTasks().add(new Task("id", "desc", false));
        task.execute("delete 1", projects);

        assertEquals(1, projects.projectCount());
        assertEquals(1, projects.getProject("test").getTasks().size());
        assertTrue(projects.existTask("id"));
    }

    @Test
    void delete_command_should_remove_task_when_exists() {
        DeleteCommand task = new DeleteCommand();
        Projects projects = new Projects();

        projects.addProject("test");
        projects.getProject("test").getTasks().add(new Task("id", "desc", false));

        projects.addProject("test2");
        projects.getProject("test2").getTasks().add(new Task("1", "desc", false));
        projects.getProject("test2").getTasks().add(new Task("2", "desc", false));

        task.execute("delete 1", projects);

        assertEquals(2, projects.projectCount());
        assertEquals(1, projects.getProject("test").getTasks().size());
        assertEquals(1, projects.getProject("test2").getTasks().size());
        assertTrue(projects.existTask("id"));
        assertTrue(projects.existTask("2"));
    }

    @Test
    void delete_command_should_not_remove_project_after_removing_the_last_task() {
        DeleteCommand task = new DeleteCommand();
        Projects projects = new Projects();

        projects.addProject("test");
        projects.getProject("test").getTasks().add(new Task("id", "desc", false));

        projects.addProject("test2");
        projects.getProject("test2").getTasks().add(new Task("1", "desc", false));

        task.execute("delete 1", projects);

        assertEquals(2, projects.projectCount());
        assertEquals(1, projects.getProject("test").getTasks().size());
        assertEquals(0, projects.getProject("test2").getTasks().size());
        assertTrue(projects.existTask("id"));
    }
}
