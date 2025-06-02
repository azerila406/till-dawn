package com.tilldawn.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.tilldawn.Main;
import com.tilldawn.model.Assets;
import com.tilldawn.model.game.Player;
import com.tilldawn.model.language.menus.MenuCommands;
import com.tilldawn.model.user.User;

public class EndGameScreen implements Screen {

    private final Stage stage;
    private final Skin skin = Assets.getSkin();
    private final Main main = Main.getInstance();

    public enum GameResult { WIN, DEAD, GAVE_UP }

    private final GameResult result;
    private final Player player = main.getGame().getPlayer();

    public EndGameScreen(GameResult result) {
        this.result = result;
        this.stage = new Stage(new ScreenViewport());
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);

        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        Label resultLabel = new Label(getResultText(), skin, "default");
        resultLabel.setFontScale(2.5f);
        resultLabel.setColor(getResultColor());

        Label timeLabel = new Label("Time Survived: " + (int) main.getGame().timePassed, skin);
        Label killsLabel = new Label("Kills: " + player.getKills(), skin);
        Label scoreLabel = new Label("Score: " + player.getScore(), skin);
        Label levelLabel = new Label("Level: " + player.getLevel(), skin);

        timeLabel.setFontScale(1.5f);
        killsLabel.setFontScale(1.5f);
        scoreLabel.setFontScale(1.5f);
        levelLabel.setFontScale(1.5f);

        UIUtil.createBack(() -> main.setScreen(new MainMenuScreen()), stage);

        table.add(resultLabel).padBottom(30).row();
        table.add(timeLabel).pad(10).row();
        table.add(killsLabel).pad(10).row();
        table.add(scoreLabel).pad(10).row();
        table.add(levelLabel).pad(10).row();
    }

    private String getResultText() {
        switch (result) {
            case WIN: return MenuCommands.YOU_WIN.get(Main.getInstance().lang);
            case DEAD: return MenuCommands.YOU_DIED.get(Main.getInstance().lang);
            case GAVE_UP: return MenuCommands.YOU_GAVE_UP.get(Main.getInstance().lang);
            default: return "Game Over";
        }
    }

    private Color getResultColor() {
        switch (result) {
            case WIN: return Color.GREEN;
            case DEAD: return Color.RED;
            case GAVE_UP: return Color.GRAY;
            default: return Color.WHITE;
        }
    }

    @Override public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0.1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();
    }
    @Override public void resize(int width, int height) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    @Override public void dispose() { stage.dispose(); }
}
