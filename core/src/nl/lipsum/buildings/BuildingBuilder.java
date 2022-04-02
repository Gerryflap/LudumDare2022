package nl.lipsum.buildings;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import nl.lipsum.Drawable;
import nl.lipsum.LudumDare2022;
import nl.lipsum.controllers.CameraController;
import nl.lipsum.gameLogic.Base;
import nl.lipsum.gameLogic.playermodel.PlayerModel;

import static java.lang.Math.max;
import static java.lang.Math.min;
import static nl.lipsum.Config.TILE_SIZE;
import static nl.lipsum.ui.UiConstants.*;

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

    public void buildBuildingClick(int x, int y, BuildingGrid bg, PlayerModel player){
        Gdx.graphics.getHeight();
        boolean notOnUi = y < Gdx.graphics.getHeight() - MINIMAP_HEIGHT || (x < Gdx.graphics.getWidth() - MINIMAP_WIDTH && y < Gdx.graphics.getHeight() - BAR_HEIGHT);
        if(active && notOnUi){
            int[] tileCoords = camCon.screenToTile(x, y);
            int tx = tileCoords[0];
            int ty = tileCoords[1];
            buildBuilding(tx, ty, bg, player);
        }
    }

    public void buildBuilding(int x, int y, BuildingGrid bg, PlayerModel player){
        boolean canbuild = false;

        for (Base base: LudumDare2022.gameController.getBaseGraph().getBases()) {
            if(base.getOwner() == player && x < base.getX() + base.getBuildrange() && x > base.getX() - base.getBuildrange() && y < base.getY() + base.getBuildrange() && y > base.getY() - base.getBuildrange()){
                canbuild = true;
            }
        }
        Building nb = null;
        switch (this.type) {
            case RESOURCE:
                nb = new ResourceBuilding(x, y, player, 10);
                break;
            case UNIT:
                nb = new InfantryBuilding(x, y, player, 10, 10, 10);
                break;
        }
        try {
            if (canbuild){
                bg.setBuilding(x, y, nb);
            }
        } catch (Exception e){
            System.out.println("wie dit leest trekt een ad");
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
