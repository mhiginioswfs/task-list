package com.codurance.training.tasks.command.message;

public abstract class AbstractProjectMessage extends AbstractMessage {
    private String projectName;

    @Override
    final void setData(String[] data) {
        projectName = data[2];
        internalSetData(data);
    }

    abstract void internalSetData(String[] data);

    public String getProjectName() {
        return projectName;
    }
}
