package main.java.com.tbroski.northdalespy.client.renderer;

import main.java.com.tbroski.northdalespy.client.model.TexturedModel;
import main.java.com.tbroski.northdalespy.client.shaders.impl.EntityShader;
import main.java.com.tbroski.northdalespy.entity.Entity;
import main.java.com.tbroski.northdalespy.util.MathHelper;
import main.java.com.tbroski.northdalespy.world.World;
import org.lwjgl.opengl.*;
import org.lwjgl.util.vector.Matrix4f;

public class RenderHandler {

    private static final float FOV = 70;
    private static final float NEAR_PLANE = 0.1F;
    private static final float FAR_PLANE = 100;
    private Matrix4f projectionMatrix;

    public RenderHandler(EntityShader shader) {
        createProjectionMatrix(FOV, NEAR_PLANE, FAR_PLANE);
        shader.start();
        shader.loadProjectionMatrix(projectionMatrix);
        shader.stop();
    }

    public void prepare(World world) {
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT|GL11.GL_DEPTH_BUFFER_BIT);
        GL11.glClearColor(world.getSkyColor().getRed(), world.getSkyColor().getGreen(), world.getSkyColor().getBlue(), world.getSkyColor().getAlpha());
    }

    public void renderWorld(World world, EntityShader shader) {
        for (Entity entity : world.getEntities()) {
            TexturedModel model = entity.getModel();
            GL30.glBindVertexArray(model.getModel().getVaoID());
            GL20.glEnableVertexAttribArray(0);
            GL20.glEnableVertexAttribArray(1);
            Matrix4f transMatix = MathHelper.createTransformationMatrix(entity.getPosition(), entity.getRotation(), entity.getScale());
            shader.loadTransformationMatrix(transMatix);
            GL13.glActiveTexture(GL13.GL_TEXTURE0);
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, model.getTexture().getTextureId());
            GL11.glDrawElements(GL11.GL_TRIANGLES, model.getModel().getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
            GL20.glDisableVertexAttribArray(0);
            GL20.glDisableVertexAttribArray(1);
            GL30.glBindVertexArray(0);
        }
    }

    private void createProjectionMatrix(float fov, float near_plane, float far_plane) {
        float aspectRatio = (float) Display.getWidth() / (float) Display.getHeight();
        float y_scale = (float) ((1F / Math.tan(Math.toRadians(fov / 2F))) * aspectRatio);
        float x_scale = y_scale / aspectRatio;
        float frustrum_length = far_plane - near_plane;

        projectionMatrix = new Matrix4f();
        projectionMatrix.m00 = x_scale;
        projectionMatrix.m11 = y_scale;
        projectionMatrix.m22 = -((far_plane + near_plane) / frustrum_length);
        projectionMatrix.m23 = -1;
        projectionMatrix.m32 = -((2 * near_plane * far_plane) / frustrum_length);
        projectionMatrix.m33 = 0;
    }
}
