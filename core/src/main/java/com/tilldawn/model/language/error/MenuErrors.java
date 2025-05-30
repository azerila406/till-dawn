package com.tilldawn.model.language.error;

import com.tilldawn.model.language.Lang;

public enum MenuErrors implements Error {
    USER_ALREADY_EXIST("", ""),
    WEAK_PASS("", ""),
    USER_DOESNT_EXIST("", ""),
    PASSWORD_DONT_MATCH("", ""),
    PASSWORDS_DIFFER("", ""),
    SECURITY_ANSWER_IS_TOO_SHORT,
    WRONG_SECURITY_QUESTION,
    WRONG_ANSWER,
    ;

    String eng;
    String fa;

    MenuErrors() {
        this.eng = name();
        this.fa = name();
    }

    MenuErrors(String eng, String fa) {
        this.eng = eng;
        this.fa = fa;
        this.eng = this.name();
    }

    public String get(Lang lang) {
        if (lang == Lang.ENG) return eng;
        else return fa;
    }
}
