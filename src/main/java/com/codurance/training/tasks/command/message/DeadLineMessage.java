package com.codurance.training.tasks.command.message;

import java.time.LocalDate;

public class DeadLineMessage extends AbstractTaskMessage {

    private LocalDate deadline;

    @Override
    int getChunks() {
        return 3;
    }

    @Override
    void internalSetData(String[] data) {
        deadline = LocalDate.parse(data[2]);
    }

    public LocalDate getDeadline() {
        return deadline;
    }
}
