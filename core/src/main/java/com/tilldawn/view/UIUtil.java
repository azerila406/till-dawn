package com.tilldawn.view;

import com.badlogic.gdx.*;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.tilldawn.Main;
import com.tilldawn.model.Assets;
import com.tilldawn.model.sounds.GameSound;
import javax.swing.*;
import java.io.File;

public class UIUtil {
    public static void addClickSound(Button button) {
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GameSound.CLICK.play(Assets.getVolume());
            }
        });
    }

    public static void createBack(Runnable backAction, Stage stage) {
        InputMultiplexer multiplexer = new InputMultiplexer();

        multiplexer.addProcessor(new InputAdapter() {
            @Override
            public boolean keyDown(int keycode) {
                if (keycode == Input.Keys.ESCAPE) {
                    backAction.run();
                    return true;
                }
                return false;
            }
        });

        multiplexer.addProcessor(stage);

        Gdx.input.setInputProcessor(multiplexer);
    }

    public static class FilePicker {
        public static File openFileDialog() {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Select File");
            int result = fileChooser.showOpenDialog(null);

            if (result == JFileChooser.APPROVE_OPTION) {
                return fileChooser.getSelectedFile();
            }
            return null;
        }
    }
}
