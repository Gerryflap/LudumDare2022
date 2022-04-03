package nl.lipsum.gameLogic;

import nl.lipsum.entities.AbstractEntity;
import nl.lipsum.entities.combat_units.Infantry;

import java.util.HashSet;
import java.util.Set;

import static nl.lipsum.Config.TILE_SIZE;

public class Army {
    public Set<AbstractEntity> entities;

    public Army(Base startBase){
        entities = new HashSet<>();
        AbstractEntity entity = new Infantry(startBase.getX()*TILE_SIZE, startBase.getY()*TILE_SIZE, startBase);
        entity.setArmy(this);
    }

    public void goTo(Base b){
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

    public void setTextures(Texture texture){
        for(AbstractEntity e:entities){
            e.setTexture(texture);
        }
    }
}
