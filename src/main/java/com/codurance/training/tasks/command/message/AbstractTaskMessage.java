package com.codurance.training.tasks.command.message;

public abstract class AbstractTaskMessage extends AbstractMessage {
    private String taskId;

    @Override
    final void setData(String[] data) {
        taskId = data[1];
        internalSetData(data);
    }

    abstract void internalSetData(String[] data);

    public String getTaskId() {
        return taskId;
    }
}
