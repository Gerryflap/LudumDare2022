package nl.lipsum.ui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import nl.lipsum.CameraController;
import nl.lipsum.GenericController;
import nl.lipsum.gameLogic.GameController;

/**
 * Controller of the static user interface (minimap and top/bottom bars)
 */
public class UiController implements GenericController {
    private final ShapeRenderer shapeRenderer = new ShapeRenderer();
    private final BarController barController;
    private final MinimapController minimapController;

    public UiController(GameController gameController){
        this.barController = new BarController(gameController);
        this.minimapController = new MinimapController(gameController);
    }

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
