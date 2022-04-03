package nl.lipsum.buildings;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import nl.lipsum.gameLogic.playermodel.PlayerModel;
import nl.lipsum.StaticUtils;
import nl.lipsum.controllers.CameraController;

import static nl.lipsum.Config.RESOURCES_PER_BUILDING_PER_SECOND;
import static nl.lipsum.Config.TILE_SIZE;

public class ResourceBuilding extends Building{
    public final Texture tileTexture;

    public ResourceBuilding(int x, int y, PlayerModel owner) {
        super(x, y, owner);
        this.tileTexture = new Texture(String.format("player%d/resource_building.png", owner.getId()));
    }

    public void step(){
        this.owner.addResources(RESOURCES_PER_BUILDING_PER_SECOND * Gdx.graphics.getDeltaTime());
    }
    @Override
    public void draw(SpriteBatch batch, CameraController cameraController) {
        StaticUtils.smartDraw(batch, cameraController, tileTexture, TILE_SIZE * this.x - TILE_SIZE/2, TILE_SIZE * this.y- TILE_SIZE/2, TILE_SIZE, TILE_SIZE);
    }

    public void dispose(){

    }

    public void click(){}
}
