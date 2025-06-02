package com.tilldawn.model.language.menus;

import com.tilldawn.model.language.Lang;
import com.tilldawn.model.language.Message;

public enum PreMenuCommands implements Message {
    EXIT("Exit", "khorooj"),
    SETTING("Setting", "tanzimat"),
    REGISTER("Register", "sabtenam"),
    LOGIN("Login", "vorood");

    private final String eng;
    private final String persian;

    PreMenuCommands(String eng, String persian) {
        this.eng = eng;
        this.persian = persian;
    }

    @Override
    public String get(Lang lang) {
        return switch (lang) {
            case ENG -> eng;
            case PERSIAN -> persian;
        };
    }
}
