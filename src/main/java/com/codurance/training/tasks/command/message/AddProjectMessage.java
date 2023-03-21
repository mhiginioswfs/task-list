package com.codurance.training.tasks.command.message;

public class AddProjectMessage extends AbstractProjectMessage {

    @Override
    int getChunks() {
        return 3;
    }

    @Override
    void internalSetData(String[] data) {
    }
}
