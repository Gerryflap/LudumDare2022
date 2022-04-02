package nl.lipsum.ui;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import nl.lipsum.GenericController;

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
    public void render(SpriteBatch batch, Camera camera) {
        barController.render(shapeRenderer, camera);
        minimapController.render(shapeRenderer, camera);

    }

    public void dispose() {
        shapeRenderer.dispose();

    }
}
