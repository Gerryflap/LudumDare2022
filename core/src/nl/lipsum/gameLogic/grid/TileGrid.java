package nl.lipsum.gameLogic.grid;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import nl.lipsum.Drawable;

public class TileGrid implements Drawable {
    public final int SIZE_X;
    public final int SIZE_Y;

    private final Tile[][] tiles;

    public TileGrid(int sizeX, int sizeY) {
        this.SIZE_X = sizeX;
        this.SIZE_Y = sizeY;
        this.tiles = new Tile[this.SIZE_X][this.SIZE_Y];
    }

    public Tile getTile(int x, int y) {
        return tiles[x][y];
    }

    public void setTile(int x, int y, Tile tile) {
        this.tiles[x][y] = tile;
    }


    @Override
    public void draw(SpriteBatch batch) {
        for (int x = 0; x < SIZE_X; x++) {
            for (int y = 0; y < SIZE_Y; y++) {
                if(this.tiles[x][y] != null){
                    this.tiles[x][y].draw(batch, x, y);
                }
            }
        }
    }

    public void dispose() {
        for (int x = 0; x < SIZE_X; x++) {
            for (int y = 0; y < SIZE_Y; y++) {
                if(this.tiles[x][y] != null) {
                    this.tiles[x][y].dispose();
                }
            }
        }
    }
}
