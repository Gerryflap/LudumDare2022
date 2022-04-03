package nl.lipsum.buildings;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import nl.lipsum.Config;
import nl.lipsum.Drawable;
import nl.lipsum.controllers.CameraController;
import nl.lipsum.entities.Targetable;
import nl.lipsum.gameLogic.Ownable;
import nl.lipsum.gameLogic.playermodel.PlayerModel;

import static nl.lipsum.Config.TILE_SIZE;

public abstract class Building implements Drawable, Ownable, Targetable {
    public final int x;
    public final int y;
    public final PlayerModel owner;

    public Building(int x, int y, PlayerModel owner) {
        this.x = x;
        this.y = y;
        this.owner = owner;
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

    public float getxPosition(){
        return x*TILE_SIZE;
    };
    public float getyPosition(){
        return y*TILE_SIZE;
    };
    public boolean isDead(){
        return false;
    };
}
