package com.codurance.training.tasks;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.verify;

import com.codurance.training.tasks.command.AddNamedTaskCommand;
import com.codurance.training.tasks.data.Task;
import com.codurance.training.tasks.data.Tasks;
import java.io.PrintWriter;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class AddNamedTaskCommandTest {

    @Mock
    private PrintWriter out;

    @Test
    void add_named_task_should_fail_if_project_doesnt_exist() {
        AddNamedTaskCommand task = new AddNamedTaskCommand(out);
        Tasks tasks = new Tasks(out);
        task.execute("add namedTask myProject task bla bla bla", tasks);

        verify(out).printf("Could not find a project with the name \"%s\".", "myProject");
        verify(out).println();

        assertNull(tasks.getProject("myProject"));
    }

    @Test
    void add_named_task_should_add_task_to_project() {
        AddNamedTaskCommand task = new AddNamedTaskCommand(out);
        Tasks tasks = new Tasks(out);
        tasks.addProject("myProject");
        task.execute("add namedTask myProject myTask bla bla bla", tasks);

        List<Task> projectTasks = tasks.getProject("myProject");
        assertNotNull(projectTasks);
        assertEquals(1, projectTasks.size());
        assertEquals("bla bla bla", projectTasks.get(0).getDescription());
        assertEquals("myTask", projectTasks.get(0).getId());
    }

    @Test
    void add_named_task_should_fail_if_task_already_exists() {
        AddNamedTaskCommand task = new AddNamedTaskCommand(out);
        Tasks tasks = new Tasks(out);
        tasks.addProject("myProject");
        task.execute("add namedTask myProject myTask bla bla bla", tasks);

        task.execute("add namedTask myProject myTask Add my task again", tasks);

        List<Task> projectTasks = tasks.getProject("myProject");
        assertNotNull(projectTasks);
        assertEquals(1, projectTasks.size());
        assertEquals("bla bla bla", projectTasks.get(0).getDescription());
        assertEquals("myTask", projectTasks.get(0).getId());

        verify(out).printf("Task \"%s\" already exists.", "myTask");
        verify(out).println();
    }

    @Test
    void add_named_task_should_fail_if_task_code_is_numeric() {
        AddNamedTaskCommand task = new AddNamedTaskCommand(out);
        Tasks tasks = new Tasks(out);
        tasks.addProject("myProject");
        task.execute("add namedTask myProject 123 bla bla bla", tasks);

        assertEquals(0, tasks.getProject("myProject").size());

        verify(out).printf("Task name can't be a number or contain special characters.");
        verify(out).println();
    }

    @Test
    void add_named_task_should_fail_if_task_code_has_special_characters() {
        AddNamedTaskCommand task = new AddNamedTaskCommand(out);
        Tasks tasks = new Tasks(out);
        tasks.addProject("myProject");
        task.execute("add namedTask myProject a.a bla bla bla", tasks);

        assertEquals(0, tasks.getProject("myProject").size());

        verify(out).printf("Task name can't be a number or contain special characters.");
        verify(out).println();
    }
}
