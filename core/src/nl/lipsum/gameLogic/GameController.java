package nl.lipsum.gameLogic;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import nl.lipsum.*;
import nl.lipsum.entities.AbstractEntity;

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
        try {
            exampleEntity = new AbstractEntity(389, 340, textureStore.getTileTextureByName("background"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        tileGrid.setTile(0, 0, new Tile(0, 0, "orange", textureStore));
        tileGrid.setTile(20, 0, new Tile(20, 0, "orange", textureStore));
        tileGrid.setTile(0, 20, new Tile(0, 20, "orange", textureStore));
        tileGrid.setTile(20, 20, new Tile(20, 20, "orange", textureStore));
        tileGrid.setTile(10, 10, new Tile(10, 10, "white", textureStore));
        tileGrid.setTile(0, 10, new Tile(0, 10, "white", textureStore));
        tileGrid.setTile(10, 0, new Tile(10, 0, "white", textureStore));
        tileGrid.setTile(10, 20, new Tile(10, 20, "white", textureStore));
        tileGrid.setTile(20, 10, new Tile(20, 10, "white", textureStore));

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
