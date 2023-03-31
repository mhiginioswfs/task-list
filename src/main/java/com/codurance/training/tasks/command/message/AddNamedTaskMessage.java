package com.codurance.training.tasks.command.message;

import com.codurance.training.tasks.exception.ExecutionException;

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
        validate();
    }

    public String getTaskId() {
        return taskId;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    private boolean isInvalidId(String taskId) {
        return isNumeric(taskId) || !isAlphanumeric(taskId);
    }

    private boolean isAlphanumeric(String taskId) {
        return taskId.chars().filter(k -> !(Character.isAlphabetic(k) || Character.isDigit(k))).findFirst().isEmpty();
    }

    private boolean isNumeric(String taskId) {
        return taskId.chars().filter(k -> !Character.isDigit(k)).findFirst().isEmpty();
    }

    private void validate() {
        if (isInvalidId(taskId)) {
            throw new ExecutionException("Task name can't be a number or contain special characters.");
        }
    }
}
