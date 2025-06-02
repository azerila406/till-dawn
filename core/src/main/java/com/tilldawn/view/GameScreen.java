package com.tilldawn.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.tilldawn.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.tilldawn.Main;
import com.tilldawn.controller.GameController;
import com.tilldawn.model.Assets;
import com.tilldawn.model.game.Player;
import com.tilldawn.model.*;
import com.tilldawn.model.texture.Textures;

public class GameScreen implements Screen {
    private final Main game;
    private final GameController controller;
    private final MouseController mouseController;

    private SpriteBatch batch;
    private Stage hudStage;

    private ProgressBar xpBar;
    private Label levelLabel;
    private Label ammoLabel;
    private Label hpLabel;
    private Label timerLabel;
    private Label killsLabel;

    public GameScreen() {
        this.game = Main.getInstance();
        this.controller = new GameController(Main.getGame(), this);
        this.batch = new SpriteBatch();
        this.mouseController = game.mouseController;
        setupHUD();
        setupInput();
    }

    private void setupHUD() {
        hudStage = new Stage(new ScreenViewport());
        Skin skin = Assets.getSkin();

        Table table = new Table();
        table.top().left().pad(10);
        table.setFillParent(true);
        hudStage.addActor(table);

        xpBar = new ProgressBar(0, controller.getMaxXP(), 1, false, skin);

        levelLabel = new Label("", skin);
        ammoLabel = new Label("", skin);
        hpLabel = new Label("", skin);
        timerLabel = new Label("", skin);
        killsLabel = new Label("", skin);

        table.add(xpBar).width(200).padBottom(10).row();
        table.add(levelLabel).left().row();
        table.add(ammoLabel).left().row();
        table.add(hpLabel).left().row();
        table.add(timerLabel).left().row();
        table.add(killsLabel).left().row();

        setUI();
    }

    private void setupInput() {
        Gdx.input.setInputProcessor(new InputMultiplexer(hudStage, new InputAdapter() {
            @Override
            public boolean keyDown(int keycode) {
                controller.handleKeyDown(keycode);
                return true;
            }

            @Override
            public boolean keyUp(int keycode) {
                controller.handleKeyUp(keycode);
                return true;
            }
        }));
    }

    @Override
    public void render(float delta) {
        update(delta);

        Player player = controller.getPlayer();
        float viewportWidth  = Gdx.graphics.getWidth();
        float viewportHeight = Gdx.graphics.getHeight();
        float px = player.getX() + player.getWidth() * 0.5f;
        float py = player.getY() + player.getHeight() * 0.5f;

        Matrix4 transform = new Matrix4().setToTranslation(
            viewportWidth * 0.5f - px,
            viewportHeight * 0.5f - py,
            0
        );

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setShader(game.getShader());
        batch.setTransformMatrix(transform);
        batch.begin();
        drawLoopingBackground(player.getX(), player.getY(), viewportWidth, viewportHeight);
        controller.render(batch);
        batch.end();

        hudStage.act(delta);
        hudStage.draw();
    }

    private void setUI() {
        xpBar.setValue(controller.getXp());
        levelLabel.setText("Level: " + controller.getLevel());
        ammoLabel.setText("Ammo: " + controller.getAmmo());
        hpLabel.setText("HP: " + controller.getHp());
        timerLabel.setText("Time: " + controller.getRemainingTime());
        killsLabel.setText("Kills: " + controller.getKills());
    }

    private void update(float delta) {
        controller.update(delta);
        setUI();
    }

    private void drawLoopingBackground(float playerX, float playerY, float screenWidth, float screenHeight) {
        var bg = Assets.getBackground();
        float bgWidth = bg.getWidth();
        float bgHeight = bg.getHeight();

        float startX = playerX - screenWidth * 0.5f;
        float startY = playerY - screenHeight * 0.5f;

        float offsetX = ((startX % bgWidth) + bgWidth) % bgWidth;
        float offsetY = ((startY % bgHeight) + bgHeight) % bgHeight;

        float originX = startX - offsetX;
        float originY = startY - offsetY;

        int tilesX = (int) Math.ceil(screenWidth / bgWidth) + 1;
        int tilesY = (int) Math.ceil(screenHeight / bgHeight) + 1;

        for (int x = 0; x < tilesX; x++) {
            for (int y = 0; y < tilesY; y++) {
                batch.draw(bg, originX + x * bgWidth, originY + y * bgHeight);
            }
        }
    }

    public Vector getMousePosition() {
        return mouseController.getMousePosition(controller.getPlayer());
    }

    public void setMouse(Vector worldPos) {
        mouseController.setMousePosition(worldPos, controller.getPlayer());
    }

    @Override
    public void resize(int width, int height) {
        hudStage.getViewport().update(width, height, true);
    }

    @Override public void show() {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    @Override public void dispose() {
        batch.dispose();
        hudStage.dispose();
    }
}
