package main.java.com.tbroski.northdalespy.world;

import main.java.com.tbroski.northdalespy.client.shaders.impl.EntityShader;
import main.java.com.tbroski.northdalespy.entity.Entity;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class World {

    private List<Entity> entities = new ArrayList<>();
    private List<Object> eventListeners = new ArrayList<>();

    private Color skyColor;

    public World(Color skyColor) {
        this.skyColor = skyColor;
    }
    public void addEntity(Entity entity) {
        this.entities.add(entity);
    }

    public void addEventListener(Object object) {
        this.eventListeners.add(object);
    }

    public void setSkyColor(Color color) {
        this.skyColor = color;
    }

    public Color getSkyColor() {
        return skyColor;
    }

    public List<Entity> getEntities() {
        return entities;
    }
}
