package nl.lipsum.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import nl.lipsum.Config;
import nl.lipsum.controllers.CameraController;
import nl.lipsum.controllers.GenericController;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * This class has supreme command of all entities
 */
public class EntityController implements GenericController {
    public static final int UPDATE_TARGETS_EVERY_N_STEPS = 10;

    public static boolean[][] collisionGrid;

    private final Set<AbstractEntity> entities;
    private PositionalEntityResolver positionalEntityResolver;
    private int targetUpdateCounter = UPDATE_TARGETS_EVERY_N_STEPS;

    public EntityController() {
        this.entities = new HashSet<>();
        collisionGrid = new boolean[Config.COLLISION_GRID_HEIGHT][Config.COLLISION_GRID_WIDTH];
        positionalEntityResolver = new PositionalEntityResolver();
    }

    /**
     * Adds the given entity, so it can step and drawn etc
     * @param entity the entity
     */
    public void addEntity(AbstractEntity entity) {
        entities.add(entity);
        collisionGrid[(int) entity.getxPosition()][(int) entity.getyPosition()] = true;
    }

    /**
     * Removes the given entity, so it can step and drawn etc
     * @param entity the entity
     */
    public void removeEntity(AbstractEntity entity) {
        entities.remove(entity);
        collisionGrid[(int) entity.getxPosition()][(int) entity.getyPosition()] = false;
    }

    /**
     * CAUTION: THIS METHOD IS EXPENSIVE!
     * The expensiveness becomes more expensive the longer the range.
     * This method can only be used in the step of entities, otherwise it will throw an exception
     *
     * Finds the closest enemy in vision range for all entities and sets their target.
     */
    private void updateClosestEntities() {
        for (AbstractEntity entity : entities) {
            entity.setTarget(positionalEntityResolver.getClosestHostileTarget(entity, entity.getVisionRange()));
        }
    }

    @Override
    public void step() {

        if (--targetUpdateCounter == 0) {
            updateClosestEntities();
            targetUpdateCounter = UPDATE_TARGETS_EVERY_N_STEPS;
        }

        // Clone the entity set to allow adding and removing entities in the step
        Set<AbstractEntity> entitiesClone = new HashSet<>(entities);
        for (AbstractEntity entity :entitiesClone) {
            entity.step();
        }
        if (positionalEntityResolver.isMapValid()){
            positionalEntityResolver.invalidate();
        }
    }

    @Override
    public void render(SpriteBatch batch, CameraController cameraController) {
        for (AbstractEntity entity :entities) {
            entity.draw(batch, cameraController);
        }

    }

    @Override
    public void dispose() {

    }

    /**
     * @return the set of entities
     */
    public Set<AbstractEntity> getEntities() {
        return Collections.unmodifiableSet(entities);
    }
}
