package com.ernestas.entities;

import com.ernestas.models.TexturedModel;
import com.ernestas.renderEngine.DisplayManager;
import com.ernestas.terrains.Terrain;
import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

import java.util.List;

public class Player extends Entity {

    private static final float RUN_SPEED = 20 * 2;
    private static final float TURN_SPEED = 160;
    private static float GRAVITY = -50;
    private static float JUMP_POWER = 30;

    private float currentSpeed = 0;
    private float currentTurnSpeed = 0;
    private float upwardsSpeed = 0;

    private boolean isInAir = false;

    public Player(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale) {
        super(model, position, rotX, rotY, rotZ, scale);
    }

    public void move(Terrain terrain) {
        checkInputs();
        super.increaseRotation(0, currentTurnSpeed * DisplayManager.getFrameTimeSeconds(), 0);
        float distance = currentSpeed * DisplayManager.getFrameTimeSeconds();
        float dx = (float) (distance * Math.sin(Math.toRadians(super.getRotY())));
        float dz = (float) (distance * Math.cos(Math.toRadians(super.getRotY())));
        super.increasePosition(dx, 0, dz);

        upwardsSpeed += GRAVITY * DisplayManager.getFrameTimeSeconds();
        super.increasePosition(0, upwardsSpeed * DisplayManager.getFrameTimeSeconds(), 0);

        float terrainHeight = 0f;
        if (terrain != null) {
            terrainHeight = terrain.getHeightOfTerrain(super.getPosition().x, super.getPosition().z);
        }
        if (super.getPosition().y < terrainHeight) {
            upwardsSpeed = 0;
            isInAir = false;
            super.getPosition().y = terrainHeight;
        }
    }

    private void jump() {
        if (!isInAir) {
            this.upwardsSpeed = JUMP_POWER;
            isInAir = true;
        }
    }

    private void checkInputs() {
        if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
            this.currentSpeed = RUN_SPEED;
        } else if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
            this.currentSpeed = -RUN_SPEED;
        } else {
            this.currentSpeed = 0;
        }

        if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
            this.currentTurnSpeed = -TURN_SPEED;
        } else if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
            this.currentTurnSpeed = TURN_SPEED;
        } else {
            this.currentTurnSpeed = 0;
        }

        if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
            jump();
        }
    }
}