package nl.lipsum.gameLogic;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import nl.lipsum.*;
import nl.lipsum.controllers.CameraController;
import nl.lipsum.controllers.GenericController;
import nl.lipsum.gameLogic.grid.TileGrid;
import nl.lipsum.gameLogic.grid.WorldGen;
import nl.lipsum.gameLogic.playermodel.AIPlayerModel;
import nl.lipsum.gameLogic.playermodel.HumanPlayerModel;
import nl.lipsum.gameLogic.playermodel.PlayerStatus;
import nl.lipsum.ui.UiSelectedItem;

import java.util.Random;

import static nl.lipsum.Config.HEIGHT_IN_TILES;
import static nl.lipsum.Config.WIDTH_IN_TILES;

public class GameController implements GenericController {
    TextureStore textureStore;
    TileGrid tileGrid;
    public static PlayerController playerController;
//    AbstractEntity exampleEntity;
    public static BaseGraph baseGraph;

    private int currentTemperatureUpdateCount;
    private static final int globalTemperatureUpdateTime = 50;
    public static float globalTemperature;

    private Random random = new Random();

    public GameController(HumanPlayerModel humanPlayerModel){
        textureStore = new TextureStore();
        tileGrid = new TileGrid(WIDTH_IN_TILES, HEIGHT_IN_TILES);
        WorldGen.generateWorld(tileGrid);

        globalTemperature = 5;

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

        boolean win = true;

        for (AIPlayerModel aiPlayerModel : PlayerController.aiPlayerModels) {
            win = win && aiPlayerModel.playerStatus == PlayerStatus.DEAD;
        }

        if (win) {
            LudumDare2022.setGameState(GameState.WIN);
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

    public void setSelectedArmy(int selectedArmy, UiSelectedItem uiSelectedItem){
        this.playerController.humanPlayerModel.setSelectedArmy(selectedArmy);
        this.playerController.humanPlayerModel.setUiArmySelect(uiSelectedItem);
    }

    public UiSelectedItem getUiArmySelect(){
        return this.playerController.humanPlayerModel.getUiArmySelected();
    }
}
