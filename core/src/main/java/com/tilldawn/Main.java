package com.tilldawn;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.tilldawn.model.Assets;
import com.tilldawn.model.language.Lang;
import com.tilldawn.model.user.User;
import com.tilldawn.model.user.UserRepository;
import com.tilldawn.view.LoginScreen;
import com.tilldawn.view.MainMenuScreen;
import com.tilldawn.view.PreScreen;
import com.tilldawn.view.RegisterScreen;

import java.lang.reflect.Array;
import java.util.List;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends Game {
    private static Main main;
    private User user = UserRepository.getInstance().getByUsername("GUEST");
    private com.tilldawn.model.game.Game game;

    public Main() {
        super();
        main = this;
    }

    public static Main getInstance() {
        return main;
    }

    public Lang getLang() {
        return Lang.ENG;
    }

    public static User getUser() {
        return getInstance().user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<String> getSecurityQuestions() {
        return List.of("Where do you live?", "When is your birthday?");
    }

    @Override
    public void create() {
        Assets.load();
        Assets.finishLoading();
        setScreen(new MainMenuScreen());
    }

    @Override
    public void setScreen(Screen screen) {
        System.out.println("Switching to screen: " + screen.getClass().getSimpleName());
        super.setScreen(screen);
    }

    public static com.tilldawn.model.game.Game getGame() {
        return getInstance().game;
    }

    public void setGame(com.tilldawn.model.game.Game game) {
        this.game = game;
    }
}
