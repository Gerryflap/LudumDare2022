package nl.lipsum.gameLogic.playermodel;

import com.badlogic.gdx.Gdx;
import nl.lipsum.LudumDare2022;
import nl.lipsum.buildings.Building;
import nl.lipsum.buildings.BuildingBuilder;
import nl.lipsum.buildings.InfantryBuilding;
import nl.lipsum.buildings.ResourceBuilding;
import nl.lipsum.gameLogic.Army;
import nl.lipsum.gameLogic.Base;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Contains the AI code
 */
public class AIPlayerModel extends PlayerModel {
    private static Random random = new Random();
    private static final int TARGET_RESOURCE_BUILDINGS = 3;
    private static final int DECISION_INTERVAL = 60;
    private BuildingBuilder builder;
    private int stepsTillDecision = DECISION_INTERVAL;
    private Base targetBase = null;
    private int resourceNodes = 0;

    public AIPlayerModel(){
        super();
        random = new Random();
    }

    @Override
    public void step(){
        super.step();

        if (--stepsTillDecision <= 0) {
            stepsTillDecision = DECISION_INTERVAL;
            decide();
        }
    }

    private void decide() {

        moveArmies();

        Set<Base> bases = getOwnBases();

        if (bases.size() > 0) {
            if (resourceNodes < TARGET_RESOURCE_BUILDINGS) {
                BuildingBuilder buildingBuilder = getBuilder();
                Base base = bases.iterator().next();
                int x = base.getX() - base.getBuildrange() + random.nextInt(base.getBuildrange() * 2);
                int y = base.getY() - base.getBuildrange() + random.nextInt(base.getBuildrange() * 2);
                buildingBuilder.buildBuilding(new ResourceBuilding(x, y, this), x, y);
            } else {
                BuildingBuilder buildingBuilder = getBuilder();
                Base base = bases.iterator().next();
                int x = base.getX() - base.getBuildrange() + random.nextInt(base.getBuildrange() * 2);
                int y = base.getY() - base.getBuildrange() + random.nextInt(base.getBuildrange() * 2);
                buildingBuilder.buildBuilding(new InfantryBuilding(x, y, this), x, y);
            }
        }


    }

    private void moveArmies() {
        Set<Base> ownBases = getOwnBases();
        Set<Base> unprotectedBases = new HashSet<>(ownBases);
        Iterator<Base> toProtect = unprotectedBases.iterator();
        updateTargetBase();

        long ownArmySize = LudumDare2022.entityController.getEntities().stream().filter(e -> e.getOwner() == this).count();
        long avgArmySize = LudumDare2022.entityController.getEntities().stream().filter(e -> e.getOwner() == this).count();
        avgArmySize /= 3;

        Base base = armies.get(0).getDestBase();
        if (base == null) {
            armies.get(0).goTo(toProtect.next());
        }

        if (ownArmySize > avgArmySize) {
            armies.get(2).goTo(targetBase);
        } else {
            if (toProtect.hasNext()) {
                armies.get(2).goTo(toProtect.next());
            } else {
                armies.get(2).goTo(armies.get(1).getDestBase());
            }
        }

        armies.get(1).goTo(targetBase);
    }

    private BuildingBuilder getBuilder() {
        if (builder == null) {
            builder = LudumDare2022.buildingController.getBuildingBuilder(this);
        }
        return builder;
    }

    private Set<Base> getOwnBases() {
        return LudumDare2022.gameController.getBaseGraph().getBases().stream()
                .filter(base -> base.getOwner() == this)
                .collect(Collectors.toSet());
    }

    private void updateTargetBase() {
        if (targetBase == null || targetBase.getOwner() == this) {
            // Get a base that isn't ours (to capture)
            targetBase = LudumDare2022.gameController.getBaseGraph().getBases().stream()
                    .filter(base -> base.getOwner() != this)
                    .findFirst().orElse(null);
        }
    }
}
