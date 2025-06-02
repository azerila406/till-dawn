package com.tilldawn.model.language.error;

import com.tilldawn.model.language.Lang;

public enum MenuErrors implements Error {
    USER_ALREADY_EXIST("User already exists", "karbar ghablan sabtenam karde"),
    WEAK_PASS("Password is too weak", "ramz zaeef ast"),
    USER_DOESNT_EXIST("User doesn't exist", "karbar vojood nadarad"),
    PASSWORD_DONT_MATCH("Passwords don't match", "ramzha motabegh nistand"),
    PASSWORDS_DIFFER("Passwords are different", "ramzha fargh darand"),
    SECURITY_ANSWER_IS_TOO_SHORT("Security answer is too short", "pasokh kootah ast"),
    WRONG_SECURITY_QUESTION("Wrong security question", "soale amniyati eshtebah ast"),
    WRONG_ANSWER("Wrong answer", "pasokh eshtebah ast");

    private final String eng;
    private final String fa;

    MenuErrors(String eng, String fa) {
        this.eng = eng;
        this.fa = fa;
    }

    @Override
    public String get(Lang lang) {
        return switch (lang) {
            case ENG -> eng;
            case PERSIAN -> fa;
        };
    }
}
