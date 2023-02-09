package com.codurance.training.tasks.data;

import java.time.LocalDate;

public final class Task {
    private final String id;
    private final String description;
    private boolean done;

    private LocalDate deadline;

    public Task(String id, String description, boolean done) {
        this.id = id;
        this.description = description;
        this.done = done;
    }

    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    public boolean endsNow() {
        return deadline != null && deadline.equals(LocalDate.now());
    }
}
