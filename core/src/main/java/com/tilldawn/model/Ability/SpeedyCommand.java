package com.tilldawn.model.Ability;

import com.tilldawn.model.game.Game;
import com.tilldawn.model.game.Player;

public class SpeedyCommand implements Command {
    @Override
    public void execute(Game game) {
        Player player = game.getPlayer();
        player.setSpeed(player.getSpeed() * 2);
    }

    @Override
    public void end(Game game) {
        Player player = game.getPlayer();
        player.setSpeed(player.getSpeed() / 2);
    }
}
