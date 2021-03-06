package nl.lipsum;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import nl.lipsum.buildings.BuildingType;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import nl.lipsum.controllers.CameraController;
import nl.lipsum.entities.AbstractEntity;
import nl.lipsum.entities.Targetable;

import javax.swing.text.TabableView;

import static nl.lipsum.Config.*;

public class StaticUtils {

    public static boolean inRange(CameraController cameraController, float x, float y) {
        final OrthographicCamera camera = cameraController.getCamera();
        float trueWidth = camera.viewportWidth * camera.zoom;
        float trueHeight = camera.viewportHeight * camera.zoom;

        float minimumX = camera.position.x - trueWidth / 2 - Config.TILE_SIZE;
        float maximumX = camera.position.x + trueWidth / 2 + Config.TILE_SIZE;
        float minimumY = camera.position.y - trueHeight / 2 - Config.TILE_SIZE;
        float maximumY = camera.position.y + trueHeight / 2 + Config.TILE_SIZE;

        return x >= minimumX && x <= maximumX && y >= minimumY && y <= maximumY;
    }

    public static void smartDraw(SpriteBatch batch, CameraController cameraController, Texture texture, float x, float y) {
        if (inRange(cameraController, x, y)) {
            batch.draw(texture, x, y);
        }
    }

    public static void smartDraw(SpriteBatch batch, CameraController cameraController, Texture texture, float x, float y, float width, float height) {
        if (inRange(cameraController, x, y)) {
            batch.draw(texture, x, y, width, height);
        }
    }

    /**
     * @param e1 entity 1
     * @param e2 entity 2
     * @return the distance^2 between the two
     */
    public static double squareDistance(Targetable e1, Targetable e2) {
        return Math.pow(e1.getxPosition() - e2.getxPosition(), 2) + Math.pow(e1.getyPosition() - e2.getyPosition(), 2);
    }

    /**
     * @param e1 entity 1
     * @param e2 entity 2
     * @return the distance between the two
     */
    public static double distance(Targetable e1, Targetable e2) {
        return Math.sqrt(squareDistance(e1, e2));
    }

    public static Integer getCost(BuildingType type) {
        switch (type) {
            case RESOURCE:
                return RESOURCE_BUILDING_COST;
            case INFANTRY:
                return INFANTRY_BUILDING_COST;
            case TANK:
                return TANK_BUILDING_COST;
            case SNIPER:
                return SNIPER_BUILDING_COST;
            case HEAT:
                return HEAT_BUILDING_COST;
            case TURRET:
                return TURRET_BUILDING_COST;
            default:
                return null;
        }
    }
}


