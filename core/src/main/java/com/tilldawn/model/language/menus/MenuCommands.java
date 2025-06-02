package com.tilldawn.model.language.menus;

import com.tilldawn.model.language.Lang;
import com.tilldawn.model.language.Message;

public enum MenuCommands implements Message {
    SUCCESSFUL("Successful", "movafagh"),
    LANGUAGE("Language", "zaban"),
    MUSIC("Music", "moosighi"),
    MUSIC_TRACKS("Music Tracks", "ahangha"),
    SETTINGS("Settings", "tanzimat"),
    AUTO_RELOAD("Auto Reload", "kheshab khodkar"),
    GRAY_SCALE("Gray Scale", "safe khakestari"),
    MUSIC_VOLUME("Music Volume", "volume moosighi"),
    NAME("Name", "nam"),
    TIME("Time", "zaman"),
    SCORE("Score", "emtiyaz"),
    KILLS("Kills", "koshtar"),
    LONGEST_TIME_ALIVE("Longest Time Alive", "bishtarin zaman zende boudan"),
    CHANGE_AVATAR("Change Avatar", "avatare jadid"),
    CHANGE_PASSWORD("Change Password", "taghire ramz"),
    CHANGE_USERNAME("Change Username", "taghire nam karbari"),
    DELETE_ACCOUNT("Delete Account", "hazf hesab"),
    RESUME("Resume", "edame"),
    PAUSED("Paused", "tavaqof"),
    GIVE_UP("Give Up", "taslim"),
    SAVE_AND_EXIT("Save and Exit", "zakhire o khorooj"),
    CHOOSE_YOUR_ABILITY("Choose Your Ability", "ghodrat ra entekhab konid"),
    NEW_USERNAME("New Username", "nam karybari jadid"),
    NEW_PASSWORD("New Password", "ramze jadid"),
    NEW_AVATAR("New Avatar", "avatare jadid"),
    CHANGE("Change", "taghir"),
    YOU_DIED("You Died", "shoma mordid"),
    YOU_WIN("You Win", "shoma bordid"),
    YOU_GAVE_UP("You Gave Up", "taslim shodid"),
    TIME_SURVIVED("Time Survived", "zamane zende monden"),
    NEW("New", "jadid"),
    CONTINUEE("Continue", "edame"),
    HINT("Hint", "rahnama"),
    SCOREBOARD("Scoreboard", "emtiyaza"),
    PROFILE("Profile", "safe karbari"),
    LOGOUT("Logout", "khrooj")
    ;

    private final String eng;
    private final String fa;

    MenuCommands(String eng, String fa) {
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
