package nl.lipsum.buildings;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import nl.lipsum.Drawable;
import nl.lipsum.StaticUtils;
import nl.lipsum.TextureStore;
import nl.lipsum.controllers.CameraController;
import nl.lipsum.gameLogic.playermodel.PlayerModel;

import static nl.lipsum.Config.TILE_SIZE;

public abstract class Building implements Drawable {
    public final int x;
    public final int y;
    public final PlayerModel owner;
    public final int cost;

    public Building(int x, int y, PlayerModel owner, int cost) {
        this.x = x;
        this.y = y;
        this.owner = owner;
        this.cost = cost;
    }


    public abstract void step();

    @Override
    public abstract void draw(SpriteBatch batch, CameraController cameraController);
//        batch.draw(this.tileTexture, TILE_SIZE * this.x - TILE_SIZE/2, TILE_SIZE * this.y- TILE_SIZE/2);

    public abstract void dispose();
}
