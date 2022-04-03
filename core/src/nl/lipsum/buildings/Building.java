package nl.lipsum.buildings;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import nl.lipsum.Config;
import nl.lipsum.Drawable;
import nl.lipsum.LudumDare2022;
import nl.lipsum.controllers.CameraController;
import nl.lipsum.entities.EntitySoundType;
import nl.lipsum.entities.EntityStatus;
import nl.lipsum.entities.Targetable;
import nl.lipsum.gameLogic.Ownable;
import nl.lipsum.gameLogic.playermodel.PlayerModel;

import java.util.Optional;

import static nl.lipsum.Config.BUILDING_HEALTH;
import static nl.lipsum.Config.TILE_SIZE;

public abstract class Building implements Drawable, Ownable, Targetable {
    public final int x;
    public final int y;
    public final PlayerModel owner;
    private float health;
    private boolean dead;

    public Building(int x, int y, PlayerModel owner) {
        this.x = x;
        this.y = y;
        this.owner = owner;
        this.dead = false;
        this.health = BUILDING_HEALTH;
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
    public void damage(float damageAmount) {
        health -= damageAmount;

        if (health <= 0) {
            kill();
        }
    }

    public void kill() {
        if (!this.dead) {
            //possibly emit sound when building is destroyed
//            emitSound(EntitySoundType.DEATH);
        }
        this.dead = true;
//        Optional.ofNullable(unitBuilding).ifPresent(building -> building.reportKilled(this));
//        Optional.ofNullable(army).ifPresent(army -> army.removeEntity(this));
        LudumDare2022.buildingController.removeBuilding(this);
    }

    public boolean isDead(){
        return dead;
    }
}
