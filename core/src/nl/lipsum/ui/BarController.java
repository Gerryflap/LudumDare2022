package nl.lipsum.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import nl.lipsum.gameLogic.GameController;
import nl.lipsum.TextureStore;
import nl.lipsum.gameLogic.playermodel.HumanPlayerModel;

import static nl.lipsum.ui.UiConstants.*;

/**
 * Controls the main UI bar
 */
public class BarController {
    private final GameController gameController;
    private TextureStore textureStore;
    private UiItem[] uiItems = new UiItem[]{null, null, null, null, null, null, null, null, null, null};
    private HumanPlayerModel humanPlayerModel;
    private BitmapFont font = new BitmapFont();
    private SpriteBatch fontSpriteBatch = new SpriteBatch();

    public BarController(GameController gameController, HumanPlayerModel humanPlayerModel) {
        this.gameController = gameController;
        this.humanPlayerModel = humanPlayerModel;
        this.textureStore = new TextureStore();

        try {
            UiItem exampleHaveEnough = new UiItem(textureStore.getTileTextureByName("blue"), ICON_WIDTH, ICON_HEIGHT);
            exampleHaveEnough.setRequiredResources(100);
            UiItem exampleDontHaveEnough = new UiItem(textureStore.getTileTextureByName("orange"), ICON_WIDTH, ICON_HEIGHT);

            this.uiItems[0] = exampleHaveEnough;
            this.uiItems[1] = exampleDontHaveEnough;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void step() {

    }

    public void render(ShapeRenderer shapeRenderer, Camera camera) {
        // draw ui backgroundz
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(BAR_COLOR);
        shapeRenderer.rect(0, 0, camera.viewportWidth, BAR_HEIGHT);
        shapeRenderer.rect(0, BAR_HEIGHT, RESOURCE_COUNTER_WIDTH, RESOURCE_COUNTER_HEIGHT);
        shapeRenderer.circle(RESOURCE_COUNTER_WIDTH, BAR_HEIGHT, RESOURCE_COUNTER_HEIGHT);
        shapeRenderer.rect(camera.viewportWidth - MINIMAP_WIDTH, 0, camera.viewportWidth, MINIMAP_HEIGHT);
        shapeRenderer.end();

        // draw resource counter

        font.setColor(UI_FONT_COLOR);

        fontSpriteBatch.begin();
        font.draw(fontSpriteBatch, String.format("Resources: %s", humanPlayerModel.getAmountResources()), 5, BAR_HEIGHT + font.getLineHeight() - 3);
        fontSpriteBatch.end();

        //draw item icons
        SpriteBatch spriteBatch = new SpriteBatch();
        spriteBatch.begin();
        float uiItemX = 5;
        float uiItemY = 3;
        for (UiItem uiItem : uiItems) {
            if (uiItem != null) {
                spriteBatch.draw(uiItem.getTexture(), uiItemX, uiItemY, ICON_WIDTH, ICON_HEIGHT);
            }
            uiItemX = uiItemX + 5 + ICON_WIDTH;
        }
        spriteBatch.end();


        // draw icon borders
        ShapeRenderer borderShapeRenderer = new ShapeRenderer();
        borderShapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        borderShapeRenderer.setColor(ICON_BORDER_COLOR);
        uiItemX = 5;
        uiItemY = 3;
        for (UiItem uiItem : uiItems) {
            borderShapeRenderer.rect(uiItemX, uiItemY, ICON_WIDTH, ICON_HEIGHT);
            uiItemX = uiItemX + 5 + ICON_WIDTH;
        }
        borderShapeRenderer.end();

        // draw not enough resources greyed out effect
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(ICON_NOT_IN_STORE_COLOR);
        uiItemX = 5;
        uiItemY = 3;
        for (UiItem uiItem : uiItems) {
            if (uiItem != null && uiItem.getRequiredResources() < humanPlayerModel.getAmountResources()) {
                shapeRenderer.rect(uiItemX, uiItemY, ICON_WIDTH, ICON_HEIGHT);
            }
            uiItemX = uiItemX + 5 + ICON_WIDTH;
        }
        shapeRenderer.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);
    }

    public void dispose() {
        font.dispose();
        fontSpriteBatch.dispose();
    }
}
