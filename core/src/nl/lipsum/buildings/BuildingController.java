package nl.lipsum.buildings;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import nl.lipsum.LudumDare2022;
import nl.lipsum.controllers.CameraController;
import nl.lipsum.controllers.GenericController;
import nl.lipsum.gameLogic.GameController;
import nl.lipsum.gameLogic.playermodel.HumanPlayerModel;
import nl.lipsum.gameLogic.playermodel.PlayerModel;

import static nl.lipsum.Config.HEIGHT_IN_TILES;
import static nl.lipsum.Config.WIDTH_IN_TILES;

public class BuildingController implements GenericController {
    BuildingGrid buildingGrid;
    BuildingBuilder[] buildingBuilders;
    HumanPlayerModel humanPlayer;
    GameController gameController;
    BuildingBuilder buildingBuilder;


    public BuildingController(CameraController cameraController, HumanPlayerModel humanPlayer){
        buildingGrid = new BuildingGrid(WIDTH_IN_TILES, HEIGHT_IN_TILES);
        buildingBuilders = new BuildingBuilder[4];
        buildingBuilders[0] = new BuildingBuilder(LudumDare2022.humanPlayerModel);
        this.humanPlayer = humanPlayer;
        // Voor support van alle legacy shit
        buildingBuilder = buildingBuilders[0];
    }

    public void startBuilder(BuildingType b){
        buildingBuilder.start(b);
    }
    public void stopBuilder(){
        buildingBuilder.stop();
    }

    public void onClick(int x, int y){
        buildingBuilder.buildBuildingClick(x,y, buildingGrid);
    }

    @Override
    public void step() {
        buildingGrid.step();
    }

    @Override
    public void render(SpriteBatch batch, CameraController cameraController) {
        buildingGrid.draw(batch, cameraController);
        buildingBuilder.draw(batch, cameraController);
    }

    public BuildingBuilder getBuildingBuilder(PlayerModel player) {
        if (buildingBuilders[player.getId()] == null) {
            buildingBuilders[player.getId()] = new BuildingBuilder(player);
        }
        return buildingBuilders[player.getId()];
    }

    @Override
    public void dispose() {
        buildingGrid.dispose();
    }

    public void setActive(boolean isActive){
        buildingBuilder.setActive(isActive);
    }

    public void setBuildingType(BuildingType buildingType){
        buildingBuilder.setType(buildingType);
    }

    public BuildingBuilder getHumanBuildingBuilder() {
        return buildingBuilder;
    }

    public BuildingGrid getBuildingGrid() {
        return buildingGrid;
    }
}
