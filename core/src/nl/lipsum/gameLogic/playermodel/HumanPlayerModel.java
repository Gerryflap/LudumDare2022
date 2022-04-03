package nl.lipsum.gameLogic.playermodel;

import nl.lipsum.gameLogic.Base;
import nl.lipsum.ui.UiSelectedItem;

public class HumanPlayerModel extends PlayerModel {
    UiSelectedItem uiSelectedArmy;
    UiSelectedItem uiSelectedBuilding;

    public HumanPlayerModel(){
        super();
    }
    public void setUiArmySelect(UiSelectedItem uiSelectedItem) {
        this.uiSelectedArmy = uiSelectedItem;
    }
    public void setUiBuildingSelect(UiSelectedItem uiSelectedItem) {this.uiSelectedBuilding = uiSelectedItem;}

    public UiSelectedItem getUiArmySelected() {
        return uiSelectedArmy;
    }

    public UiSelectedItem getUiBuildingSelected() {return uiSelectedBuilding;}

    public void step() {
        super.step();
    }

    public void goTo(Base base){
        //TODO: actually make sure the right army is selected
        armies.get(selectedArmy).goTo(base);
    }
}
