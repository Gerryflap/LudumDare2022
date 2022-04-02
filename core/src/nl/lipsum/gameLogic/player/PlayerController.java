package nl.lipsum.gameLogic.player;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import nl.lipsum.controllers.CameraController;
import nl.lipsum.controllers.GenericController;

public class PlayerController implements GenericController {
    Army army;
    BaseGraph baseGraph;

    public PlayerController(Base base, BaseGraph baseGraph){
        this.army = new Army(base);
        this.baseGraph = baseGraph;
    }

    @Override
    public void step() {
        army.step();
    }

    @Override
    public void render(SpriteBatch batch, CameraController cameraController) {
        army.render(batch, cameraController);
    }

    @Override
    public void dispose() {

    }

    public void goTo(Base base){
        //TODO: actually make sure the right army is selected
        army.goTo(base, baseGraph);
    }
}
