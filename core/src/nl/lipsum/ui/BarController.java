package nl.lipsum.ui;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import nl.lipsum.gameLogic.GameController;

import static nl.lipsum.ui.UiConstants.*;

/**
 * Controls the main UI bar
 */
public class BarController {
    private final GameController gameController;

    public BarController(GameController gameController) {
        this.gameController = gameController;
    }

    public void step() {

    }

    public void render(ShapeRenderer shapeRenderer, Camera camera) {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(BAR_COLOR);
        shapeRenderer.rect(0, 0, camera.viewportWidth, BAR_HEIGHT);
        shapeRenderer.rect(camera.viewportWidth - MINIMAP_WIDTH, 0, camera.viewportWidth, MINIMAP_HEIGHT);
        shapeRenderer.end();
    }

    public void dispose() {

    }
}
