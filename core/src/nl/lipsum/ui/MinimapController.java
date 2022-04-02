package nl.lipsum.ui;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import static nl.lipsum.ui.UiConstants.*;

/**
 * This class is in control of the minimap
 */
public class MinimapController {
    public void step() {

    }

    public void render(ShapeRenderer shapeRenderer, Camera camera) {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(MINIMAP_BACKGROUND);
        shapeRenderer.rect(camera.viewportWidth - (MINIMAP_WIDTH - MINIMAP_BORDER_SIZE), MINIMAP_BORDER_SIZE,
                MINIMAP_WIDTH - MINIMAP_BORDER_SIZE * 2, MINIMAP_HEIGHT - MINIMAP_BORDER_SIZE * 2);
        shapeRenderer.end();
    }

    public void dispose() {

    }
}
