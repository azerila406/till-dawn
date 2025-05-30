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
import com.tilldawn.model.Assets;
import com.tilldawn.model.language.Result;
import com.tilldawn.model.language.menus.RegisterMenuCommands;

public class ForgetPasswordScreen implements Screen {
    private final Main game;
    private final LoginController controller;
    private Stage stage;

    private TextField usernameField;
    private SelectBox<String> securityQuestionBox;
    private TextField securityAnswerField;
    private TextField newPasswordField;
    private TextField repeatNewPasswordField;
    private Label messageLabel;

    public ForgetPasswordScreen() {
        this.game = Main.getInstance();
        this.controller = new LoginController(this);
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

        securityQuestionBox = new SelectBox<>(skin);
        securityQuestionBox.setItems(game.getSecurityQuestions().toArray(new String[0]));
        table.add(new Label(RegisterMenuCommands.SECURITY_QUESTION.get(game.getLang()), skin)).right().pad(10);
        table.add(securityQuestionBox).width(200).row();

        securityAnswerField = new TextField("", skin);
        table.add(new Label(RegisterMenuCommands.SECURITY_ANSWER.get(game.getLang()), skin)).right().pad(10);
        table.add(securityAnswerField).width(200).row();

        newPasswordField = new TextField("", skin);
        newPasswordField.setPasswordMode(true);
        newPasswordField.setPasswordCharacter('*');
        table.add(new Label(RegisterMenuCommands.NEW_PASSWORD.get(game.getLang()), skin)).right().pad(10);
        table.add(newPasswordField).width(200).row();

        repeatNewPasswordField = new TextField("", skin);
        repeatNewPasswordField.setPasswordMode(true);
        repeatNewPasswordField.setPasswordCharacter('*');
        table.add(new Label(RegisterMenuCommands.REPEAT_PASSWORD.get(game.getLang()), skin)).right().pad(10);
        table.add(repeatNewPasswordField).width(200).row();

        messageLabel = new Label("", skin);
        table.add(messageLabel).colspan(2).center().padTop(10).row();

        TextButton resetButton = new TextButton(RegisterMenuCommands.SUBMIT.get(game.getLang()), skin);
        resetButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                handleResetPassword();
            }
        });
        table.add(resetButton).colspan(2).center().padTop(20).row();

        InputMultiplexer multiplexer = new InputMultiplexer();

        multiplexer.addProcessor(new InputAdapter() {
            @Override
            public boolean keyDown(int keycode) {
                if (keycode == Input.Keys.ESCAPE) {
                    game.setScreen(new LoginScreen());
                    return true;
                }
                return false;
            }
        });

        multiplexer.addProcessor(stage);
        Gdx.input.setInputProcessor(multiplexer);
    }

    private void handleResetPassword() {
        String username = usernameField.getText();
        String securityQuestion = securityQuestionBox.getSelected();
        String securityAnswer = securityAnswerField.getText();
        String newPassword = newPasswordField.getText();
        String repeatPassword = repeatNewPasswordField.getText();

        Result<Void> result = controller.handleResetPassword(
            username, securityQuestion, securityAnswer, newPassword, repeatPassword
        );

        if (result.isSuccess()) {
            showMessage("Password reset successfully!", Color.GREEN);
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
    public void show() {
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int i, int i1) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
