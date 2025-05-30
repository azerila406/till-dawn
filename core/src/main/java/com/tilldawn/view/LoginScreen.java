package com.tilldawn.view;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.tilldawn.Main;
import com.tilldawn.controller.LoginController;
import com.tilldawn.model.language.menus.LoginMenuCommands;
import com.tilldawn.model.Assets;
import com.tilldawn.model.language.Result;
import com.tilldawn.model.user.User;

public class LoginScreen implements Screen {
        private final Main game;
        private final LoginController controller = new LoginController(this);
        private Stage stage;
        private TextField usernameField;
        private TextField passwordField;
        private TextButton loginButton;
        private TextButton forgetButton;
        private Label messageLabel;

        public LoginScreen() {
            this.game = Main.getInstance();
            createUI();
        }

        private void createUI() {
            stage = new Stage(new ScreenViewport());
            Gdx.input.setInputProcessor(stage);

            Skin skin = Assets.getSkin();

            Table table = new Table();
            table.setFillParent(true);
            table.center();
            stage.addActor(table);

            usernameField = new TextField("", skin);
            passwordField = new TextField("", skin);
            passwordField.setPasswordMode(true);
            passwordField.setPasswordCharacter('*');

            messageLabel = new Label("", skin);

            table.add(new Label(LoginMenuCommands.USERNAME.get(game.getLang()), skin)).right().pad(10);
            table.add(usernameField).row();

            table.add(new Label(LoginMenuCommands.PASSWORD.get(game.getLang()), skin)).right().pad(10);
            table.add(passwordField).row();

            Table buttonRow = new Table();
            loginButton = new TextButton(LoginMenuCommands.LOGIN.get(game.getLang()), skin);
            forgetButton = new TextButton(LoginMenuCommands.FORGET_PASS.get(game.getLang()), skin);
            forgetButton.getLabel().setFontScale(0.5f);
            buttonRow.add(loginButton).width(200);
            buttonRow.add(forgetButton).width(200);

            table.add(buttonRow).colspan(2).center().padTop(20).row();

            table.add(messageLabel).row();

            loginButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    handleLogin();
                }
            });

            forgetButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    handleForget();
                }
            });

            UIUtil.addClickSound(loginButton);
            UIUtil.addClickSound(forgetButton);

            InputMultiplexer multiplexer = new InputMultiplexer();

            multiplexer.addProcessor(new InputAdapter() {
                @Override
                public boolean keyDown(int keycode) {
                    if (keycode == Input.Keys.ESCAPE) {
                        game.setScreen(new PreScreen());
                        return true;
                    }
                    return false;
                }
            });

            multiplexer.addProcessor(stage);

            Gdx.input.setInputProcessor(multiplexer);
        }

    private void handleForget() {
            game.setScreen(new ForgetPasswordScreen());
    }

    public void showMessage(String msg, Color color) {
            messageLabel.setText(msg);
            messageLabel.setColor(color);
        }

        public void handleLogin() {
            String username = usernameField.getText();
            String password = passwordField.getText();

            Result<User> result = controller.handleLogin(username, password);

            if (result.isSuccess()) {
                game.setUser(result.getData());
                game.setScreen(new MainMenuScreen());
            } else {
                showMessage(result.getError().get(game.getLang()), Color.RED);
            }
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
