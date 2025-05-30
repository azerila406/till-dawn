package com.tilldawn.model.language.menus;

import com.tilldawn.model.language.Lang;
import com.tilldawn.model.language.Message;

public enum PreMenuCommands implements Message {
    EXIT,
    SETTING,
    REGISTER,
    LOGIN
    ;

    @Override
    public String get(Lang lang) {
        return this.name();
    }
}
