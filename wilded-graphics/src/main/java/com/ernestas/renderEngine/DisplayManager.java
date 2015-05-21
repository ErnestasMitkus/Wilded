package com.ernestas.renderEngine;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.opengl.*;

public class DisplayManager {

    public static final int WIDTH = 1280;
    public static final int HEIGHT = 720;
    public static final int FPS = 120;
    public static final String TITLE = "Wilded";

    private static long lastFrameTime;
    private static float delta;

    public static void createDisplay(){
        ContextAttribs attribs = new ContextAttribs(3, 2)
            .withForwardCompatible(true)
            .withProfileCore(true)
        ;

        try {
            Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));
            Display.setLocation(0, 0);
            Display.setTitle(TITLE);
            Display.create(new PixelFormat(), attribs);
        } catch (LWJGLException e) {
            e.printStackTrace();
        }

        GL11.glViewport(0, 0, WIDTH, HEIGHT);
        lastFrameTime = getCurrentTime();
    }

    public static void updateDisplay() {
        Display.sync(FPS);
        Display.update();
        long currentFrameTime = getCurrentTime();
        delta = (currentFrameTime - lastFrameTime) / 1000f;
        lastFrameTime = currentFrameTime;
    }

    public static float getFrameTimeSeconds() {
        return delta;
    }

    public static void closeDisplay() {
        Display.destroy();
    }

    private static long getCurrentTime() {
        return Sys.getTime()*1000 / Sys.getTimerResolution();
    }

}
