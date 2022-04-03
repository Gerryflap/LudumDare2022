package nl.lipsum.gameLogic;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import nl.lipsum.LudumDare2022;
import nl.lipsum.TextureStore;
import nl.lipsum.TextureStore;
import nl.lipsum.controllers.CameraController;
import nl.lipsum.controllers.GenericController;
import nl.lipsum.gameLogic.playermodel.AIPlayerModel;
import nl.lipsum.gameLogic.playermodel.HumanPlayerModel;
import nl.lipsum.gameLogic.playermodel.PlayerModel;
import nl.lipsum.gameLogic.playermodel.PlayerStatus;

import java.util.ArrayList;
import java.util.List;

public class PlayerController implements GenericController {
    BaseGraph baseGraph;

    static HumanPlayerModel humanPlayerModel;
    static List<AIPlayerModel> aiPlayerModels;


    public PlayerController(BaseGraph baseGraph, HumanPlayerModel humanPlayerModel) {
        this.baseGraph = baseGraph;
        aiPlayerModels = new ArrayList<>();
        aiPlayerModels.add(new AIPlayerModel());
        aiPlayerModels.add(new AIPlayerModel());
        aiPlayerModels.add(new AIPlayerModel());
        PlayerController.humanPlayerModel = humanPlayerModel;
        PlayerController.humanPlayerModel.initiateArmies(baseGraph.getBases().get(0));
        baseGraph.getBases().get(2).setOwner(aiPlayerModels.get(0));
        baseGraph.getBases().get(6).setOwner(aiPlayerModels.get(1));
        baseGraph.getBases().get(8).setOwner(aiPlayerModels.get(2));
        aiPlayerModels.get(0).initiateArmies(baseGraph.getBases().get(2));
        aiPlayerModels.get(1).initiateArmies(baseGraph.getBases().get(6));
        aiPlayerModels.get(2).initiateArmies(baseGraph.getBases().get(8));
    }

    @Override
    public void step() {
        for(AIPlayerModel aiPlayerModel: aiPlayerModels){
            if (aiPlayerModel.playerStatus == PlayerStatus.ALIVE) {
                aiPlayerModel.step();
            }
        }
        if (humanPlayerModel.playerStatus == PlayerStatus.ALIVE) {
            humanPlayerModel.step();
        }
    }

    @Override
    public void render(SpriteBatch batch, CameraController cameraController) {
    }

    @Override
    public void dispose() {

    }

    public static PlayerModel getPlayedModelByOwnerId(int id) {
        if (id == 0) {
            return humanPlayerModel;
        } else {
            return aiPlayerModels.get(id - 1);
        }
    }


    public void goTo(Base base){
        humanPlayerModel.goTo(base);
    }

    public HumanPlayerModel getHumanPlayerModel(){
        return humanPlayerModel;
    }
}
