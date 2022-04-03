package nl.lipsum.buildings;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import nl.lipsum.LudumDare2022;
import nl.lipsum.controllers.CameraController;
import nl.lipsum.controllers.GenericController;
import nl.lipsum.gameLogic.GameController;
import nl.lipsum.gameLogic.playermodel.HumanPlayerModel;

import java.util.List;

import static nl.lipsum.Config.HEIGHT_IN_TILES;
import static nl.lipsum.Config.WIDTH_IN_TILES;

public class BuildingController implements GenericController {
    BuildingGrid buildingGrid;
    BuildingBuilder buildingBuilder;
    HumanPlayerModel humanPlayer;

    public BuildingController(CameraController cameraController, HumanPlayerModel humanPlayer){
        buildingGrid = new BuildingGrid(WIDTH_IN_TILES, HEIGHT_IN_TILES);
        buildingBuilder = new BuildingBuilder(cameraController, humanPlayer);
        this.humanPlayer = humanPlayer;
    }

    public void startBuilder(BuildingType b){
        buildingBuilder.start(b);
    }
    public void stopBuilder(){
        buildingBuilder.stop();
        LudumDare2022.humanPlayerModel.setUiBuildingSelect(null);
    }

    public void onClick(int x, int y){
        buildingBuilder.buildBuildingClick(x,y, buildingGrid, this.humanPlayer);
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

    public BuildingBuilder getBuildingBuilder() {
        return buildingBuilder;
    }

    public void removeBuilding(Building b){
        buildingGrid.removeBuilding(b);
    }

    public Building[][] getBuildings(){
        return buildingGrid.getBuildings();
    }
}
