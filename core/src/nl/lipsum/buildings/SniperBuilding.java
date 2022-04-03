package nl.lipsum.buildings;

import com.badlogic.gdx.graphics.Texture;
import nl.lipsum.entities.AbstractEntity;
import nl.lipsum.entities.combat_units.Sniper;
import nl.lipsum.entities.combat_units.Tank;
import nl.lipsum.gameLogic.playermodel.PlayerModel;

import static nl.lipsum.Config.TILE_SIZE;

public class SniperBuilding extends UnitBuilding {
    private final Texture tileTexture;

    public SniperBuilding(int x, int y, PlayerModel owner, int trainingTime, int unitCap) {
        super(x, y, owner, trainingTime, unitCap);
        this.tileTexture = new Texture(String.format("player%s/sniper_building.png", owner.getId()));
    }

    public Texture getTileTexture() {
        return tileTexture;
    }

    @Override
    public void step() {
        if(!isDead()){
            trainingProgress += 1;
            if (trainingProgress > trainingTime) {
                if (this.units.size() >= unitCap) {

                } else {
                    trainingProgress = 0;
                    AbstractEntity unit = new Sniper(x*TILE_SIZE, y*TILE_SIZE, owner);
                    unit.setBuilding(this);
                    unit.setArmy(owner.armies.get(selectedArmy));
                    owner.armies.get(selectedArmy).entities.add(unit);
                    unit.goTo(owner.armies.get(selectedArmy).getDestBase());
                    this.units.add(unit);
                }
            }
        }
    }
}
