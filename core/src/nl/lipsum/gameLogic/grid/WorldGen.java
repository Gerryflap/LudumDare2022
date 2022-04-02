package nl.lipsum.gameLogic.grid;

import java.util.Random;

public class WorldGen {
    private WorldGen() {
        // Private constructor to prevent initialization
    }

    public static void generateWorld(TileGrid tileGrid) {
        generateWorld(tileGrid, System.currentTimeMillis());
    }


    public static void generateWorld(TileGrid tileGrid, long seed) {
        float[][] map = new float[tileGrid.SIZE_X][tileGrid.SIZE_Y];

        Random random = new Random(seed);

        int nWaves = 10;

        for (int divisor = 1; divisor <= nWaves; divisor++) {
            float angle = (float) (random.nextFloat() * Math.PI * 2.0f);
            addWave(map, angle, divisor, nWaves);
        }

        for (int x = 0; x < tileGrid.SIZE_X; x++) {
            for (int y = 0; y < tileGrid.SIZE_Y; y++) {
                setTileFor(x, y, tileGrid, map);
            }
        }

    }

    private static void setTileFor(int x, int y, TileGrid tileGrid, float[][] map) {
        float val = map[x][y];

        if (val > 0.2) {
            tileGrid.setTile(x, y, Tile.GRASS);
        } else if (val < -0.2) {
            tileGrid.setTile(x, y, Tile.DIRT);
        } else {
            tileGrid.setTile(x, y, Tile.SAND);
        }
    }

    private static void addWave(float[][] map, float angle, float divisor, int nWaves) {
        int maxLen = map.length;
        float multiplier =  (1.0f / ((float) nWaves));
        for (int x = 0; x < map.length; x++) {
            for (int y = 0; y < map[x].length; y++) {
                double t = Math.cos(angle) * x + Math.sin(angle) * y;
                map[x][y] += multiplier * Math.sin(t * Math.PI * 2 / (maxLen/divisor));
            }
        }
    }
}
