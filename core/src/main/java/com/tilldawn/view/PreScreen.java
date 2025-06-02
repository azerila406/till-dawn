package com.tilldawn.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.tilldawn.Main;
import com.tilldawn.model.Assets;
import com.tilldawn.model.language.menus.LoginMenuCommands;
import com.tilldawn.model.language.menus.PreMenuCommands;

public class PreScreen implements Screen {
    private final Main game;
    private Stage stage;

    public PreScreen() {
        this.game = Main.getInstance();
        createUI();
    }

    private void createUI() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        Texture backgroundTexture = Assets.getMenuBackground();
        Image backgroundImage = new Image(new TextureRegionDrawable(new TextureRegion(backgroundTexture)));
        backgroundImage.setFillParent(true);
        stage.addActor(backgroundImage);

        Skin skin = Assets.getSkin();

        Table table = new Table();
        table.setFillParent(true);
        table.center();
        stage.addActor(table);


        TextButton registerButton = new TextButton(PreMenuCommands.REGISTER.get(game.getLang()), skin);
        registerButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new RegisterScreen());
            }
        });
        table.add(registerButton).width(300).height(60).padBottom(20).row();

        TextButton loginButton = new TextButton(PreMenuCommands.LOGIN.get(game.getLang()), skin);
        loginButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new LoginScreen());
            }
        });
        table.add(loginButton).width(300).height(60).padBottom(20).row();

        TextButton settingsButton = new TextButton(PreMenuCommands.SETTING.get(game.getLang()), skin);
        settingsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new SettingScreen());
            }
        });
        table.add(settingsButton).width(300).height(60).padBottom(20).row();

        TextButton exitButton = new TextButton(PreMenuCommands.EXIT.get(game.getLang()), skin);
        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });
        table.add(exitButton).width(300).height(60).row();

        UIUtil.addClickSound(loginButton);
        UIUtil.addClickSound(registerButton);
        UIUtil.addClickSound(exitButton);
        UIUtil.addClickSound(settingsButton);
    }

    @Override
    public void show() {}

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        stage.dispose(); // Dispose only the stage, not global assets
    }
}
