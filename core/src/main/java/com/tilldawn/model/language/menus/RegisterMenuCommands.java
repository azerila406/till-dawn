package com.tilldawn.model.language.menus;

import com.tilldawn.model.language.Lang;
import com.tilldawn.model.language.Message;

import com.tilldawn.model.language.Lang;
import com.tilldawn.model.language.Message;

public enum RegisterMenuCommands implements Message {
    LOGIN("Login", "vorood"),
    REGISTER("Register", "sabtenam"),
    FORGET_PASS("Forget Password", "faramooshi ramz"),
    USERNAME("Username", "nam karybari"),
    PASSWORD("Password", "ramz"),
    NEW_PASSWORD("New Password", "ramz jadid"),
    REPEAT_PASSWORD("Repeat Password", "tekrare ramz"),
    SECURITY_QUESTION("Security Question", "soale amniyati"),
    SECURITY_ANSWER("Security Answer", "pasokhe amniyati"),
    REGISTER_SUCCESS("Register Success", "sabtenam movafagh"),
    SUBMIT("Submit", "ersal");

    private final String eng;
    private final String persian;

    RegisterMenuCommands(String eng, String persian) {
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

