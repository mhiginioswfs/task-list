package com.codurance.training.tasks.command;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.codurance.training.tasks.command.HelpCommand;
import com.codurance.training.tasks.data.Projects;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class HelpCommandTest {

    @Test
    void help_command_should_write_help_to_configured_writer() {
        HelpCommand task = new HelpCommand();
        Projects projects = new Projects();
        List<String> helpResult = task.execute("help", projects);

        List<String> expected = List.of("Commands:",
                "  deadline <task ID> <date>",
                "  show",
                "  today",
                "  add task <project name> <task description>",
                "  add project <project name>",
                "  add namedTask <project name> <task id> <task description>",
                "  delete <task ID>",
                "  check <task ID>\r\n" +
                        "  uncheck <task ID>");

        assertEquals(expected, helpResult);
        assertNull(projects.getProject("myProject"));
    }

}
