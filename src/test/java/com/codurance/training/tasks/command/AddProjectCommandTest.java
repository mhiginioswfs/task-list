package com.codurance.training.tasks.command;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.codurance.training.tasks.command.AddProjectCommand;
import com.codurance.training.tasks.data.Projects;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class AddProjectCommandTest {

    @Test
    void add_project_should_add_project_to_project_map() {
        AddProjectCommand task = new AddProjectCommand();
        Projects projects = new Projects();

        task.execute("add project myProject", projects);

        assertEquals(1, projects.projectCount());
        assertNotNull(projects.getProject("myProject"));
    }
}
