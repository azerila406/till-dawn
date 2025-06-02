package com.tilldawn.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.tilldawn.Main;
import com.tilldawn.model.Assets;
import com.tilldawn.model.game.Keys;

public class TalentScreen implements Screen {
        private final Stage stage;
        private final Skin skin = Assets.getSkin();
        private final Main main = Main.getInstance();
        private final Table contentTable;

        public TalentScreen() {
            this.stage = new Stage(new ScreenViewport());
            this.contentTable = new Table(skin);
        }

        @Override
        public void show() {
            Gdx.input.setInputProcessor(stage);

            Table root = new Table(skin);
            root.setFillParent(true);
            stage.addActor(root);

            Label title = new Label("=== HINT MENU ===", skin, "default");
            title.setFontScale(2f);
            title.setColor(Color.YELLOW);
            root.add(title).padBottom(20).row();

            contentTable.top().left();
            ScrollPane scroller = new ScrollPane(contentTable, skin);
            scroller.setFadeScrollBars(false);
            root.add(scroller).width(Gdx.graphics.getWidth() * 0.9f)
                .height(Gdx.graphics.getHeight() * 0.7f).row();

            UIUtil.createBack(() -> main.setScreen(new MainMenuScreen()), stage);

            populateHints();
        }

        private void populateHints() {
            Label cheatHeader = new Label("Cheat Codes:", skin, "default");
            cheatHeader.setFontScale(1.5f);
            cheatHeader.setColor(Color.CYAN);
            contentTable.add(cheatHeader).padTop(10).left().row();

            Label hCheat = new Label(
                "H key:  Increase player’s health.",
                skin);
            hCheat.setFontScale(1.2f);
            contentTable.add(hCheat).padLeft(10).padTop(5).left().row();

            Label tCheat = new Label("T key:  Advance game time by 5 seconds. ", skin);
            tCheat.setFontScale(1.2f);
            contentTable.add(tCheat).padLeft(10).padTop(5).left().row();

            Label pCheat = new Label("P key:  give the player +1 level", skin);
            pCheat.setFontScale(1.2f);
            contentTable.add(pCheat).padLeft(10).padTop(5).left().row();


            Label abilityHeader = new Label("Abilities:", skin, "default");
            abilityHeader.setFontScale(1.5f);
            abilityHeader.setColor(Color.LIME);
            contentTable.add(abilityHeader).padTop(20).left().row();


            Label vitality = new Label("• VITALITY:  Adds players health", skin);
            vitality.setFontScale(1.2f);
            contentTable.add(vitality).padLeft(10).padTop(5).left().row();

           Label damager = new Label("• DAMAGER (duration 10s):  When active, weapon damage is doubled ", skin);
            damager.setFontScale(1.2f);
            contentTable.add(damager).padLeft(10).padTop(5).left().row();

            Label procrease = new Label("• PROCREASE:  Increases projectile count by 1.  ",skin);
            procrease.setFontScale(1.2f);
            contentTable.add(procrease).padLeft(10).padTop(5).left().row();

            Label ammoCrease = new Label("• AMOCREASE:  Increases weapon’s max ammo by 5.  ", skin);
            ammoCrease.setFontScale(1.2f);
            contentTable.add(ammoCrease).padLeft(10).padTop(5).left().row();

            Label speedy = new Label("• SPEEDY (duration 10s):  Doubles player’s movement speed (speed ×2).  ", skin);
            speedy.setFontScale(1.2f);
            contentTable.add(speedy).padLeft(10).padTop(5).left().row();


            Label heroesHeader = new Label("Heroes (HP / Speed):", skin, "default");
            heroesHeader.setFontScale(1.5f);
            heroesHeader.setColor(Color.ORANGE);
            contentTable.add(heroesHeader).padTop(20).left().row();
            Label dasher = new Label(
                "• DASHER:  HP = 130, Speed = 100. " +
                    "A well-rounded warrior—decent health and decent movement speed.  " +
                    "Good for beginners who want survivability without being too slow.",
                skin);
            dasher.setFontScale(1.2f);
            contentTable.add(dasher).padLeft(10).padTop(5).left().row();

            Label diamond = new Label(
                "• DIAMOND:  HP = 50,  Speed = 200.  " +
                    "Higher mobility but very low HP.  Great for hit-and-run tactics if you can avoid direct hits.",
                skin);
            diamond.setFontScale(1.2f);
            contentTable.add(diamond).padLeft(10).padTop(5).left().row();

            Label lilith = new Label(
                "• LILITH:  HP = 10,  Speed = 500.  " +
                    "Nearly paper-thin—one hit and you’re down—but can zip around the battlefield at lightning pace.  " +
                    "Use if you’re confident evasion is your greatest weapon.",
                skin);
            lilith.setFontScale(1.2f);
            contentTable.add(lilith).padLeft(10).padTop(5).left().row();


            Label hotkeysHeader = new Label("Hotkeys (Movement & Reload):", skin, "default");
            hotkeysHeader.setFontScale(1.5f);
            hotkeysHeader.setColor(Color.PINK);
            contentTable.add(hotkeysHeader).padTop(20).left().row();

            for (Keys key : Keys.values()) {
                Label label = new Label(key.name() + " " + key.getKeyName(), skin);
                label.setFontScale(1.2f);
                contentTable.add(label).padLeft(10).padTop(5).left().row();
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
        public void resize(int width, int height) {
            stage.getViewport().update(width, height, true);
        }

        @Override public void pause() {}
        @Override public void resume() {}
        @Override public void hide() {}
        @Override public void dispose() {
            stage.dispose();
        }
    }
