package nl.lipsum.buildings;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import nl.lipsum.controllers.CameraController;
import nl.lipsum.controllers.GenericController;

import static nl.lipsum.Config.HEIGHT_IN_TILES;
import static nl.lipsum.Config.WIDTH_IN_TILES;

public class BuildingController implements GenericController {
    BuildingGrid buildingGrid;
    BuildingBuilder buildingBuilder;

    public BuildingController(CameraController cameraController){
        buildingGrid = new BuildingGrid(WIDTH_IN_TILES, HEIGHT_IN_TILES);
        buildingBuilder = new BuildingBuilder(cameraController);
        buildingBuilder.start("resource");
    }

    public void onClick(int x, int y){
        buildingBuilder.placeBuilding(x,y, buildingGrid);
    }

    @Override
    public void step() {

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
