package main.java.com.tbroski.northdalespy.client.shaders;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.FloatBuffer;

public abstract class Shader {

    private static FloatBuffer matrixBuffer = BufferUtils.createFloatBuffer(16);

    private int programId;
    private int vertexShaderId;
    private int fragmentShaderId;

    public abstract void bindAttributes();
    public abstract void getUniformLocations();

    public Shader(String vertexFile, String fragmentFile) {
        vertexShaderId = loadShader(vertexFile, GL20.GL_VERTEX_SHADER);
        fragmentShaderId = loadShader(fragmentFile, GL20.GL_FRAGMENT_SHADER);
        programId = GL20.glCreateProgram();
        GL20.glAttachShader(programId, vertexShaderId);
        GL20.glAttachShader(programId, fragmentShaderId);
        bindAttributes();
        GL20.glLinkProgram(programId);
        GL20.glValidateProgram(programId);
        getUniformLocations();
    }

    public void start() {
        GL20.glUseProgram(this.programId);
    }

    public void stop() {
        GL20.glUseProgram(0);
    }

    public void clean() {
        stop();
        GL20.glDetachShader(this.programId, this.vertexShaderId);
        GL20.glDetachShader(this.programId, this.fragmentShaderId);
        GL20.glDeleteShader(this.vertexShaderId);
        GL20.glDeleteShader(this.fragmentShaderId);
        GL20.glDeleteProgram(this.programId);
    }

    public int getUniformId(String variableName) {
        return GL20.glGetUniformLocation(this.programId, variableName);
    }

    public void bindAttribute(int attribute, String variableName) {
        GL20.glBindAttribLocation(this.programId, attribute, variableName);
    }

    public void loadFloat(int location, float value) {
        GL20.glUniform1f(location, value);
    }

    public void loadVector(int location, Vector3f vector) {
        GL20.glUniform3f(location, vector.x, vector.y, vector.z);
    }

    public void loadBoolean(int location, boolean value) {
        GL20.glUniform1f(location, value ? 1 : 0);
    }

    public void loadMatrix(int location, Matrix4f matrix) {
        matrix.store(matrixBuffer);
        matrixBuffer.flip();
        GL20.glUniformMatrix4(location, false, matrixBuffer);
    }
    private static int loadShader(String file, int type) {
        StringBuilder shaderSource = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while((line = reader.readLine())!=null){
                shaderSource.append(line).append("//\n");
            }
            reader.close();
        } catch(IOException e){
            e.printStackTrace();
            System.exit(-1);
        }
        int shaderID = GL20.glCreateShader(type);
        GL20.glShaderSource(shaderID, shaderSource);
        GL20.glCompileShader(shaderID);
        if(GL20.glGetShaderi(shaderID, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE) {
            System.out.println(GL20.glGetShaderInfoLog(shaderID, 500));
            System.err.println("Could not compile shader!");
            System.exit(-1);
        }
        return shaderID;
    }
}
