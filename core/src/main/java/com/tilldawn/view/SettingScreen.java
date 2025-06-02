package com.tilldawn.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.tilldawn.Main;
import com.tilldawn.model.Assets;
import com.tilldawn.model.game.Keys;
import com.tilldawn.model.language.Lang;
import com.tilldawn.model.language.menus.MenuCommands;

public class SettingScreen implements Screen {
        private final Main game = Main.getInstance();
        private Stage stage;
        private Skin skin = Assets.getSkin();

        private CheckBox sfxCheckbox;
        private CheckBox musicCheckbox;
        private CheckBox autoReloadCheckbox;
        private CheckBox grayScaleCheckbox;
        private Slider musicVolumeSlider;
        private SelectBox<String> musicTrackSelect;
        private SelectBox<Lang> lang;

        @Override
        public void show() {
            stage = new Stage(new ScreenViewport());
            Gdx.input.setInputProcessor(stage);

            Table table = new Table();
            table.top().pad(10);

            sfxCheckbox = new CheckBox(" SFX", skin);
            musicCheckbox = new CheckBox(MenuCommands.MUSIC.get(Main.getInstance().lang), skin);
            autoReloadCheckbox = new CheckBox(MenuCommands.AUTO_RELOAD.get(Main.getInstance().lang), skin);
            grayScaleCheckbox = new CheckBox(MenuCommands.GRAY_SCALE.get(Main.getInstance().lang), skin);

            musicVolumeSlider = new Slider(0, 1, 0.01f, false, skin);
            Label volumeLabel = new Label(MenuCommands.MUSIC_VOLUME.get(Main.getInstance().lang), skin);

            musicTrackSelect = new SelectBox<>(skin);
            musicTrackSelect.setItems("Track 1", "Track 2", "Track 3");

            lang = new SelectBox<>(skin);
            lang.setItems(Lang.values());

            table.add(new Label(MenuCommands.SETTINGS.get(Main.getInstance().lang), skin)).colspan(2).pad(20).row();
            table.add(sfxCheckbox).left().pad(10).row();
            table.add(musicCheckbox).left().pad(10).row();
            table.add(autoReloadCheckbox).left().pad(10).row();
            table.add(grayScaleCheckbox).left().pad(10).row();
            table.add(volumeLabel).left().pad(10);
            table.add(musicVolumeSlider).width(200).pad(10).row();
            table.add(new Label(MenuCommands.MUSIC_TRACKS.get(Main.getInstance().lang), skin)).left().pad(10);
            table.add(musicTrackSelect).width(150).pad(10).row();
            table.add(new Label(MenuCommands.LANGUAGE.get(Main.getInstance().lang), skin)).left().pad(10);
            table.add(lang).width(150).pad(10).row();


            for (Keys key : Keys.values()) {
                Label actionLabel = new Label(key.name(), skin);
                TextButton keyButton = new TextButton(key.getKeyName(), skin);

                keyButton.addListener(new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent event, Actor actor) {
                        keyButton.setText("...");
                        Gdx.input.setInputProcessor(new InputAdapter() {
                            @Override
                            public boolean keyDown(int keycode) {
                                key.setKeyCode(keycode);
                                keyButton.setText(Input.Keys.toString(keycode));
                                UIUtil.createBack(() -> game.goToMain(), stage);

                                return true;
                            }
                        });
                    }
                });

                table.add(actionLabel).left().pad(5);
                table.add(keyButton).width(150).pad(5).row();
            }

            UIUtil.createBack(() -> game.goToMain(), stage);

            ScrollPane scrollPane = new ScrollPane(table, skin);
            scrollPane.setFillParent(true);
            scrollPane.setFadeScrollBars(false);
            scrollPane.setScrollingDisabled(false, false);

            stage.addActor(scrollPane);

            musicCheckbox.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    if (musicCheckbox.isChecked()) {
                        Assets.getMusic().play();
                    } else {
                        Assets.getMusic().pause();
                    }
                }
            });

            musicTrackSelect.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    String selectedTrack = musicTrackSelect.getSelected();
                    Assets.switchToTrack(selectedTrack);
                }
            });

            lang.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    Lang selectedLang = lang.getSelected();
                    Main.getInstance().setLang(selectedLang);
                }
            });

            sfxCheckbox.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    game.settings.sfx = sfxCheckbox.isChecked();
                }
            });

            autoReloadCheckbox.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    game.settings.autoReload = autoReloadCheckbox.isChecked();
                }
            });

            grayScaleCheckbox.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    game.toggleGray();
                    game.setGray(grayScaleCheckbox.isChecked());
                }
            });
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
