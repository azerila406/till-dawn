package com.tilldawn;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tilldawn.model.Assets;
import com.tilldawn.model.game.GameSettings;
import com.tilldawn.model.language.Lang;
import com.tilldawn.model.user.User;
import com.tilldawn.model.user.UserRepository;
import com.tilldawn.view.*;

import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.List;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends Game {
    private static Main main;
    public GameSettings settings = new GameSettings();
    private ShaderProgram shader = null;
    private Music backgroundMusic = null;
    private User user;
    private com.tilldawn.model.game.Game game;
    public final MouseController mouseController;

    public Main(MouseController mouseController) {
        super();
        this.mouseController = mouseController;
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
        game = null;
        tryLoadGame();
    }

    public void tryLoadGame() {
        final String filepath = user.getUsername() + ".ser";
        System.err.println(filepath);

        ObjectMapper mapper = new ObjectMapper();

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filepath))) {
            this.game = (com.tilldawn.model.game.Game) in.readObject();
            this.game.reload();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public List<String> getSecurityQuestions() {
        return List.of("Where do you live?", "When is your birthday?");
    }

    @Override
    public void create() {
        Assets.load();
        Assets.finishLoading();
        setUser(UserRepository.getInstance().getByUsername("GUEST"));
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

    public ShaderProgram getShader() {
        return shader;
    }

    public void toggleGray() {
        if (shader == null) {
            shader = Assets.getShaderProgram();
        }
        else
            shader = null;
    }
}
