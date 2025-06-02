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
import com.tilldawn.model.game.Game;
import com.tilldawn.model.game.GameSettings;
import com.tilldawn.model.game.WeaponType;
import com.tilldawn.model.language.menus.LoginMenuCommands;
import com.tilldawn.model.language.menus.RegisterMenuCommands;
import com.tilldawn.model.texture.Heros;

public class PreGameScreen implements Screen {
    private final Main game = Main.getInstance();
    private Stage stage;
    private SelectBox<Heros> heros;
    private SelectBox<Integer> time;
    private SelectBox<WeaponType> weapon;
    private Skin skin;
    private Button startButton;

    public PreGameScreen() {
        skin = Assets.getSkin();
        fillSelects();
        createUI();
        createBack();
    }

    private void createUI() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        Table table = new Table();
        table.setFillParent(true);
        table.center();

        startButton = new TextButton("Start", skin);

        startButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                handleStart();
            }
        });
        UIUtil.addClickSound(startButton);

        table.add(new Label("Select Hero:", skin)).left();
        table.add(heros).width(400).pad(10).row();

        table.add(new Label("Select Weapon:", skin)).left();
        table.add(weapon).width(400).pad(10).row();

        table.add(new Label("Select Time:", skin)).left();
        table.add(time).width(400).pad(10).row();

        table.add(startButton).colspan(2).center().padTop(20);

        stage.addActor(table);
    }

    private void handleStart() {
        Heros hero = heros.getSelected();
        int timer = time.getSelected();
        WeaponType weaponType = weapon.getSelected();
        GameSettings settings = Main.getInstance().settings;
        settings.totalTime = 60 * timer;
        settings.heroType = hero;
        settings.weaponType = weaponType;
        Game newGame = new Game(Main.getUser(), settings);
        game.setGame(newGame);
        game.setScreen(new GameScreen());
    }

    private void fillSelects() {
        heros = new SelectBox<>(skin);
        heros.setItems(Heros.values());
        time = new SelectBox<>(skin);
        time.setItems(2, 5, 10, 20);
        weapon = new SelectBox<>(skin);
        weapon.setItems(WeaponType.values());
    }

    private void createBack() {
        InputMultiplexer multiplexer = new InputMultiplexer();

        multiplexer.addProcessor(new InputAdapter() {
            @Override
            public boolean keyDown(int keycode) {
                if (keycode == Input.Keys.ESCAPE) {
                    game.setScreen(new MainMenuScreen());
                    return true;
                }
                return false;
            }
        });

        multiplexer.addProcessor(stage);
        Gdx.input.setInputProcessor(multiplexer);
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
