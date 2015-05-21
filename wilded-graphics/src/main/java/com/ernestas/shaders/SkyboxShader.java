package com.ernestas.shaders;

import com.ernestas.entities.Camera;
import com.ernestas.renderEngine.DisplayManager;
import com.ernestas.toolbox.Maths;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

public class SkyboxShader extends ShaderProgram {

    private static final String VERTEX_FILE = "shaders/skyboxVertexShader.txt";
    private static final String FRAGMENT_FILE = "shaders/skyboxFragmentShader.txt";

    private static final float ROTATE_SPEED = 1;

    private int locationProjectionMatrix;
    private int locationViewMatrix;
    private int locationFogColor;
    private int locationCubeMap;
    private int locationCubeMap2;
    private int locationBlendFactor;

    private float rotation = 0;

    public SkyboxShader() {
        super(VERTEX_FILE, FRAGMENT_FILE);
    }

    public void loadProjectionMatrix(Matrix4f matrix){
        super.loadMatrix(locationProjectionMatrix, matrix);
    }

    public void loadViewMatrix(Camera camera){
        Matrix4f matrix = Maths.createViewMatrix(camera);
        matrix.m30 = 0; //Make skybox not move with the world
        matrix.m31 = 0; //camera moves in the completely opposite direction of the world
        matrix.m32 = 0;
        rotation += ROTATE_SPEED * DisplayManager.getFrameTimeSeconds();
        // We should rotate the transformation matrix, but since we are not using the needed columns of the view matrix
        //  we might as well do the transformation directly on the view matrix
        Matrix4f.rotate((float) Math.toRadians(rotation), new Vector3f(0, 1, 0), matrix, matrix);
        super.loadMatrix(locationViewMatrix, matrix);
    }

    @Override
    protected void getAllUniformLocations() {
        locationProjectionMatrix = super.getUniformLocation("projectionMatrix");
        locationViewMatrix = super.getUniformLocation("viewMatrix");
        locationFogColor = super.getUniformLocation("fogColor");
        locationCubeMap = super.getUniformLocation("cubeMap");
        locationCubeMap2 = super.getUniformLocation("cubeMap2");
        locationBlendFactor = super.getUniformLocation("blendFactor");
    }

    public void connectTextureUnits() {
        super.loadInt(locationCubeMap, 0);
        super.loadInt(locationCubeMap2, 1);
    }

    public void loadBlendFactor(float blendFactor) {
        super.loadFloat(locationBlendFactor, blendFactor);
    }

    public void loadFogColor(Vector3f fogColor) {
        super.loadVector(locationFogColor, fogColor);
    }

    @Override
    protected void bindAttributes() {
        super.bindAttribute(0, "position");
    }

}
