package nl.lipsum;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public interface GenericController {
    /**
     * Handles a step. This method should handle input and compute the new game state
     */
    void step();

    /**
     * Handles rendering of everything that falls under this controller
     */
    void render(SpriteBatch batch, Camera camera);

    /**
     * Disposes everything that needs disposing under this controller
     */
    void dispose();
}
