package nl.lipsum.gameLogic;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import nl.lipsum.TextureStore;
import nl.lipsum.controllers.CameraController;
import nl.lipsum.controllers.GenericController;
import nl.lipsum.gameLogic.playermodel.AIPlayerModel;
import nl.lipsum.gameLogic.playermodel.HumanPlayerModel;

import java.util.ArrayList;
import java.util.List;

public class PlayerController implements GenericController {
    BaseGraph baseGraph;
    HumanPlayerModel humanPlayerModel;
    List<AIPlayerModel> aiPlayerModels;


    public PlayerController(BaseGraph baseGraph, HumanPlayerModel humanPlayerModel, TextureStore textureStore) throws Exception{
        this.baseGraph = baseGraph;
        aiPlayerModels = new ArrayList<>();
        aiPlayerModels.add(new AIPlayerModel());
        aiPlayerModels.add(new AIPlayerModel());
        aiPlayerModels.add(new AIPlayerModel());
        this.humanPlayerModel = humanPlayerModel;
        humanPlayerModel.initiateArmies(baseGraph.getBases().get(0));
        aiPlayerModels.get(0).initiateArmies(baseGraph.getBases().get(2));
        aiPlayerModels.get(1).initiateArmies(baseGraph.getBases().get(6));
        aiPlayerModels.get(2).initiateArmies(baseGraph.getBases().get(8));
//        this.humanPlayerModel.setTextures(textureStore.getTileTextureByName("green"));
//        aiPlayerModels.get(0).setTextures(textureStore.getTileTextureByName("red"));
//        aiPlayerModels.get(1).setTextures(textureStore.getTileTextureByName("blue"));
//        aiPlayerModels.get(2).setTextures(textureStore.getTileTextureByName("orange"));
    }

    @Override
    public void step() {
        for(AIPlayerModel aiPlayerModel: aiPlayerModels){
            aiPlayerModel.step();
        }
        humanPlayerModel.update();
    }

    @Override
    public void render(SpriteBatch batch, CameraController cameraController) {
    }

    @Override
    public void dispose() {

    }

    public void goTo(Base base){
        humanPlayerModel.goTo(base);
    }

    public HumanPlayerModel getHumanPlayerModel(){
        return humanPlayerModel;
    }
}
