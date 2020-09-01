package main.java.com.tbroski.northdalespy;

import main.java.com.tbroski.northdalespy.client.display.Camera;
import main.java.com.tbroski.northdalespy.client.display.DisplayHandler;
import main.java.com.tbroski.northdalespy.client.model.TexturedModel;
import main.java.com.tbroski.northdalespy.client.renderer.Loader;
import main.java.com.tbroski.northdalespy.client.renderer.RenderHandler;
import main.java.com.tbroski.northdalespy.client.renderer.Texture;
import main.java.com.tbroski.northdalespy.client.shaders.impl.EntityShader;
import main.java.com.tbroski.northdalespy.entity.Entity;
import main.java.com.tbroski.northdalespy.world.World;
import org.lwjgl.opengl.Display;

import java.awt.*;
import java.io.IOException;

public class Main {

    public Main() throws IOException {
        DisplayHandler.create();
        Loader loader = new Loader();
        EntityShader entityShader = new EntityShader();
        RenderHandler renderer = new RenderHandler(entityShader);
        World world = new World(Color.CYAN);
        Camera camera = new Camera();

        float[] vertices = {
                -0.5f,0.5f,-0.5f,
                -0.5f,-0.5f,-0.5f,
                0.5f,-0.5f,-0.5f,
                0.5f,0.5f,-0.5f,

                -0.5f,0.5f,0.5f,
                -0.5f,-0.5f,0.5f,
                0.5f,-0.5f,0.5f,
                0.5f,0.5f,0.5f,

                0.5f,0.5f,-0.5f,
                0.5f,-0.5f,-0.5f,
                0.5f,-0.5f,0.5f,
                0.5f,0.5f,0.5f,

                -0.5f,0.5f,-0.5f,
                -0.5f,-0.5f,-0.5f,
                -0.5f,-0.5f,0.5f,
                -0.5f,0.5f,0.5f,

                -0.5f,0.5f,0.5f,
                -0.5f,0.5f,-0.5f,
                0.5f,0.5f,-0.5f,
                0.5f,0.5f,0.5f,

                -0.5f,-0.5f,0.5f,
                -0.5f,-0.5f,-0.5f,
                0.5f,-0.5f,-0.5f,
                0.5f,-0.5f,0.5f

        };

        float[] uv = {

                0,0,
                0,1,
                1,1,
                1,0,
                0,0,
                0,1,
                1,1,
                1,0,
                0,0,
                0,1,
                1,1,
                1,0,
                0,0,
                0,1,
                1,1,
                1,0,
                0,0,
                0,1,
                1,1,
                1,0,
                0,0,
                0,1,
                1,1,
                1,0


        };

        int[] indices = {
                0,1,3,
                3,1,2,
                4,5,7,
                7,5,6,
                8,9,11,
                11,9,10,
                12,13,15,
                15,13,14,
                16,17,19,
                19,17,18,
                20,21,23,
                23,21,22

        };

        TexturedModel model = new TexturedModel(loader.loadToVAO(vertices, indices, uv), new Texture(loader.loadTexture("shaggy")));
        Entity entity = new Entity("lol", model);
        world.addEntity(entity);

        while(!Display.isCloseRequested()) {
            //entity.move(0,0,-0.002F);
            //entity.rotate(0,0.5F, 0);
            camera.move();
            renderer.prepare(world);
            entityShader.start();
            entityShader.loadViewMatrix(camera);
            renderer.renderWorld(world, entityShader);
            entityShader.stop();
            DisplayHandler.update();
        }

        entityShader.clean();
        loader.clean();
        DisplayHandler.destroy();
    }

    public static void main(String[] args) throws IOException {
        new Main();
    }
}
