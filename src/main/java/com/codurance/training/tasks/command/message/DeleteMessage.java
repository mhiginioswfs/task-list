package com.codurance.training.tasks.command.message;

public class DeleteMessage extends AbstractMessage {

    private String taskId;

    @Override
    int getChunks() {
        return 2;
    }

    @Override
    void setData(String[] data) {
        taskId = data[1];
    }

    public String getTaskId() {
        return taskId;
    }
}
