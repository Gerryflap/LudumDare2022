package nl.lipsum.entities;

import nl.lipsum.Coordinate;
import nl.lipsum.LudumDare2022;
import nl.lipsum.StaticUtils;
import nl.lipsum.buildings.Building;
import nl.lipsum.gameLogic.playermodel.PlayerModel;

import java.lang.annotation.Target;
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
    private final Map<Coordinate, Set<Targetable>> targetMap;
    private boolean mapValid = false;

    public PositionalEntityResolver(int tileSize) {
        this.tileSize = tileSize;
        targetMap = new HashMap<>();
    }

    public PositionalEntityResolver() {
        this(20);
    }

    public Set<Targetable> getTargetsInRange(float worldX, float worldY, float range) {
        return getTargetsInRange(new DummyTarget(worldX, worldY), range);
    }

    private Set<Targetable> getTargetsInRange(Coordinate gridPos, float range) {
        if (!mapValid) {
            buildMap();
        }

        int gridRange = (int) Math.ceil(range/tileSize);

        Set<Targetable> targets = new HashSet<>();
        for (int dx = -gridRange; dx <= gridRange; dx++) {
            for (int dy = -gridRange; dy <= gridRange; dy++) {
                targets.addAll(getTargetsAtGridPos(gridPos.translate(dx, dy)));
            }
        }
        return targets;
    }

    public Set<Targetable> getTargetsInRange(Targetable entity, float range) {
        Coordinate currentCoordinates = getGridCoordinate(entity);
        return getTargetsInRange(currentCoordinates, range);

    }

    public Targetable getClosestTargetMatchingPredicate(Targetable entity, float range,
                                                            Predicate<Targetable> predicate) {
        Set<Targetable> targets = getTargetsInRange(entity, range);
        Iterator<Targetable> iterator = targets.stream().filter(predicate).iterator();

        Targetable closest = null;
        double minDist = Double.MAX_VALUE;

        while (iterator.hasNext()) {
            Targetable e = iterator.next();
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

    public Targetable getClosestTargetMatchingPredicate(float worldX, float worldY, float range,
                                                        Predicate<Targetable> predicate) {
        return getClosestTargetMatchingPredicate(new DummyTarget(worldX, worldY), range, predicate);
    }

    public Targetable getClosestHostileTarget(Targetable entity, float range) {
        return getClosestTargetMatchingPredicate(entity, range, unit -> unit.getOwner() != entity.getOwner());
    }

    private Set<Targetable> getTargetsAtGridPos(Coordinate coordinates) {
        if (mapValid && targetMap.containsKey(coordinates)) {
            return targetMap.get(coordinates);
        } else {
            return Collections.emptySet();
        }
    }

    private Coordinate getGridCoordinate(Targetable target) {
        return new Coordinate(
                (int) (target.getxPosition() / tileSize),
                (int) (target.getyPosition() / tileSize)
        );
    }

    private void addTarget(Targetable target) {
        Coordinate coordinates = getGridCoordinate(target);

        if (!targetMap.containsKey(coordinates)) {
            targetMap.put(coordinates, new HashSet<>());
        }
        Set<Targetable> atPos = targetMap.get(coordinates);
        atPos.add(target);
    }

    /**
     * Call at the end of step to invalidate the map (since units moved)
     */
    public void invalidate() {
        targetMap.clear();
        mapValid = false;
    }

    public boolean isMapValid() {
        return mapValid;
    }

    private void buildMap() {
        for (AbstractEntity entity: LudumDare2022.entityController.getEntities()) {
            addTarget(entity);
        }
        for (Building[] buildingList: LudumDare2022.buildingController.getBuildings()){
            for(Building building: buildingList){
                if(building != null){
                    addTarget(building);
                }
            }
        }
        mapValid = true;
    }

    private class DummyTarget implements Targetable {
        float x;
        float y;

        public DummyTarget(float x, float y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public float getxPosition() {
            return x;
        }

        @Override
        public float getyPosition() {
            return y;
        }

        @Override
        public PlayerModel getOwner() {
            return null;
        }

        @Override
        public boolean isDead() {
            return false;
        }

        @Override
        public void damage(float d) {

        }
    }
}
