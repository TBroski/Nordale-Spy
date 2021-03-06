package main.java.com.tbroski.northdalespy.util;

import main.java.com.tbroski.northdalespy.client.display.Camera;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

public class MathHelper {

    public static Matrix4f createTransformationMatrix(Vector3f translation, Vector3f rotation, float scale) {
        Matrix4f matrix = new Matrix4f();
        matrix.setIdentity();
        Matrix4f.translate(translation, matrix, matrix);
        Matrix4f.rotate((float) Math.toRadians(rotation.x),new Vector3f(1,0,0), matrix, matrix);
        Matrix4f.rotate((float) Math.toRadians(rotation.y),new Vector3f(0,1,0), matrix, matrix);
        Matrix4f.rotate((float) Math.toRadians(rotation.z),new Vector3f(0,0,1), matrix, matrix);
        Matrix4f.scale(new Vector3f(scale, scale, scale), matrix, matrix);
        return matrix;
    }

    public static Matrix4f createViewMatrix(Camera camera) {
        Matrix4f matrix = new Matrix4f();
        matrix.setIdentity();
        Matrix4f.rotate((float) Math.toRadians(camera.getPitch()),new Vector3f(1,0,0), matrix, matrix);
        Matrix4f.rotate((float) Math.toRadians(camera.getYaw()),new Vector3f(0,1,0), matrix, matrix);
        Matrix4f.rotate((float) Math.toRadians(camera.getRoll()),new Vector3f(0,0,1), matrix, matrix);
        Vector3f negativeCameraPos = new Vector3f(-camera.getPosition().x, -camera.getPosition().y, -camera.getPosition().z);
        Matrix4f.translate(negativeCameraPos, matrix, matrix);
        return matrix;
    }
}
