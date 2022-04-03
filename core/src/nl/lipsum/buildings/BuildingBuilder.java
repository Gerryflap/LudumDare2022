package nl.lipsum.buildings;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import nl.lipsum.Drawable;
import nl.lipsum.StaticUtils;
import nl.lipsum.LudumDare2022;
import nl.lipsum.controllers.CameraController;
import nl.lipsum.gameLogic.Base;
import nl.lipsum.gameLogic.PlayerController;
import nl.lipsum.gameLogic.playermodel.PlayerModel;

import static java.lang.Math.max;
import static java.lang.Math.min;
import static nl.lipsum.Config.*;
import static nl.lipsum.ui.UiConstants.*;

public class BuildingBuilder implements Drawable {
    private final PlayerModel playerModel;
    private boolean active;
    private BuildingType type;
    public static final Texture errorTexture = new Texture("redTile.jpg");
    public final Texture resourceTexture;
    public final Texture unitTexture;
    private CameraController camCon = LudumDare2022.cameraController;


    public BuildingBuilder(PlayerModel playerModel){
        this.active = false;
        this.resourceTexture = new Texture(String.format("player%d/resource_building.png", playerModel.getId()));
        this.unitTexture = new Texture(String.format("player%d/infantry_building.png", playerModel.getId()));
        this.playerModel = playerModel;
    }

    public void start(BuildingType type){
        this.active = true;
        this.type = type;
    }

    public void stop(){
        this.active = false;
    }

    public void buildBuildingClick(int x, int y, BuildingGrid bg){
        Gdx.graphics.getHeight();
        boolean notOnUi = y < Gdx.graphics.getHeight() - MINIMAP_HEIGHT || (x < Gdx.graphics.getWidth() - MINIMAP_WIDTH && y < Gdx.graphics.getHeight() - BAR_HEIGHT);
        if(active && notOnUi){
            int[] tileCoords = camCon.screenToTile(x, y);
            int tx = tileCoords[0];
            int ty = tileCoords[1];
            try {
                Building b = bg.getBuilding(tx, ty);
                buildBuilding(b, tx, ty);
            } catch (Exception e){
                System.out.println("Click outside of grid");
            }
        }
    }

    public boolean buildBuilding(Building b, int x, int y){
        BuildingGrid bg = LudumDare2022.buildingController.getBuildingGrid();
        boolean canbuild = false;

        for (Base base: LudumDare2022.gameController.getBaseGraph().getBases()) {
            if(base.getOwner() == playerModel && x < base.getX() + base.getBuildrange() && x > base.getX() - base.getBuildrange() && y < base.getY() + base.getBuildrange() && y > base.getY() - base.getBuildrange()){
                canbuild = true;
            }
        }
        if (b != null){
            b.click();
        } else {
            Building nb = null;
            switch (this.type) {
                case RESOURCE:
                    if(playerModel.getAmountResources() >= RESOURCE_BUILDING_COST){
                        if(canbuild){
                            playerModel.addResources(-RESOURCE_BUILDING_COST);
                            nb = new ResourceBuilding(x, y, playerModel);
                        }
                    } else {
                        canbuild = false;
                    }
                    break;
                case UNIT:
                    if(playerModel.getAmountResources() >= INFANTRY_BUILDING_COST){
                        if(canbuild){
                            playerModel.addResources(-INFANTRY_BUILDING_COST);
                            nb = new InfantryBuilding(x, y, playerModel);
                        }
                    } else {
                        canbuild = false;
                    }
                    break;
            }
            try {
                if (canbuild) {
                    bg.setBuilding(x, y, nb);
                    return true;
                }
            } catch (Exception e) {
                System.out.println("wie dit leest trekt een ad");
            }
        }
        return false;
    }

    @Override
    public void draw(SpriteBatch batch, CameraController cameraController) {
        if(active && playerModel == LudumDare2022.humanPlayerModel){
            Texture tex = null;
            boolean canbuild = false;
            int[] tileCoords = camCon.screenToTile(Gdx.input.getX(), Gdx.input.getY());
            int tx = tileCoords[0];
            int ty = tileCoords[1];
            for (Base base: LudumDare2022.gameController.getBaseGraph().getBases()) {
                if(base.getOwner() == LudumDare2022.humanPlayerModel &&
                        tx < base.getX() + base.getBuildrange() &&
                        tx > base.getX() - base.getBuildrange() &&
                        ty < base.getY() + base.getBuildrange() &&
                        ty > base.getY() - base.getBuildrange()){
                    canbuild = true;
                }
            }
            switch (this.type) {
                case RESOURCE:
                    if(LudumDare2022.humanPlayerModel.getAmountResources() >= RESOURCE_BUILDING_COST) {
                        tex = resourceTexture;
                    } else {
                        canbuild = false;
                    }
                    break;
                case UNIT:
                    if(LudumDare2022.humanPlayerModel.getAmountResources() >= INFANTRY_BUILDING_COST) {
                        tex = unitTexture;
                    } else {
                        canbuild = false;
                    }
                    break;
            }
            if(!canbuild){
                tex = errorTexture;
            }
//            int[] tileCoords = camCon.screenToTile(Gdx.input.getX(), Gdx.input.getY());
            batch.enableBlending();
            batch.setColor(1,1,1,(float)0.5);
            StaticUtils.smartDraw(batch, cameraController, tex, max(0, TILE_SIZE * tileCoords[0]) - TILE_SIZE/2, max(0,TILE_SIZE * tileCoords[1]) - TILE_SIZE/2, TILE_SIZE, TILE_SIZE);
            batch.setColor(1,1,1,1);
        }
    }


    public void dispose() {

    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setType(BuildingType type) {
        this.type = type;
    }
}
