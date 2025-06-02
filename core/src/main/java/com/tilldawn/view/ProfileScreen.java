package com.tilldawn.view;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.tilldawn.Main;
import com.tilldawn.model.Assets;
import com.tilldawn.model.language.menus.MenuCommands;
import com.tilldawn.model.user.UserRepository;

public class ProfileScreen implements Screen {
    private final Main game = Main.getInstance();
    private Stage stage;
    private final Skin skin = Assets.getSkin();


    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        TextButton changeAvatarButton = new TextButton(MenuCommands.CHANGE_AVATAR.get(Main.getInstance().lang), skin);
        TextButton changePasswordButton = new TextButton(MenuCommands.CHANGE_PASSWORD.get(Main.getInstance().lang), skin);
        TextButton changeUsernameButton = new TextButton(MenuCommands.CHANGE_USERNAME.get(Main.getInstance().lang), skin);
        TextButton deleteAccountButton = new TextButton(MenuCommands.DELETE_ACCOUNT.get(Main.getInstance().lang), skin);

        changeAvatarButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new ChangeAvatarScreen());
            }
        });

        changePasswordButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new ChangePasswordScreen());
            }
        });

        changeUsernameButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new ChangeUserScreen());
            }
        });

        deleteAccountButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                UserRepository.getInstance().deleteUser(game.getUser().getUsername());
                game.setScreen(new PreScreen());
            }
        });
        table.add(Assets.getImageAvatar(game.getUser())).size(128, 128).padBottom(20).center().row();

        table.defaults().width(600).pad(10).center();

        table.add(changeAvatarButton).pad(10).row();
        table.add(changePasswordButton).pad(10).row();
        table.add(changeUsernameButton).pad(10).row();
        table.add(deleteAccountButton).pad(10).row();

        UIUtil.addClickSound(changeAvatarButton);
        UIUtil.addClickSound(changePasswordButton);
        UIUtil.addClickSound(changeUsernameButton);
        UIUtil.addClickSound(deleteAccountButton);

        UIUtil.createBack(() -> game.setScreen(new MainMenuScreen()), stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
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
        skin.dispose();
    }
}
