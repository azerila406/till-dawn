package com.tilldawn.controller;

import com.tilldawn.Main;
import com.tilldawn.view.MainMenuScreen;

public class MainMenuController {
    private final Main game = Main.getInstance();
    private final MainMenuScreen screen;

    public MainMenuController(MainMenuScreen screen) {
        this.screen = screen;
    }

}
