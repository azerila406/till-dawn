package com.tilldawn.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.tilldawn.controller.GameController;
import com.tilldawn.model.Ability.AbilityType;
import com.tilldawn.model.Assets;

import java.util.*;
import java.util.List;

public class NewLevelScreen implements Screen {

    private Stage stage;
    private Skin skin;
    private AbilityType[] chosenAbilities;
    private final GameController controller;

    public NewLevelScreen(GameController controller) {
        this.controller = controller;
    }


    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        skin = Assets.getSkin();

        Table table = new Table();
        table.setFillParent(true);
        table.center();

        Label title = new Label("Choose Your Ability", skin);
        table.add(title).colspan(3).padBottom(20).row();

        chosenAbilities = pickThreeAbilities();

        for (AbilityType ability : chosenAbilities) {
            TextButton button = new TextButton(ability.name(), skin);
            button.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    controller.choose(ability);
                }
            });
            UIUtil.addClickSound(button);
            table.add(button).pad(10);
        }

        stage.addActor(table);
    }

    private AbilityType[] pickThreeAbilities() {
        List<AbilityType> all = new ArrayList<>(Arrays.asList(AbilityType.values()));
        Collections.shuffle(all);
        return new AbilityType[]{all.get(0), all.get(1), all.get(2)};
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0.1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();
    }

    @Override public void resize(int width, int height) { stage.getViewport().update(width, height, true); }
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    @Override public void dispose() { stage.dispose(); skin.dispose(); }
}
