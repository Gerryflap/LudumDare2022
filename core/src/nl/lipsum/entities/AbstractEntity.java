package nl.lipsum.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import nl.lipsum.Drawable;

public class AbstractEntity implements Drawable {
    float xPosition;
    float yPosition;
    float xSize;
    float ySize;
    Texture texture;

    public AbstractEntity(float xPosition, float yPosition, Texture texture) {
        this(xPosition, yPosition, 50, 50, texture);
    }

    public AbstractEntity(float xPosition, float yPosition, float xSize, float ySize, Texture texture) {
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.xSize = xSize;
        this.ySize = ySize;
        this.texture = texture;
    }

    @Override
    public void draw(SpriteBatch batch) {
        batch.draw(texture, this.xPosition, this.yPosition);
    }
}
