package com.tilldawn.view;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.tilldawn.model.Assets;
import com.tilldawn.model.sounds.GameSound;

public class UIUtil {
    public static void addClickSound(Button button) {
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GameSound.CLICK.play(Assets.getVolume());
            }
        });
    }
}
