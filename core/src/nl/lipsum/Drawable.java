package nl.lipsum;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import nl.lipsum.controllers.CameraController;

public interface Drawable {
    void draw(SpriteBatch batch, CameraController cameraController);
}
