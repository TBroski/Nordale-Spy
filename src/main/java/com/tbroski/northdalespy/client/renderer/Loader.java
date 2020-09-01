package main.java.com.tbroski.northdalespy.client.renderer;

import main.java.com.tbroski.northdalespy.client.model.Model;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

public class Loader {

    private List<Integer> vaos = new ArrayList<>();
    private List<Integer> vbos = new ArrayList<>();
    private List<Integer> textures = new ArrayList<>();

    public Model loadToVAO(float[] positions, int[] indices, float[] uv) {
        int vaoID = createVAO();
        bindIndicesBuffer(indices);
        storeDataInAttributeList(0, 3, positions);
        storeDataInAttributeList(1, 2, uv);
        unbindVAO();
        return new Model(vaoID, indices.length);
    }

    public int loadTexture(String fileName) throws IOException {
        Texture texture = TextureLoader.getTexture("PNG", new FileInputStream("src/main/resources/assets/textures/" + fileName + ".png"));
        int id = texture.getTextureID();
        this.textures.add(id);
        return id;
    }

    public void clean() {
        for (int vao : this.vaos) {
            GL30.glDeleteVertexArrays(vao);
        }
        for (int vbo : this.vbos) {
            GL15.glDeleteBuffers(vbo);
        }
        for (int tex : this.textures) {
            GL11.glDeleteTextures(tex);
        }
    }

    private int createVAO() {
        int vaoID = GL30.glGenVertexArrays();
        this.vaos.add(vaoID);
        GL30.glBindVertexArray(vaoID);
        return vaoID;
    }

    private void storeDataInAttributeList(int attributeNumber, int coordSize, float[] data) {
        int vboID = GL15.glGenBuffers();
        this.vbos.add(vboID);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboID);
        FloatBuffer buffer = storeDataInFloatBuffer(data);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
        GL20.glVertexAttribPointer(attributeNumber, coordSize, GL11.GL_FLOAT, false, 0, 0);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
    }

    private void bindIndicesBuffer(int[] indices) {
        int vboId = GL15.glGenBuffers();
        this.vbos.add(vboId);
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vboId);
        IntBuffer buffer = storeDataInIntBuffer(indices);
        GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
    }

    private void unbindVAO() {
        GL30.glBindVertexArray(0);
    }

    private FloatBuffer storeDataInFloatBuffer(float[] data) {
        FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
        buffer.put(data);
        buffer.flip();
        return buffer;
    }

    private IntBuffer storeDataInIntBuffer(int[] data) {
        IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
        buffer.put(data);
        buffer.flip();
        return buffer;
    }
}
