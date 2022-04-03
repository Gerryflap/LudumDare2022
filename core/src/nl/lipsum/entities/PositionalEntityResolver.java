package nl.lipsum.entities;

import nl.lipsum.Coordinate;
import nl.lipsum.LudumDare2022;
import nl.lipsum.StaticUtils;

import java.util.*;
import java.util.function.Predicate;

/**
 * This class can be used to apply positional queries for entities such as "get the closest entity" and
 *  "get entities in range".
 *
 *  Everything in this class will be based on the information at the start of the step of the EntityController,
 *      there may be a delay.
 */
public class PositionalEntityResolver {
    private final int tileSize;
    private final Map<Coordinate, Set<AbstractEntity>> entityMap;
    private boolean mapValid = false;

    public PositionalEntityResolver(int tileSize) {
        this.tileSize = tileSize;
        entityMap = new HashMap<>();
    }

    public PositionalEntityResolver() {
        this(20);
    }

    public Set<AbstractEntity> getEntitiesInRange(AbstractEntity entity, float range) {
        if (!mapValid) {
            buildMap();
        }

        int gridRange = (int) Math.ceil(range/tileSize);
        Coordinate currentCoordinates = getGridCoordinate(entity);

        Set<AbstractEntity> entities = new HashSet<>();
        for (int dx = -gridRange; dx <= gridRange; dx++) {
            for (int dy = -gridRange; dy <= gridRange; dy++) {
                entities.addAll(getUnitsAtGridPos(currentCoordinates.translate(dx, dy)));
            }
        }
        return entities;
    }

    public AbstractEntity getClosestEntityMatchingPredicate(AbstractEntity entity, float range,
                                                            Predicate<AbstractEntity> predicate) {
        Set<AbstractEntity> entities = getEntitiesInRange(entity, range);
        Iterator<AbstractEntity> iterator = entities.stream().filter(predicate).iterator();

        AbstractEntity closest = null;
        double minDist = Double.MAX_VALUE;

        while (iterator.hasNext()) {
            AbstractEntity e = iterator.next();
            double currentDist = StaticUtils.squareDistance(entity, e);
            if (closest == null) {
                closest = e;
                minDist = currentDist;
            } else {
                if (minDist > currentDist) {
                    closest = e;
                    minDist = currentDist;
                }
            }
        }
        return closest;
    }

    public AbstractEntity getClosestHostileEntity(AbstractEntity entity, float range) {
        return getClosestEntityMatchingPredicate(entity, range, unit -> unit.getOwner() != entity.getOwner());
    }

    private Set<AbstractEntity> getUnitsAtGridPos(Coordinate coordinates) {
        if (mapValid && entityMap.containsKey(coordinates)) {
            return entityMap.get(coordinates);
        } else {
            return Collections.emptySet();
        }
    }

    private Coordinate getGridCoordinate(AbstractEntity entity) {
        return new Coordinate(
                (int) (entity.getxPosition() / tileSize),
                (int) (entity.getyPosition() / tileSize)
        );
    }

    private void addEntity(AbstractEntity entity) {
        Coordinate coordinates = getGridCoordinate(entity);

        if (!entityMap.containsKey(coordinates)) {
            entityMap.put(coordinates, new HashSet<>());
        }
        Set<AbstractEntity> atPos = entityMap.get(coordinates);
        atPos.add(entity);
    }

    /**
     * Call at the end of step to invalidate the map (since units moved)
     */
    public void invalidate() {
        entityMap.clear();
        mapValid = false;
    }

    public boolean isMapValid() {
        return mapValid;
    }

    private void buildMap() {
        for (AbstractEntity entity: LudumDare2022.entityController.getEntities()) {
            addEntity(entity);
        }
        mapValid = true;
    }
}
