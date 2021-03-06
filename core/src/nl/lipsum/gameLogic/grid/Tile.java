package nl.lipsum.gameLogic.grid;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import nl.lipsum.StaticUtils;
import nl.lipsum.controllers.CameraController;

import static nl.lipsum.Config.TILE_SIZE;

public enum Tile {
    GRASS_1(new Texture("grass_1.png")),
    GRASS_2(new Texture("grass_2.png")),
    GRASS_3(new Texture("grass_3.png")),
    DIRT(new Texture("dirt_1.png")),
    SAND(new Texture("sand_1.png")),
    SNOW_1(new Texture("snow_1.png")),
    ICE_1(new Texture("ice_1.png"));

    private final Texture tileTexture;

    Tile(Texture texture) {
        this.tileTexture = texture;
    }


    public void draw(SpriteBatch batch, CameraController cameraController, int gx, int gy) {
        StaticUtils.smartDraw(batch, cameraController, this.tileTexture, TILE_SIZE * gx - TILE_SIZE/2, TILE_SIZE * gy- TILE_SIZE/2, TILE_SIZE, TILE_SIZE);
    }

    public void dispose() {
        // literally the only place where you should NOT dispose
    }
}
