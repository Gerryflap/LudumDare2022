package nl.lipsum.buildings;

import com.badlogic.gdx.graphics.Texture;
import nl.lipsum.gameLogic.playermodel.PlayerModel;

public class InfantryBuilding extends UnitBuilding {
    private final Texture tileTexture;

    public InfantryBuilding(int x, int y, PlayerModel owner) {
        super(x, y, owner, 50, 10);
        this.tileTexture = new Texture(String.format("player%s/infantry_building.png", owner.getId()));
    }

    public Texture getTileTexture() {
        return tileTexture;
    }
}
