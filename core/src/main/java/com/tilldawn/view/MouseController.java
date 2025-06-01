package com.tilldawn.view;

import com.tilldawn.model.Vector;
import com.tilldawn.model.game.Player;

public interface MouseController {
    void setMousePosition(Vector worldPos, Player player);
    Vector getMousePosition(Player player);
    void setOverride(boolean override);
}
