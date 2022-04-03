package nl.lipsum.gameLogic;

import com.badlogic.gdx.graphics.Texture;
import nl.lipsum.entities.AbstractEntity;
import nl.lipsum.entities.AttackType;
import nl.lipsum.entities.EntityType;

import java.util.HashSet;
import java.util.Set;

import static nl.lipsum.Config.TILE_SIZE;

public class Army {
    public Set<AbstractEntity> entities;
    private Base destBase;

    public Army(Base startBase){
        entities = new HashSet<>();
        AbstractEntity entity = new AbstractEntity(startBase.getX()*TILE_SIZE, startBase.getY()*TILE_SIZE, new Texture("greenTile.jpg"), startBase,
                100, 100, 300, 10, 25, 100, AttackType.RANGED, EntityType.INFANTRY);
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

    public void setTextures(Texture texture){
        for(AbstractEntity e:entities){
            e.setTexture(texture);
        }
    }
}
