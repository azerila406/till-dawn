package com.tilldawn.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.tilldawn.Main;
import com.tilldawn.controller.RegisterController;
import com.tilldawn.model.Assets;
import com.tilldawn.model.language.error.MenuErrors;
import com.tilldawn.model.language.menus.RegisterMenuCommands;
import com.tilldawn.model.user.UserRepository;

public class ChangePasswordScreen implements Screen {
    private final Main main = Main.getInstance();
    private Stage stage;
    private final Skin skin = Assets.getSkin();
    private TextField passwordField;
    private TextButton submitButton;
    private Label infoLabel;

    public ChangePasswordScreen() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        Label passwordLabel = new Label("New Password:", skin);
        passwordField = new TextField("", skin);
        passwordField.setPasswordCharacter('*');
        passwordField.setPasswordMode(true);

        submitButton = new TextButton("Change", skin);
        infoLabel = new Label("", skin);
        infoLabel.setColor(Color.RED);

        submitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                String newPassword = passwordField.getText().trim();
                if (RegisterController.isPasswordStrong(newPassword)) {
                    UserRepository.getInstance().changePassword(main.getUser().getUsername(), newPassword);
                    main.setUser(null);
                    main.setScreen(new PreScreen());
                } else {
                    infoLabel.setText(MenuErrors.WEAK_PASS.get(main.getLang()));
                }
            }
        });

        table.add(passwordLabel).pad(10);
        table.add(passwordField).width(200).pad(10);
        table.row();
        table.add(submitButton).colspan(2).pad(10);
        table.row();
        table.add(infoLabel).colspan(2).pad(10);

        UIUtil.createBack(() -> main.setScreen(new ProfileScreen()), stage);
        UIUtil.addClickSound(submitButton);
    }

    @Override
    public void show() { }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() { }

    @Override
    public void resume() { }

    @Override
    public void hide() { }

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
    }
}
