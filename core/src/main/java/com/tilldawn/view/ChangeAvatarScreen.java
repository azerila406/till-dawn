package com.tilldawn.view;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.tilldawn.Main;
import com.tilldawn.model.Assets;
import com.tilldawn.model.user.User;
import com.tilldawn.model.user.UserRepository;

import java.io.File;

public class ChangeAvatarScreen implements Screen {
    private Stage stage;
    private final Skin skin = Assets.getSkin();
    private final Main game = Main.getInstance();
    private SelectBox<String> avatarSelectBox;

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        User user = game.getUser();

        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        avatarSelectBox = new SelectBox<>(skin);
        avatarSelectBox.setItems(Assets.avatars.toArray(new String[0]));

        String currentAvatar = user.getPath();
        for (String avatar : Assets.avatars) {
            if (currentAvatar.equals(avatar)) {
                avatarSelectBox.setSelected(avatar);
                break;
            }
        }

        avatarSelectBox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                String selectedAvatar = avatarSelectBox.getSelected();
                user.setPath(selectedAvatar);
                user.update();
            }
        });

        TextButton uploadButton = new TextButton("Upload", skin);
        uploadButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                File selected = UIUtil.FilePicker.openFileDialog();
                if (selected != null) {
                    user.setPath(selected.getAbsolutePath());
                    user.update();
                }
            }
        });

        table.defaults().width(600).pad(10).center();

        table.add(new Label("Select Avatar:", skin)).padBottom(10).row();
        table.add(avatarSelectBox).padBottom(20).row();
        table.add(uploadButton).padBottom(20).row();
        UIUtil.addClickSound(uploadButton);

        UIUtil.createBack(() -> game.setScreen(new ProfileScreen()), stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

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
