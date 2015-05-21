package com.ernestas;

import com.ernestas.entities.Camera;
import com.ernestas.entities.Entity;
import com.ernestas.entities.Light;
import com.ernestas.entities.Player;
import com.ernestas.guis.GuiTexture;
import com.ernestas.renderEngine.GuiRenderer;
import com.ernestas.models.TexturedModel;
import com.ernestas.objConverter.OBJFileLoader;
import com.ernestas.renderEngine.*;
import com.ernestas.terrains.Terrain;
import com.ernestas.textures.ModelTexture;
import com.ernestas.textures.TerrainTexture;
import com.ernestas.textures.TerrainTexturePack;
import com.ernestas.toolbox.MousePicker;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GraphicsMain {
    /**
     * Compile with maven:
     * clean compile assembly:single
     *
     **/

    public static void main(String args[]) {

        DisplayManager.createDisplay();

        Loader loader = new Loader();
        MasterRenderer renderer = new MasterRenderer(loader);


        //*******************TERRAIN TEXTURE STUFF******************

        TerrainTexture backgroundTexture = new TerrainTexture(loader.loadTexture("grassy"));
        TerrainTexture rTexture = new TerrainTexture(loader.loadTexture("dirt"));
        TerrainTexture gTexture = new TerrainTexture(loader.loadTexture("pinkFlowers"));
        TerrainTexture bTexture = new TerrainTexture(loader.loadTexture("path"));

        TerrainTexturePack texturePack = new TerrainTexturePack(backgroundTexture, rTexture, gTexture, bTexture);
        TerrainTexture blendMap = new TerrainTexture(loader.loadTexture("blendMap"));

        List<Terrain> terrains = new ArrayList<>();
        Terrain terrain = new Terrain(0, -1, loader, texturePack, blendMap, "heightMap");
        terrains.add(new Terrain(-1, -1, loader, texturePack, blendMap, "heightMap"));
        terrains.add(new Terrain(-1, 0, loader, texturePack, blendMap, "heightMap"));
        terrains.add(new Terrain(0, 0, loader, texturePack, blendMap, "heightMap"));
        terrains.add(terrain);
        //**********************************************************


        //*******************MODEL STUFF******************
        TexturedModel staticModel = new TexturedModel(loader.loadToVAO(OBJFileLoader.loadOBJ("tree")),
                new ModelTexture(loader.loadTexture("tree")));

        TexturedModel grass = new TexturedModel(loader.loadToVAO(OBJFileLoader.loadOBJ("grassModel")),
                new ModelTexture(loader.loadTexture("grassTexture")));

        TexturedModel flower = new TexturedModel(loader.loadToVAO(OBJFileLoader.loadOBJ("grassModel")),
                new ModelTexture(loader.loadTexture("flower")));

        TexturedModel fern = new TexturedModel(loader.loadToVAO(OBJFileLoader.loadOBJ("fern")),
                new ModelTexture(loader.loadTexture("fern"), 2));

        TexturedModel bobble = new TexturedModel(loader.loadToVAO(OBJFileLoader.loadOBJ("lowPolyTree")),
            new ModelTexture(loader.loadTexture("lowPolyTree")));

        TexturedModel lamp = new TexturedModel(loader.loadToVAO(OBJFileLoader.loadOBJ("lamp")),
            new ModelTexture(loader.loadTexture("lamp")));

        grass.getTexture().setHasTransparency(true);
        grass.getTexture().setUseFakeLighting(true);
        flower.getTexture().setHasTransparency(true);
        flower.getTexture().setUseFakeLighting(true);
        lamp.getTexture().setUseFakeLighting(true);
        fern.getTexture().setHasTransparency(true);


        List<Entity> entities = new ArrayList<>();
        Random random = new Random(676452);
        for (int i = 0; i < 400; i++) {
            if (i % 10 == 0) {
                float x = random.nextFloat() * 800 - 400;
                float z = random.nextFloat() * -600;
                float y = Terrain.getCurrentTerrain(terrains, x, z).getHeightOfTerrain(x, z);
                entities.add(new Entity(fern, random.nextInt(4), new Vector3f(x, y, z), 0, random.nextFloat() * 360, 0, 0.9f));
            }
            if (i % 5 == 0) {
                float x = random.nextFloat() * 800 - 400;
                float z = random.nextFloat() * -600;
                float y = Terrain.getCurrentTerrain(terrains, x, z).getHeightOfTerrain(x, z);
                entities.add(new Entity(bobble, new Vector3f(x, y, z), 0, random.nextFloat() * 360,
                    0, random.nextFloat() * 0.1f + 0.6f));

                x = random.nextFloat() * 800 - 400;
                z = random.nextFloat() * -600;
                y = Terrain.getCurrentTerrain(terrains, x, z).getHeightOfTerrain(x, z);
                entities.add(new Entity(staticModel, new Vector3f(x, y, z), 0, 0, 0, random.nextFloat() * 1 + 4));
            }
        }
        //************************************************

        //**************** PLAYER ************************
        TexturedModel playerTexture = new TexturedModel(loader.loadToVAO(OBJFileLoader.loadOBJ("person")),
                new ModelTexture(loader.loadTexture("playerTexture")));
        Player player = new Player(playerTexture, new Vector3f(153, 5, -274), 0, 100, 0, 0.6f);
        //************************************************


        //**************** GUIS ************************
        List<GuiTexture> guis = new ArrayList<>();
        GuiTexture gui = new GuiTexture(loader.loadTexture("health"),
            new Vector2f(-0.76f, 0.92f), new Vector2f(0.2f, 0.25f));

        guis.add(gui);

        GuiRenderer guiRenderer = new GuiRenderer(loader);
        //**********************************************


        //**************** LIGHTS ************************
        List<Light> lights = new ArrayList<>();
        lights.add(new Light(new Vector3f(0, 1000, -7000), new Vector3f(0.4f, 0.4f, 0.4f)));

        lights.add(new Light(new Vector3f(185, 10, -293), new Vector3f(2, 0, 0), new Vector3f(1, 0.01f, 0.002f)));
        lights.add(new Light(new Vector3f(370, 17, -300), new Vector3f(0, 2, 2), new Vector3f(1, 0.01f, 0.002f)));
//        lights.add(new Light(new Vector3f(293, 7, -305), new Vector3f(2, 2, 0), new Vector3f(1, 0.01f, 0.002f)));

        entities.add(new Entity(lamp, new Vector3f(185, -4.7f, -293), 0, 0, 0, 1));
        entities.add(new Entity(lamp, new Vector3f(370, 4.2f, -300), 0, 0, 0, 1));
        entities.add(new Entity(lamp, new Vector3f(293, -6.8f, -305), 0, 0, 0, 1));

        Entity lampEntity = new Entity(lamp, new Vector3f(215, -4.7f, -293), 0, 0, 0, 1);
        Light light = new Light(new Vector3f(215, 10, -293), new Vector3f(0, 2, 2), new Vector3f(1, 0.01f, 0.002f));

        entities.add(lampEntity);
        lights.add(light);
        //************************************************

        Camera camera = new Camera(player);
        MousePicker picker = new MousePicker(camera, renderer.getProjectionMatrix(), terrains);

        while (!Display.isCloseRequested()) {
            camera.move();
            player.move(Terrain.getCurrentTerrain(terrains, player.getPosition().x, player.getPosition().z));
            picker.update();
            Vector3f terrainPoint = picker.getCurrentTerrainPoint();
            if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) && terrainPoint != null) {
                lampEntity.setPosition(terrainPoint);
                light.setPosition(new Vector3f(terrainPoint.x, terrainPoint.y + 15, terrainPoint.z));
            }

            renderer.processEntity(player);

            for (Terrain terrainObj : terrains) {
                renderer.processTerrain(terrainObj);
            }

            // render all entities here
            for (Entity entity: entities) {
                renderer.processEntity(entity);
            }

            renderer.render(lights, camera);

            guiRenderer.render(guis);

            DisplayManager.updateDisplay();

        }

        guiRenderer.cleanUp();
        renderer.cleanUp();
        loader.cleanUp();
        DisplayManager.closeDisplay();
    }

}
