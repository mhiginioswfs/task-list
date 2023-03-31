package com.codurance.training.tasks.command.message;

public class AddTaskMessage extends AbstractProjectMessage {

    private String taskDescription;

    @Override
    int getChunks() {
        return 4;
    }

    @Override
    void internalSetData(String[] data) {
        taskDescription = data[3];
    }

    public String getTaskDescription() {
        return taskDescription;
    }
}
