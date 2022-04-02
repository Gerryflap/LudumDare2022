package nl.lipsum.gameLogic;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import nl.lipsum.*;
import nl.lipsum.controllers.CameraController;
import nl.lipsum.controllers.GenericController;
import nl.lipsum.entities.AbstractEntity;
import nl.lipsum.gameLogic.grid.Tile;
import nl.lipsum.gameLogic.grid.TileGrid;
import nl.lipsum.gameLogic.grid.WorldGen;

import java.util.ArrayList;
import java.util.List;

import static nl.lipsum.Config.HEIGHT;
import static nl.lipsum.Config.WIDTH;

public class GameController implements GenericController {
    TextureStore textureStore;
    TileGrid tileGrid;
    List<PlayerController> playerControllers;
    AbstractEntity exampleEntity;
    BaseGraph baseGraph;

    public GameController(){
        textureStore = new TextureStore();
        tileGrid = new TileGrid(WIDTH,HEIGHT);
        WorldGen.generateWorld(tileGrid);
        try {
            exampleEntity = new AbstractEntity(389, 340, textureStore.getTileTextureByName("background"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        playerControllers = new ArrayList<>();
        playerControllers.add(new PlayerController());

        baseGraph = new BaseGraph();
    }

    @Override
    public void step() {

    }

    @Override
    public void render(SpriteBatch batch, CameraController cameraController) {
        tileGrid.draw(batch);
        exampleEntity.draw(batch);
    }

    @Override
    public void dispose() {
        tileGrid.dispose();

    }
}
