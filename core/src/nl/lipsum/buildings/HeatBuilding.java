package nl.lipsum.buildings;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import nl.lipsum.StaticUtils;
import nl.lipsum.controllers.CameraController;
import nl.lipsum.gameLogic.playermodel.PlayerModel;

import static nl.lipsum.Config.TILE_SIZE;

public class HeatBuilding extends Building {

    public final Texture tileTexture;

    public HeatBuilding(int x, int y, PlayerModel owner) {
        super(x, y, owner);
        this.tileTexture = new Texture(String.format("player%d/resource_building.png", owner.getId()));
        getOwner().heatBuildingsAmount += 1;
    }

    @Override
    public void step() {

    }

    @Override
    public void draw(SpriteBatch batch, CameraController cameraController) {
        if(!isDead()){
            StaticUtils.smartDraw(batch, cameraController, tileTexture, TILE_SIZE * this.x - TILE_SIZE/2, TILE_SIZE * this.y- TILE_SIZE/2, TILE_SIZE, TILE_SIZE);
        }
    }

    @Override
    public void kill() {
        super.kill();
        getOwner().heatBuildingsAmount -= 1;
    }

    @Override
    public void dispose() {

    }

    @Override
    public void click() {

    }
}
