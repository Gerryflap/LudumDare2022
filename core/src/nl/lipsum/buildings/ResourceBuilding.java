package nl.lipsum.buildings;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import nl.lipsum.gameLogic.playermodel.PlayerModel;
import nl.lipsum.StaticUtils;
import nl.lipsum.controllers.CameraController;

import static nl.lipsum.Config.TILE_SIZE;

public class ResourceBuilding extends Building{
    public static final Texture tileTexture = new Texture("whiteTile.jpg");

    public ResourceBuilding(int x, int y, PlayerModel owner, int cost) {
        super(x, y, owner, cost);
    }

    public void step(){
        this.owner.addResources(1);
    }
    @Override
    public void draw(SpriteBatch batch, CameraController cameraController) {
        StaticUtils.smartDraw(batch, cameraController, tileTexture, TILE_SIZE * this.x - TILE_SIZE/2, TILE_SIZE * this.y- TILE_SIZE/2, TILE_SIZE, TILE_SIZE);
    }

    public void dispose(){

    }
}
