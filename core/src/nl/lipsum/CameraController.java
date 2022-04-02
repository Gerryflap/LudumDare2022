package nl.lipsum;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class CameraController implements GenericController {
    private OrthographicCamera camera;
    private int cameraMovementSpeed = 15;

    public CameraController(OrthographicCamera camera) {
        this.camera = camera;
    }

    @Override
    public void step() {
        float verticalMovement = this.cameraMovementSpeed *  (Gdx.input.isKeyPressed(Input.Keys.W) ? 1 : (Gdx.input.isKeyPressed(Input.Keys.S) ? -1 : 0));
        float horizontalMovement = this.cameraMovementSpeed * (Gdx.input.isKeyPressed(Input.Keys.D) ? 1 : (Gdx.input.isKeyPressed(Input.Keys.A) ? -1 : 0));
        if (verticalMovement != 0) {
            // normalize diagonal speed. Go from factor sqrt(2) to 1, which means sqrt(2)/2 per dimension.
            final float SPEED_NORMALIZATION_FACTOR = 0.7071067811865f;
            verticalMovement = verticalMovement * SPEED_NORMALIZATION_FACTOR;
            horizontalMovement = horizontalMovement * SPEED_NORMALIZATION_FACTOR;
        }
        this.camera.translate(horizontalMovement, verticalMovement);

        this.camera.update();
    }

    @Override
    public void render(SpriteBatch batch, Camera camera) {
        batch.setProjectionMatrix(this.camera.combined);
    }

    @Override
    public void dispose() {

    }
}
