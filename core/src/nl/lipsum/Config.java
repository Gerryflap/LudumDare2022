package nl.lipsum;

public class Config {
    public static final int TILE_SIZE = 50;
    public static final int BASE_SIZE = 150;

    //MUST be uneven
    public static final int WIDTH_IN_TILES = 51;
    public static final int HEIGHT_IN_TILES = 51;

    public static final int COLLISION_GRID_WIDTH = WIDTH_IN_TILES * TILE_SIZE;
    public static final int COLLISION_GRID_HEIGHT = HEIGHT_IN_TILES * TILE_SIZE;

    public static final int RESOURCE_BUILDING_COST = 50;
    public static final int INFANTRY_BUILDING_COST = 50;
    public static final int SNIPER_BUILDING_COST = 50;
    public static final int TANK_BUILDING_COST = 50;
    public static final int HEAT_BUILDING_COST = 50;
    public static final int HEAT_COOLING_POWER = 1;
    public static final int RESOURCES_PER_BUILDING_PER_SECOND = 5;
    public static final int BUILDING_HEALTH = 100;

    public static final int PLAYER_COUNT = 4;
}
