package com.tilldawn.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.tilldawn.Main;
import com.tilldawn.model.Assets;
import com.tilldawn.model.sounds.GameSound;
import com.tilldawn.model.user.User;

public class MainMenuScreen implements Screen {
    private final Main game;
    private Stage stage;

    public MainMenuScreen() {
        this.game = Main.getInstance();
        createUI();
    }

    Label messageLabel;

    private void createUI() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        Skin skin = Assets.getSkin();
        User user = game.getUser();

        Table table = new Table();
        table.setFillParent(true);
        table.center();
        stage.addActor(table);

        Label userLabel = new Label("Player: " + user.getUsername(), skin);
        Label scoreLabel = new Label("Score: " + user.getScore(), skin);

        Table userInfoTable = new Table();
        userInfoTable.add(userLabel).left().padBottom(5).row();
        userInfoTable.add(scoreLabel).colspan(2).left();

        table.add(userInfoTable).colspan(2).padBottom(30).row();

        TextButton newGameButton = new TextButton("New", skin);
        TextButton continueGame = new TextButton("Continue", skin);
        TextButton talentButton = new TextButton("Talent", skin);
        TextButton scoreboardButton = new TextButton("Scoreboard", skin);
        TextButton profileButton = new TextButton("Profile", skin);
        TextButton settingsButton = new TextButton("Settings", skin);
        TextButton logoutButton = new TextButton("Logout", skin);

        table.add(newGameButton).width(300).height(60).padBottom(15).row();
        table.add(continueGame).width(300).height(60).padBottom(15).row();
        table.add(talentButton).width(300).height(60).padBottom(15).row();
        table.add(scoreboardButton).width(300).height(60).padBottom(15).row();
        table.add(profileButton).width(300).height(60).padBottom(15).row();
        table.add(settingsButton).width(300).height(60).padBottom(15).row();
        table.add(logoutButton).width(300).height(60).row();

        messageLabel = new Label("", skin);
        table.add(messageLabel).colspan(2).center().padTop(10).row();


        UIUtil.addClickSound(newGameButton);
        UIUtil.addClickSound(continueGame);
        UIUtil.addClickSound(talentButton);
        UIUtil.addClickSound(scoreboardButton);
        UIUtil.addClickSound(profileButton);
        UIUtil.addClickSound(settingsButton);
        UIUtil.addClickSound(logoutButton);

        newGameButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new PreGameScreen());
            }
        });

        continueGame.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (Main.getGame() == null || Main.getGame().getPlayer().getUser() != Main.getUser()) {
                    showMessage("You don't have any game!", Color.RED);
                }
                else
                    game.setScreen(new GameScreen());
            }
        });

        talentButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new TalentScreen());
            }
        });

        scoreboardButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new ScoreboardScreen());
            }
        });

        profileButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new ProfileScreen());
            }
        });

        settingsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new SettingScreen());
            }
        });

        logoutButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setUser(null);
                game.setScreen(new PreScreen());
            }
        });
    }

    public void showMessage(String msg, Color color) {
        messageLabel.setText(msg);
        messageLabel.setColor(color);
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
        stage.dispose();
    }
}
