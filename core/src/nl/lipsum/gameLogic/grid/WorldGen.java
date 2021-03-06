package nl.lipsum.gameLogic.grid;

import java.util.Random;

/**
 * Class with static methods for world generation.
 * Ripples sine waves at random angles and with different period through the map.
 * Uses wave height to determine tile type.
 */
public class WorldGen {

    private static boolean snowWorld = true;

    private WorldGen() {
        // Private constructor to prevent initialization
    }

    /**
     * Generates a world using the random sine waves generation strategy
     * @param tileGrid the tile grid
     */
    public static void generateWorld(TileGrid tileGrid) {
        generateWorld(tileGrid, System.currentTimeMillis());
    }

    /**
     * Generates a world using the random sine waves generation strategy
     * @param tileGrid the tile grid
     * @param seed the seed
     */
    public static void generateWorld(TileGrid tileGrid, long seed) {
        float[][] map = new float[tileGrid.SIZE_X][tileGrid.SIZE_Y];

        Random random = new Random(seed);

        int nWaves = 10;
        float total = 0;

        // Compute the total amplitude of all waves combined (so we can normalize)
        for (int divisor = 1; divisor <= nWaves; divisor++) {
            // A wave's amplitude is inversely related to its frequency (which scales linearly with divisor).
            // Higher frequencies are less powerful.
            total += 1.0f/divisor;
        }

        /*
        Add waves that ripple through the landscape at increasing frequencies and decreasing amplitudes,
            from random angles.
         */
        for (int divisor = 1; divisor <= nWaves; divisor++) {
            float angle = (float) (random.nextFloat() * Math.PI * 2.0f);
            float phase = (float) (random.nextFloat() * Math.PI * 2.0f);
            addWave(map, angle, divisor, (1.0f/divisor)/total, phase);
        }

        /*
        Compute the tiles based on the numbers (range -1 to 1) in the map for every tile
         */
        for (int x = 0; x < tileGrid.SIZE_X; x++) {
            for (int y = 0; y < tileGrid.SIZE_Y; y++) {
                setTileFor(x, y, tileGrid, map, random);
            }
        }

    }

    /**
     * Sets the tile to a certain enum value depending on the float (range -1 to 1) generated by the generator.
     * @param x grid x of the tile
     * @param y grid y of the tile
     * @param tileGrid the tile grid
     * @param map the float map generated by the generator
     */
    private static void setTileFor(int x, int y, TileGrid tileGrid, float[][] map, Random random) {
        float val = map[x][y];

        if (!snowWorld) {
            if (val > 0.2) {
                int textureNr = random.nextInt(2);
                switch (textureNr) {
                    case 0:
                        tileGrid.setTile(x, y, Tile.GRASS_1);
                        break;
                    case 1:
                        tileGrid.setTile(x, y, Tile.GRASS_2);
                        break;
                    default:
                        tileGrid.setTile(x, y, Tile.GRASS_3);
                        break;
                }
            } else if (val < -0.2) {
                tileGrid.setTile(x, y, Tile.DIRT);
            } else {
                tileGrid.setTile(x, y, Tile.SAND);
            }
        } else {
            if (val > 0.2) {
                tileGrid.setTile(x, y, Tile.ICE_1);
            } else {
                tileGrid.setTile(x, y, Tile.SNOW_1);
            }
        }
    }

    /**
     * Adds a wave from a certain angle that ripples through the map
     * @param map the map of floats used for generation
     * @param angle the angle of the ripple
     * @param divisor the divisor (map_width/divisor determines period)
     * @param amplitude the amplitude of the wave (amplitudes should add to one)
     * @param phase the phase of the wave (offset in radials)
     */
    private static void addWave(float[][] map, float angle, float divisor, float amplitude, float phase) {
        int maxLen = map.length;
        for (int x = 0; x < map.length; x++) {
            for (int y = 0; y < map[x].length; y++) {
                double t = Math.cos(angle) * x + Math.sin(angle) * y;
                map[x][y] += amplitude * Math.sin(phase + t * Math.PI * 2 / (maxLen/divisor));
            }
        }
    }
}
