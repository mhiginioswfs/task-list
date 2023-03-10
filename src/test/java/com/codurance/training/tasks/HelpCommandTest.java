package com.codurance.training.tasks;


import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.verify;

import com.codurance.training.tasks.command.HelpCommand;
import com.codurance.training.tasks.data.Projects;
import com.codurance.training.tasks.output.Outputter;
import java.io.PrintWriter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class HelpCommandTest {

    @Mock
    private PrintWriter out;

    @BeforeEach
    public void initOutputter() {
        Outputter.getInstance().setPrintWriter(out);
    }

    @Test
    void help_command_should_write_help_to_configured_writer() {
        HelpCommand task = new HelpCommand();
        Projects projects = new Projects();
        task.execute("help", projects);

        verify(out).println("Commands:");
        verify(out).println("  deadline <task ID> <date>");
        verify(out).println("  show");
        verify(out).println("  today");
        verify(out).println("  add task <project name> <task description>");
        verify(out).println("  add namedTask <project name> <task id> <task description>");
        verify(out).println("  delete <task ID>");
        verify(out).println("  add project <project name>\r\n" +
                "  check <task ID>\r\n" +
                "  uncheck <task ID>");

        assertNull(projects.getProject("myProject"));
    }

}
