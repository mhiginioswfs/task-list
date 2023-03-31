package com.codurance.training.tasks.command.message;

public class CheckTaskMessage extends AbstractTaskMessage {

    private boolean check;

    @Override
    int getChunks() {
        return 2;
    }

    @Override
    void internalSetData(String[] data) {
        check = "check".equals(data[0]);
    }

    public boolean isCheck() {
        return check;
    }
}
