package com.tilldawn.model.game;

public enum State {
    IDLE("idle", "Idle"),
    RUN("run", "Run"),
    WALK("walk", "Walk");

    final String subfolder;
    final String prefix;

    State(String subfolder, String prefix) {
        this.subfolder = subfolder;
        this.prefix = prefix;
    }

    public String getSubfolder() {
        return subfolder;
    }

    public String getPrefix() {
        return prefix;
    }
}
