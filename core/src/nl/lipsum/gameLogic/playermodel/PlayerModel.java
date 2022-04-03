package nl.lipsum.gameLogic.playermodel;

import nl.lipsum.Config;
import nl.lipsum.gameLogic.*;

import java.util.ArrayList;
import java.util.List;

// abstract omdat we ook ai player models hebben straks
public abstract class PlayerModel {

    private static int amountPlayers;
    private float amountResources;
    public List<Army> armies;
    private Base base;
    int selectedArmy = 0;
    private final int id;

    public int heatBuildingsAmount;

    private int health;

    private int basesOwnedCount;

    public final static int TEMPERATURE_MAX_CAN_SURVIVE_WITHOUT_DAMAGE = 0;

    public PlayerStatus playerStatus;

    public PlayerModel() {
        this.id = amountPlayers;
        amountPlayers++;
        this.amountResources = 50;
        this.health = 1000;
        this.playerStatus = PlayerStatus.ALIVE;
        this.heatBuildingsAmount = 0;
    }

    public void initiateArmies(Base base){
        armies = new ArrayList<>();
        armies.add(new Army(base, this));
        armies.add(new Army(base, this));
        armies.add(new Army(base, this));
        this.base = base;
        base.setOwner(this);
    }

    public int getCoolingPower() {
        return Config.HEAT_COOLING_POWER * heatBuildingsAmount;
    }

    public void step() {
        float tempDiff = GameController.globalTemperature + heatBuildingsAmount * Config.HEAT_COOLING_POWER - TEMPERATURE_MAX_CAN_SURVIVE_WITHOUT_DAMAGE;
        if (tempDiff < 0) {
            health += tempDiff;
        }

        if (health <= 0) {
            this.playerStatus = PlayerStatus.DEAD;
        }

        basesOwnedCount = 0;
        for (Base _base : GameController.baseGraph.getBases()) {
            if (_base.getOwner() == this) {
                basesOwnedCount += 1;
            }
        }

        if (basesOwnedCount == 0) {
            this.playerStatus = PlayerStatus.DEAD;
        }

    }

    public float getAmountResources() {
        return amountResources;
    }

    public void addResources(float amount){
        this.amountResources += amount;
    }

    public void setSelectedArmy(int selectedArmy){
        this.selectedArmy = selectedArmy;
    }

    public int getSelectedArmy() {
        return selectedArmy;
    }

    public Base getBase() {
        return base;
    }

    public int getId() {
        return id;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public static void reset() {
        amountPlayers = 0;
    }
}
