package nl.lipsum.controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import nl.lipsum.Config;

import java.util.ArrayList;

import static java.lang.Math.floor;

public class CameraController implements GenericController {

    private OrthographicCamera camera;
    public float zoomedAmount = 0;

    private static final float MIN_ZOOM = 0.1f;
    private static final float MAX_ZOOM = 6f;

    private static final float CAMERA_MIN_X = 0;
    private static final float CAMERA_MIN_Y = 0;
    private static final float CAMERA_MAX_X = Config.TILE_SIZE * Config.WIDTH_IN_TILES;
    private static final float CAMERA_MAX_Y = Config.TILE_SIZE * Config.HEIGHT_IN_TILES;

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

            if (this.camera.zoom > MAX_ZOOM) {
                this.camera.zoom = MAX_ZOOM;
            }
        } else if (this.zoomedAmount < 0) {
            // zoom in
            this.camera.zoom = this.camera.zoom * 0.83333333333f;
            this.zoomedAmount = 0;

            if (this.camera.zoom < MIN_ZOOM) {
                this.camera.zoom = MIN_ZOOM;
            }
        }

        // Lock camera to place where all the action takes place
        if (this.camera.position.x < CAMERA_MIN_X) {
            this.camera.position.x = CAMERA_MIN_X;
        }
        if (this.camera.position.x > CAMERA_MAX_X) {
            this.camera.position.x = CAMERA_MAX_X;
        }

        if (this.camera.position.y < CAMERA_MIN_Y) {
            this.camera.position.y = CAMERA_MIN_Y;
        }

        if (this.camera.position.y > CAMERA_MAX_Y) {
            this.camera.position.y = CAMERA_MAX_Y;
        }

        this.camera.update();
    }

    public int[] screenToTile(int x, int y){
        Vector3 touchPos = new Vector3(x, y, 0);
        Vector3 worldPos = camera.unproject(touchPos);
//        return new int[] {(int) worldPos.x,(int) worldPos.y};

        return new int[] {((int) (floor(worldPos.x + Config.TILE_SIZE/2))) / Config.TILE_SIZE,((int) floor(worldPos.y + Config.TILE_SIZE/2) ) / Config.TILE_SIZE};
    }

    @Override
    public void render(SpriteBatch batch, CameraController cameraController) {
        batch.setProjectionMatrix(this.camera.combined);
    }

    @Override
    public void dispose() {

    }
}
