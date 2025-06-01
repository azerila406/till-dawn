package com.tilldawn.lwjgl3;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Graphics;
import com.tilldawn.model.Vector;
import com.tilldawn.model.game.Player;
import com.tilldawn.view.MouseController;
import org.lwjgl.glfw.GLFW;

public class DesktopMouse implements MouseController {
    boolean override = false;
    Vector mousePos;

    public void setMousePosition(Vector worldPos, Player player) {
        if (!(Gdx.graphics instanceof Lwjgl3Graphics)) return;
        override = true;
        mousePos = worldPos;

        Lwjgl3Graphics graphics = (Lwjgl3Graphics) Gdx.graphics;
        long windowHandle = graphics.getWindow().getWindowHandle();

        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();

        float playerCenterX = player.getX() + player.getWidth() * 0.5f;
        float playerCenterY = player.getY() + player.getHeight() * 0.5f;

        float screenX = worldPos.x - (playerCenterX - screenWidth * 0.5f);
        float logicalY = worldPos.y - (playerCenterY - screenHeight * 0.5f);
        float screenY = screenHeight - logicalY;

        screenX = Math.max(0, Math.min(screenWidth, screenX));
        screenY = Math.max(0, Math.min(screenHeight, screenY));

        GLFW.glfwSetCursorPos(windowHandle, screenX, screenY);
    }

    @Override
    public Vector getMousePosition(Player player) {
        if (override) {
            override = false;
            return mousePos;
        }
        float screenX = Gdx.input.getX();
        float screenY = Gdx.input.getY();

        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();

        screenY = screenHeight - screenY;

        float playerCenterX = player.getX() + player.getWidth() * 0.5f;
        float playerCenterY = player.getY() + player.getHeight() * 0.5f;

        float worldX = playerCenterX - screenWidth * 0.5f + screenX;
        float worldY = playerCenterY - screenHeight * 0.5f + screenY;
        return new Vector(worldX, worldY);
    }

    @Override
    public void setOverride(boolean override) {
        this.override = override;
    }
}
