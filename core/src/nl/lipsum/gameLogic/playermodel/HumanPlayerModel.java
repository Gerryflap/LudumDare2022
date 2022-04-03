package nl.lipsum.gameLogic.playermodel;

import nl.lipsum.gameLogic.Base;
import nl.lipsum.ui.UiArmySelect;

public class HumanPlayerModel extends PlayerModel {
    UiArmySelect uiArmySelect;

    public HumanPlayerModel(){
        super();
    }
    public void setUiArmySelect(UiArmySelect uiArmySelect) {
        this.uiArmySelect = uiArmySelect;
    }

    public UiArmySelect getUiArmySelect() {
        return uiArmySelect;
    }

    public void step() {
        super.step();
    }

    public void goTo(Base base){
        //TODO: actually make sure the right army is selected
        armies.get(selectedArmy).goTo(base);
    }
}
