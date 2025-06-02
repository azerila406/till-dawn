package com.tilldawn.model.game;

import java.io.Serializable;

public enum State implements Serializable {
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
