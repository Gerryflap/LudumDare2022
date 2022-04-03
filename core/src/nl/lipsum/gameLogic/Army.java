package nl.lipsum.gameLogic;

import com.badlogic.gdx.graphics.Texture;
import nl.lipsum.entities.AbstractEntity;
import nl.lipsum.entities.combat_units.Infantry;
import nl.lipsum.gameLogic.playermodel.PlayerModel;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import static nl.lipsum.Config.TILE_SIZE;

public class Army implements Ownable{
    public Set<AbstractEntity> entities;
    private final PlayerModel owner;
    private Base destBase;
    private static HashMap<PlayerModel, Integer> armyCounter = new HashMap<>();
    private final int id;
    private final Texture texture;

    public Army(Base startBase, PlayerModel owner){
        if (armyCounter.containsKey(owner)) {
            this.id = armyCounter.get(owner);
            armyCounter.put(owner, id + 1);
        } else {
            this.id = 0;
            armyCounter.put(owner, 1);
        }

        System.out.println(String.format("getting resource army%d.png", this.id));
        this.texture = new Texture(String.format("army%d.png", this.id));

        entities = new HashSet<>();
        this.owner = owner;
        this.destBase = startBase;
        AbstractEntity entity = new Infantry(startBase.getX()*TILE_SIZE, startBase.getY()*TILE_SIZE, owner);
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

    public int getId() {
        return id;
    }

    public Texture getTexture() {
        return this.texture;
    }
}
