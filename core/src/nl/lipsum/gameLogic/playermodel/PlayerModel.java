package nl.lipsum.gameLogic.playermodel;

// abstract omdat we ook ai player models hebben straks
public abstract class PlayerModel {
    private int amountResources;

    public PlayerModel() {
        this.amountResources = 50;
    }

    public int getAmountResources() {
        return amountResources;
    }
}
