package nl.lipsum.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import nl.lipsum.controllers.CameraController;
import nl.lipsum.controllers.GenericController;

import java.util.HashSet;
import java.util.Set;

/**
 * This class has supreme command of all entities
 */
public class EntityController implements GenericController {
    private final Set<AbstractEntity> entities;
    private boolean chunkMapValid = false;

    public EntityController() {
        this.entities = new HashSet<>();
    }

    /**
     * Adds the given entity, so it can step and drawn etc
     * @param entity the entity
     */
    public void addEntity(AbstractEntity entity) {
        entities.add(entity);
    }

    /**
     * Removes the given entity, so it can step and drawn etc
     * @param entity the entity
     */
    public void removeEntity(AbstractEntity entity) {
        entities.remove(entity);
    }

    /**
     * CAUTION: THIS METHOD IS EXPENSIVE!
     * The expensiveness becomes more expensive the longer the range.
     * This method can only be used in the step of entities, otherwise it will throw an exception
     *
     * Finds the closest enemy in the given range.
     * @param radius the radius in which to check, usually the max range of the weapon. Try to keep this smol.
     */
    public void findClosestEnemyInRange(float radius) {
        if (chunkMapValid) {
            throw new RuntimeException("Ik ben lui");
        } else {
            throw new IllegalStateException("Cannot compute: chunks invalid");
        }
    }

    @Override
    public void step() {
        generateChunkMap();

        // Clone the entity set to allow adding and removing entities in the step
        Set<AbstractEntity> entitiesClone = new HashSet<>(entities);
        for (AbstractEntity entity :entitiesClone) {
            entity.step();
        }
        invalidateChunkMap();
    }

    private void generateChunkMap() {
        chunkMapValid = true;
    }

    private void invalidateChunkMap() {
        chunkMapValid = false;
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
}
