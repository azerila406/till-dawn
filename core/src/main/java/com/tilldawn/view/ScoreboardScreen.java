package com.tilldawn.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.tilldawn.Main;
import com.tilldawn.model.Assets;
import com.tilldawn.model.user.User;
import com.tilldawn.model.user.UserRepository;

import java.util.List;

public class ScoreboardScreen implements Screen {
    private Stage stage;
    private Table table;
    private final Skin skin = Assets.getSkin();
    private final UserRepository repo = UserRepository.getInstance();
    private final Main main = Main.getInstance();
    private final String loggedInUsername = main.getUser().getUsername();

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        TextButton sortByName = new TextButton("Name", skin);
        TextButton sortByTime = new TextButton("Time", skin);
        TextButton sortByKills = new TextButton("Kills", skin);
        TextButton sortByScore = new TextButton("Score", skin);

        Table buttonRow = new Table();
        buttonRow.add(sortByName).pad(5);
        buttonRow.add(sortByTime).pad(5);
        buttonRow.add(sortByKills).pad(5);
        buttonRow.add(sortByScore).pad(5);
        table.add(buttonRow).colspan(2).padBottom(20);
        table.row();

        updateUserList(repo.getByScore());

        sortByName.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                updateUserList(repo.getByUsername());
            }
        });

        sortByTime.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                updateUserList(repo.getByLongestTime());
            }
        });

        sortByKills.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                updateUserList(repo.getByKills());
            }
        });

        sortByScore.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                updateUserList(repo.getByScore());
            }
        });

        UIUtil.createBack(() -> main.setScreen(new MainMenuScreen()), stage);
        UIUtil.addClickSound(sortByKills);
        UIUtil.addClickSound(sortByScore);
        UIUtil.addClickSound(sortByTime);
        UIUtil.addClickSound(sortByName);
    }

    private void updateUserList(List<User> users) {
        while (table.getRows() > 1) {
            table.removeActor(table.getChildren().get(1));
        }

        int index = 0;
        for (User user : users) {
            Label label = new Label(user.getUsername() + " | Score: " + user.getScore() +
                " | Time: " + user.getLongestTime() + " | Kills: " + user.getKills(), skin);

            label.setFontScale(2f);

            if (user.getUsername().equalsIgnoreCase(loggedInUsername)) {
                label.setColor(Color.BLUE);
            } else if (index == 0) {
                label.setColor(Color.GOLD);
            } else if (index == 1) {
                label.setColor(Color.GRAY);
            } else if (index == 2) {
                label.setColor(Color.BROWN);
            } else {
                label.setColor(Color.WHITE);
            }

            table.row();
            table.add(label).left().pad(5);
            index++;
        }
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0.1f, 1);
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
        stage.dispose();
    }
}
