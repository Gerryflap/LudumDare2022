package nl.lipsum.buildings;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import static nl.lipsum.Config.TILE_SIZE;

public class ResourceBuilding extends Building{
    public static final Texture tileTexture = new Texture("whiteTile.jpg");

    public ResourceBuilding(int x, int y) {
        super(x, y);
    }


    @Override
    public void draw(SpriteBatch batch) {
        batch.draw(tileTexture, TILE_SIZE * this.x - TILE_SIZE/2, TILE_SIZE * this.y- TILE_SIZE/2);
    }

    public void dispose(){

    }
}
