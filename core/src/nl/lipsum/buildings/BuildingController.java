package nl.lipsum.buildings;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import nl.lipsum.controllers.CameraController;
import nl.lipsum.controllers.GenericController;
import nl.lipsum.gameLogic.playermodel.HumanPlayerModel;

import static nl.lipsum.Config.HEIGHT_IN_TILES;
import static nl.lipsum.Config.WIDTH_IN_TILES;

public class BuildingController implements GenericController {
    BuildingGrid buildingGrid;
    BuildingBuilder buildingBuilder;
    HumanPlayerModel humanPlayer;

    public BuildingController(CameraController cameraController, HumanPlayerModel humanPlayer){
        buildingGrid = new BuildingGrid(WIDTH_IN_TILES, HEIGHT_IN_TILES);
        buildingBuilder = new BuildingBuilder(cameraController);
        this.humanPlayer = humanPlayer;
        buildingBuilder.start("resource");
    }

    public void onClick(int x, int y){
        buildingBuilder.buildBuilding(x,y, buildingGrid, this.humanPlayer);
    }

    @Override
    public void step() {
        buildingGrid.step();
    }

    @Override
    public void render(SpriteBatch batch, CameraController cameraController) {
        buildingGrid.draw(batch);
        buildingBuilder.draw(batch);
    }

    @Override
    public void dispose() {
        buildingGrid.dispose();
    }
}
