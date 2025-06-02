package com.tilldawn.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.tilldawn.Main;
import com.tilldawn.controller.GameController;
import com.tilldawn.model.Ability.Ability;
import com.tilldawn.model.Assets;

import java.util.Arrays;
import java.util.List;

public class PauseScreen implements Screen {

    private final Main game = Main.getInstance();
    private final GameController controller;
    private Stage stage;

    public PauseScreen(GameController controller) {
        this.controller = controller;
    }
    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        Skin skin = Assets.getSkin();

        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        Label title = new Label("Paused", skin, "title");
        TextButton resumeButton = new TextButton("Resume", skin);
        TextButton shaderButton = new TextButton("Toggle Grayscale Shader", skin);
        TextButton giveUpButton = new TextButton("Give Up", skin);
        TextButton saveExitButton = new TextButton("Save & Exit", skin);

        resumeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new GameScreen());
            }
        });

        shaderButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.toggleGray();
            }
        });

        giveUpButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.giveUp();
            }
        });

        saveExitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.saveAndExit();
            }
        });

        List<Ability> abilities = controller.getPlayer().abilities;
        StringBuilder abilityText = new StringBuilder("Abilities:\n");
        for (Ability ability : abilities) {
            abilityText.append("- ").append(ability.abilityType).append("\n");
        }
        Label abilitiesLabel = new Label(abilityText.toString(), skin);

        List<String> cheats = Arrays.asList(
            "T -> Advances Time",
            "H -> Add Health"
        );
        StringBuilder cheatText = new StringBuilder("Cheat Codes:\n");
        for (String cheat : cheats) {
            cheatText.append("- ").append(cheat).append("\n");
        }
        Label cheatLabel = new Label(cheatText.toString(), skin);

        table.add(title).padBottom(20).colspan(2).row();
        table.add(resumeButton).fillX().uniformX().pad(5).colspan(2).row();
        table.add(shaderButton).fillX().uniformX().pad(5).colspan(2).row();
        table.add(giveUpButton).fillX().uniformX().pad(5).colspan(2).row();
        table.add(saveExitButton).fillX().uniformX().pad(5).colspan(2).row();
        table.add(abilitiesLabel).left().top().padTop(30).padRight(50);
        table.add(cheatLabel).left().top().padTop(30);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 0.8f);
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
