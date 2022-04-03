package nl.lipsum.gameLogic.playermodel;

import nl.lipsum.LudumDare2022;
import nl.lipsum.gameLogic.Base;
import nl.lipsum.gameLogic.BaseGraph;
import nl.lipsum.gameLogic.GameController;
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


    public void goTo(Base base){
        //TODO: actually make sure the right army is selected
        armies.get(selectedArmy).goTo(base);
    }
}
