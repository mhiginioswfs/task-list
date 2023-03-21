package com.codurance.training.tasks.command.message;

public abstract class AbstractMessage {

    public <T extends AbstractMessage> T parse(String command) {
        setData(command.split(" ", getChunks()));
        return (T) this;
    }

    abstract int getChunks();

    abstract void setData(String[] data);
}
