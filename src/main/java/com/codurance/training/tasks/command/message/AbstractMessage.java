package com.codurance.training.tasks.command.message;

import com.codurance.training.tasks.exception.ExecutionException;

public abstract class AbstractMessage {

    @SuppressWarnings("unchecked")
    public <T extends AbstractMessage> T parse(String command) {
        String[] chunks = command.split(" ", getChunks());
        if (chunks.length < getChunks()) {
            throw new ExecutionException(String.format("%d parameters needed. Command: %s", getChunks(), command));
        }
        setData(chunks);
        return (T) this;
    }

    abstract int getChunks();

    abstract void setData(String[] data);

}
