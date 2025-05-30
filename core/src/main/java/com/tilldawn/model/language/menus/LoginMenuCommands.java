package com.tilldawn.model.language.menus;

import com.tilldawn.model.language.Lang;
import com.tilldawn.model.language.Message;

public enum LoginMenuCommands implements Message {
    LOGIN("Login"),
    REGISTER("Register"),
    FORGET_PASS("Forget"),
    USERNAME("Username"),
    PASSWORD("Password"),
    ;

    String eng;

    LoginMenuCommands(String string) {
        eng = string;
        eng = this.name();
    }

    public String get(Lang lang) {
        return eng;
    }
}
