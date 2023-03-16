package com.codurance.training.tasks.output;

import java.io.PrintWriter;

public class Output {

    private static final Output INSTANCE = new Output(new PrintWriter(System.out));
    private PrintWriter writer;

    public Output(PrintWriter writer) {
        this.writer = writer;
    }

    public static Output getInstance() {
        return INSTANCE;
    }

    public void println(String text) {
        writer.println(text);
    }

    public void flush() {
        writer.flush();
    }

    public void print(String text) {
        writer.print(text);
    }

    public void setPrintWriter(PrintWriter writer) {
        this.writer = writer;
    }
}
