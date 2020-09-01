package main.java.com.tbroski.northdalespy.client.shaders.impl;

import main.java.com.tbroski.northdalespy.client.display.Camera;
import main.java.com.tbroski.northdalespy.client.shaders.Shader;
import main.java.com.tbroski.northdalespy.util.MathHelper;
import org.lwjgl.util.vector.Matrix4f;

public class EntityShader extends Shader {

    private static final String VERTEX = "src/main/java/com/tbroski/northdalespy/client/shaders/vertexShader.txt";
    private static final String FRAGMENT = "src/main/java/com/tbroski/northdalespy/client/shaders/fragmentShader.txt";

    private int location_transformationMatrix;
    private int location_projectionMatrix;
    private int location_viewMatrix;

    public EntityShader() {
        super(VERTEX, FRAGMENT);
    }

    @Override
    public void bindAttributes() {
        super.bindAttribute(0, "position");
        super.bindAttribute(1, "uv");
    }

    @Override
    public void getUniformLocations() {
        location_transformationMatrix = super.getUniformId("transformationMatrix");
        location_projectionMatrix = super.getUniformId("projectionMatrix");
        location_viewMatrix = super.getUniformId("viewMatrix");
    }

    public void loadTransformationMatrix(Matrix4f matrix) {
        super.loadMatrix(location_transformationMatrix, matrix);
    }

    public void loadProjectionMatrix(Matrix4f matrix) {
        super.loadMatrix(location_projectionMatrix, matrix);
    }

    public void loadViewMatrix(Camera camera) {
        super.loadMatrix(location_viewMatrix, MathHelper.createViewMatrix(camera));
    }
}
