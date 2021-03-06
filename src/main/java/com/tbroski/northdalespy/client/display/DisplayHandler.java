package main.java.com.tbroski.northdalespy.client.display;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.*;

public class DisplayHandler {

    private static final int WIDTH = 1280;
    private static final int HEIGHT = 720;
    private static final int FPS_CAP = 120;
    private static final String TITLE = "Northdale's Spy";

    public static void create() {
        ContextAttribs attribs = new ContextAttribs(3, 2).withForwardCompatible(true).withProfileCore(true);
        try {
            Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));
            Display.create(new PixelFormat(), attribs);
            Display.setTitle(TITLE);
        } catch (LWJGLException e) {
            e.printStackTrace();
        }
        GL11.glViewport(0, 0, WIDTH, HEIGHT);
    }

    public static void update() {
        Display.sync(FPS_CAP);
        Display.update();
    }

    public static void destroy() {
        Display.destroy();
    }

}
