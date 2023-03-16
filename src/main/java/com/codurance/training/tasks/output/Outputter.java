package com.codurance.training.tasks.output;

import java.io.PrintWriter;

public class Outputter {

    private static final Outputter INSTANCE = new Outputter(new PrintWriter(System.out));
    private PrintWriter writer;

    public Outputter(PrintWriter writer) {
        this.writer = writer;
    }

    public static Outputter getInstance() {
        return INSTANCE;
    }

    public void println() {
        writer.println();
    }

    public void printf(String format, Object... params) {
        writer.printf(format, params);
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
