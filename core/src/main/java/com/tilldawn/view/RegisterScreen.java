package com.tilldawn.view;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.tilldawn.Main;
import com.tilldawn.controller.RegisterController;
import com.tilldawn.model.language.menus.LoginMenuCommands;
import com.tilldawn.model.Assets;
import com.tilldawn.model.language.Result;
import com.tilldawn.model.language.menus.RegisterMenuCommands;

public class RegisterScreen implements Screen {
    private final Main game;
    private final RegisterController controller;
    private Stage stage;
    private TextField usernameField;
    private TextField passwordField;
    private TextField repeatPasswordField;
    private SelectBox<String> securityQuestionBox;
    private TextField securityAnswerField;
    private Label messageLabel;

    public RegisterScreen() {
        this.game = Main.getInstance();
        this.controller = new RegisterController(this);
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
        table.add(new Label(RegisterMenuCommands.USERNAME.get(game.getLang()), skin)).right().pad(10);
        table.add(usernameField).width(200).row();

        passwordField = new TextField("", skin);
        passwordField.setPasswordMode(true);
        passwordField.setPasswordCharacter('*');
        table.add(new Label(RegisterMenuCommands.PASSWORD.get(game.getLang()), skin)).right().pad(10);
        table.add(passwordField).width(200).row();

        repeatPasswordField = new TextField("", skin);
        repeatPasswordField.setPasswordMode(true);
        repeatPasswordField.setPasswordCharacter('*');
        table.add(new Label(RegisterMenuCommands.REPEAT_PASSWORD.get(game.getLang()), skin)).right().pad(10);
        table.add(repeatPasswordField).width(200).row();

        securityQuestionBox = new SelectBox<>(skin);
        securityQuestionBox.setItems(game.getSecurityQuestions().toArray(new String[0]));
        table.add(new Label(RegisterMenuCommands.SECURITY_QUESTION.get(game.getLang()), skin)).right().pad(10);
        table.add(securityQuestionBox).width(200).row();

        securityAnswerField = new TextField("", skin);
        table.add(new Label(RegisterMenuCommands.SECURITY_ANSWER.get(game.getLang()), skin)).right().pad(10);
        table.add(securityAnswerField).width(200).row();

        messageLabel = new Label("", skin);
        table.add(messageLabel).colspan(2).center().padTop(10).row();

        TextButton registerButton = new TextButton(LoginMenuCommands.REGISTER.get(game.getLang()), skin);
        registerButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                handleRegistration();
            }
        });
        table.add(registerButton).colspan(2).center().padTop(20).row();

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

    private void handleRegistration() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        String repeatPassword = repeatPasswordField.getText();
        String securityQuestion = securityQuestionBox.getSelected();
        String securityAnswer = securityAnswerField.getText();

        Result<Void> result = controller.handleRegister(username, password, repeatPassword, securityQuestion, securityAnswer);

        if (result.isSuccess()) {
            showMessage(RegisterMenuCommands.REGISTER_SUCCESS.get(game.getLang()), Color.GREEN);
            game.setScreen(new LoginScreen());
        } else {
            showMessage(result.getMessage().get(game.getLang()), Color.RED);
        }
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
