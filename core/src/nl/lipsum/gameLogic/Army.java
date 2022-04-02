package nl.lipsum.gameLogic;

import com.badlogic.gdx.graphics.Texture;
import nl.lipsum.entities.AbstractEntity;
import nl.lipsum.entities.AttackType;

import java.util.HashSet;
import java.util.Set;

import static nl.lipsum.Config.TILE_SIZE;

public class Army {
    public Set<AbstractEntity> entities;

    public Army(Base startBase){
        entities = new HashSet<>();
        AbstractEntity entity = new AbstractEntity(startBase.getX()*TILE_SIZE, startBase.getY()*TILE_SIZE, new Texture("greenTile.jpg"), startBase,
                100, 100, 300, 10, 25, 100, AttackType.RANGED);
        entity.setArmy(this);
    }

    public void goTo(Base b, BaseGraph baseGraph){
        for(AbstractEntity e:entities){
            e.goTo(b, baseGraph);
        }
    }

    public void addEntity(AbstractEntity entity) {
        entities.add(entity);
    }

    public void removeEntity(AbstractEntity entity) {
        entities.remove(entity);
    }
}
