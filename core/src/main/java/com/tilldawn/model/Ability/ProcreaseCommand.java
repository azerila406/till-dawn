package com.tilldawn.model.Ability;

import com.tilldawn.model.game.Game;
import com.tilldawn.model.game.Weapon;

public class ProcreaseCommand implements Command {
    @Override
    public void execute(Game game) {
        Weapon weapon = game.getPlayer().getWeapon();
        weapon.setProjectile(weapon.getProjectile() + 1);
    }

    @Override
    public void end(Game game) {
        Weapon weapon = game.getPlayer().getWeapon();
        weapon.setProjectile(weapon.getProjectile() - 1);
    }
}
