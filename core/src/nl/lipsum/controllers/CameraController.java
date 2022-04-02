package nl.lipsum.controllers;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;

public class CameraController implements GenericController {

    private OrthographicCamera camera;
    public float zoomedAmount = 0;

    public int cameraMovementSpeed = 15;
    private ArrayList<Integer> activeKeys;

    public CameraController(OrthographicCamera camera) {
        this.camera = camera;
        this.activeKeys = new ArrayList<>();
    }

    public void setKeyActive(int keycode) {
        if (!this.activeKeys.contains(keycode)) {
            this.activeKeys.add(keycode);
        }
    }

    public void setKeyInactive(int keycode) {
        if (this.activeKeys.contains(keycode)) {
            this.activeKeys.remove((Integer) keycode);
        }
    }

    public OrthographicCamera getCamera() {
        return this.camera;
    }

    @Override
    public void step() {
        // handle camera panning
        float verticalMovement = this.camera.zoom * this.cameraMovementSpeed *  (activeKeys.contains(Input.Keys.W) ? 1 : (activeKeys.contains(Input.Keys.S) ? -1 : 0));
        float horizontalMovement = this.camera.zoom * this.cameraMovementSpeed * (activeKeys.contains(Input.Keys.D) ? 1 : (activeKeys.contains(Input.Keys.A) ? -1 : 0));
        if (verticalMovement != 0 && horizontalMovement != 0) {
            // normalize diagonal speed. Go from factor sqrt(2) to 1, which means sqrt(2)/2 per dimension.
            final float SPEED_NORMALIZATION_FACTOR = 0.7071067811865f;
            verticalMovement = verticalMovement * SPEED_NORMALIZATION_FACTOR;
            horizontalMovement = horizontalMovement * SPEED_NORMALIZATION_FACTOR;
        }

        this.camera.translate(horizontalMovement, verticalMovement);

        // handle camera zooming
        if (this.zoomedAmount > 0) {
            // zoom out
            this.camera.zoom = this.camera.zoom * 1.2f;
            this.zoomedAmount = 0;
        } else if (this.zoomedAmount < 0) {
            // zoom in
            this.camera.zoom = this.camera.zoom * 0.83333333333f;
            this.zoomedAmount = 0;
        }

        this.camera.update();
    }

    @Override
    public void render(SpriteBatch batch, CameraController cameraController) {
        batch.setProjectionMatrix(this.camera.combined);
    }

    @Override
    public void dispose() {

    }
}
