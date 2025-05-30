package com.tilldawn.model.language.menus;

import com.tilldawn.model.language.Lang;
import com.tilldawn.model.language.Message;

public enum RegisterMenuCommands implements Message {
    LOGIN("Login"),
    REGISTER("Register"),
    FORGET_PASS("Forget"),
    USERNAME("Username"),
    PASSWORD("Password"),
    NEW_PASSWORD(),
    REPEAT_PASSWORD(""),
    SECURITY_QUESTION(""),
    SECURITY_ANSWER(""),
    REGISTER_SUCCESS,
    SUBMIT(),
    ;

    String eng;

    RegisterMenuCommands() {
        eng = this.name();
    }

    RegisterMenuCommands(String string) {
        eng = string;
        eng = this.name();
    }

    public String get(Lang lang) {
        return eng;
    }
}
