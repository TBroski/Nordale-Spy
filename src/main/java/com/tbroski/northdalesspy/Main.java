package main.java.com.tbroski.northdalesspy;

import main.java.com.tbroski.northdalesspy.client.display.DisplayHandler;
import org.lwjgl.opengl.Display;

public class Main {

    public Main() {
        DisplayHandler.create();
        while(!Display.isCloseRequested()) {
            DisplayHandler.update();
        }
        DisplayHandler.destroy();
    }

    public static void main(String[] args) {
        new Main();
    }
}
