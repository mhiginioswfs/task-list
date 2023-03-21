package com.codurance.training.tasks.command.message;

public class AddNamedTaskMessage extends AbstractProjectMessage {

    private String taskId;
    private String taskDescription;

    @Override
    int getChunks() {
        return 5;
    }

    @Override
    void internalSetData(String[] data) {
        taskId = data[3];
        taskDescription = data[4];
    }

    public String getTaskId() {
        return taskId;
    }

    public String getTaskDescription() {
        return taskDescription;
    }
}
