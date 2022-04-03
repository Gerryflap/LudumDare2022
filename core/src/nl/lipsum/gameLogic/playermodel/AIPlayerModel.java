package nl.lipsum.gameLogic.playermodel;

import com.badlogic.gdx.Gdx;
import nl.lipsum.LudumDare2022;
import nl.lipsum.StaticUtils;
import nl.lipsum.buildings.*;
import nl.lipsum.gameLogic.Army;
import nl.lipsum.gameLogic.Base;

import java.util.*;
import java.util.stream.Collectors;

import static nl.lipsum.Config.RESOURCE_BUILDING_COST;

/**
 * Contains the AI code
 */
public class AIPlayerModel extends PlayerModel {
    private static final BuildingType[] buildingTypes = BuildingType.values();

    private static Random random = new Random();
    private static final int TARGET_RESOURCE_BUILDINGS = 3;
    private static final int DECISION_INTERVAL = 60;
    private int stepsTillDecision = DECISION_INTERVAL;
    private Base targetBase = null;
    private int resourceNodes = 0;
    private final Set<Building> buildings;

    public AIPlayerModel(){
        super();
        random = new Random();
        buildings = new HashSet<>();
    }

    @Override
    public void step(){
        super.step();

        if (--stepsTillDecision <= 0) {
            stepsTillDecision = DECISION_INTERVAL;
            decide();
        }

        for (Building building : new HashSet<>(buildings)) {
            if (building.isDead()) {
                buildings.remove(building);
            }
        }
    }

    private void decide() {

        moveArmies();

        Set<Base> bases = getOwnBases();

        if (bases.size() > 0) {
            if (resourceNodes < TARGET_RESOURCE_BUILDINGS) {
                Base base = getRandomOwnBase();
                int x = base.getX() - base.getBuildrange() + random.nextInt(base.getBuildrange() * 2);
                int y = base.getY() - base.getBuildrange() + random.nextInt(base.getBuildrange() * 2);
                boolean hasBuilt = build(BuildingType.RESOURCE, x, y);
                if (hasBuilt) {
                    resourceNodes += 1;
                }
            } else {
                Base base = getRandomOwnBase();
                int x = base.getX() - base.getBuildrange() + random.nextInt(base.getBuildrange() * 2);
                int y = base.getY() - base.getBuildrange() + random.nextInt(base.getBuildrange() * 2);
                boolean success = build(buildingTypes[random.nextInt(buildingTypes.length)], x, y);
                if (success) {
                    selectedArmy = (selectedArmy + 1)%3;
                }

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

    private boolean canBuild(BuildingType type, int gridX, int gridY) {
        BuildingGrid grid = LudumDare2022.buildingController.getBuildingGrid();
        if (gridX < 0 || gridY < 0 || grid.SIZE_X <= gridX || grid.SIZE_Y <= gridY) {
            return false;
        }

        boolean canbuild = false;
        for (Base base: LudumDare2022.gameController.getBaseGraph().getBases()) {
            if(base.getOwner() == this && gridX < base.getX() + base.getBuildrange() &&
                    gridX > base.getX() - base.getBuildrange() &&
                    gridY < base.getY() + base.getBuildrange() && gridY > base.getY() - base.getBuildrange()){
                canbuild = true;
                break;
            }
        }

        Integer cost = StaticUtils.getCost(type);
        canbuild &= cost != null && cost <= getAmountResources();
        return canbuild;
    }

    // Returns whether it is successful
    private boolean build(BuildingType type, int gridX, int gridY) {
        Building building;
        boolean built = false;
        if (canBuild(type, gridX, gridY)) {
            switch (type) {
                case RESOURCE:
                    building = new ResourceBuilding(gridX, gridY, this);
                    break;
                case INFANTRY:
                    building = new InfantryBuilding(gridX, gridY, this);
                    break;
                case TANK:
                    building = new TankBuilding(gridX, gridY, this);
                    break;
                case SNIPER:
                    building = new SniperBuilding(gridX, gridY, this);
                    break;
                default:
                    building = null;
                    break;
            }

            if (building != null) {
                LudumDare2022.buildingController.getBuildingGrid().setBuilding(gridX, gridY, building);
                buildings.add(building);
                return true;
            }
        }
        return built;
    }


    private Set<Base> getOwnBases() {
        return LudumDare2022.gameController.getBaseGraph().getBases().stream()
                .filter(base -> base.getOwner() == this)
                .collect(Collectors.toSet());
    }

    private Set<Base> getOtherBases() {
        return LudumDare2022.gameController.getBaseGraph().getBases().stream()
                .filter(base -> base.getOwner() != this)
                .collect(Collectors.toSet());
    }

    private void updateTargetBase() {
        if (targetBase == null || targetBase.getOwner() == this) {
            // Get a base that isn't ours (to capture)
            targetBase = getRandomOtherBase();
        }
    }

    private Base getRandomOwnBase() {
        List<Base> baseList = new ArrayList<>(getOwnBases());
        return baseList.get(random.nextInt(baseList.size() ));

    }

    private Base getRandomOtherBase() {
        List<Base> baseList = new ArrayList<>(getOtherBases());
        return baseList.get(random.nextInt(baseList.size() ));

    }
}
