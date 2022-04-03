package nl.lipsum.gameLogic;

import nl.lipsum.entities.AbstractEntity;
import nl.lipsum.entities.combat_units.Infantry;
import nl.lipsum.gameLogic.playermodel.PlayerModel;

import java.util.HashSet;
import java.util.Set;

import static nl.lipsum.Config.TILE_SIZE;

public class Army implements Ownable{
    public Set<AbstractEntity> entities;
    private final PlayerModel owner;
    private Base destBase;

    public Army(Base startBase, PlayerModel owner){
        entities = new HashSet<>();
        this.owner = owner;
        AbstractEntity entity = new Infantry(startBase.getX()*TILE_SIZE, startBase.getY()*TILE_SIZE, startBase, owner);
        entity.setArmy(this);
    }

    public void goTo(Base b){
        destBase = b;
        for(AbstractEntity e:entities){
            e.goTo(b);
        }
    }

    public void addEntity(AbstractEntity entity) {
        entities.add(entity);
    }

    public void removeEntity(AbstractEntity entity) {
        entities.remove(entity);
    }

    @Override
    public PlayerModel getOwner() {
        return owner;
    }

    public Base getDestBase() {
        return destBase;
    }
}
