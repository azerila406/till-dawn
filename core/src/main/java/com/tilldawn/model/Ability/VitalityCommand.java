package com.tilldawn.model.Ability;

import com.tilldawn.model.game.Game;
import com.tilldawn.model.game.Player;

public class VitalityCommand implements Command {
    @Override
    public void execute(Game game) {
        Player player = game.getPlayer();
        player.setHealth(player.getHealth() + 1);
    }

    @Override
    public void end(Game game) {

    }
}
