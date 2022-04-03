package nl.lipsum.buildings;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import nl.lipsum.Drawable;
import nl.lipsum.controllers.CameraController;
import nl.lipsum.gameLogic.Ownable;
import nl.lipsum.gameLogic.playermodel.PlayerModel;

public abstract class Building implements Drawable, Ownable {
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

    public abstract void click();

    public PlayerModel getOwner() {
        return owner;
    }
}
