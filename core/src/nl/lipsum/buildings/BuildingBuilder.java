package nl.lipsum.buildings;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import nl.lipsum.Drawable;
import nl.lipsum.controllers.CameraController;
import nl.lipsum.gameLogic.playermodel.PlayerModel;

import java.lang.reflect.Array;
import java.util.Arrays;

import static java.lang.Math.max;
import static java.lang.Math.min;
import static nl.lipsum.Config.TILE_SIZE;

public class BuildingBuilder implements Drawable {
    private boolean active;
    private BuildingType type;
    public static final Texture resourceTexture = new Texture("whiteTile.jpg");
    public static final Texture unitTexture = new Texture("greenTile.jpg");
    private CameraController camCon;


    public BuildingBuilder(CameraController camCon){
        this.active = false;
        this.camCon = camCon;
    }

    public void start(BuildingType type){
        this.active = true;
        this.type = type;
    }

    public void stop(){
        this.active = false;
    }

    public void placeBuilding(int x, int y, BuildingGrid bg, PlayerModel player){
        if(active){
            int[] tileCoords = camCon.screenToTile(x, y);
            int tx = tileCoords[0];
            int ty = tileCoords[1];
            Building nb = null;
            switch (this.type) {
                case RESOURCE:
                    nb = new ResourceBuilding(tx, ty, player, 10);
                    break;
                case UNIT:
                    nb = new InfantryBuilding(tx, ty, player, 10, 10, 10);
                    break;
            }
            try {
                bg.setBuilding(tx, ty, nb);
            } catch (Exception e){
                System.out.println("wie dit leest trekt een ad");
            }
        }
    }

    @Override
    public void draw(SpriteBatch batch) {
        if(active){
            Texture tex = null;
            switch (this.type) {
                case RESOURCE:
                    tex = resourceTexture;
                    break;
                case UNIT:
                    tex = unitTexture;
                    break;
            }
            int[] tileCoords = camCon.screenToTile(Gdx.input.getX(), Gdx.input.getY());
            batch.enableBlending();
            batch.setColor(1,1,1,(float)0.5);
            batch.draw(tex, max(0, TILE_SIZE * tileCoords[0]) - TILE_SIZE/2, max(0,TILE_SIZE * tileCoords[1]) - TILE_SIZE/2, TILE_SIZE, TILE_SIZE);
            batch.setColor(1,1,1,1);
        }
    }


    public void dispose() {

    }


}
