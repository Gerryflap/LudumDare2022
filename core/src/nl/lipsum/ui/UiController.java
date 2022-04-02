package nl.lipsum.ui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import nl.lipsum.controllers.CameraController;
import nl.lipsum.controllers.GenericController;

/**
 * Controller of the static user interface (minimap and top/bottom bars)
 */
public class UiController implements GenericController {
    private final ShapeRenderer shapeRenderer = new ShapeRenderer();
    private final BarController barController = new BarController();
    private final MinimapController minimapController = new MinimapController();


    @Override
    public void step() {
        barController.step();
        minimapController.step();
    }

    @Override
    public void render(SpriteBatch batch, CameraController cameraController) {
        batch.end();
        barController.render(shapeRenderer, cameraController.getCamera());
        minimapController.render(shapeRenderer, cameraController.getCamera());
        batch.begin();
    }

    public void dispose() {
        shapeRenderer.dispose();

    }
}
