package nl.lipsum.gameLogic;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import nl.lipsum.controllers.CameraController;
import nl.lipsum.controllers.GenericController;
import nl.lipsum.entities.AbstractEntity;
import nl.lipsum.entities.AttackType;

import java.util.ArrayList;
import java.util.List;

import static nl.lipsum.Config.TILE_SIZE;

public class Army implements GenericController {

    public List<AbstractEntity> entities;

    public Army(Base startBase){
        entities = new ArrayList<>();
        entities.add(new AbstractEntity(startBase.getX()*TILE_SIZE, startBase.getY()*TILE_SIZE, new Texture("greenTile.jpg"), startBase,
                100, 100, 150, 10, 10, 100, AttackType.RANGED));
    }

    @Override
    public void step() {
        for(AbstractEntity e:entities){
            e.step();
        }
    }

    @Override
    public void render(SpriteBatch batch, CameraController cameraController) {
        for(AbstractEntity e:entities){
            e.draw(batch);
        }
    }

    @Override
    public void dispose() {

    }

    public void goTo(Base b, BaseGraph baseGraph){
        for(AbstractEntity e:entities){
            e.goTo(b, baseGraph);
        }
    }

}
