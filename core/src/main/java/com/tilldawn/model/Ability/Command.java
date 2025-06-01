package com.tilldawn.model.Ability;

import com.tilldawn.model.game.Game;
import com.tilldawn.model.game.Player;

public interface Command {
    void execute(Game game);
    void end(Game game);
}
