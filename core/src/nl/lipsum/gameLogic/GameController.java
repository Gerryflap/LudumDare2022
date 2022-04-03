package nl.lipsum.gameLogic;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import nl.lipsum.*;
import nl.lipsum.buildings.BuildingBuilder;
import nl.lipsum.buildings.BuildingGrid;
import nl.lipsum.controllers.CameraController;
import nl.lipsum.controllers.GenericController;
import nl.lipsum.gameLogic.grid.TileGrid;
import nl.lipsum.gameLogic.grid.WorldGen;
import nl.lipsum.gameLogic.playermodel.HumanPlayerModel;
import nl.lipsum.gameLogic.playermodel.PlayerStatus;
import nl.lipsum.ui.UiArmySelect;

import java.util.Random;

import static nl.lipsum.Config.HEIGHT_IN_TILES;
import static nl.lipsum.Config.WIDTH_IN_TILES;

public class GameController implements GenericController {
    TextureStore textureStore;
    TileGrid tileGrid;
    public static PlayerController playerController;
//    AbstractEntity exampleEntity;
    BaseGraph baseGraph;

    private int currentTemperatureUpdateCount;
    private static final int globalTemperatureUpdateTime = 250;
    public static float globalTemperature;

    private Random random = new Random();

    public GameController(HumanPlayerModel humanPlayerModel){
        textureStore = new TextureStore();
        tileGrid = new TileGrid(WIDTH_IN_TILES, HEIGHT_IN_TILES);
        WorldGen.generateWorld(tileGrid);

        globalTemperature = 5;

//        try {
//            exampleEntity = new AbstractEntity(389, 340, textureStore.getTileTextureByName("background"), );
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        tileGrid.setTile(0, 0, new Tile(0, 0, "orange", textureStore));
//        tileGrid.setTile(20, 0, new Tile(20, 0, "orange", textureStore));
//        tileGrid.setTile(0, 20, new Tile(0, 20, "orange", textureStore));
//        tileGrid.setTile(20, 20, new Tile(20, 20, "orange", textureStore));
//        tileGrid.setTile(10, 10, new Tile(10, 10, "white", textureStore));
//        tileGrid.setTile(0, 10, new Tile(0, 10, "white", textureStore));
//        tileGrid.setTile(10, 0, new Tile(10, 0, "white", textureStore));
//        tileGrid.setTile(10, 20, new Tile(10, 20, "white", textureStore));
//        tileGrid.setTile(20, 10, new Tile(20, 10, "white", textureStore));

        baseGraph = new BaseGraph();
        try{
            playerController = new PlayerController(baseGraph, humanPlayerModel);
        } catch (Exception e){
            System.err.println(e.getMessage());
            e.printStackTrace();
            System.out.println("tried to use textures that don't exist");
        }

    }

    public void updateGlobalTemperature() {
        currentTemperatureUpdateCount += 1;
        if (currentTemperatureUpdateCount >= globalTemperatureUpdateTime) {
            currentTemperatureUpdateCount = 0;
            if (random.nextFloat() > 0.6) {
                globalTemperature += 0.1;
            } else {
                globalTemperature -= 0.1;
            }
        }
    }

    @Override
    public void step() {
        if (playerController.humanPlayerModel.playerStatus == PlayerStatus.DEAD) {
            LudumDare2022.setGameState(GameState.GAME_OVER);
        }

        baseGraph.step();
        playerController.step();
        updateGlobalTemperature();
    }

    @Override
    public void render(SpriteBatch batch, CameraController cameraController) {
        tileGrid.draw(batch, cameraController);
//        exampleEntity.draw(batch);
        baseGraph.render(batch, cameraController);
        playerController.render(batch, cameraController);
    }

    @Override
    public void dispose() {
        tileGrid.dispose();
        baseGraph.dispose();
        playerController.dispose();
    }

    public BaseGraph getBaseGraph() {
        return baseGraph;
    }

    public void goTo(Base base){
        playerController.goTo(base);
    }

    public void setSelectedArmy(int selectedArmy, UiArmySelect uiArmySelect){
        this.playerController.humanPlayerModel.setSelectedArmy(selectedArmy);
        this.playerController.humanPlayerModel.setUiArmySelect(uiArmySelect);
    }

    public UiArmySelect getUiArmySelect(){
        return this.playerController.humanPlayerModel.getUiArmySelect();
    }
}
