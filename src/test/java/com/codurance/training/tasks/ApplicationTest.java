package com.codurance.training.tasks;

import static java.lang.System.lineSeparator;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.codurance.training.tasks.output.Output;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public final class ApplicationTest {

    public static final String PROMPT = "> ";
    private final PipedOutputStream inStream = new PipedOutputStream();
    private final PrintWriter inWriter = new PrintWriter(inStream, true);

    private final PipedInputStream outStream = new PipedInputStream();
    private final BufferedReader outReader = new BufferedReader(new InputStreamReader(outStream));

    private final Thread applicationThread;
    private final TaskList taskList;

    public ApplicationTest() throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(new PipedInputStream(inStream)));
        taskList = new TaskList(in);
        applicationThread = new Thread(taskList);
        PrintWriter out = new PrintWriter(new PipedOutputStream(outStream), true);
        Output.getInstance().setPrintWriter(out);
    }

    @BeforeEach
    public void start_the_application() {
        applicationThread.start();
    }

    @AfterEach
    public void kill_the_application() throws InterruptedException {
        if (!stillRunning()) {
            return;
        }

        Thread.sleep(1000);
        if (!stillRunning()) {
            return;
        }

        applicationThread.interrupt();
        throw new IllegalStateException("The application is still running.");
    }

    @Test
    public void it_works() throws IOException {
        execute("show");

        execute("add project secrets");
        execute("add task secrets Eat more donuts.");
        execute("add task secrets Destroy all humans.");

        execute("show");
        readLines(
                "secrets",
                "    [ ] 1: Eat more donuts.",
                "    [ ] 2: Destroy all humans.",
                "");

        execute("add project training");
        execute("add task training Four Elements of Simple Design");
        execute("add task training SOLID");
        execute("add task training Coupling and Cohesion");
        execute("add task training Primitive Obsession");
        execute("add task training Outside-In TDD");
        execute("add task training Interaction-Driven Design");

        execute("check 1");
        execute("check 3");
        execute("check 5");
        execute("check 6");

        execute("show");
        readLines(
                "secrets",
                "    [x] 1: Eat more donuts.",
                "    [ ] 2: Destroy all humans.",
                "",
                "training",
                "    [x] 3: Four Elements of Simple Design",
                "    [ ] 4: SOLID",
                "    [x] 5: Coupling and Cohesion",
                "    [x] 6: Primitive Obsession",
                "    [ ] 7: Outside-In TDD",
                "    [ ] 8: Interaction-Driven Design",
                "");

        execute("quit");
    }

    @Test
    public void when_deadline_command_is_called_task_should_be_updated() throws IOException {
        execute("add project secrets");
        execute("add task secrets Eat more donuts.");
        execute("deadline 1 2023-10-10");


        execute("quit");

        assertEquals(LocalDate.of(2023, 10, 10), taskList.getTasks().getTaskById("1").getDeadline());
    }

    @Test
    public void when_task_doesnt_exist_deadline_command_should_throw_an_error() throws Exception {
        execute("add project secrets");
        execute("add task secrets Eat more donuts.");
        execute("deadline 3 2023-10-10");

        Thread.sleep(100);
        assertTrue(taskList.getError() instanceof NoSuchElementException);
        assertEquals("Task 3 not found", taskList.getError().getMessage());
    }

    @Test
    void add_named_task_should_fail_if_project_doesnt_exist() throws Exception {
        execute("add namedTask myProject task bla bla bla");

        readLines("Could not find a project with the name \"myProject\".");

        execute("quit");
    }

    @Test
    public void delete_task_should_remove_the_task() throws Exception {
        execute("add project secrets");
        execute("add task secrets Eat more donuts.");
        execute("add task secrets Very important task.");
        execute("delete 1");

        execute("show");
        readLines(
                "secrets",
                "    [ ] 2: Very important task.",
                "");
        execute("quit");
    }

    @Test
    public void delete_task_should_remove_named_tasks() throws Exception {
        execute("add project secrets");
        execute("add task secrets Eat more donuts.");
        execute("add namedTask secrets very Very important task.");
        execute("delete very");

        execute("show");
        readLines(
                "secrets",
                "    [ ] 1: Eat more donuts.",
                "");
        execute("quit");
    }

    @Test
    public void help_command_returns_help_for_all_commands() throws Exception {
        execute("help");
        readLines(
                "Commands:",
                "  deadline <task ID> <date>",
                "  show",
                "  today",
                "  add task <project name> <task description>",
                "  add namedTask <project name> <task id> <task description>",
                "  delete <task ID>",
                "  add project <project name>",
                "  check <task ID>",
                "  uncheck <task ID>");
        execute("quit");
    }

    @Test
    public void today_command_should_show_all_tasks_due_today() throws Exception {
        execute("add project secrets");
        execute("add task secrets Eat more donuts.");
        execute("add task secrets Destroy all humans.");
        execute("add project empty");
        execute("add task empty Empty task");
        execute("add project training");
        execute("add task training Four Elements of Simple Design");
        execute("add namedTask training SOLID SOLID");
        execute("add task training Coupling and Cohesion");
        execute("add task training Primitive Obsession");
        execute("add task training Outside-In TDD");
        execute("add task training Interaction-Driven Design");

        String now = LocalDate.now().toString();
        String future = LocalDate.now().plusDays(2).toString();
        execute("deadline 1 " + now);
        execute("deadline 2 2000-01-01");
        execute("deadline 3 2000-01-01");
        execute("deadline 5 " + future);
        execute("deadline 6 " + now);
        execute("deadline SOLID " + now);

        execute("check 1");
        execute("check 6");

        execute("today");

        readLines(
                "secrets",
                "    [x] 1: Eat more donuts.",
                "",
                "training",
                "    [ ] SOLID: SOLID",
                "    [x] 6: Primitive Obsession",

                "");

        execute("quit");
    }

    private void execute(String command) throws IOException {
        read(PROMPT);
        write(command);
    }

    private void read(String expectedOutput) throws IOException {
        int length = expectedOutput.length();
        char[] buffer = new char[length];
        outReader.read(buffer, 0, length);
        assertThat(String.valueOf(buffer), is(expectedOutput));
    }

    private void readLines(String... expectedOutput) throws IOException {
        for (String line : expectedOutput) {
            read(line + lineSeparator());
        }
    }

    private void write(String input) {
        inWriter.println(input);
    }

    private boolean stillRunning() {
        return applicationThread != null && applicationThread.isAlive();
    }
}
