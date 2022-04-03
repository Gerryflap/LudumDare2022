package nl.lipsum.gameLogic;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import nl.lipsum.LudumDare2022;
import nl.lipsum.controllers.CameraController;
import nl.lipsum.controllers.GenericController;
import nl.lipsum.ui.UiArmySelect;

import java.util.ArrayList;
import java.util.List;

public class PlayerController implements GenericController {
    public List<Army> armies;
    int selectedArmy = 0;
    BaseGraph baseGraph;
    UiArmySelect uiArmySelect;

    public Base base;

    public PlayerController(Base base, BaseGraph baseGraph){
        this.base = base;
        armies = new ArrayList<>();
        armies.add(new Army(base, LudumDare2022.humanPlayerModel));
        armies.add(new Army(base, LudumDare2022.humanPlayerModel));
        armies.add(new Army(base, LudumDare2022.humanPlayerModel));
        this.baseGraph = baseGraph;
    }

    @Override
    public void step() {
    }

    @Override
    public void render(SpriteBatch batch, CameraController cameraController) {
    }

    @Override
    public void dispose() {

    }

    public void goTo(Base base){
        //TODO: actually make sure the right army is selected
        armies.get(selectedArmy).goTo(base, baseGraph);
    }

    public void setSelectedArmy(int selectedArmy){
        this.selectedArmy = selectedArmy;
    }

    public void setUiArmySelect(UiArmySelect uiArmySelect) {
        this.uiArmySelect = uiArmySelect;
    }

    public UiArmySelect getUiArmySelect() {
        return uiArmySelect;
    }

}
