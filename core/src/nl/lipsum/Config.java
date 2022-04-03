package nl.lipsum;

public class Config {
    public static final int TILE_SIZE = 50;
    public static final int BASE_SIZE = 150;

    //MUST be uneven
    public static final int WIDTH_IN_TILES = 51;
    public static final int HEIGHT_IN_TILES = 51;

    public static final int COLLISION_GRID_WIDTH = WIDTH_IN_TILES * TILE_SIZE;
    public static final int COLLISION_GRID_HEIGHT = HEIGHT_IN_TILES * TILE_SIZE;
}
