package main.java.com.tbroski.northdalespy.entity;

import main.java.com.tbroski.northdalespy.client.model.Model;
import main.java.com.tbroski.northdalespy.client.model.TexturedModel;
import org.lwjgl.util.vector.Vector3f;

import java.awt.*;

public class Entity {

    private TexturedModel model;

    private Vector3f position = new Vector3f(0,0,0);
    private Vector3f rotation = new Vector3f(0,0,0);
    private float scale = 1F;

    private int lightAmount;
    private Color lightColor = null;

    //public abstract void tick();

    public Entity(String registryName, TexturedModel model) {
        this.model = model;
    }

    public Entity(String registryName, TexturedModel model, int lightAmount, Color lightColor) {
        this(registryName, model);
        this.lightAmount = lightAmount;
        this.lightColor = lightColor;
    }

    public void move(float dx, float dy, float dz) {
        this.position.x += dx;
        this.position.y += dy;
        this.position.z += dz;
    }

    public void rotate(float rx, float ry, float rz) {
        this.rotation.x += rx;
        this.rotation.y += ry;
        this.rotation.z += rz;
    }

    public void setPosition(float x, float y, float z) {
        this.position = new Vector3f(x, y, z);
    }

    public void setRotation(float rx, float ry, float rz) {
        this.rotation = new Vector3f(rx, ry, rz);
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public Vector3f getPosition() {
        return position;
    }

    public Vector3f getRotation() {
        return rotation;
    }

    public float getScale() {
        return scale;
    }

    public void setLight(int lightAmount, Color lightColor) {
        this.lightAmount = lightAmount;
        this.lightColor = lightColor;
    }

    public TexturedModel getModel() {
        return model;
    }

    public boolean isLightSource() {
        return this.lightColor != null;
    }

    public int getLightAmount() {
        return lightAmount;
    }

    public Color getLightColor() {
        return lightColor;
    }
}
