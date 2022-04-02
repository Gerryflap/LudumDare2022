package nl.lipsum.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import nl.lipsum.gameLogic.Base;
import nl.lipsum.gameLogic.GameController;

import static nl.lipsum.ui.UiConstants.*;
import static nl.lipsum.Config.*;

/**
 * This class is in control of the minimap
 */
public class MinimapController {
    private final GameController gameController;

    public MinimapController(GameController gameController) {
        this.gameController = gameController;
    }

    public void step() {
        if (Gdx.input.justTouched()){
            if (Gdx.graphics.getWidth() - MINIMAP_WIDTH < Gdx.input.getX() && Gdx.input.getX() < Gdx.graphics.getWidth()){
                if (Gdx.graphics.getHeight() - MINIMAP_HEIGHT < Gdx.input.getY() && Gdx.input.getY() < Gdx.graphics.getHeight()){
                    for(Base b:gameController.getBaseGraph().getBases()){

                        float xDist = (Gdx.graphics.getWidth() - MINIMAP_WIDTH +  2*MINIMAP_BORDER_SIZE + ((MINIMAP_WIDTH-4*MINIMAP_BORDER_SIZE)/WIDTH_IN_TILES)* (b.getX()+1) - MINIMAP_BASE_SIZE/2)-Gdx.input.getX();
                        float yDist = (Gdx.graphics.getHeight() - MINIMAP_HEIGHT +  2*MINIMAP_BORDER_SIZE + ((MINIMAP_HEIGHT-4*MINIMAP_BORDER_SIZE)/HEIGHT_IN_TILES)* (b.getY()+1) - MINIMAP_BASE_SIZE/2)-Gdx.input.getY();
                        if(xDist*xDist + yDist*yDist <= (MINIMAP_BASE_SIZE*3)*(MINIMAP_BASE_SIZE*3)){
                            System.out.println("Base Clicked");
                            System.out.println(b.getX());
                            System.out.println(b.getY());
                        }

                    }
                }
            }
        }
    }

    public void render(ShapeRenderer shapeRenderer, Camera camera) {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(MINIMAP_BACKGROUND);
        shapeRenderer.rect(camera.viewportWidth - (MINIMAP_WIDTH - MINIMAP_BORDER_SIZE), MINIMAP_BORDER_SIZE,
                MINIMAP_WIDTH - MINIMAP_BORDER_SIZE * 2, MINIMAP_HEIGHT - MINIMAP_BORDER_SIZE * 2);
        shapeRenderer.setColor(NODE_COLOR);
        for(Base b:gameController.getBaseGraph().getBases()){
            shapeRenderer.rect(
                    camera.viewportWidth - MINIMAP_WIDTH +  2*MINIMAP_BORDER_SIZE + ((MINIMAP_WIDTH-4*MINIMAP_BORDER_SIZE)/WIDTH_IN_TILES)* (b.getX()+1) - MINIMAP_BASE_SIZE/2,
                    2*MINIMAP_BORDER_SIZE + ((MINIMAP_HEIGHT - 4*MINIMAP_BORDER_SIZE)/HEIGHT_IN_TILES)* (b.getY()+1) - MINIMAP_BASE_SIZE/2,
                    MINIMAP_BASE_SIZE,
                    MINIMAP_BASE_SIZE
            );
        }
        shapeRenderer.end();
    }

    public void dispose() {

    }
}
