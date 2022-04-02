package nl.lipsum.controllers;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import nl.lipsum.controllers.CameraController;

public interface GenericController {
    /**
     * Handles a step. This method should handle input and compute the new game state
     */
    void step();

    /**
     * Handles rendering of everything that falls under this controller
     */
    void render(SpriteBatch batch, CameraController cameraController);

    /**
     * Disposes everything that needs disposing under this controller
     */
    void dispose();
}
